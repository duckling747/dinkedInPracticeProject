package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message extends AbstractPersistable<Long> {

  @Column(columnDefinition = "TEXT")
  private String message;

  @JsonIgnoreProperties("sentMessages")
  @ManyToOne
  private Account sender;

  @JsonIgnoreProperties("receivedMessages")
  @ManyToOne
  private Account recipient;



}
