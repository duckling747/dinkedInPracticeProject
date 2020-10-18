package projekti.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import projekti.models.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    
  List<FriendRequest> findAll();

  List<FriendRequest> findByAcceptedTrue();

  List<FriendRequest> findByAcceptedFalse();


}
