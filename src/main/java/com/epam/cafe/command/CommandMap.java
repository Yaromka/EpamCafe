package com.epam.cafe.command;

import com.epam.cafe.command.admin.*;
import com.epam.cafe.command.authorized.ChangePasswordCommand;
import com.epam.cafe.command.authorized.EditUserCommand;
import com.epam.cafe.command.authorized.LogOutCommand;
import com.epam.cafe.command.guest.LogInCommand;
import com.epam.cafe.command.guest.SignUpCommand;
import com.epam.cafe.command.locale.LocaleCommand;
import com.epam.cafe.command.page.GetPageCommand;
import com.epam.cafe.command.user.*;
import com.epam.cafe.service.CategoryService;
import com.epam.cafe.service.DishService;
import com.epam.cafe.service.OrderService;
import com.epam.cafe.service.UserService;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.epam.cafe.command.CommandType.*;

public class CommandMap {
    private EnumMap<CommandType, Command> map = new EnumMap<>(CommandType.class);
    private static CommandMap instance = new CommandMap();

    /**
     * Returns suitable command.
     * It is used to define the type of concrete command by string value.
     * There are different access levels for different commands.
     * @param commandParameter holds the value of command.
     */
    public static Command defineCommandType(String commandParameter) {
        List<Command> list = new ArrayList<>();
        EnumMap<CommandType, Command> commandMap = getInstance().map;

        CommandType commandType = CommandType.valueOf(commandParameter);
        for (Map.Entry<CommandType,Command> currentCommand : commandMap.entrySet()) {
            CommandType currentCommandType = currentCommand.getKey();
            if(currentCommandType.equals(commandType))
            {
                Command command = currentCommand.getValue();
                list.add(command);
                break;
            }
        }
        return list.get(0);
    }

    private CommandMap() {
        map.put(LOGIN, new LogInCommand(new UserService(), new CategoryService()));
        map.put(LOGOUT, new LogOutCommand());
        map.put(LOCALE, new LocaleCommand());
        map.put(SIGN_UP, new SignUpCommand(new UserService()));
        map.put(GET_DISHES, new GetDishCommand(new DishService()));
        map.put(ADD_TO_BASKET, new AddToBasketCommand(new DishService()));
        map.put(REMOVE_FROM_BASKET, new RemoveFromBasketCommand(new DishService()));
        map.put(MAKE_ORDER, new MakeOrderCommand(new OrderService(), new DishService()));
        map.put(GET_USER_BY_SURNAME, new FindUserBySurnameCommand(new UserService()));
        map.put(GET_ORDERS_BY_USER, new GetOrdersByUserCommand(new OrderService(), new DishService()));
        map.put(GET_MY_ORDERS, new GetMyOrderCommand(new OrderService(), new DishService()));
        map.put(ADD_DISH, new AddDishCommand(new DishService(), new CategoryService()));
        map.put(GET_CATEGORIES, new GetCategoryCommand(new CategoryService()));
        map.put(ADD_CATEGORY, new AddCategoryCommand(new CategoryService()));
        map.put(GET_ALL_USERS, new FindAllUsersCommand(new UserService()));
        map.put(CHANGE_PASS, new ChangePasswordCommand(new UserService()));
        map.put(EDIT_USER, new EditUserCommand(new UserService()));
        map.put(CHANGE_PAY_STATUS, new ChangePayStatusCommand(new OrderService()));
        map.put(ADD_REVIEW, new AddReviewCommand(new OrderService()));
        map.put(GET_ORDERS_BY_PARAMETERS, new GetOrdersByParametersCommand(new OrderService(), new DishService()));
        map.put(UPDATE_ENABLE_STATUS, new UpdateDishStockStatus(new DishService()));
        map.put(UPDATE_LOYALTY_POINTS, new UpdateLoyaltyPointsCommand(new UserService()));
        map.put(GET_MENU, new GetMenuCommand(new DishService()));
        map.put(GET_PAGE, new GetPageCommand());
    }

    private static CommandMap getInstance() {
        return instance;
    }
}
