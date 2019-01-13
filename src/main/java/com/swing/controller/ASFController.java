package com.swing.controller;

import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.io.*;

@ResponseBody
@Controller
@RequestMapping("/asf")
public class ASFController {

    @RequestMapping(path = "/findAll",method = RequestMethod.GET)
    public RestResult<ArrayList<String>> findAll(@RequestParam(value = "pathname") String pathname) {
        System.out.println("asf findall");
        ArrayList<String> list = new ArrayList<>();
//        String dirname = "/Users/songguohua/Desktop";
        File f1 = new File(pathname);
        if (f1.isDirectory()) {
            String sub[] = f1.list();
            for (int index = 0; index < sub.length; index++) {
                File f = new File(pathname + "/" + sub[index]);
                if (!f.isDirectory()) {
                    System.out.println(sub[index] + " is a file");
                    if (f.toString().endsWith(".json")) {
                        try {
                            String content = FileUtils.readFileToString(f);
                            JSONObject jsonObject= new JSONObject(content);
                            list.add(jsonObject.toString());
                            System.out.println("jsonObject："+ jsonObject.toString());
                        } catch (IOException e) {
                            System.out.println("JSONObjectError："+ e);
                        }
                    }
                }
            }
        } else {
            System.out.println(pathname + " is not a directory");
            return RestResultGenerator.genErrorResult(pathname + " is not a directory");
        }
        return RestResultGenerator.genSuccessResult(list);
    }

    @RequestMapping(path = "/save",method = RequestMethod.POST)
    public RestResult save(@RequestParam(value = "filename") String filename, @RequestParam(value = "content") String content) {
        try {
            // 输出新的json文件
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            return RestResultGenerator.genErrorResult(e.getMessage());
        }
        return RestResultGenerator.genSuccessResult();
    }

}
