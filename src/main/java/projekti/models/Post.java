package projekti.models;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Post extends AbstractPersistable<BigInteger> {
    
  @Column(columnDefinition = "TEXT")
  private String text;

  @ManyToOne
  private Account user;

  public Post(final String text, final Account user) {
    this.text = text;
    this.user = user;
  }

  public String getText() {
    return text;
  }

  public Account getUser() {
    return user;
  }

}
