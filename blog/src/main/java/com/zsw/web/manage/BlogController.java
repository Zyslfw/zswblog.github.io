package com.zsw.web.manage;

import com.zsw.po.Blog;
import com.zsw.po.User;
import com.zsw.service.BlogService;
import com.zsw.service.TagService;
import com.zsw.service.TypeService;
import com.zsw.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/manage")
public class BlogController {

    private static  String INPUT ="manage/blogs-input";
    private static  String List ="manage/blogs";
    private static  String REDIRECT_LIST ="redirect:/manage/blogs";


    //注入
    private BlogService blogService;
    @Autowired
    private void setBlogService(BlogService blogService){
        this.blogService = blogService;
    }

    private TypeService typeService;
    @Autowired
    private void setTypeService(TypeService typeService){
        this.typeService = typeService;
    }

    private TagService tagService;
    @Autowired
    private void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping("/blogs")//查询到page对象放到model模型
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("types",typeService.listType());
        return List;
    }


    @PostMapping("/blogs/search")//局部刷新
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                     Pageable pageable,BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "manage/blogs :: blogList";
    }

    /*新增*/
    @GetMapping("/blogs/input")
    public String input(Model model){
        //标签分类博客初始化
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }
    /*修改*/
    @GetMapping("/blogs/{id}/input")
    public String editinput(@PathVariable Long id, Model model){
        //标签分类博客初始化
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();//初始化tagIds
        model.addAttribute("blog",blogService.getBlog(id));
        return INPUT;
    }

    /*发布*/
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){//设置user对象
        blog.setUser((User) session.getAttribute("user"));//获取当前的登录用户
        blog.setType(typeService.getType(blog.getType().getId()));//对象属性初始化
        blog.setTags(tagService.listTag(blog.getTagIds()));//根据idlist获取到一组tag对象

        Blog b = blogService.saveBlog(blog);
        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        }
        else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;//返回到列表页面

    }
    /*删除*/
    @GetMapping("blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/manage/blogs";
    }


}
