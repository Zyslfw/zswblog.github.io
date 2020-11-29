package com.zsw.web.manage;

import com.zsw.po.User;
import com.zsw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage")//访问manage下的方法就会跳转到login页面
public class LoginController {
    //注入
    private UserService userService;
    @Autowired
    private void setUserService(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public String loginPage() {
        return "manage/login";
    }
    @PostMapping("/login")//用户名和密码post到login
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){//@RequestParam来接收参数
        User user =userService.checkUser(username,password);
        if (user != null){
            user.setPassword(null);//不要把密码传过去
            session.setAttribute("user",user);
            return "manage/login-index";

        }else {
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/manage";//重定向到login
        }
    }
/*注销*/
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/manage";//重定向到login

    }

}
