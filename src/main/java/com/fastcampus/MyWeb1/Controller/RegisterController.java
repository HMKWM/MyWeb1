package com.fastcampus.MyWeb1.Controller;

import com.fastcampus.MyWeb1.Domain.UserDto;
import com.fastcampus.MyWeb1.Service.RegisterService;
import com.fastcampus.MyWeb1.Validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @InitBinder
    public void toDate(WebDataBinder binder){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
        binder.addValidators(new UserDtoValidator());

    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String save(@Valid UserDto userDto, BindingResult result, Model m){
        System.out.println("result = " + result);
        if(result.hasErrors()){
            return "register";
        }

        try{
            registerService.register(userDto);
        } catch(Exception e){
            m.addAttribute("msg", "이미 존재하는 ID입니다.");
            return "register";
        }

        m.addAttribute("msg", "가입에 성공하셨습니다.");
        return "registerInfo";
    }
}
