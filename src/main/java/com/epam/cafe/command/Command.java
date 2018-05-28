package com.epam.cafe.command;

import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.exception.ServiceException;

public interface Command {

    /**
     * Returns processed result of request.
     * It is used to get the result of different commands
     * @param requestContent sets attributes for session and give parameters from request.
     */
    RequestResult execute(RequestContent requestContent) throws ServiceException;
}