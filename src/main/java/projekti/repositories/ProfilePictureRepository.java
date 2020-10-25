package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.models.ProfilePicture;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

  public static final String getProfilePictureQuery
      = "SELECT p "
      + "FROM ProfilePicture p JOIN Account a "
      + "ON a.id = p.account "
      + "WHERE a.id = :id";

  @Query(getProfilePictureQuery)
  ProfilePicture findProfilePicture(@Param("id") Long id);

}
