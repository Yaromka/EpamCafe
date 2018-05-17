package com.epam.cafe.command;

import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.exception.ServiceException;

public interface Command {
    RequestResult execute(RequestContent requestContent) throws ServiceException;
}