package com.fanyank.dao;

import com.fanyank.entity.User;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class UserDao {
    public void save(User user) {
        String sql = "insert into t_user(username,password,email,avatar,createtime,loginip,logintime,state) values(?,?,?,?,?,?,?,?)";
        DBHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),user.getCreatetime(),user.getLoginip(),user.getLogintime(),user.getState());
    }

    public void update(User user) {
        String sql = "update t_user set password = ?,email = ?,avatar = ?,loginip = ?,logintime = ?,state = ? where id = ?";
        DBHelp.update(sql,user.getPassword(),user.getEmail(),user.getAvatar(),user.getLoginip(),user.getLogintime(),user.getState(),user.getId());
    }

    public void delete(Integer id) {
        String sql = "delete from t_user where id = ?";
        DBHelp.update(sql,id);
    }

    public User findById(Integer id) {
        String sql = "select * from t_user where id = ?";
        return DBHelp.query(sql,new BeanHandler<User>(User.class),id);
    }

    public User findByUsername(String username) {
        String sql = "select * from t_user where username = ?";
        return DBHelp.query(sql,new BeanHandler<User>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email = ?";
        return DBHelp.query(sql,new BeanHandler<User>(User.class),email);
    }
}
