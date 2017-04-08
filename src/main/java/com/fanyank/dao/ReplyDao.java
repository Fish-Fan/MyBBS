package com.fanyank.dao;

import com.fanyank.entity.Reply;
import com.fanyank.entity.User;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by yanfeng-mac on 2017/4/6.
 */
public class ReplyDao {
    public void save(Reply reply) {
        String sql = "insert into t_reply(content,replytime,comment_id,user_id,to_user_id) values(?,?,?,?,?)";
        DBHelp.update(sql,reply.getContent(),reply.getReplytime(),reply.getComment_id(),reply.getUser_id(),reply.getTo_user_id());
    }

    public List<Reply> findByCommentId(Integer commentId) {
        String sql = "select * from t_reply where comment_id = ?";
        return DBHelp.query(sql,new BeanListHandler<Reply>(Reply.class),commentId);
    }

    public Integer getUnReadCount(User user) {
        String sql = "select count(*) from t_reply where to_user_id = ? and isRead = 0";
        return DBHelp.query(sql,new ScalarHandler<Long>(),user.getId()).intValue();
    }

    public List<Reply> getUnReadMsg(User user) {
        String sql = "select * from t_reply where to_user_id = ? and isRead = 0";
        return DBHelp.query(sql,new BeanListHandler<Reply>(Reply.class),user.getId());
    }
}
