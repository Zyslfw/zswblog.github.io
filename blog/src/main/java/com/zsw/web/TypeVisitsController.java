package com.zsw.web;

import com.zsw.po.Type;
import com.zsw.service.BlogService;
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
public class TypeVisitsController {

    private TypeService typeService;
    @Autowired
    private void setTypeService(TypeService typeService){
        this.typeService=typeService;
    }

    private BlogService blogService;
    @Autowired
    private void setBlogService(BlogService blogService){
        this.blogService= blogService;
    }

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, @PathVariable  Long id, Model model){
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
