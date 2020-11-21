package com.example.article.controller;

import com.example.article.service.interfaces.ArticleService;
import com.example.article.service.interfaces.ArticleSupportService;
import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Article;
import com.example.basic.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.ws.rs.POST;
import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/11 7:52 下午
 */
@RestController
@RequestMapping("/Article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleSupportService articleSupportService;

    @Autowired
    public ArticleController(ArticleService articleService, ArticleSupportService articleSupportService) {
        this.articleService = articleService;
        this.articleSupportService = articleSupportService;
    }

    /*
    * 上传文章
    * */
    @PostMapping
    public SimpleDto uploadArticle(MultipartHttpServletRequest request, @RequestParam Long userId, @RequestParam Integer contentType, @RequestParam String title, @RequestParam String content,@RequestParam String thumbnail) throws IOException {
        SimpleDto res = null;
        try {
            MultipartFile articleFile = request.getFile("article");
            if(articleFile != null){
                Article article = new Article(userId, title, content, thumbnail, contentType);
                articleService.addArticle(article);
                String fileName = article.getId() + ".txt";
                articleFile.transferTo(new File("/Users/linruixin/Desktop/upload/ZhiHu/Article/"+fileName));
                res = new SimpleDto(true,null,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            res = new SimpleDto(false,"上传失败",null);
        }
        return res;
    }

    /*
    * 获取文章内容，此处因为需要的 authorId(判断读者是否关注) 在推荐页面点击进入时已经有了，所以直接传入 authorId 可以减少一次查询 article 的操作。
    * @id 文章Id
    * @authorId 作者Id
    * @userId 读者Id
    * */
    @GetMapping("/{id}/{authorId}/{userId}")
    public SimpleDto getArticle(@PathVariable Long id,@PathVariable Long authorId,@PathVariable Long userId){
        ArticleVo article = articleService.getArticle(id, authorId, userId);
        return article != null ? new SimpleDto(true,null,article) : new SimpleDto(false,"请求失败",null);
    }

    /*
    * 获取文章内容，为避免耦合，authorId 在没有的情况下也可以直接传入文章的Id，然后会进行一次数据库查找
    * @id 文章Id
    * @userId 读者Id
    * */
    @GetMapping("/{id}/{userId}")
    public SimpleDto getArticle(@PathVariable Long id,@PathVariable Long userId){
        ArticleVo article = articleService.getArticle(id, userId);
        return article != null ? new SimpleDto(true,null,article) : new SimpleDto(false,"请求失败",null);
    }

    /*
    * 为文章点赞，如果用户已经点赞，那么不插入数据
    * @articleId 文章Id
    * @userId 用户Id
    * */
    @PostMapping("/Support")
    public SimpleDto supportArticle(@RequestParam Long articleId,@RequestParam Long userId){
        articleSupportService.support(articleId,userId);
        return new SimpleDto(true,null,null);
    }

    /*
     * 取消文章点赞，如果用户未点赞，那么不删除数据
     * @articleId 文章Id
     * @userId 用户Id
     * */
    @PostMapping("/UnSupport")
    public SimpleDto unSupportArticle(@RequestParam Long articleId,@RequestParam Long userId){
        articleSupportService.unSupport(articleId,userId);
        return new SimpleDto(true,null,null);
    }

    private String newFileName(String originalFilename, Long userId) {
        long l = System.currentTimeMillis();
        String[] split = originalFilename.split("\\.");
        String suffix = split[(split.length)-1];
        String fileName = l + userId + "." + suffix;
        return fileName;
    }
}
