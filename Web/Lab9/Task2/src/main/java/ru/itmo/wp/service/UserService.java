package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.RoleRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * @noinspection FieldCanBeLocal, unused
     */
    private final RoleRepository roleRepository;

    private final TagService tagService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, TagService tagService) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
        this.tagService = tagService;
        for (Role.Name name : Role.Name.values()) {
            if (!roleRepository.existsByName(name)) {
                roleRepository.save(new Role(name));
            }
        }
    }

    public User register(UserCredentials userCredentials) {
        User user = new User();
        user.setLogin(userCredentials.getLogin());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(), userCredentials.getLogin(), userCredentials.getPassword());
        return user;
    }

    public boolean isLoginVacant(String login) {
        return userRepository.countByLogin(login) == 0;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public void writePost(User user, PostForm postForm) {
        Post post = new Post();

        post.setText(postForm.getText());
        post.setTitle(postForm.getTitle());

        if (postForm.getTags().isEmpty()) {
            post.setTags(new HashSet<>());
        } else {
            post.setTags(
                    Arrays.stream(postForm.getTags().split("\\s+"))
                            .distinct()
                            .map(this::getTag)
                            .collect(Collectors.toSet())
            );
        }
        user.addPost(post);
        post.setUser(user);
        userRepository.save(user);
    }

    public boolean isIdValid(long id) {
        return userRepository.countById(id) != 0;
    }

    private Tag getTag(String name) {
        Tag tag = tagService.findByName(name);
        return tag == null ? new Tag(name) : tag;
    }

}
