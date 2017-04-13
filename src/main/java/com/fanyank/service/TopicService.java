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
        UserDao userDao = new UserDao();

        List<Topic> topicList = null;

        if(nodeId == null || "".equals(nodeId)) {
            topicList = topicDao.getAllTopic();
            for(Topic topic : topicList) {
                topic.setUser(userDao.findById(topic.getUserid()));
                System.out.println(topic);
            }

        } else {
            Integer nodeID = new Integer(nodeId);

            topicList = topicDao.findByNode(nodeID);
            for(Topic topic : topicList) {
                topic.setUser(userDao.findById(topic.getUserid()));
            }

        }

        return topicList;

    }

    /**
     * 按ID查找话题
     * @param topicId
     * @return
     */
    public Topic findById(String topicId) {
        Integer id = new Integer(topicId);

        TopicDao topicDao = new TopicDao();
        NodeDao nodeDao = new NodeDao();
        UserDao userDao = new UserDao();

        Topic topic = topicDao.findById(id);
        topic.setNode(nodeDao.findById(topic.getNodeid()));
        topic.setUser(userDao.findById(topic.getUserid()));
        return topic;
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

    /**
     * 保存新回复
     * @param content
     * @param commentId
     * @param user_id
     * @param toUserId
     * @param topic_id
     */
    public void saveNewReply(String content,String commentId,Integer user_id,String toUserId,String topic_id) {
        Integer id = new Integer(commentId);
        Integer toId = new Integer(toUserId);
        Integer topicId = new Integer(topic_id);

        ReplyDao replyDao = new ReplyDao();
        TopicDao topicDao = new TopicDao();

        Reply reply = new Reply();
        Topic topic = topicDao.findById(topicId);

        reply.setContent(content);
        reply.setComment_id(id);
        reply.setReplytime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        reply.setUser_id(user_id);
        reply.setTo_user_id(toId);
        reply.setTopic_id(topicId);
        reply.setTopic_title(topic.getTitle());

        replyDao.save(reply);
    }

    /**
     * 发布一个新话题
     * @param title
     * @param content
     * @param node_id
     * @param user
     * @return
     */
    public int saveNewTopic(String title,String content,String node_id,User user) {
        TopicDao topicDao = new TopicDao();
        Topic topic = new Topic();

        topic.setUserid(new Integer(user.getId()));
        topic.setNodeid(new Integer(node_id));
        topic.setTitle(title);
        topic.setText(content);
        topic.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

        return topicDao.save(topic);
    }

    /**
     * 收藏或取消收藏主题
     * @param topic
     * @param user
     * @param action
     */
    public void favTopic(Topic topic, User user, String action) {
        FavDao favDao = new FavDao();
        TopicDao topicDao = new TopicDao();
        Fav fav = favDao.findByTopicIdAndUserId(topic.getId(),user.getId());

        if("fav".equals(action)) {
            if(fav == null) {
                fav = new Fav();
                fav.setUserid(user.getId());
                fav.setTopicid(topic.getId());
                fav.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                favDao.save(fav);

                topic.setFavnum(topic.getFavnum() + 1);

                topicDao.update(topic);
            }
        } else {
            if(fav != null) {
                favDao.delete(fav);
                topic.setFavnum(topic.getFavnum() - 1);
                topicDao.update(topic);
            }
        }
    }

}
