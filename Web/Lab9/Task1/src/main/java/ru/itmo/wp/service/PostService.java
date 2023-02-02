package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public boolean isIdValid(long id) {
        return postRepository.countById(id) != 0;
    }

    public void writeComment(User user, long postId, CommentForm commentForm) {
        Comment comment = new Comment();

        comment.setText(commentForm.getComment());
        comment.setUser(user);

        Post post = findById(postId);
        comment.setPost(post);

        post.getComments().add(comment);

        postRepository.save(post);
    }

}
