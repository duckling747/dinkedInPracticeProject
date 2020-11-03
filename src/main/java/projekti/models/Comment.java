package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment extends AbstractPersistable<Long> {

  @Column(columnDefinition = "TEXT")
  @Basic(fetch = FetchType.LAZY)
  private String text;

  private LocalDateTime timestamp;

  @JsonIgnoreProperties("comments")
  @ManyToOne
  private Post post;

  @JsonIgnoreProperties("comments")
  @ManyToOne
  private Account user;

  public Comment(final String text, final Post post, final Account user) {
    this.timestamp = LocalDateTime.now();
    this.text = text;
    this.post = post;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }    
    if (!(o instanceof Comment)) {
      return false;
    }
    final Comment comment = (Comment) o;
    return Objects.equals(text, comment.text)
        && Objects.equals(timestamp, comment.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, timestamp);
  }

}
