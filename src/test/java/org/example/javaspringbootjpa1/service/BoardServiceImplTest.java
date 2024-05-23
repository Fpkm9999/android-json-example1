package org.example.javaspringbootjpa1.service;

import lombok.extern.log4j.Log4j2;
import org.example.javaspringbootjpa1.dto.BoardDTO;
import org.example.javaspringbootjpa1.dto.PageRequestDTO;
import org.example.javaspringbootjpa1.dto.PageResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Log4j2
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    void register() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("테스트")
                .content("내용")
                .writer("작성자")
                .build();
        Long bno = boardService.register(boardDTO);
        log.info(bno);

    }

    @Test
    void readOne() {
        Long bno = 302L;
        BoardDTO result = boardService.readOne(bno);
        log.info(result);

    }

    @Test
    public void testModify() {
        // 변경이 필요한 데이터만
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(1008L)
                .title("update...")
                .content("Akaps_Content2")
                .writer("Akaps_Writer1")
                .build();
        boardService.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 102L;
        boardService.remove(bno);
    }

    @Test
    public void testList() {
        /*
        페이지번호
        전체계시물수
        현재페이지에서 출력될 게시물을 반복문을 이용해서 순서대러 출력
         */
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).type("t").keyword("..").build();
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);
        log.info(pageResponseDTO);
        log.info("페이지 번호: " + pageResponseDTO.getPage());
        log.info("전체 계시물수: " + pageResponseDTO.getTotal());
        for (BoardDTO boardDTO : pageResponseDTO.getDtoList()) {
            log.info(boardDTO);
        }

    }


}