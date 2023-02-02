package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserPage extends Page{
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    //@GetMapping(path = "{id:\\d+}")
    @GetMapping(path = "{id}")
    public String userDetail(HttpSession session, Model model, @PathVariable("id") String strId) {

        try {
            long id = Long.parseLong(strId);
            model.addAttribute("userDetail", userService.findById(id));
        } catch (NumberFormatException ignored) {
            setMessage(session, "Invalid id: " + strId);
        }

        return "UserPage";
    }
}
