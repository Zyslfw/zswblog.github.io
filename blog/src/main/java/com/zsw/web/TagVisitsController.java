package com.zsw.web;

import com.zsw.po.Tag;
import com.zsw.po.Type;
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

import java.util.List;

@Controller
public class TagVisitsController {

    private TagService tagService;
    @Autowired
    private void setTagService(TagService tagService){
        this.tagService=tagService;
    }

    private BlogService blogService;
    @Autowired
    private void setBlogService(BlogService blogService){
        this.blogService= blogService;
    }

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, @PathVariable  Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
