package com.fastcampus.MyWeb1.controller;

import com.fastcampus.MyWeb1.domain.BoardDto;
import com.fastcampus.MyWeb1.domain.PageHandler;
import com.fastcampus.MyWeb1.domain.SearchCondition;
import com.fastcampus.MyWeb1.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    final
    BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String list(SearchCondition sc , Model m){

        Calendar today = Calendar.getInstance();
        today.set(Calendar.MILLISECOND, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.HOUR, 0);

        try {
            List<BoardDto> list = boardService.getList(sc);
            int totalCnt = boardService.getCount(sc);;
            PageHandler ph = new PageHandler(totalCnt, sc);

            m.addAttribute("ph", ph);
            m.addAttribute("list", list);
            m.addAttribute("today", today.getTimeInMillis());
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("msg", "오류 발생");
        }
        return "boardList";
    }

    @GetMapping("/{bno}")
    public String board(@PathVariable Integer bno, SearchCondition sc, String mode, Model m){
        try{
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute("boardDto", boardDto);
            m.addAttribute("sc",sc);
            if("mod".equals(mode))
                m.addAttribute("mode",mode);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "board";
    }

    @GetMapping("/write")
    public String write(Model m, SearchCondition sc){
        m.addAttribute("mode", "new");
        m.addAttribute("sc", sc);
        return "board";
    }

    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity write(@RequestBody BoardDto boardDto, HttpSession session){
        Map map = new HashMap();
        // 나중에 검증으로 처리할 것
        if(boardDto.getTitle().length() < 5 || boardDto.getContent().length() < 5){
            map.put("result","invalid length");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        
        String id = (String) session.getAttribute("id");

        try{
            if(id==null||!id.equals(boardDto.getWriter()))
                throw new Exception("id mismatch");
           boardService.write(boardDto);
            map.put("result", "OK");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            map.put("result","id mismatch");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @DeleteMapping("/{bno}")
    public ResponseEntity<Map> remove(@RequestBody @PathVariable Integer bno, HttpSession session){
        Map map = new HashMap();
        String id = (String)session.getAttribute("id");

        if(bno == null){
            map.put("result", "Wrong Access");
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

        try{
            boardService.remove(bno, id);
            map.put("result", "OK");
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            map.put("result", "delete fail");
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @ResponseBody
    @PutMapping("/{bno}")
    public ResponseEntity modify(@RequestBody BoardDto boardDto,@PathVariable Integer bno, HttpSession session){
        Map map = new HashMap();
        boardDto.setBno(bno);
        String id = (String)session.getAttribute("id");

        if(id==null || !id.equals(boardDto.getWriter())){
            map.put("result", "id mismatch");
            return new ResponseEntity(map, HttpStatus.OK);
        }

        try{
            boardService.modify(boardDto);
            map.put("result", "OK");
            return new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            map.put("result", "modify fail");
            return new ResponseEntity(map, HttpStatus.OK);
        }
    }
}
