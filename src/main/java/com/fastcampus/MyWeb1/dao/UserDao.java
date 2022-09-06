package com.fastcampus.MyWeb1.dao;

import com.fastcampus.MyWeb1.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    final
    SqlSession session;

    private static String namespace = "com.fastcampus.MyWeb1.dao.userMapper.";

    public UserDao(SqlSession session) {
        this.session = session;
    }

    public UserDto select(String id) throws Exception{
        return session.selectOne(namespace+"select", id);
    }


    public int insertUser(UserDto user) {
        return session.insert(namespace+"insert", user);
    }

    // 매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드

    public int updateUser(UserDto user) {
        return session.update(namespace+"update", user);
    }

    public int deleteAll(String id) throws Exception {
        return session.delete(namespace+"deleteAll", id);
    }

    public int deleteUser(String id) {
        return session.delete(namespace+"delete",id);
    }
}
