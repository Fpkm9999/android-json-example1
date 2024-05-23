package org.example.javaspringbootjpa1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.javaspringbootjpa1.dto.BoardDTO;
import org.example.javaspringbootjpa1.dto.PageRequestDTO;
import org.example.javaspringbootjpa1.dto.PageResponseDTO;
import org.example.javaspringbootjpa1.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor  // new 를 쓰지않아도 생성자를 자동으로 생성해줌
public class BoardController {
    private final BoardService boardService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {  // model은 view에 작업들어갈떄 넣어줄라고 사용함
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

//    @GetMapping("/register")
//    public void registerGet() {
//
//    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("board POST register...");

        if (bindingResult.hasErrors()) {
            log.info("has errors...");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register"; // 등록실패시 다시 등록화면에 보내는거
        }
        log.info(boardDTO);
        Long bno = boardService.register(boardDTO);
        redirectAttributes.addFlashAttribute("result", bno);
        return "redirect:/board/list"; // 등록성공시 리스트화면으로 보냄
    }

    //    @GetMapping("/read")
//    public void read(
//            BoardDTO boardDTO,
//            PageRequestDTO pageRequestDTO,
//            Model model
//    ) {
//        log.info("read... ");
//
//
//        log.info(boardDTO.getBno());
//
//        BoardDTO resultDTO = boardService.readOne(boardDTO.getBno());
//
//        log.info(resultDTO);
//
//        model.addAttribute("dto", resultDTO);
//    }
    @GetMapping({"/read", "/modify"})
    public void read(BoardDTO boardDTO,
                     PageRequestDTO pageRequestDTO,
                     Model model) {
        log.info("read, modify Get()... ");
        BoardDTO resultDTO = boardService.readOne(boardDTO.getBno());

        log.info(resultDTO);

        model.addAttribute("dto", resultDTO);
    }

    /**
     * 컨트롤러에 Modify 와 POST 매핑된 메서드 작성.
     * 일단 유효성 검사는 안하는 것으로 작성
     * <p>
     * 1) 수정 내용이 반영 되는지
     * 2) 수정이 되면 해당 게시물의 조회 화면으로 이동 되도록
     */

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post Mapping(modify)...");
        log.info(boardDTO);

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", boardDTO.getBno());
        return "redirect:/board/list";

    }


//    @PostMapping("/modify")
//    public String modify(
//            PageRequestDTO pageRequestDTO,
//            @Valid BoardDTO boardDTO,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//        log.info("board modify post.....{}", boardDTO);
//
//        if (bindingResult.hasErrors()) {
//            log.info("has errors...");
//
//            String link = pageRequestDTO.getLink();
//            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//            redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());
//            return "redirect:/board/modify" + link;
//        }
//
//        boardService.modify(boardDTO);
//
//        redirectAttributes.addFlashAttribute("result", "modified");
//
//        redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());
//
//        return "redirect:/board/read";
//
//        /*
//        modify() 에는 문제가 발생시 'errors'라는 이름으로 다시 수정 페이지로 수동할 수있도록 구성
//         */
//    }
    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("remove() ...");
        log.info("bno: {}", bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/board/list";
    }


}