package com.soft1721.janyue.api.controller;

import com.soft1721.janyue.api.entity.Follow;
import com.soft1721.janyue.api.entity.vo.FollowVO;
import com.soft1721.janyue.api.service.FollowService;
import com.soft1721.janyue.api.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/follow")
public class FollowController {
    @Resource
    private FollowService followService;


    @PostMapping("/add")
    public ResponseResult followUser(@RequestParam("fromUId") int fromUId, @RequestParam("toUId") int toUId) {
        Follow follow = new Follow();
        follow.setFromUId(fromUId);
        follow.setToUId(toUId);
        followService.insertFollow(follow);
        return ResponseResult.success();
    }

    @PostMapping("/cancel")
    public ResponseResult cancelFollow(@RequestParam("fromUId") int fromUId, @RequestParam("toUId") int toUId) {
        followService.deleteFollow(fromUId, toUId);
        return ResponseResult.success();
    }

    @GetMapping("list")
    public ResponseResult getrticleByUId(@RequestParam("fromUId") int fromUId) {
        List<FollowVO> followVOList = followService.getFollowsByUId(fromUId);
        return ResponseResult.success(followVOList);
    }

}