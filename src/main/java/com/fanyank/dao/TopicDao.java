package com.fanyank.dao;

import com.fanyank.entity.Topic;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/29.
 */
public class TopicDao {
    public Integer save(Topic topic) {
        String sql = "insert into t_topic(title,text,createtime,userid,nodeid) values(?,?,?,?,?)";
        return DBHelp.insert(sql,topic.getTitle(),topic.getText(),topic.getCreatetime(),topic.getUserid(),topic.getNodeid()).intValue();
    }

    public List<Topic> getAllTopic() {
        String sql = "select * from t_topic";
        return DBHelp.query(sql,new BeanListHandler<Topic>(Topic.class));
    }

    public List<Topic> findByNode(Integer nodeId) {
        String sql = "select * from t_topic where nodeid = ?";
        return DBHelp.query(sql,new BeanListHandler<Topic>(Topic.class),nodeId);
    }

    public Topic findById(Integer id) {
        String sql = "select * from t_topic where id = ?";
        return DBHelp.query(sql,new BeanHandler<Topic>(Topic.class),id);
    }

    public void update(Topic topic) {
        String sql = "update t_topic set replynum = ?,replytime = ? where id = ?";
        DBHelp.update(sql,topic.getReplynum(),topic.getReplytime(),topic.getId());
    }
}
