package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // IOC 등록
public class CommentRepository {

  private final EntityManager em;

  // 댓글 등록
  public Comment save(Comment comment) {
    em.persist(comment); // insert됨
    return comment;
  }

  // 댓글 삭제
  public void deleteById(Long id) {
    em.createQuery("delete from Comment c where c.id = :id")
        .setParameter("id", id)
        .executeUpdate();
  }

  // 댓글 수정
}
