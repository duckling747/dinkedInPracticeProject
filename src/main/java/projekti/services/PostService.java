package projekti.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.PostRepository;

@Service
@Transactional
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private AccountRepository accountRepository;
    
  public List<Post> getUsersAndFriendsPosts(final Long id) {
    final List<Post> ret = postRepository.findAllUsersAndFriendsPosts(id);
    assert ret != null;
    return ret;
  }

  public Post addPost(final Long id, final String post) {
    final Optional<Account> oa = accountRepository.findById(id);
    final Account a = oa.get();
    final Post p = new Post(post, LocalDateTime.now(), a);
    return postRepository.save(p);
  }
}
