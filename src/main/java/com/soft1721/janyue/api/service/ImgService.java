package com.soft1721.janyue.api.service;

import com.soft1721.janyue.api.entity.Img;


import java.util.List;

public interface ImgService {
    List<Img> selectImgsByAId(int aId);

    void insertImg(Img img);
}
