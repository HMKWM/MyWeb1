package com.fastcampus.MyWeb1.dao;

import com.fastcampus.MyWeb1.domain.BoardDto;
import com.fastcampus.MyWeb1.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    final
    SqlSession session;

    private static String namespace = "com.fastcampus.MyWeb1.dao.BoardMapper.";

    public BoardDao(SqlSession session) {
        this.session = session;
    }

    // CRUD
    public int insert(BoardDto boardDto) throws Exception{
        return session.insert(namespace+"insert", boardDto);
    }

    public BoardDto select(int bno) throws Exception{
        return session.selectOne(namespace+"select", bno);
    }

    public int update(BoardDto boardDto) throws Exception{
        return session.update(namespace+"update", boardDto);
    }

    public int delete(Integer bno, String writer) throws Exception{
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace+"delete", map);
    }

    public int deleteAll() throws Exception{
        return session.delete(namespace+"deleteAll");
    }

    public int increaseViewCnt(Integer bno) throws Exception{
        return session.update(namespace+"increaseViewCnt", bno);
    }

    public int updateCommentCnt(Integer bno, Integer count) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("count", count);

        return session.update(namespace+"updateCommentCnt", map);
    }

    public List<BoardDto> selectPage(SearchCondition sc) throws Exception{
        return session.selectList(namespace+"selectPage", sc);
    }

    public int searchResultCnt(SearchCondition sc) throws Exception{
        return session.selectOne(namespace+"searchResultCnt", sc);
    }


}
