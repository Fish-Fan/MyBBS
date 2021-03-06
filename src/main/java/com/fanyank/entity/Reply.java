package com.fanyank.entity;

/**
 * Created by yanfeng-mac on 2017/4/6.
 */
public class Reply {
    private Integer id;
    private String content;
    private String replytime;
    private Integer comment_id;
    private Integer user_id;
    private Integer to_user_id;
    private Integer isRead;
    private Integer topic_id;
    private String topic_title;
    private User user;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplytime() {
        return replytime;
    }

    public void setReplytime(String replytime) {
        this.replytime = replytime;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(Integer to_user_id) {
        this.to_user_id = to_user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", replytime='" + replytime + '\'' +
                ", comment_id=" + comment_id +
                ", user_id=" + user_id +
                ", to_user_id=" + to_user_id +
                ", isRead=" + isRead +
                ", topic_id=" + topic_id +
                ", topic_title='" + topic_title + '\'' +
                ", user=" + user +
                '}';
    }
}
