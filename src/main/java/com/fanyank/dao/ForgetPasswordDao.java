package com.fanyank.dao;

import com.fanyank.entity.ForgetPassword;
import com.fanyank.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by yanfeng-mac on 2017/4/11.
 */
public class ForgetPasswordDao {

    public void save(ForgetPassword fp) {
        String sql = "insert into t_forgetpassword(token,createtime,uid) values(?,?,?)";
        DBHelp.update(sql,fp.getToken(),fp.getCreatetime(),fp.getUid());
    }

    public ForgetPassword findByToken(String token) {
        String sql = "select * from t_forgetpassword where token = ?";
        return DBHelp.query(sql,new BeanHandler<>(ForgetPassword.class),token);
    }

    public void deleteByUid(Integer uid) {
        String sql = "delete from t_forgetpassword where uid = ?";
        DBHelp.update(sql,uid);
    }
}

