package com.fastcampus.MyWeb1.service;

import com.fastcampus.MyWeb1.dao.UserDao;
import com.fastcampus.MyWeb1.domain.UserDto;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    final
    UserDao userDao;

    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean login(String id, String pwd) throws Exception {
        // 1. 아이디가 있는지 체크
        UserDto userDto = userDao.select(id);

        if(userDto.getId() == null || "".equals(userDto)){
            return false;
        }

        if(userDto.getPwd().equals(pwd))
            return true;

        return false;
    }
}
