package projekti.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.Comment;
import projekti.models.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.CommentRepository;
import projekti.repositories.PostRepository;

@Service
@Transactional
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CommentRepository commentRepository;
    
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

  public void addComment(final Long accountId, final Long postId,
      final String comment) {
    final Account a = accountRepository.findById(accountId).get();
    final Post p = postRepository.findById(postId).get();
    final Comment c = new Comment(comment, p, a);
    commentRepository.save(c);
    a.getComments().add(c);
    accountRepository.save(a);
    p.getComments().add(c);
    postRepository.save(p);
  }

  public List<Comment> getComments(final Long postId) {
    // limit to first 10 hits by default
    final Pageable pageable = PageRequest.of(0, 10);
    final List<Comment> comments = commentRepository.getCommentsForPost(postId, pageable);
    return comments;
  }
}
