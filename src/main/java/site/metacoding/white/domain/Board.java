package site.metacoding.white.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // identity : 해당 DB의 id증가 전략을 따라감.
  private Long id;
  private String title;
  @Column(length = 1000)
  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;
}
