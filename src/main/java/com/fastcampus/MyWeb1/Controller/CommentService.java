package com.fastcampus.MyWeb1.Controller;

import com.fastcampus.MyWeb1.Dao.BoardDao;
import com.fastcampus.MyWeb1.Dao.CommentDao;
import com.fastcampus.MyWeb1.Domain.CommentDto;
import com.fastcampus.MyWeb1.Domain.SearchCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {
    BoardDao boardDao;
    //    @Autowired
    CommentDao commentDao;

    //    @Autowired
    public CommentService(CommentDao commentDao, BoardDao boardDao){
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    }

    
    public int getCount(Integer bno) throws Exception{
        return commentDao.count(bno);
    }

    
    @Transactional(rollbackFor=Exception.class)
    public int write(CommentDto commentDto) throws Exception{
        int bno =commentDto.getBno();
        if(boardDao.select(bno)==null)
            throw new Exception("no exist board");
        boardDao.updateCommentCnt(bno, 1);
        return commentDao.insert(commentDto);
    }

    
    @Transactional(rollbackFor=Exception.class)
    public int remove(Integer cno, Integer bno, String writer) throws Exception{
        boardDao.updateCommentCnt(bno, -1);
        int rowCnt = commentDao.delete(cno, writer);
//        throw new Exception();
        return rowCnt;
    }

    
    public List<CommentDto> getList(Integer bno, SearchCondition sc) throws Exception {
        return commentDao.selectAll(bno, sc);
    }

    
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }


}
