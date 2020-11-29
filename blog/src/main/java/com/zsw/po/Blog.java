package com.zsw.po;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "t_blog")
@Table
public class Blog {
    @Id//主键
    @GeneratedValue//自动生成
    private Long id;
    private String title;
    private String content;
    private String description;
    private String firstPicture;
    private String flag;//开关
    private Integer views;//浏览次数
    private boolean appreciate;//赞赏
    private boolean shareStatement;//转载声明
    private boolean commentable;//评论开启
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Transient
    private String tagIds;

    @ManyToOne
    private Type type;//blog对于type是多对一的关系

    @ManyToMany(cascade = {CascadeType.PERSIST})//级联存储
    private List<Tag>  tags = new ArrayList<>();//blog对于tag是多对多

    @ManyToOne
    private User user;//多个blog对应一个用户

    @OneToMany(mappedBy = "blog")//comment作为维护端
    private List<Comment> comments = new ArrayList<>();

    public Blog() {
    }

/*set get方法*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPic() {
        return firstPicture;
    }

    public void setFirstPic(String firstPic) {
        this.firstPicture = firstPic;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciate() {
        return appreciate;
    }

    public void setAppreciate(boolean appreciate) {
        this.appreciate = appreciate;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    /*type:Type set get*/
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
/*List<Tag> set get*/
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
/*User:user set get*/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
/*List<Comment> set get*/
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /*TagIds set get*/

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

/*tagid初始化*/
    public void init(){
        this.tagIds = tagsToIds(this.getTags());

    }

    private String tagsToIds(List<Tag> tags){//数组转化成字符串
        if (!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags){
                if (flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return  ids.toString();
        }else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPic='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciate=" + appreciate +
                ", shareStatement=" + shareStatement +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
