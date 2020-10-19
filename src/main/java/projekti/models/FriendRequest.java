package projekti.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FriendRequest extends AbstractPersistable<Long> {

  private boolean accepted;

  @ManyToOne
  private Account issuer;

  @ManyToOne
  private Account targetFriend;


}
