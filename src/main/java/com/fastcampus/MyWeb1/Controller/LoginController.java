package com.fastcampus.MyWeb1.Controller;

import com.fastcampus.MyWeb1.Domain.UserDto;
import com.fastcampus.MyWeb1.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. 세션을 종료
        session.invalidate();
        // 2. 홈으로 이동
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(String toURL, HttpServletRequest request,HttpSession session, Model m){
        if(session.getAttribute("id")!=null){
            return "redirect:/";
        }
        toURL = "".equals(toURL) ? request.getServletPath() : toURL;
        m.addAttribute("toURL", toURL);
        return "loginForm";
    }

//    @GetMapping("/board")
//    public String loginPage2(HttpServletRequest request, RedirectAttributes rattr){
//        rattr.addAttribute("toURL", request.getServletPath());
//        return "redirect:/login";
//    }

    @PostMapping("/login")
    public String login(UserDto userDto, String toURL, HttpServletRequest request,HttpServletResponse response, boolean rememberId, HttpSession session, RedirectAttributes rattr){
        // 0. url로 로그인 되있는데 로그인하기 => index로 이동

        // 1. 로그인 하기
        try {
            if(!loginService.login(userDto.getId(), userDto.getPwd())){
                rattr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
                return "redirect:/login";
            }
        } catch (Exception e){
            rattr.addFlashAttribute("msg", "오류가 발생했습니다. 잠시 후에 시도해주시길 바랍니다.");
            return "redirect:/login";
        }

        // 2. 성공 했으면 rememberId 유무에 따라 쿠키 생성 제거 하기
        if(rememberId){
            Cookie cookie = new Cookie("rememberId", userDto.getId());
            cookie.setMaxAge(60*30);
            response.addCookie(cookie);
        } else{
            Cookie cookie = new Cookie("rememberId", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        // 3. 세션에 아이디 생성
        session.setAttribute("id", userDto.getId());


        // 3. 접속 하려던 페이지로 보내주기

        toURL = toURL==null || toURL.equals("") ? "/" : toURL;

        return "redirect:"+toURL;
    }

}
