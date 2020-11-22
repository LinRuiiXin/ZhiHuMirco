package com.example.answer.service;

import com.example.answer.dao.AnswerDao;
import com.example.answer.dao.AnswerSupportDao;
import com.example.answer.service.interfaces.AnswerService;
import com.example.answer.service.rpc.UserService;
import com.example.basic.dto.SimpleDto;
import com.example.basic.po.Answer;
import com.example.basic.po.User;
import com.example.basic.util.StringUtils;
import com.example.basic.vo.AnswerVo;
import com.example.basic.vo.RecommendViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Value("${spring.servlet.multipart.location}")
    private String resourcesLocation;
    private final UserService userService;

    /**
     *    key-questionId value-lock
     *    根据questionId决定哪个问题由哪个锁保护
     */
    private final Map<Long,Object> locks;

    private final RedisTemplate redisTemplate;
    private final AnswerDao answerDao;
    private final AnswerSupportDao answerSupportDao;
    private final ExecutorService executorService;

    @Autowired
    public AnswerServiceImpl(AnswerDao answerDao, ExecutorService executorService, UserService userService, RedisTemplate redisTemplate, AnswerSupportDao answerSupportDao){
        locks = new ConcurrentHashMap<>();
        this.answerDao = answerDao;
        this.executorService = executorService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.answerSupportDao = answerSupportDao;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public SimpleDto insertAnswer(MultipartFile file, Answer answer) {
        try {
            answerDao.insertAnswer(answer);
            File newFile = new File("/Users/linruixin/Desktop/upload/ZhiHu/Answer/" + answer.getId() + ".txt");
            file.transferTo(newFile);
            updateAnswerOrder(answer.getQuestionId());
            return new SimpleDto(true,null,null);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new SimpleDto(false,"IO异常",null);
        }
    }

    @Override
    public Answer queryRandomAnswerByQuestionId(Long questionId) {
        return answerDao.queryRandomAnswerByQuestionId(questionId);
    }

    @Override
    public List<Answer> getRandomAnswer(Integer sum) {
        return answerDao.getRandomAnswer(sum);
    }

    @Override
    public AnswerVo getAnswerById(Long userId,Long id) throws ExecutionException, InterruptedException {
        Answer answer = answerDao.queryAnswerById(id);
        return answer != null ? wrapAnswer(userId,answer) : null;
    }

    @Override
    public AnswerVo getNextAnswer(Long userId, Long questionId, Long id) throws ExecutionException, InterruptedException {
        List<Long> candidateId = getCandidateId(questionId, id, 1);
        if(candidateId.size() == 0)
            return null;
        Future<String> answerContent = getAnswerContent(candidateId.get(0));
        AnswerVo answerById = getAnswerById(userId, candidateId.get(0));
        answerById.getAnswer().setContent(answerContent.get());
        return answerById;
    }

    @Override
    public AnswerVo getPreviousAnswer(Long userId,Long questionId, Long id) throws ExecutionException, InterruptedException {
        Long previousId = getPreviousId(questionId, id);
        if(previousId != null){
            Future<String> answerContent = getAnswerContent(id);
            AnswerVo answerById = getAnswerById(userId,previousId);
            answerById.getAnswer().setContent(answerContent.get());
            return answerById;
        }
        return null;
    }

    @Override
    public List<AnswerVo> getNextTreeAnswer(Long userId,Long questionId,Long id) throws ExecutionException, InterruptedException {
        List<Long> candidateId = getCandidateId(questionId, id, 2);
        candidateId.add(0,id);
        System.out.println(candidateId);
        List<AnswerVo> answerBatch = getAnswerVoBatch(userId,candidateId);
        return answerBatch;
    }

    @Override
    public boolean userHasAttention(Long userId, Long answererId) {
        return answerDao.userHasAttention(userId,answererId) != null;
    }

    @Override
    public boolean userHasSupport(Long userId, Long answerId) {
        return answerDao.userHasSupport(userId,answerId) != null;
    }

    @Override
    public void updateAnswerCommentSum(Long answerId) {
        answerDao.updateAnswerCommentSum(answerId);
    }

    private AnswerVo wrapAnswer(Long userId,Answer answer) throws ExecutionException, InterruptedException {
        AnswerVo answerVo = new AnswerVo();
        if(userId != -1){
            answerVo.setAttention(userHasAttention(userId,answer.getUserId()));
            answerVo.setSupport(userHasSupport(userId,answer.getId()));
        }
        User user = userService.getUserById(answer.getUserId());
        answerVo.setUser(user);
        answerVo.setAnswer(answer);
        return answerVo;
    }

    private Future<String> getAnswerContent(Long id) {
        return executorService.submit(()-> {
            return getAnswerFileContent(id);
        });
    }

    private String getAnswerFileContent(Long id) {
        FileReader fileReader = null;
        StringBuffer stringBuffer;
        try {
            fileReader = new FileReader(resourcesLocation+"/Answer/"+id+".txt");
            char [] buffer = new char[1024];
            int len = 0;
            stringBuffer= new StringBuffer();
            while ((len = fileReader.read(buffer))!=-1){
                stringBuffer.append(buffer,0,len);
            }
            return stringBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    private List<Long> getCandidateId(Long questionId,Long id,Integer len){
        List<Long> answerOrder = getAnswerOrder(questionId);
        List<Long> ids = new ArrayList<>();
        int index = -1;
        for(int i = 0;i<answerOrder.size();i++){
            Long idInOrder = answerOrder.get(i);
            if(idInOrder.equals(id)){
                index = i;
                break;
            }
        }
        if(index != -1){
            for(int i = 1;i<=len;i++){
                int indexNow = index+i;
                if(indexNow>=answerOrder.size())
                    break;
                ids.add(answerOrder.get(indexNow));
            }
        }
        return ids;
    }

    private Long getPreviousId(Long questionId,Long id){
        List<Long> answerOrder = getAnswerOrder(questionId);
        int index = -1;
        for(int i = 0;i<answerOrder.size();i++){
            Long idInOrder = answerOrder.get(i);
            if(idInOrder.equals(id)){
                index = i;
                break;
            }
        }
        if(index != -1){
            int indexNow = index - 1;
            if(indexNow >= 0){
                return answerOrder.get(indexNow);
            }
            return null;
        }
        return null;
    }
    private List<AnswerVo> getAnswerVoBatch(Long userId,List<Long> ids) throws ExecutionException, InterruptedException {
        List<Future<String>> futures = getContentBatch(ids);
        List<AnswerVo> res = new ArrayList<>();
        ids.forEach(id-> {
            try {
                res.add(getAnswerById(userId,id));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        for(int i = 0 ; i < res.size() ; i++){
            res.get(i).getAnswer().setContent(futures.get(i).get());
        }
        return res;
    }

    private List<Future<String>> getContentBatch(List<Long> ids) throws InterruptedException {
        List<Callable<String>> callableList = new ArrayList<>();
        ids.forEach(id->{
            callableList.add(()->{
                return getAnswerFileContent(id);
            });
        });
        List<Future<String>> futures = executorService.invokeAll(callableList);
        return futures;
    }

    /**
     *  共享变量 -> locks
     *  应该将这个业务分离到AnswerService中
     */
    @Override
    public List<Long> getAnswerOrder(Long questionId) {
        Object lock = locks.get(questionId);
        if(lock == null){
            Object o = new Object();
            lock = locks.putIfAbsent(questionId,o);
            if(lock == null)
                lock = o;
        }
        String key = "AnswerOrder:"+questionId;
        List<Long> answerOrder = null;
        if(!redisTemplate.hasKey(key)){
            synchronized (lock) {
                if(!redisTemplate.hasKey(key)){
                    answerOrder = answerDao.getAnswerOrder(questionId);
                    redisTemplate.opsForValue().set(key, answerOrder);
                }else
                    answerOrder = (List<Long>) redisTemplate.opsForValue().get(key);
            }
        }else{
            answerOrder = (List<Long>) redisTemplate.opsForValue().get(key);
        }
        return answerOrder;
    }

    @Override
    public void updateAnswerOrder(Long questionId) {
        Object lock = locks.get(questionId);
        if(lock == null){
            Object o = new Object();
            lock = locks.putIfAbsent(questionId, o);
            if(lock == null)
                lock = o;
        }
        String key = "AnswerOrder:"+questionId;
        List<Long> answerOder = answerDao.getAnswerOrder(questionId);
        if(!redisTemplate.hasKey(key)){
            synchronized (lock) {
                if(!redisTemplate.hasKey(key))
                    redisTemplate.opsForValue().set(key, answerOder);
                else
                    redisTemplate.boundValueOps(key).set(answerOder);
            }
        }else{
            redisTemplate.boundValueOps(key).set(answerOder);
        }
    }

    @Override
    public void supportAnswer(Long answerId, Long userId) {
        synchronized (this){
            boolean isSupport = userHasSupport(userId, answerId);
            if(!isSupport){
                answerSupportDao.supportAnswer(answerId,userId);
                answerDao.incrementSupportSum(answerId);
            }
        }
    }

    @Override
    public void unSupportAnswer(Long answerId, Long userId) {
        synchronized (this){
            boolean isSupport = userHasSupport(userId, answerId);
            if(isSupport){
                answerSupportDao.unSupportAnswer(answerId,userId);
                answerDao.decrementSupportSum(answerId);
            }
        }
    }

    @Override
    public List<RecommendViewBean> getOrderAnswerViewBean(Long questionId, int limit) throws NoSuchFieldException, IllegalAccessException {
        List<Long> answerIds = getOrderAnswerIds(questionId,limit,10);
        List<RecommendViewBean> res = new ArrayList<>(answerIds.size());
        if(answerIds.size() > 0){
            List<Answer> answers = getAnswerBatch(answerIds);
            List<User> users = userService.getUserBatch(StringUtils.jointString('-', answers, "userId"));
            for (int i = 0; i < answerIds.size(); i++) {
                Answer answer = answers.get(i);
                User user = users.get(i);
                RecommendViewBean recommendViewBean = convertRecommendViewBean(answer,user);
                res.add(recommendViewBean);
            }
        }
        return res;
    }

    private RecommendViewBean convertRecommendViewBean(Answer answer, User user) {
        return new RecommendViewBean()
                .contentId(answer.getId())
                .questionId(answer.getQuestionId())
                .userId(answer.getUserId())
                .contentType(1)
                .type(answer.getContentType())
                .username(user.getUserName())
                .portraitFileName(user.getPortraitFileName())
                .introduction(user.getProfile())
                .content(answer.getContent())
                .thumbnail(answer.getThumbnail())
                .supportSum(answer.getSupportSum())
                .commentSum(answer.getCommentSum());
    }

    private List<Answer> getAnswerBatch(List<Long> answerIds) {
        List<Answer> res = new ArrayList<>(answerIds.size());
        answerIds.forEach(id -> res.add(answerDao.queryFullAnswerById(id)));
        return res;
    }

    /*
    * 获取某个问题下的回答Id，按点赞数降序，Id升序
    * @questionId 问题Id
    * @limit 起始下标
    * @len 长度
    * */
    private List<Long> getOrderAnswerIds(Long questionId, int limit, int len) {
        List<Long> answerOrder = getAnswerOrder(questionId);
        List<Long> answerIds = new ArrayList<>(10);
        for (int i = 0; i < len; i++) {
            int index = limit + i;
            if(index < answerOrder.size()){
                answerIds.add(answerOrder.get(index));
            }
        }
        return answerIds;
    }
}
