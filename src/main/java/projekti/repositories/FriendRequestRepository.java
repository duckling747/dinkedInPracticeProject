package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import projekti.models.Account;
import projekti.models.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    
  @EntityGraph(value = "FriendRequest.populate")
  List<FriendRequest> findAll();

  @EntityGraph(value = "FriendRequest.populate")
  List<FriendRequest> findByAcceptedTrue();

  @EntityGraph(value = "FriendRequest.populate")
  List<FriendRequest> findByAcceptedFalse();
  
}
