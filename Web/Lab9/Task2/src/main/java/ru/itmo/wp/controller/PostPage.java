package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.utils.Util;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable String id, Model model) {

        long postId = Util.parseStrToLong(id);
        if (postId != -1) {
            Post post = postService.findById(Long.parseLong(id));
            model.addAttribute("commentForm", new CommentForm());
            model.addAttribute("post", post);
        }

        return "PostPage";
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/post/{id}")
    public String addCommentToPost(
            @PathVariable String id
            , @Valid @ModelAttribute("commentForm") CommentForm commentForm
            , BindingResult bindingResult
            , HttpSession httpSession
            , Model model) {

        long postId = Util.parseStrToLong(id);
        if (postId == -1)
            return "PostPage";

        if (bindingResult.hasErrors()) {
            Post post = postService.findById(Long.parseLong(id));
//            model.addAttribute("commentForm", new CommentForm());
            model.addAttribute("post", post);
            return "PostPage";
        }

        postService.writeComment(getUser(httpSession), postId, commentForm);
        putMessage(httpSession, "You published new comment");

        return "redirect:/post/" + id + "/";
    }
}
