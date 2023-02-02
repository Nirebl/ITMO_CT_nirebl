package ru.itmo.wp.form;

import ru.itmo.wp.domain.User;
import ru.itmo.wp.domain.Post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentForm {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 100)
    private String comment;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
