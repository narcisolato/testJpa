package com.example.demo.repository;

import com.example.demo.dto.MemberDto;
import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 네임드쿼리
    List<Member> findByUsername(@Param("username") String username);

    // JPQL로 쿼리 직접 정의
    @Query("select m from Member m where m.id=:id")
    Optional<Member> findById(@Param("id") Long m_id);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 직접 조회. JPA의 new 명령어 사용. 생성자가 있는 DTO가 필요
    @Query("select new com.example.demo.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // in절로 Collection 타입 지원
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    /* 여기가 아닌가보다 
    //엔티티그래프
    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    //메서드 이름으로 쿼리에서 특히 편리하다.
    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsernameEntityGraph(String username);
    */
}
