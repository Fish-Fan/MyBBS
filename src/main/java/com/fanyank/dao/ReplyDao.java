package com.fanyank.dao;

import com.fanyank.entity.Reply;
import com.fanyank.entity.Topic;
import com.fanyank.entity.User;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfeng-mac on 2017/4/6.
 */
public class ReplyDao {
    public void save(Reply reply) {
        String sql = "insert into t_reply(content,replytime,comment_id,user_id,to_user_id,topic_id,topic_title) values(?,?,?,?,?,?,?)";
        DBHelp.update(sql,reply.getContent(),reply.getReplytime(),reply.getComment_id(),reply.getUser_id(),reply.getTo_user_id(),reply.getTopic_id(),reply.getTopic_title());
    }

    public List<Reply> findByCommentId(Integer commentId) {
        String sql = "select * from t_reply where comment_id = ?";
        return DBHelp.query(sql,new BeanListHandler<Reply>(Reply.class),commentId);
    }

    public Integer getUnReadCount(User user) {
        String sql = "select count(*) from t_reply where to_user_id = ? and isRead = 0";
        return DBHelp.query(sql,new ScalarHandler<Long>(),user.getId()).intValue();
    }

    public List<Reply> getMessage(User user,Integer messageState) {
        String sql = "select t_reply.*,t_user.username,t_user.avatar,t_topic.title,t_topic.id\n" +
                " from (t_reply left join t_user on t_reply.user_id = t_user.id)\n" +
                "left join t_topic on t_reply.topic_id = t_topic.id \n" +
                "where t_reply.isRead = ? and t_reply.to_user_id = ?";
        return DBHelp.query(sql, new ResultSetHandler<List<Reply>>() {
            public List<Reply> handle(ResultSet resultSet) throws SQLException {
                List<Reply> replyList = new ArrayList<Reply>();
                BasicRowProcessor basicRowProcessor = new BasicRowProcessor();

                while (resultSet.next()) {
                    Reply reply = (Reply) basicRowProcessor.toBean(resultSet,Reply.class);
                    User user1 = basicRowProcessor.toBean(resultSet,User.class);

                    reply.setUser(user1);
                    replyList.add(reply);
                }

                return replyList;
            }
        },messageState, user.getId());
    }

    public void setAlreadyRead(User user) {
        String sql = "update t_reply set isRead = 1 where to_user_id = ?";
        DBHelp.update(sql,user.getId());
    }


}
