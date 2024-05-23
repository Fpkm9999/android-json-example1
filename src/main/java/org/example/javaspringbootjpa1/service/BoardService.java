package org.example.javaspringbootjpa1.service;

import org.example.javaspringbootjpa1.dto.BoardDTO;
import org.example.javaspringbootjpa1.dto.PageRequestDTO;
import org.example.javaspringbootjpa1.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO );
}
