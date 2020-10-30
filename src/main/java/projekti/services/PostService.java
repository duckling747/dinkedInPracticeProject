package projekti.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

  public void likePost(final Long accountId, final Long postId) {
    final Account a = accountRepository.findById(accountId).get();
    final Post p = postRepository.findById(postId).get();
    final Set<Account> likes = p.getLikes();
    if (likes.contains(a)) {
      likes.remove(a);
      a.getLikedPosts().remove(p);
    } else {
      likes.add(a);
      a.getLikedPosts().add(p);
    }
    accountRepository.save(a);
    postRepository.save(p);
  }
}
