package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post extends AbstractPersistable<Long> {
    
  @Column(columnDefinition = "TEXT")
  private String post;

  private LocalDateTime timestamp;

  @JsonIgnoreProperties("posts")
  @ManyToOne
  private Account user;

  @JsonIgnoreProperties("post")
  @OneToMany(mappedBy = "post")
  private List<LikeTable> likes;

  public Post(final String post, final Account user) {
    this.post = post;
    this.user = user;
    this.timestamp = LocalDateTime.now();
    this.likes = new ArrayList<>();
  }

}
