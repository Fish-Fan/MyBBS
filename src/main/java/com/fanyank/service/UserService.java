package com.fanyank.service;

import com.fanyank.dao.ForgetPasswordDao;
import com.fanyank.dao.ReplyDao;
import com.fanyank.dao.UserDao;
import com.fanyank.entity.ForgetPassword;
import com.fanyank.entity.Reply;
import com.fanyank.entity.User;
import com.fanyank.util.ConfigProp;
import com.fanyank.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.UUID;

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

    /**
     * 向用户发送找回密码的邮件
     * @param username
     */
    public void forgetPassword(String username) {
        UserDao userDao = new UserDao();
        ForgetPasswordDao forgetPasswordDao = new ForgetPasswordDao();
        User user = userDao.findByUsername(username);

        if(user != null) {
            String uuid = UUID.randomUUID().toString();
            String email = user.getEmail();
            String title = "来自樊主席的找回密码邮件";
            String url = "http://localhost:8080/forget/callback.do?token="+uuid;
            String msg = user.getUsername() + ":<br>\n" +
                    "点击该<a href='"+url+"'>链接</a>进行设置新密码，该链接30分钟内有效。<br>\n" +
                    url;

            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            forgetPassword.setToken(uuid);
            forgetPassword.setUid(user.getId());

            forgetPasswordDao.save(forgetPassword);
            EmailUtil.sendHtmlEmail(title,msg,user.getEmail());
        }
    }

    /**
     * 验证用户邮箱中的链接是否失效
     * @param token
     * @param password
     * @return
     */
    public Integer validateCallbackToken(String token) {
        ForgetPasswordDao forgetPasswordDao = new ForgetPasswordDao();
        UserDao userDao = new UserDao();
        ForgetPassword fp = forgetPasswordDao.findByToken(token);

        if(fp != null) {
            String createTime = fp.getCreatetime();

            DateTimeFormatter formattert = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime dateTime = formattert.parseDateTime(createTime);
            dateTime = dateTime.plusMinutes(30);

            if(dateTime.isAfterNow()) {
                //链接有效
                return fp.getUid();
            }

        }

        return null;
    }

    /**
     * 验证邮箱通过后设置新密码
     * @param password
     * @param token
     */
    public void forgetPasswordSetNew(String password,String token) {
        ForgetPasswordDao forgetPasswordDao = new ForgetPasswordDao();
        UserDao userDao = new UserDao();
        ForgetPassword fp = forgetPasswordDao.findByToken(token);

        if(fp != null) {
            Integer user_id = fp.getUid();
            User user = userDao.findById(user_id);
            user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));

            userDao.update(user);
            forgetPasswordDao.deleteByUid(user_id);
        }
    }




}
