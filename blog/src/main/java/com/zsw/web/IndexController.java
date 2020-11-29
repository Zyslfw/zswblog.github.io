package com.zsw.web;

import com.zsw.service.BlogService;
import com.zsw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    private BlogService blogService;
    @Autowired
    private void setBlogService(BlogService blogService){
        this.blogService=blogService;
    }

    private TypeService typeService;
    @Autowired
    private void setTypeService(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping("/index")
    public String index(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable,
                       Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("type",typeService.listTypeTop(4));//显示4条type
        model.addAttribute("newBlogs",blogService.listNewBlogTop(1));
        return "index";

    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                 Pageable pageable, @RequestParam String query , Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%", pageable));//select t-blog like"%%"
        model.addAttribute("query",query);//查询的内容返回到页面
        return "search";

    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    }


