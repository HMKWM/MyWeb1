package com.fastcampus.MyWeb1.Service;

import com.fastcampus.MyWeb1.Dao.UserDao;
import com.fastcampus.MyWeb1.Domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    UserDao userDao;

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
