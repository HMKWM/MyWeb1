package com.fastcampus.MyWeb1.Controller;

import com.fastcampus.MyWeb1.Domain.CommentDto;
import com.fastcampus.MyWeb1.Domain.PageHandler;
import com.fastcampus.MyWeb1.Domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private Integer pageSize = 20;

    @Autowired
    CommentService commentService;

    @GetMapping("/{commentPage}")
    public ResponseEntity<List<CommentDto>> list(@PathVariable Integer commentPage, Integer bno){
        //sc에서 받아야 하는것 : page, pageSize
        List<CommentDto> list = null;
        SearchCondition sc = new SearchCondition();

        try {
            int totalCnt = commentService.getCount(bno);

            if(commentPage == 0){ //마지막 페이지 totalPage 값임
                commentPage = (int)Math.ceil(totalCnt/(double)pageSize);
            }

            sc.setPage(commentPage);
            sc.setPageSize(20);

            list = commentService.getList(bno,sc);

            return new ResponseEntity(list, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(list,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")// /comments
    public ResponseEntity<String> write(@RequestBody CommentDto commentDto, HttpSession session){
        String id = (String)session.getAttribute("id");
        commentDto.setCommenter(id);
        try{
            int rowCnt = commentService.write(commentDto);
            if(rowCnt==0)
                throw new Exception("write error");

            return new ResponseEntity<String>("WRT_OK", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{cno}")
    public ResponseEntity<String> delete(@RequestBody CommentDto commentDto, @PathVariable Integer cno, HttpSession session){
        String id = (String)session.getAttribute("id");
        commentDto.setCommenter(id);
        commentDto.setCno(cno);

        try{
            int rowCnt = commentService.remove(commentDto.getCno(),commentDto.getBno(), commentDto.getCommenter());
            if(rowCnt!=1){
                throw new Exception();
            }

            return new ResponseEntity<String>("DEL_OK",HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("DEL_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{cno}")
    public ResponseEntity<String> update(@RequestBody CommentDto commentDto, @PathVariable Integer cno, HttpSession session){
        //comment cno commenter
        String id = (String)session.getAttribute("id");
        commentDto.setCommenter(id);
        commentDto.setCno(cno);

        try{
            int rowCnt = commentService.modify(commentDto);
            if(rowCnt!=1){
                throw new Exception();
            }

            return new ResponseEntity<String>("DEL_OK",HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>("DEL_ERR",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paging/{commentPage}")
    public ResponseEntity<PageHandler> paging(Integer bno, @PathVariable Integer commentPage){
        // 필요한 것: bno commentPage 이 뒤로는 있어도 없어도 그만 commentPageSize
        PageHandler ph = null;

        try {
            int totalCnt = commentService.getCount(bno);
            if(commentPage == 0){ //마지막 페이지 totalPage 값임
                commentPage = (int)Math.ceil(totalCnt/(double)pageSize);
            }
            SearchCondition sc = new SearchCondition(commentPage, pageSize);
            ph = new PageHandler(totalCnt, sc);

            return new ResponseEntity<PageHandler>(ph,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<PageHandler>(ph,HttpStatus.BAD_REQUEST);
        }

    }
}
