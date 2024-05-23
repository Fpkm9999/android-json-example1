package org.example.javaspringbootjpa1.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.javaspringbootjpa1.domain.Board;
import org.example.javaspringbootjpa1.dto.BoardDTO;
import org.example.javaspringbootjpa1.dto.PageRequestDTO;
import org.example.javaspringbootjpa1.dto.PageResponseDTO;
import org.example.javaspringbootjpa1.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    // 등록
    @Override
    public Long register(BoardDTO boardDTO) {
        Board board = modelMapper.map(boardDTO, Board.class);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) { // 조회 페이지 눌럿을떄 나오는거
        Optional<Board> optionalBoard = boardRepository.findById(bno); // 밑에 코드는 데이터데이스 차이
        Board board = optionalBoard.orElseThrow();
        return modelMapper.map(board, BoardDTO.class);
    }

    @Override
    public void modify(BoardDTO boardDTO) { // 수정 페이지
        Optional<Board> optionalBoard = boardRepository.findById(boardDTO.getBno());
        Board board = optionalBoard.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getWriter());

        boardRepository.save(board);


    }

    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> boardPage = boardRepository.searchAll(types, keyword, pageable);
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : boardPage.getContent()) {
            boardDTOList.add(modelMapper.map(board, BoardDTO.class));
        }
        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total((int) boardPage.getTotalElements())
                .build();
    }
}