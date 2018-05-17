package com.epam.cafe.command.admin;

import com.epam.cafe.command.Command;
import com.epam.cafe.constants.Messages;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.constants.RequestParameter;
import com.epam.cafe.constants.SessionAttr;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.entity.Category;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import com.epam.cafe.service.CategoryService;

public class AddCategoryCommand implements Command{
    private static final String ADD_CATEGORY_PATH = ConfigurationManager.getProperty(Paths.GET_NEW_CATEGORY_PAGE);

    private CategoryService categoryService;

    public AddCategoryCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public RequestResult execute(RequestContent requestContent) throws ServiceException {
        String categoryName = requestContent.getRequestParameterByName(RequestParameter.CATEGORY_NAME);

        if (categoryService.isCategoryExist(categoryName)) {
            requestContent.setSessionAttributes(SessionAttr.ADD_CATEGORY_STATUS_NEGATIVE, Messages.CATEGORY_ALREADY_EXIST);
            return new RequestResult(ADD_CATEGORY_PATH, NavigationType.FORWARD);
        }

        Category category = new Category();
        category.setName(categoryName);
        if(!categoryService.addCategory(category)) {
            return new RequestResult(ADD_CATEGORY_PATH, NavigationType.FORWARD);
        }

        requestContent.setSessionAttributes(SessionAttr.ADD_CATEGORY_STATUS_POSITIVE, Messages.SUCCESS);
        return new RequestResult(ADD_CATEGORY_PATH, NavigationType.REDIRECT);
    }
}
