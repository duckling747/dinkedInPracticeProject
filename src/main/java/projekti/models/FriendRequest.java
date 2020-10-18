package projekti.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

  @ManyToOne
  @JsonBackReference
  private Account issuer;

  @OneToOne
  private Account targetFriend;

  private boolean accepted;

}
