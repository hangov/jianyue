package com.soft1721.janyue.api.service.impl;

import com.soft1721.janyue.api.entity.Article;
import com.soft1721.janyue.api.entity.vo.ArticleVO;
import com.soft1721.janyue.api.mapper.ArticleMapper;
import com.soft1721.janyue.api.mapper.ImgMapper;
import com.soft1721.janyue.api.service.ArticleService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ImgMapper imgMapper;

    @Override
    public List<ArticleVO> selectAll() {
        List<ArticleVO> articles =articleMapper.selectAll();
        for(ArticleVO articleVO:articles){
            articleVO.setImgs(imgMapper.selectImgsByAId(articleVO.getId()));
        }
        return articles;
    }


    @Override
    public ArticleVO getArticleById(int aId) {
        return articleMapper.getArticleById(aId);
    }

    @Override
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public List<ArticleVO> getArtcleByUId(int uId) {
        List<ArticleVO> articles=articleMapper.getArticleByUId(uId);
        for (ArticleVO articleVO:articles){
            articleVO.setImgs(imgMapper.selectImgsByAId(articleVO.getId()));
        }
        return articles;

    }


}