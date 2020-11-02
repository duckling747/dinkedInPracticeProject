package projekti.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

  @Size(min = 3)
  @NotBlank
  @Column(unique = true)
  private String username;

  @JsonIgnore
  @NotBlank
  private String password;

  private String firstName;
  private String lastName;

  @JsonIgnoreProperties("account")
  @JsonIgnore
  @OneToOne(mappedBy = "account")
  @Nullable
  private ProfilePicture image;

  @JsonIgnoreProperties("user")
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

  @JsonIgnoreProperties("issuer")
  @JsonIgnore
  @OneToMany(mappedBy = "issuer")
  private List<FriendRequest> sentFriendRequests = new ArrayList<>();

  @JsonIgnoreProperties("targetFriend")
  @JsonIgnore
  @OneToMany(mappedBy = "targetFriend")
  private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

  @JsonIgnoreProperties("sender")
  @JsonIgnore
  @OneToMany(mappedBy = "sender")
  private List<Message> sentMessages = new ArrayList<>();

  @JsonIgnoreProperties("recipient")
  @JsonIgnore
  @OneToMany(mappedBy = "recipient")
  private List<Message> receivedMessages = new ArrayList<>();

  @JsonIgnoreProperties("likes")
  @JsonIgnore
  @ManyToMany(mappedBy = "likes")
  private Set<Post> likedPosts = new HashSet<>();

  @JsonIgnoreProperties("user")
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Skill> skills = new ArrayList<>();

  public Account(final String username,
      final String password, final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Account)) {
      return false;
    }
    Account account = (Account) o;
    return username.equals(account.username);
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }
  

}
