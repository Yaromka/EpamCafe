package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.CategoryService;

import java.util.List;

public class GetCategoryCommand implements Command{

    private static final String CATEGORY_CORT_PATH = ConfigurationManager.getProperty(Paths.CATEGORY_CORT_PAGE);

    private CategoryService categoryService;

    public GetCategoryCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns processed result of request.
     * It is used to show all categories.
     * @param content sets attributes for session.
     */
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        List<Category> categoryList = categoryService.getAll();

        content.setSessionAttributes(SessionAttr.CATEGORY_LIST, categoryList);
        return new RequestResult(CATEGORY_CORT_PATH, NavigationType.FORWARD);
    }
}

