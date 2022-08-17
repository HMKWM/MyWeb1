package com.fastcampus.MyWeb1.Controller;

import com.fastcampus.MyWeb1.Domain.BoardDto;
import com.fastcampus.MyWeb1.Domain.PageHandler;
import com.fastcampus.MyWeb1.Domain.SearchCondition;
import com.fastcampus.MyWeb1.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("")
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

    @PostMapping("/write")
    public String write(BoardDto boardDto, SearchCondition sc, HttpSession session, Model m, RedirectAttributes rattr){
        String id = (String) session.getAttribute("id");

        try{
            if(!id.equals(boardDto.getWriter()))
                throw new Exception("id mismatch");
           boardService.write(boardDto);
        } catch (Exception e){
            e.printStackTrace();
            boardDto.setWriter(id);
            rattr.addFlashAttribute("boardDto",boardDto);
            rattr.addFlashAttribute("msg","WRT_ERR");
            return "redirect:/board/write";
        }

        rattr.addFlashAttribute("msg", "WRT_OK");

        return "redirect:/board";
    }

    @PostMapping("/remove")
    public String remove(BoardDto boardDto, SearchCondition sc, RedirectAttributes rattr, Model m, HttpSession session){
        String id = (String)session.getAttribute("id");
        Integer bno = boardDto.getBno();

        try{
            boardService.remove(bno, id);
        } catch (Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg","DEL_ERR");
            return "redirect:/board/"+bno;
        }

        rattr.addFlashAttribute("msg","DEL_OK");
        return "redirect:/board"+sc.getQueryString();
    }
    @GetMapping("/modify")
    public String modify(){
        return "redirect:/board";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, SearchCondition sc, HttpSession session, RedirectAttributes rattr){
        // 1. boardDto의 writer값과 session값을 비교한다. 다르면 예외
        // 2. 원래 있는지 확인한다.
        // 3. 있으면 수정한다.
        String id = (String)session.getAttribute("id");
        boardDto.setWriter(id);
        try{
            if(boardService.read(boardDto.getBno())==null)
                throw new RuntimeException("board no exists");

            boardService.modify(boardDto);
        } catch (RuntimeException e){
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "EXIST_ERR");
            return "redirect:/board"+sc.getQueryString();
        } catch (Exception e){
            e.printStackTrace();
            rattr.addFlashAttribute("boardDto", boardDto);
            rattr.addFlashAttribute("msg", "MOD_ERR");
            return "redirect:/board/modify"+sc.getQueryString();
        }

        rattr.addFlashAttribute("msg","MOD_OK");
        return "redirect:/board/"+ boardDto.getBno()+sc.getQueryString();
    }

}
