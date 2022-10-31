package site.metacoding.white.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Getter
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // identity : 해당 DB의 id증가 전략을 따라감.
  private Long id;
  private String content;

  // User 누가 했는지
  @OnDelete(action = OnDeleteAction.CASCADE)
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  // Board 어디에 썼는지
  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Board board;

  @Builder
  public Comment(Long id, String content, User user, Board board) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.board = board;
  }

}
