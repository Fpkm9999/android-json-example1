package org.example.javaspringbootjpa1.repository;

import lombok.extern.log4j.Log4j2;
import org.example.javaspringbootjpa1.domain.OneBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Log4j2
class OneRepositoryTest {
    @Autowired
    private OneRepository oneRepository;

    @Test
    public void testInsert() {
        OneBoard oneBoard = OneBoard.builder()
                .email("fpkm9999@gmnail.com").name("akaps11").build();
        oneRepository.save(oneBoard);
        log.info(oneBoard);

    }

    @Test
    public void testingByEmail() {
        OneBoard saveBoard = oneRepository.findByEmail("fpkm9999@gmnail.com");
        log.info("결과 : " + saveBoard);
    }

    @Test
    public void testingById() {
        Optional<OneBoard> oneBoard = oneRepository.findById(1L);
//        assertThat(oneBoard).isPresent();
//        assertThat(oneBoard.get().getEmail()).isEqualTo("fpkm9999@gmnail.com");

        log.info(oneBoard.get());
    }

    @Test
    @Transactional
    public void testUpdate() {
        OneBoard oneBoard = OneBoard.builder().id(1L).email("akaps999@gmail.com").build();

        oneRepository.save(oneBoard);
        log.info(oneBoard);

        // 결과 : 반영 안됨
        /*
        업데이트가 제대로 반영되지 않는 이유는 엔티티를 수정하는 방식 때문입니다. save() 메소드를 호출할 때,
        새로 생성된 엔티티가 기존 엔티티와 일치하지 않아서 문제가 발생할 수 있습니다.
        엔티티를 업데이트하려면 먼저 데이터베이스에서 해당 엔티티를 조회한 후, 필요한 필드만 업데이트해야 합니다.
         */
    }

    @Test
    @Transactional
    public void testUpdate2() {
        // 먼저 엔티티를 조회합니다.
        Optional<OneBoard> optionalOneBoard = oneRepository.findById(1L);
        assertThat(optionalOneBoard).isPresent();

        // 엔티티의 필드를 수정합니다.
        OneBoard oneBoard = optionalOneBoard.get();
        oneBoard.setEmail("updated_email@gmail.com");
        oneRepository.save(oneBoard);

        // 업데이트가 제대로 이루어졌는지 확인합니다.
        OneBoard updatedBoard = oneRepository.findById(1L).get();
//        assertThat(updatedBoard.getEmail()).isEqualTo("updated_email@gmail.com");
        log.info(updatedBoard);
    }






//    @Test
//    public void testDelete() {
//        oneRepository.deleteById(1L);
//        Optional<OneBoard> optionalOneBoard = oneRepository.findById(1L);
//        assertThat(optionalOneBoard).isNotPresent();
//    }
//
//    @Test
//    public void testFindByName() {
//        List<OneBoard> boards = oneRepository.findByName("akaps11");
//        assertThat(boards).isNotEmpty();
//        boards.forEach(board -> log.info(board));
//    }
//
//    @Test
//    public void testFindAll() {
//        List<OneBoard> boards = oneRepository.findAll();
//        assertThat(boards).isNotEmpty();
//        boards.forEach(board -> log.info(board));
//    }


}