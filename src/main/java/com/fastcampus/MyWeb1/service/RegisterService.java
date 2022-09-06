package com.fastcampus.MyWeb1.service;

import com.fastcampus.MyWeb1.dao.UserDao;
import com.fastcampus.MyWeb1.domain.UserDto;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    final
    UserDao userDao;

    public RegisterService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean checkId(String id) throws Exception {
        return userDao.select(id) == null; // 없으면 true 있으면 false
    }

    public int register(UserDto user) throws Exception{
        try{
            if(!checkId(user.getId())){
                throw new Exception("Id already exists");
            }
        } catch (Exception e){
            throw e;
        }
        return userDao.insertUser(user);
    }
}