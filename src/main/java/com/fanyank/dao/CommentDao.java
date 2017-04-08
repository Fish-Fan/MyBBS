package com.fanyank.dao;

import com.fanyank.entity.Comment;
import com.fanyank.entity.Reply;
import com.fanyank.entity.User;
import com.fanyank.util.DBHelp;
import com.google.common.collect.Lists;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
public class CommentDao {
    public Integer save(Comment comment) {
        String sql = "insert into t_comment(comment,createtime,userid,topicid) values(?,?,?,?)";
        return DBHelp.insert(sql,comment.getComment(),comment.getCreatetime(),comment.getUserid(),comment.getTopicid()).intValue();
    }

    public List<Comment> findByTopicId(Integer topicId) {
//        String sql = "select t_comment.*,username,avatar from t_comment INNER JOIN t_user ON t_comment.userid = t_user.id where topicid = ?";
        String sql = "select t_comment.*,t_user.username username,t_user.avatar avatar,\n" +
                "\tt_reply.content content,t_reply.user_id replyUserId,t_reply.replytime replyTime\n" +
                "from (t_comment left join t_user on t_comment.userid = t_user.id) left join t_reply \n" +
                "on t_comment.id = t_reply.comment_id where t_comment.topicid = ?";
        return DBHelp.query(sql, new ResultSetHandler<List<Comment>>() {

            public List<Comment> handle(ResultSet rs) throws SQLException {
                List<Comment> commentList = Lists.newArrayList();
                BasicRowProcessor rowProcessor = new BasicRowProcessor();
                while(rs.next()) {
                    Comment comment = rowProcessor.toBean(rs,Comment.class);
                    User user = rowProcessor.toBean(rs,User.class);
                    comment.setUser(user);
                    commentList.add(comment);

                    List<Reply> replyList = new ArrayList<Reply>();
                    Reply reply = rowProcessor.toBean(rs,Reply.class);
                    replyList.add(reply);
                    comment.setReplyList(replyList);
                }
                return commentList;
            }
        }, topicId);
    }

    public List<Comment> findByTopicId2(Integer topicId) {
        String sql = "select * from t_comment where topicId = ?";
        return DBHelp.query(sql,new BeanListHandler<Comment>(Comment.class),topicId);
    }
}
