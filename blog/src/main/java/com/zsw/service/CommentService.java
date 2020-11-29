package com.zsw.service;

import com.zsw.po.Comment;

import java.util.List;

public interface CommentService {
    /*获取列表*/
    List<Comment> listCommentByBlogId(Long blogId);
    /*保存*/
    Comment saveComment(Comment comment);
}
