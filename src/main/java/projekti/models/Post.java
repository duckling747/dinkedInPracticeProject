package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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

  @JsonIgnoreProperties("likedPosts")
  @ManyToMany
  private Set<Account> likes;

  @JsonIgnoreProperties("post")
  @JsonIgnore
  @OneToMany
  private List<Comment> comments;

  public Post(final String post, final Account user) {
    this.post = post;
    this.user = user;
    this.timestamp = LocalDateTime.now();
    this.likes = new HashSet<>();
    this.comments = new ArrayList<>();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Post)) {
      return false;
    }
    Post posto = (Post) o;
    return Objects.equals(post, posto.post)
      && Objects.equals(timestamp, posto.timestamp)
      && Objects.equals(user, posto.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(post, timestamp, user);
  }

}
