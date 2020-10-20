package projekti.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedEntityGraph(name = "FriendRequest.populate",
    attributeNodes = {
      @NamedAttributeNode("accepted"),
      @NamedAttributeNode(value = "issuer", subgraph = "issuer-subgraph"),
      @NamedAttributeNode(value = "targetFriend", subgraph = "target-subgraph"),
    },
    subgraphs = {
      @NamedSubgraph(
        name = "issuer-subgraph",
        attributeNodes = {
          @NamedAttributeNode("username")
        }
      ),
      @NamedSubgraph(
        name = "target-subgraph",
        attributeNodes = {
          @NamedAttributeNode("username")
        }
      )
    }
)

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
