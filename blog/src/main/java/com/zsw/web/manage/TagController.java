package com.zsw.web.manage;


import com.zsw.po.Tag;
import com.zsw.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manage")
public class TagController {
    private TagService tagService;
    @Autowired
    private void setTagService(TagService tagService){
        this.tagService = tagService;
    }


    /*分页查询*/
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable,
                                Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "manage/tags";
    }

    /*新增*/
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "manage/tags-input";

    }
    /*提交新增*/
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.saveTag(tag);
        if (tag1 == null){
            //没有保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/manage/tags";
    }

    /*删除*/
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/manage/tags";
    }
}
