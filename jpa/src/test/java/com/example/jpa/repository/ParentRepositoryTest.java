package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 부모 한 명에 자식 2명
        // Parent p = Parent.builder().name("parent1").build();

        // Child c1 = Child.builder().name("child1").parent(p).build();
        // p.getChildList().add(c1);
        // Child c2 = Child.builder().name("child2").parent(p).build();
        // p.getChildList().add(c2);

        // parentRepository.save(p);
        // childRepository.save(c1);
        // childRepository.save(c2);
    }

    @Test
    public void insertCascadeTest() {
        // 부모 한 명에 자식 2명
        Parent p = Parent.builder().name("parent3").build();

        Child c1 = Child.builder().name("child1").parent(p).build();
        p.getChildList().add(c1);
        Child c2 = Child.builder().name("child2").parent(p).build();
        p.getChildList().add(c2);

        parentRepository.save(p);
        // childRepository.save(c1);
        // childRepository.save(c2);
    }

    @Test
    public void deleteTest() {
        // 부모가 자식을 가지고 있는경우 삭제 시 자식의 부모 아이디 변경 후 부모 삭제
        Parent p = Parent.builder().id(1L).build();

        // 부모를 null로 지정
        // Child c1 = Child.builder().id(1L).parent(null).build();
        // Child c2 = Child.builder().id(2L).parent(null).build();

        Child c1 = Child.builder().id(1L).build();
        childRepository.delete(c1);
        Child c2 = Child.builder().id(2L).build();
        childRepository.delete(c2);

        parentRepository.delete(p);
    }

    @Test
    public void deleteCascadeTest() {
        // 부모가 자식을 가지고 있는경우 삭제 시 자식의 부모 아이디 변경 후 부모 삭제
        Parent p = Parent.builder().id(52L).build();

        Child c1 = Child.builder().id(102L).build();
        p.getChildList().add(c1);
        Child c2 = Child.builder().id(103L).build();
        p.getChildList().add(c2);

        parentRepository.delete(p);
    }

    @Transactional
    @Test
    public void deleteOrphanTest() {
        // 기존 자식 여부 확인
        Parent p = parentRepository.findById(52L).get();
        // FetchType 이 LAZY인 경우 오류발생
        System.out.println("기존 자식 " + p.getChildList());

        p.getChildList().remove(0); // 인덱스 제거 => childList에서 제거(고아객체) => 엔티티 제거
        parentRepository.save(p);
    }
}
