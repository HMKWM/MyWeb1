package com.fastcampus.MyWeb1.dao;

import com.fastcampus.MyWeb1.domain.CommentDto;
import com.fastcampus.MyWeb1.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {
    private final SqlSession session;
    private static String namespace = "com.fastcampus.MyWeb1.dao.CommentMapper.";

    public CommentDao(SqlSession session) {
        this.session = session;
    }


    public int count(int bno) throws Exception{
        return session.selectOne(namespace+"count", bno);
    }

    
    public CommentDto select(int cno) throws Exception{
        return session.selectOne(namespace+"select", cno);
    }

    
    public List<CommentDto> selectAll(int bno, SearchCondition sc) throws Exception{
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("offset", sc.getOffset());
        map.put("pageSize",sc.getPageSize());
        return session.selectList(namespace+"selectAll", map);
    }

    
    public int insert(CommentDto commentDto) throws Exception{
        return session.insert(namespace+"insert", commentDto);
    }

    
    public int update(CommentDto commentDto) throws Exception{
        return session.update(namespace+"update", commentDto);
    }

    
    public int delete(Integer cno, String commenter) throws Exception{
        Map map = new HashMap();
        map.put("cno",cno);
        map.put("commenter", commenter);
        return session.delete(namespace+"delete", map);
    }

    
    public int deleteAll(int bno) throws Exception{
        return session.delete(namespace+"deleteAll", bno);
    }
}
