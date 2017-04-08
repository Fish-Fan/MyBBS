package com.fanyank.service;

import com.fanyank.dao.*;
import com.fanyank.entity.*;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
public class TopicService {
    /**
     * 返回全部话题
     * @return
     */
    public List<Topic> getAllTopic() {
        TopicDao topicDao = new TopicDao();
        return topicDao.getAllTopic();
    }

    /**
     * 返回全部节点
     */
    public List<Node> getAllNode() {
        NodeDao nodeDao = new NodeDao();
        return nodeDao.getAllNode();
    }

    /**
     * 按节点名称查找话题
     * @param nodeId
     * @return
     */
    public List<Topic> findTopicByNode(String nodeId) {
        TopicDao topicDao = new TopicDao();

        if(nodeId == null || "".equals(nodeId)) {
            return topicDao.getAllTopic();
        } else {
            Integer nodeID = new Integer(nodeId);

            return topicDao.findByNode(nodeID);
        }

    }

    /**
     * 按ID查找话题
     * @param topicId
     * @return
     */
    public Topic findById(String topicId) {
        Integer id = new Integer(topicId);
        TopicDao topicDao = new TopicDao();
        return topicDao.findById(id);
    }



    /**
     * 存储一条新评论
     * @param value
     * @param topicId
     * @param user
     */
    public void saveNewComment(String value, String topicId, User user) {
        //MM大写区分时间和月份,HH大写是24小时制，hh小写是12小时制
        String now = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        Integer id = new Integer(topicId);

        CommentDao commentDao = new CommentDao();
        TopicDao topicDao = new TopicDao();

        Comment comment = new Comment();
        Topic topic = topicDao.findById(id);

        //插入comment
        comment.setComment(value);
        comment.setTopicid(id);
        comment.setCreatetime(now);
        comment.setUserid(user.getId());

        commentDao.save(comment);

        //更新topic
        topic.setReplynum(topic.getReplynum()+1);
        topic.setReplytime(now);
        topicDao.update(topic);

    }

    /**
     * 根据话题找评论
     * @param topicId
     * @return
     */
    public List<Comment> findCommentByTopicId(String topicId) {
        Integer id = new Integer(topicId);
        CommentDao commentDao = new CommentDao();
        UserDao userDao = new UserDao();
        ReplyDao replyDao = new ReplyDao();
        List<Comment> commentList = commentDao.findByTopicId2(id);

        for(Comment comment : commentList) {
            User user = userDao.findById(comment.getUserid());
            comment.setUser(user);

            List<Reply> replyList = replyDao.findByCommentId(comment.getId());

            for(Reply reply : replyList) {
                User replyUser = userDao.findById(reply.getUser_id());
                reply.setUser(replyUser);
            }

            comment.setReplyList(replyList);
        }
        return commentList;
    }

    public void saveNewReply(String content,String commentId,Integer user_id,String toUserId) {
        Integer id = new Integer(commentId);
        Integer toId = new Integer(toUserId);

        ReplyDao replyDao = new ReplyDao();
        Reply reply = new Reply();

        reply.setContent(content);
        reply.setComment_id(id);
        reply.setReplytime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        reply.setUser_id(user_id);
        reply.setTo_user_id(toId);

        replyDao.save(reply);
    }
}
