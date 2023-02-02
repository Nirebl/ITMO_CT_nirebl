package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalksRepository;
import ru.itmo.wp.model.repository.impl.TalksRepositoryImpl;

import java.util.List;

public class TalksService {

    private final TalksRepository _talksRepository = new TalksRepositoryImpl();
    private final UserService _userService = new UserService();


    private void validateTalk(Talk talk) throws ValidationException
    {
        if(talk.getSourceUserId() == talk.getTargetUserId())
            throw new ValidationException("You can't send message to yourself");
        _userService.validateUserId(talk.getSourceUserId());
        _userService.validateUserId(talk.getTargetUserId());
        if (Strings.isNullOrEmpty(talk.getText()))
            throw new ValidationException("The text is empty");

    }

    public void sendTalk(Talk talk) throws ValidationException
    {
        validateTalk(talk);

        _talksRepository.save(talk);
    }

    public List<Talk> getUserTalks(User user) throws ValidationException {
        _userService.validateUser(user);

        return _talksRepository.getUserTalks(user.getId());
    }

}
