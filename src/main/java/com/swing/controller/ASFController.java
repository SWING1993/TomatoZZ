package com.swing.controller;

import com.swing.utils.DingChatBot;
import com.swing.utils.JsonUtils;
import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.io.*;

@ResponseBody
@Controller
@RequestMapping("/asf")
public class ASFController {

    private static final String filename = "FileName";
    private static final String ASFConfigFilePath = "/root/ArchiSteamFarm/asf_linux/config";
    private static final String ASFLogFilePath = "/root/ArchiSteamFarm/asf_linux/log.txt";

    @RequestMapping(path = "/findBots", method = RequestMethod.GET)
    public RestResult<Map<String, Object>> findBots() {
        System.out.println("asf/findBots");
        String asf = new String();
        ArrayList<String> list = new ArrayList<>();
        File file = new File(ASFConfigFilePath);
        if (file.isDirectory()) {
            String sub[] = file.list();
            for (int index = 0; index < sub.length; index++) {
                File subFile = new File(ASFConfigFilePath + "/" + sub[index]);
                if (subFile.toString().endsWith(".json")) {
                    try {
                        String content = FileUtils.readFileToString(subFile);
                        if (JsonUtils.isJSONObjectValid(content)) {
                            JSONObject jsonObject = new JSONObject(content);
                            jsonObject.put(filename, subFile.getName());
                            System.out.println("JSONObject："+ jsonObject.toString());
                            if (subFile.getName().equals("ASF.json")) {
                                asf = jsonObject.toString();
                            } else {
                                list.add(jsonObject.toString());
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("JSONObjectError："+ e);
                    }
                }
            }
        } else {
            System.out.println(ASFConfigFilePath + " is not a directory");
            return RestResultGenerator.genErrorResult(ASFConfigFilePath + " is not a directory");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bots", list);
        map.put("asf", asf);
        return RestResultGenerator.genSuccessResult(map);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public RestResult save(@RequestParam(value = "filename") String filename, @RequestParam(value = "content") String content) throws Exception {
        if (JsonUtils.isJSONObjectValid(content)) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                // 输出新的json文件
                FileWriter fileWriter = new FileWriter(ASFConfigFilePath + "/" + filename);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonObject.toString(4));
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                return RestResultGenerator.genErrorResult(ASFConfigFilePath + "/" + filename + " 无效");
            }
        } else {
            return RestResultGenerator.genErrorResult("无效的Json");
        }
        DingChatBot.sendMsg(filename + "成功保存！");
        return RestResultGenerator.genSuccessResult();
    }

    @RequestMapping(path = "/logs", method = RequestMethod.GET)
    public RestResult<List<String>> getlogs() throws IOException {
        FileInputStream fin = new FileInputStream(ASFLogFilePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        List<String> list = new ArrayList<>();
        while((strTmp = buffReader.readLine())!=null){
            list.add(strTmp);
        }
        buffReader.close();
        Collections.reverse(list);
        System.out.println(list);
        return RestResultGenerator.genSuccessResult(list);
    }

    @RequestMapping(path = "/logs", method = RequestMethod.DELETE)
    public RestResult removelogs() throws IOException {
        FileWriter fileWriter = new FileWriter(ASFLogFilePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("");
        bufferedWriter.flush();
        bufferedWriter.close();
        return RestResultGenerator.genSuccessResult();
    }
}
