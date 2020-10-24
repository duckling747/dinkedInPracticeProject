package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class FriendRequest extends AbstractPersistable<Long> {

  private boolean accepted;

  @JsonIgnoreProperties("sentFriendRequests")
  @ManyToOne
  private Account issuer;

  @JsonIgnoreProperties("receivedFriendRequests")
  @ManyToOne
  private Account targetFriend;


}
