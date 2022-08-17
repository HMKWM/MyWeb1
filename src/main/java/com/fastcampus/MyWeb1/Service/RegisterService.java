package com.fastcampus.MyWeb1.Service;

import com.fastcampus.MyWeb1.Dao.UserDao;
import com.fastcampus.MyWeb1.Domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    UserDao userDao;

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