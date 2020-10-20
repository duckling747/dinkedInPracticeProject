package projekti.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class Account extends AbstractPersistable<Long> {

  @Column(unique = true)
  private String username;

  private String password;
  private String firstName;
  private String lastName;

  @JsonBackReference
  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

  @JsonBackReference
  @OneToMany(mappedBy = "issuer")
  private List<FriendRequest> sentFriendRequests = new ArrayList<>();

  @JsonBackReference
  @OneToMany(mappedBy = "targetFriend")
  private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

  @JsonBackReference
  @OneToMany(mappedBy = "sender")
  private List<Message> sentMessages = new ArrayList<>();

  @JsonBackReference
  @OneToMany(mappedBy = "recipient")
  private List<Message> receivedMessages = new ArrayList<>();

}
