package com.zsw.web;

import com.zsw.po.Comment;
import com.zsw.po.User;
import com.zsw.service.BlogService;
import com.zsw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class CommentController {
    private BlogService blogService;
    @Autowired
    private void setBlogService(BlogService blogService){
        this.blogService=blogService;
    }
    private CommentService commentService;
    @Autowired
    private void setCommentService(CommentService commentService){
        this.commentService = commentService;
    }
    @Value("${@{https://picsum.photos/id/1003/100/100}}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User)session.getAttribute("user");//博主
        if (user != null){//如果管理员登录
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);//否则为普通访客头像
            /*comment.setNickname(user.getNickname());*/
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
