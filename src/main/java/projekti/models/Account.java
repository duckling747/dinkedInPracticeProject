package projekti.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

  @Column(unique = true)
  private String username;

  private String password;
  private String firstName;
  private String lastName;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "Connections",
      joinColumns = {@JoinColumn(name = "UserId")},
      inverseJoinColumns = {@JoinColumn(name = "ConnectionId")})
  private List<Account> connections = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Post> posts = new ArrayList<>();

}
