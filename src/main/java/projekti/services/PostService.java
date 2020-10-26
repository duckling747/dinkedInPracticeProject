package projekti.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.models.Post;
import projekti.repositories.PostRepository;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;
    
  public List<Post> getUsersAndFriendsPosts(final String uname) {
    final List<Post> ret = postRepository.findAllUsersAndFriendsPosts(uname);
    assert ret != null;
    return ret;
  }
}
