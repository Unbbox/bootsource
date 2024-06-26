package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

// JUnit : 테스트 라이브러리

@SpringBootTest // 테스트 전용 클래스
public class MemoRepositoryTest {

    @Autowired // MemoRepository memoRepository = new MemoRepositoryImpl()
    private MemoRepository memoRepository;

    @Test // 테스트 메소드(리턴타입은 void)임
    public void insertMemo() {
        // 100 개의 메모 입력

        for (int i = 1; i < 101; i++) {
            Memo memo = new Memo();
            memo.setMemoText("MemoText" + i);

            // save() : insert, update
            memoRepository.save(memo); // == dao.insert()
        }
    }

    @Test
    public void getMemo() {
        // 특정 아이디 메모 조회
        // Optional : null 체크할수 있는 메소드 보유
        Optional<Memo> result = memoRepository.findById(21L);
        System.out.println(result.get());
    }

    @Test
    public void getMemoList() {
        // 전체 메모 조회
        // Optional : null 체크할수 있는 메소드 보유
        List<Memo> result = memoRepository.findAll();
        for (Memo memo : result) {
            System.out.println(memo);
        }
    }

    @Test
    public void updateMemo() {
        // 업데이트 할 entity 찾기
        Optional<Memo> result = memoRepository.findById(25L);
        Memo memo = result.get();

        memo.setMemoText("update Title....");
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void deleteMemo() {
        // 삭제할 entity 찾기
        Optional<Memo> result = memoRepository.findById(24L);
        Memo memo = result.get();

        // 삭제
        memoRepository.delete(memo);
        System.out.println("삭제 memo " + memoRepository.findById(24L));
    }
}
