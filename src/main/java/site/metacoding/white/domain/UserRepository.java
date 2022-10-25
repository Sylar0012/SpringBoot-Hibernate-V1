package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // IOC 등록
public class UserRepository {

  // DI
  private final EntityManager em;
  // DB에서 들고온 다른 오브젝트를 자바 오브젝트로 바꿔줌.

  public User save(User user) {
    // Persistence Context에 영속화 시키기 -> 자동 flush (트랜잭션 종료시)
    System.out.println("ccc : " + user.getId()); // 영속화전
    em.persist(user); // insert됨
    System.out.println("ccc : " + user.getId()); // 영속화후 ( DB와 동기화 됨 )
    return user;
  }

  public User findByUsername(String username) {
    return em.createQuery("select u from User u where u.username = :username", User.class)
        .setParameter("username", username)
        .getSingleResult();
  }

  public User findById(Long id) {
    return em.createQuery("select u from User u where u.id=:id", User.class)
        .setParameter("id", id)
        .getSingleResult();
  }

}
