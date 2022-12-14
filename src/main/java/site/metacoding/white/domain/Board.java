package site.metacoding.white.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // identity : 해당 DB의 id증가 전략을 따라감.
  private Long id;
  private String title;
  @Column(length = 1000)
  private String content;

  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  // 조회를 위해서만 필요함.
  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Comment> comments = new ArrayList<>(); // 널 포인트 익셉션 방지

  @Builder
  public Board(Long id, String title, String content, User user) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.user = user;
  }

  // 변경하는 코드는 의미 있게 메서드로 구현
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

}
