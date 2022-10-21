package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

  private final EntityManager em;
  // DB에서 들고온 다른 오브젝트를 자바 오브젝트로 바꿔줌.

  public void save(Board board) {
    em.persist(board); // insert됨
  }
}
