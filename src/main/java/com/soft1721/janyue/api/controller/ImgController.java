package com.soft1721.janyue.api.controller;


import com.alibaba.fastjson.JSONArray;
import com.soft1721.janyue.api.entity.Img;
import com.soft1721.janyue.api.service.ImgService;
import com.soft1721.janyue.api.util.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/img")
public class ImgController {
    @Resource
    private ImgService imgService;

    @PostMapping("/add")
    public ResponseResult addImg(@RequestParam("aId") int aId,
                                 @RequestParam("imgs") String imgs) {

        List<String> imgList = JSONArray.parseArray(imgs, String.class);
        for (String imgUrl:imgList) {
            Img img = new Img();
            img.setAId(aId);
            img.setImgUrl(imgUrl);
            imgService.insertImg(img);
        }
        return ResponseResult.success();
    }

}
