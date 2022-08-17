package com.fastcampus.MyWeb1.Service;

import com.fastcampus.MyWeb1.Dao.BoardDao;
import com.fastcampus.MyWeb1.Domain.BoardDto;
import com.fastcampus.MyWeb1.Domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDao boardDao;

    // 게시글 CRUD
    // 권한이 필요한거 : UD
    // D는 댓글도 지우는 작업 필요
    public int write(BoardDto boardDto) throws Exception{
        return boardDao.insert(boardDto);
    }

    public BoardDto read(Integer bno) throws Exception{
        BoardDto boardDto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);
        return boardDto;
    }

    public int modify(BoardDto boardDto) throws Exception{
        return boardDao.update(boardDto);
    }

    public int remove(Integer bno, String writer) throws Exception{
        int rowCnt = boardDao.delete(bno, writer);
        // 추가로 댓글도 지우기
        return rowCnt;
    }

    // 게시글 리스트 불러오기 + 페이징에 필요한거
    public int getCount(SearchCondition sc) throws Exception{
        return boardDao.searchResultCnt(sc);
    }

    public List<BoardDto> getList(SearchCondition sc) throws Exception{
        return boardDao.selectPage(sc);
    }
}
