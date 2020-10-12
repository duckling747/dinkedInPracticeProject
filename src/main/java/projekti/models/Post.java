package projekti.models;

import javax.persistence.Column;
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
public class Post extends AbstractPersistable<Long> {
    
  @Column(columnDefinition = "TEXT")
  private String text;

  @ManyToOne
  private Account user;

}
