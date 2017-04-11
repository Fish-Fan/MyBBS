package com.fanyank.service;

import com.fanyank.dao.ReplyDao;
import com.fanyank.dao.UserDao;
import com.fanyank.entity.Reply;
import com.fanyank.entity.User;
import com.fanyank.util.ConfigProp;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class UserService {
    /**
     * 根据名字查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername(username);
        return user;
    }

    /**
     * 根据email查找用户
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        UserDao userDao = new UserDao();
        return userDao.findByEmail(email);
    }

    /**
     * 创建新用户
     * @param username
     * @param password
     * @param email
     */
    public void saveUser(String username,String password,String email) {
        UserDao userDao = new UserDao();
        User user = new User();

        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
        user.setEmail(email);
        user.setCreatetime(DateTime.now().toString("yyyy-mm-dd HH:mm:ss"));

        userDao.save(user);
    }

    /**
     * 登录成功
     * @param username
     * @param password
     * @param ip
     * @return
     */
    public User login(String username,String password,String ip) {
        UserDao userDao = new UserDao();
        User user = findByUsername(username);
        if(user != null && user.getPassword().equals(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")))) {
            //登录成功
            user.setLoginip(ip);
            user.setLogintime(DateTime.now().toString("yyyy-mm-dd HH:mm:ss"));

            userDao.update(user);
            return user;
        }

        return null;
    }

    /**
     * 获取用户未读消息数量
     * @param user
     * @return
     */
    public int getUnReadMsgCount(User user) {
        ReplyDao replyDao = new ReplyDao();
        return replyDao.getUnReadCount(user);
    }

    /**
     * 获取用户未读消息
     * @param user
     * @return
     */
    public List<Reply> getUnReadMsg(User user) {
        ReplyDao replyDao = new ReplyDao();
        return replyDao.getMessage(user,0);
    }

    /**
     * 将未读未读消息改为已读
     * @param user
     */
    public void setAlreadyRead(User user) {
        ReplyDao replyDao = new ReplyDao();
        replyDao.setAlreadyRead(user);
    }

    /**
     * 获取用户已读消息
     * @param user
     * @return
     */
    public List<Reply> getAlreadyReadMsg(User user) {
        ReplyDao replyDao = new ReplyDao();
        return replyDao.getMessage(user,1);
    }

    /**
     * 修改Email
     * @param user
     * @param email
     */
    public void changeEmail(User user,String email) {
        UserDao userDao = new UserDao();

        user.setEmail(email);
        userDao.update(user);
    }

    /**
     * 修改密码
     * @param user
     * @param password
     */
    public void changePassword(User user,String password) {
        UserDao userDao = new UserDao();
        user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));

        userDao.update(user);
    }

    /**
     * 修改头像
     * @param user
     * @param avatar
     */
    public void changeAvatar(User user,String avatar) {
        UserDao userDao = new UserDao();
        user.setAvatar(avatar);

        userDao.update(user);
    }




}
