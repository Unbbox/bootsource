package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {

    // DAO == TodoRepository
    // service에서 호출하는 메소드 테스트
    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입
    @Test
    public void todoInsert() {

        // IntStream.rangeClosed(1, 10).forEach(i -> {
        // Todo todo = Todo.builder().title("강아지 목욕" +
        // i).completed(false).important(true).build();
        // todoRepository.save(todo);
        // });

        // insert into todotbl (created_date, last_modified_date, title, todo_id)
        // values (?, ?, ?, ?)
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Todo todo = Todo.builder().title("강아지 목욕" + i).build();
            todoRepository.save(todo);
        });
    }

    // todo 전체 목록 조회
    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // todo 완료 목록 조회
    @Test
    public void todoCompletedList() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    // todo 중요 목록 조회
    @Test
    public void todoImportantList() {
        todoRepository.findByImportant(true).forEach(todo -> System.out.println(todo));
    }

    // todo 완료 목록 정렬 조회
    @Test
    public void findByCompletedOrderByIdDesc() {
        todoRepository.findByCompletedOrderByIdDesc(false).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void todoRow() {
        System.out.println(todoRepository.findById(3L));
    }

    // todo 수정
    // 제목 / 완료 / 중요
    @Test
    public void todoUpdate() {
        Todo entity = todoRepository.findById(3L).get();

        entity.setTitle("todo 초기화");
        entity.setCompleted(false);
        entity.setImportant(false);

        todoRepository.save(entity);
    }

    // todo 삭제
    @Test
    public void todoDelete() {
        todoRepository.deleteById(10L);
    }
}
