package projekti.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message extends AbstractPersistable<Long> {

  @Column(columnDefinition = "TEXT")
  private String message;

  @ManyToOne
  private Account sender;

  @ManyToOne
  private Account recipient;



}
