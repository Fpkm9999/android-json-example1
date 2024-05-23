package org.example.javaspringbootjpa1.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.example.javaspringbootjpa1.domain.Board;
import org.example.javaspringbootjpa1.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard board = QBoard.board; // Q 도메인 객체
        JPQLQuery<Board> query = from(board); // select ... from board

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(board.title.contains("11")); // title like
        booleanBuilder.or(board.content.contains("11")); // content like

        query.where(booleanBuilder);
        query.where(board.bno.gt(0L));

        // 페이징 적용
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        // 결과 fetch
        List<Board> list = query.fetch();

        // total count fetch
        long count = query.fetchCount(); // 혹은 query.fetch().size();로 대체 가능

        // PageImpl 객체로 반환
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board; // Q 도메인 객체
        JPQLQuery<Board> query = from(board); // select ... from board

        if((types != null && types.length > 0) && keyword != null){ // 검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }// end for
            query.where(booleanBuilder);

        } // end if

        query.where(board.bno.gt(0L));

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        List<Board> list = query.fetch();

        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }
}
