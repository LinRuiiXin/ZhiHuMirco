package com.example.search;

import com.example.basic.po.Answer;
import com.example.basic.po.Article;
import com.example.basic.po.User;
import com.example.search.dao.AnswerDao;
import com.example.search.dao.ArticleDao;
import com.example.search.dao.QuestionDao;
import com.example.search.dao.UserDao;
import com.example.search.document.Information;
import com.example.search.document.Keyword;
import com.example.search.document.UserDoc;
import com.example.search.service.interfaces.InformationService;
import com.example.search.service.interfaces.KeywordService;
import com.example.search.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * TODO
 *
 * @author LinRuiXin
 * @date 2020/11/27 7:47 下午
 */
@SpringBootTest
public class InitZhiHuSearch {

    private final QuestionDao questionDao;
    private final ArticleDao articleDao;
    private final KeywordService keywordService;
    private final InformationService informationService;
    private final UserService userService;
    private final UserDao userDao;
    private final AnswerDao answerDao;

    @Autowired
    public InitZhiHuSearch(QuestionDao questionDao, KeywordService keywordService, ArticleDao articleDao, InformationService informationService, UserService userService, UserDao userDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.keywordService = keywordService;
        this.articleDao = articleDao;
        this.informationService = informationService;
        this.userService = userService;
        this.userDao = userDao;
        this.answerDao = answerDao;
    }

    @Test
    void importAllQuestionKeyword(){
        List<String> allQuestion = questionDao.getAllQuestion();
        keywordService.addKeywordBatch(allQuestion);
    }

    @Test
    void importAllArticleKeyword(){
        keywordService.addKeywordBatch(articleDao.getAllArticleTitle());
    }

    @Test
    void testImportKeywordSuccess(){
        List<Keyword> allKeyword = keywordService.getAllKeyword(20);
        allKeyword.forEach(System.out::println);
    }

    @Test
    void importAllArticleToInformation(){
        List<Article> allArticle = articleDao.getAllArticle();
        allArticle.forEach(article -> {
            /*String articleContent = getArticleForText(article.getId());
            article.setContent(articleContent);*/
            User userById = userDao.getUserById(article.getAuthorId());
            informationService.insertInformation(convertArticleToInformation(article,userById));
        });
    }

    @Test
    void testInformation(){
        List<Information> information = informationService.getInformation(0, 20);
        information.forEach(System.out::println);
    }

    @Test
    void importAllAnswerInformation(){
        List<Answer> allAnswer = answerDao.getAllAnswer();
        allAnswer.forEach(answer -> {
            informationService.insertInformation(convertAnswerToInformation(answer));
        });
    }

    @Test
    void importAllUser(){
        List<UserDoc> allUser = userDao.getAllUser();
        userService.addUserBatch(allUser);
    }

    @Test
    void testUserImport(){
        List<UserDoc> user = userService.getUser(0, 10);
        user.forEach(System.out::println);
    }

    @Test
    void insertSingleUser(){
        userService.addUser(new UserDoc(1l,"林瑞鑫","158581734675531.png","test"));
    }

//    private String getArticleForText(Long id) {
//        return getHtmlTextFromFile("Article",String.valueOf(id));
//    }
//
//    private String getAnswerForText(Long id){
//        return getHtmlTextFromFile("Answer",String.valueOf(id));
//    }

    private String getHtmlTextFromFile(String dirName,String filename){
        try {
            File file = new File("/Users/linruixin/Desktop/upload/ZhiHu/" + dirName + "/" + filename + ".txt");
            if(file.exists()){
                StringBuilder stringBuilder = new StringBuilder();
                FileReader fileReader = new FileReader(file);
                char [] buffer = new char[1024];
                while (fileReader.read(buffer) != -1){
                    stringBuilder.append(buffer);
                }
                return getContentFromHtml(stringBuilder.toString());
            }else {
                System.out.println("could not find article : " + filename);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private Information convertArticleToInformation(Article article, User user) {
        return new Information()
                .contentType(2)
                .type(article.getContentType())
                .contentId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .thumbnail(article.getThumbnail())
                .authorId(article.getAuthorId())
                .authorName(user.getUserName())
                .profile(user.getProfile())
                .portraitFileName(user.getPortraitFileName())
                .questionId(System.currentTimeMillis());
    }

    private Information convertAnswerToInformation(Answer answer) {
        User userById = userDao.getUserById(answer.getUserId());
        String title = questionDao.getQuestionTitle(answer.getQuestionId());
        return new Information()
                .contentType(1)
                .questionId(answer.getQuestionId())
                .type(answer.getContentType())
                .contentId(answer.getId())
                .title(title)
                .content(answer.getContent())
                .thumbnail(answer.getThumbnail())
                .authorId(answer.getUserId())
                .authorName(userById.getUserName())
                .profile(userById.getProfile())
                .portraitFileName(userById.getPortraitFileName());
    }

    public static String getContentFromHtml(String htmlContent){
        if(htmlContent!=null){
            return htmlContent.replaceAll("<\\/?.+?>", "");
        }
        return "";
    }
}
