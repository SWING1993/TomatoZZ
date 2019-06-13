package com.swing.controller;

import com.swing.utils.JsonUtils;
import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> 8743b775b1661e3da51285304c2e128f119b38e0
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@ResponseBody
@Controller
@RequestMapping("/ssr")
public class SSRController {

//    private static final String SSRConfigFilePath = "/Users/songguohua/Desktop/ssruser.json";
    private static final String SSRConfigFilePath = "/usr/local/shadowsocksr/mudb.json";

    private JSONArray ssrUsers;

    private void loadSSRUserConfig(){
        File file = new File(SSRConfigFilePath);
        try {
            String usersJosnStr = FileUtils.readFileToString(file);
            if (JsonUtils.isJSONArrayValid(usersJosnStr)) {
                this.ssrUsers = new JSONArray(usersJosnStr);
            }
        } catch (IOException e) {
            System.out.println("IOException："+ e);
        }
    }

    private boolean deleteSSRUser(String username) {
        this.loadSSRUserConfig();
        boolean isDelete = false;
        for (int i = 0; i < this.ssrUsers.length(); i ++) {
            JSONObject user = ssrUsers.getJSONObject(i);
            String userNameValue = JsonUtils.getString(user, "user");
            if (userNameValue.equals(username)) {
                ssrUsers.remove(i);
                isDelete = true;
            }
        }
        return isDelete;
    }

    private void saveSSRUsers(JSONArray ssrUsers) {
        try {
            // 输出新的json文件
            FileWriter fileWriter = new FileWriter(SSRConfigFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(ssrUsers.toString(4));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("IOException："+ e);
        }
    }

    // 查找所有用户的配置
    @RequestMapping(path = "/user",method = RequestMethod.GET)
    public RestResult<String> findBots() {
        this.loadSSRUserConfig();
        String result = this.ssrUsers.toString();
        return RestResultGenerator.genSuccessResult(result);
    }

    // 新增用户配置
    @RequestMapping(path = "/user",method = RequestMethod.POST)
    public RestResult<String> addUser(@RequestBody String config) {
        if (JsonUtils.isJSONObjectValid(config)) {
            this.loadSSRUserConfig();
            JSONObject jsonObject = new JSONObject(config);
            this.ssrUsers.put(jsonObject);
            this.saveSSRUsers(ssrUsers);
        } else {
            return RestResultGenerator.genErrorResult("无效的Json");
        }
        return RestResultGenerator.genSuccessResult();
    }

    // 根据userName（用户名）删除用户配置
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public RestResult<String> deleteUser(@RequestParam(value = "username") String username) {
        if (this.deleteSSRUser(username)) {
            this.saveSSRUsers(this.ssrUsers);
            return RestResultGenerator.genSuccessResult();
        }
        return RestResultGenerator.genErrorResult("没有用户为" + username + "的配置");
    }

    // 修改用户配置
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public RestResult<String> updateUser(@RequestBody String config) {
        if (JsonUtils.isJSONObjectValid(config)) {
            this.loadSSRUserConfig();
            JSONObject updateUser = new JSONObject(config);
            String username = JsonUtils.getString(updateUser, "user");
            if (this.deleteSSRUser(username)) {
                this.ssrUsers.put(updateUser);
                this.saveSSRUsers(this.ssrUsers);
                return RestResultGenerator.genSuccessResult();
            }
            return RestResultGenerator.genErrorResult("没有用户为" + username + "的配置");
        } else {
            return RestResultGenerator.genErrorResult("无效的Json");
        }
    }
}
