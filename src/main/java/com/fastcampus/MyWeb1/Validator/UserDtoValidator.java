package com.fastcampus.MyWeb1.Validator;

import com.fastcampus.MyWeb1.Domain.UserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class UserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
//		return User.class.equals(clazz); // 검증하려는 객체가 User타입인지 확인
        return UserDto.class.isAssignableFrom(clazz); // clazz가 User 또는 그 자손인지 확인
    }

    @Override
    public void validate(Object target, Errors errors) {

        System.out.println("UserValidator.validate() is called");

        UserDto user = (UserDto)target;
        String id = user.getId();

        if(checkNull(user, errors)){
            return;
        }

//        if (id.length() <  5 || id.length() > 12){
//            errors.rejectValue("id", "invalidLength", new String[]{"5","12"}, null);
//            return;
//        }

        if(!isPattern("^[a-zA-Z0-9]{5,12}$",id)){ // 아이디 검증
            errors.rejectValue("id", "invalidLength", new String[]{"5","12"}, null);
            return;
        }

        if(!isPattern("^[a-zA-Z0-9]{5,12}$",user.getPwd())){
            errors.rejectValue("pwd", "invalidLength", new String[]{"5","12"}, null);
            return;
        }
    }

    public boolean checkNull(UserDto user, Errors errors){
        if(user.getId()==null){
            errors.rejectValue("id", "null");
            return true;
        }
        else if(user.getPwd()==null || "".equals(user.getPwd())){
            errors.rejectValue("pwd", "null");
            return true;
        }
        else if(user.getName()==null || "".equals(user.getName())){
            errors.rejectValue("name", "null");
            return true;
        }
        else if(user.getBirth()==null || "".equals(user.getBirth())){
            errors.rejectValue("birth", "null");
            return true;
        }
        return false;
    }

    public boolean isPattern(String pattern,String str){
        return Pattern.matches(pattern,str);
    }
}