package site.metacoding.white.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJpaRepositiory extends JpaRepository<User, Long> {

  @Query(value = "select u from User u where u.username = :username")
  User findByUsername(@Param("username") String username);
  // findByEmail, findByGender ==> findBy뒤에 적혀있는 값은 where 절에 다 걸람.

}
