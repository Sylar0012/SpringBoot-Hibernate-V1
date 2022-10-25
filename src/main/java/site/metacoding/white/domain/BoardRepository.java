package site.metacoding.white.domain;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

  private final EntityManager em;
  // DB에서 들고온 다른 오브젝트를 자바 오브젝트로 바꿔줌.

  public Board save(Board board) {
    em.persist(board); // insert됨
    return board;
  }

  public Board findById(Long id) {
    Board boardPS = em.createQuery("SELECT b FROM Board b WHERE b.id = :id", Board.class)
        .setParameter("id", id)
        .getSingleResult();
    return boardPS;
  }

  public void deleteById(Long id) {
    em.createQuery("delete from Board b where b.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }

  public List<Board> findAll() {
    List<Board> boardPS = em.createQuery("SELECT b FROM Board b", Board.class)
        .getResultList();
    return boardPS;
  }
}
