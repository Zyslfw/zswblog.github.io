package com.zsw.web.manage;

import com.zsw.po.Type;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manage")
public class TypeController {

    private TypeService typeService;
    @Autowired
    private void setTypeService(TypeService typeService){
        this.typeService = typeService;
    }

    /*分页查询*/
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                     Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "manage/types";
    }
    /*新增*/
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("types",new Type());
        return "manage/types-input";

    }
/*提交新增*/
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type type1 = typeService.saveType(type);
        if (type1 == null){
            //没有保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/manage/types";
    }

/*    *//*提交编辑*//*
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "/manage/types-input";
    }
*//*编辑*//*
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id ,RedirectAttributes attributes){
        Type type1 = typeService.updateType(id,type);
        if (type1 == null){
            //没有保存成功
            attributes.addFlashAttribute("message","编辑失败");
        }else {
            //保存成功
            attributes.addFlashAttribute("message","编辑成功");
        }
        return "redirect:/manage/types";
    }*/

    /*删除*/
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/manage/types";
    }
}
