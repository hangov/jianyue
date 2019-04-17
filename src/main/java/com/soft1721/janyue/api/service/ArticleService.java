package com.soft1721.janyue.api.service;

import com.soft1721.janyue.api.entity.Article;
import com.soft1721.janyue.api.entity.vo.ArticleVO;


import java.util.List;


public interface ArticleService {
    List<ArticleVO> selectAll();

    ArticleVO getArticleById(int aId);

    void insertArticle(Article article);

    List<ArticleVO> getArtcleByUId(int uId);

}
