package projekti.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    // limit to first 25 hits by default
    final Pageable pageable = PageRequest.of(0, 25);
    final List<Post> ret = postRepository.findAllUsersAndFriendsPosts(id, pageable);
    assert ret != null;
    return ret;
  }

  public Post addPost(final Long id, final String post) {
    final Optional<Account> oa = accountRepository.findById(id);
    final Account a = oa.get();
    final Post p = new Post(post, a);
    return postRepository.save(p);
  }
}
