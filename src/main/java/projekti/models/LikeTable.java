package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeTable extends AbstractPersistable<Long> {

  @JsonIgnoreProperties("sentLikes")
  @ManyToOne
  private Account user;

  @JsonIgnoreProperties("likes")
  @ManyToOne
  private Post post;

}
