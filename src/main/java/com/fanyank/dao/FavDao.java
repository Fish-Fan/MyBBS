package com.fanyank.dao;

import com.fanyank.entity.Fav;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by yanfeng-mac on 2017/4/12.
 */
public class FavDao {
    public Fav findByTopicIdAndUserId(Integer topicId, Integer userId) {
        String sql = "select * from t_fav where topicid = ? and userid = ?";
        return DBHelp.query(sql,new BeanHandler<>(Fav.class),topicId,userId);
    }

    public void save(Fav fav) {
        String sql = "insert into t_fav(topicid,userid,createtime) values(?,?,?)";
        DBHelp.update(sql,fav.getTopicid(),fav.getUserid(),fav.getCreatetime());
    }

    public void delete(Fav fav) {
        String sql = "delete from t_fav where id = ?";
        DBHelp.update(sql,fav.getId());
    }
}
