package com.soft1721.janyue.api.service.impl;

import com.soft1721.janyue.api.entity.Img;
import com.soft1721.janyue.api.mapper.ImgMapper;
import com.soft1721.janyue.api.service.ImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ImgServiceImpl implements ImgService {
    @Resource
    private ImgMapper imgMapper;

    @Override
    public List<Img> selectImgsByAId(int aId) {
        return imgMapper.selectImgsByAId(aId);
    }

    @Override
    public void insertImg(Img img) {
        imgMapper.insertImg(img);
    }
}
