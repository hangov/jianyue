package com.soft1721.janyue.api.controller;

import com.aliyun.oss.OSSClient;
import com.soft1721.janyue.api.entity.User;
import com.soft1721.janyue.api.entity.dto.UserDTO;
import com.soft1721.janyue.api.service.RedisService;
import com.soft1721.janyue.api.service.UserService;
import com.soft1721.janyue.api.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;

    @PostMapping(value = "/sign_in")
    public ResponseResult signIn(@RequestBody UserDTO userDTO) {
        User user = userService.getUserByMobile(userDTO.getMobile());
        System.out.println(userDTO);
        if (user == null) {
            return ResponseResult.error(StatusConst.USER_MOBILE_NOT_FOUND, MsgConst.USER_MOBILE_NO_FOUND);
        } else {
            //手机号存在，将明文密码转成Base64密文后进行登录
            userDTO.setPassword(StringUtil.getBase64Encoder(userDTO.getPassword()));
            int status = userService.signIn(userDTO);
            if (status == StatusConst.SUCCESS) {
                return ResponseResult.success(user);
            } else if (status == StatusConst.PASSWORD_ERROR) {
                return ResponseResult.error(status, MsgConst.PASSWORD_ERROR);
            } else {
                return ResponseResult.error(status, MsgConst.USER_STATUS_ERROR);
            }
        }
    }

    @PostMapping("/avatar")
    public String ossUpload(@RequestParam("file") MultipartFile sourceFile, @RequestParam("userId") int userId) {
        System.out.println(userId);
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAIyh6IpU7davsJ";
        String accessKeySecret = "iNFycPpfd5oOzGTcwHMBIkC2XrKlwK";
        String bucketName = "hangover";
        String filedir = "avatar/";
        // 获取文件名
        String fileName = sourceFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //uuid生成主文件名
        String prefix = UUID.randomUUID().toString();
        String newFileName = prefix + suffix;
        File tempFile = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(prefix, prefix);
            // MultipartFile to File
            sourceFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filedir + newFileName, tempFile);
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + newFileName, expiration);
        ossClient.shutdown();
        User user = userService.getUserById(userId);
        user.setAvatar(url.toString());
        userService.updateUser(user);
        return url.toString();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ResponseResult.success(user);
    }

    @PostMapping(value = "/verify")
    public ResponseResult getVerifyCode(@RequestParam("mobile") String mobile) {
        User user = userService.getUserByMobile(mobile);
        if (user != null) {
            return ResponseResult.error(StatusConst.MOBILE_EXIST, MsgConst.MOBILE_EXIST);
        } else {
            String verifyCode = SMSUtil.send(mobile);
//            String verifyCode = StringUtil.getVerifyCode();
            System.out.println(verifyCode);
            redisService.set(mobile, verifyCode);
            return ResponseResult.success();
        }
    }

    @PostMapping(value = "/check")
    public ResponseResult checkVerifyCode(@RequestParam("mobile") String mobile, @RequestParam("verifyCode") String verifyCode) {
        String code = redisService.get(mobile).toString();
        System.out.println(code + "---");
        System.out.println(verifyCode);
        if (code.equals(verifyCode)) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error(StatusConst.VERIFYCODE_ERROR, MsgConst.VERIFYCODE_ERROR);
        }
    }
    @RequestMapping(value = "/updateNickName", method = RequestMethod.POST)
    @ResponseBody
    public void updateNickName(@RequestParam("nickname") String nickname,@RequestParam("id") Integer id) {
        User user = userService.getUserById(id);
        user.setNickname(nickname);
        userService.nickname(user);
    }

    @PostMapping(value = "/sign_up")
    public ResponseResult signUp(@RequestBody UserDTO userDTO) {
        userService.signUp(userDTO);
        return ResponseResult.success();
    }
}