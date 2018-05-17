package com.epam.cafe.util;

import com.epam.cafe.dao.*;
import com.epam.cafe.dao.impl.CategoryDaoImpl;
import com.epam.cafe.dao.impl.DishDaoImpl;
import com.epam.cafe.dao.impl.UserDaoImpl;
import com.epam.cafe.entity.*;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class ResultSetConverter {
    private static final String ID_PARAM = "user_id";
    private static final String PASSPORT_PARAM = "user_passport";
    private static final String NAME_PARAM = "user_name";
    private static final String SURNAME_PARAM = "user_surname";
    private static final String PHONE_PARAM = "user_phone";
    private static final String LOYALTY_PARAM = "user_loyaltyPoints";
    private static final String ROLE_PARAM = "roles_role_id";
    private static final String EMAIL_PARAM = "user_email";
    private static final String PASSWORD_PARAM = "user_password";
    private static final String MONEY_PARAM = "user_money";

    private static final String DISH_NAME_PARAM = "dish_name";
    private static final String DISH_ID_PARAM = "dish_id";
    private static final String DISH_CATEGORY_ID_PARAM = "categories_category_id";
    private static final String DISH_PRICE_PARAM = "dish_price";
    private static final String DISH_DESCRIPTION_PARAM = "dish_description";
    private static final String DISH_WEIGHT_PARAM = "dish_weight";
    private static final String DISH_PICTURE_PARAM = "dish_picture";
    private static final String DISH_IS_ENABLE_PARAM = "dish_isEnable";

    private static final String CATEGORY_NAME_PARAM = "category_name";
    private static final String CATEGORY_ID_PARAM = "category_id";

    private static final String ORDER_ID_PARAM = "order_id";
    private static final String ORDER_USER_ID_PARAM = "users_ownerId";
    private static final String ORDER_DATE_PARAM = "order_receipt_time";
    private static final String ORDER_REVIEW_PARAM = "order_review";
    private static final String ORDER_PAYMENT_METHOD_PARAM = "order_payment_method";
    private static final String ORDER_RATING_PARAM = "order_rating";
    private static final String ORDER_STATUS_PARAM = "order_status";


    public static User createUserEntity(ResultSet resultSet) throws DAOException {
        User user;
        try {
            int userId = resultSet.getInt(ID_PARAM);
            String email = resultSet.getString(EMAIL_PARAM);
            String password = resultSet.getString(PASSWORD_PARAM);
            String roleId = resultSet.getString(ROLE_PARAM);
            Role role = Role.getRole(roleId);
            String name = resultSet.getString(NAME_PARAM);
            String surname = resultSet.getString(SURNAME_PARAM);
            String phoneNumber = resultSet.getString(PHONE_PARAM);
            String passport = resultSet.getString(PASSPORT_PARAM);
            long loyaltyPoints = resultSet.getLong(LOYALTY_PARAM);
            long money = resultSet.getLong(MONEY_PARAM);

            user = new User();

            user.setId(userId);
            user.setMail(email);
            user.setPassword(password);
            user.setRole(role);
            user.setName(name);
            user.setSurname(surname);
            user.setPhone(phoneNumber);
            user.setPassport(passport);
            user.setLoyaltyPoints(loyaltyPoints);
            user.setMoney(money);

        } catch (SQLException e) {
            throw new DAOException("Exception during creating User ", e);
        }
        return user;
    }

    public static Dish createDishEntity(ResultSet resultSet) throws DAOException {
        Dish dish;
        try {
            int id = resultSet.getInt(DISH_ID_PARAM);
            String name = resultSet.getString(DISH_NAME_PARAM);
            long price = resultSet.getLong(DISH_PRICE_PARAM);
            String description = resultSet.getString(DISH_DESCRIPTION_PARAM);
            String picture = resultSet.getString(DISH_PICTURE_PARAM);
            int weight = resultSet.getInt(DISH_WEIGHT_PARAM);
            int categoryId = resultSet.getInt(DISH_CATEGORY_ID_PARAM);
            int enableParam = resultSet.getInt(DISH_IS_ENABLE_PARAM);
            Boolean isEnable = (enableParam == 1);

            dish = new Dish();

            dish.setId(id);
            dish.setName(name);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setPicture(picture);
            dish.setWeight(weight);

            DaoFactory daoFactory = new DaoFactory();
            CategoryDao categoryDaoImpl = daoFactory.getCategoryDao();
            daoFactory.beginTransaction();
            Category category = categoryDaoImpl.getById(categoryId);
            daoFactory.close();

            dish.setCategory(category);
            dish.setEnable(isEnable);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Dish", e);
        }
        return dish;
    }

    public static Category createCategoryEntity(ResultSet resultSet) throws DAOException {
        Category category;
        try {
            int id = resultSet.getInt(CATEGORY_ID_PARAM);
            String name = resultSet.getString(CATEGORY_NAME_PARAM);

            category = new Category();

            category.setId(id);
            category.setName(name);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Category", e);
        }
        return category;
    }

    public static Order createOrderEntity(ResultSet resultSet) throws DAOException {
        Order order = new Order();
        try {

            int id = resultSet.getInt(ORDER_ID_PARAM);
            order.setId(id);

            String statusParameter = resultSet.getString(ORDER_STATUS_PARAM);
            OrderStatus status = OrderStatus.valueOf(statusParameter);
            order.setOrderStatus(status);

            String dateParameter = resultSet.getString(ORDER_DATE_PARAM);
            Date date = DateTimeConverter.convertStringToDateTime(dateParameter);
            order.setDate(date);

            String payMethodParameter = resultSet.getString(ORDER_PAYMENT_METHOD_PARAM);
            PaymentMethod paymentMethod = PaymentMethod.valueOf(payMethodParameter);
            order.setPaymentMethod(paymentMethod);

            String orderRatingParameter = resultSet.getString(ORDER_RATING_PARAM);
            if(orderRatingParameter != null) {
                OrderRating orderRating = OrderRating.stringToEnum(orderRatingParameter);
                order.setRating(orderRating);
            }

            String review = resultSet.getString(ORDER_REVIEW_PARAM);
            if(review != null) {
                order.setReview(review);
            }

            DaoFactory daoFactory = new DaoFactory();
            UserDao userDaoImpl = daoFactory.getUserDao();
            int ownerId = resultSet.getInt(ORDER_USER_ID_PARAM);

            User user = userDaoImpl.getById(ownerId);


            order.setUser(user);

            DishDao dishDaoImpl = daoFactory.getDishDao();

            Map<Dish, Integer> orderDishes = dishDaoImpl.getByOrder(order);
            daoFactory.close();

            order.setDishes(orderDishes);
        } catch (SQLException e) {
            throw new DAOException("Exception during creating Order", e);
        }
        return order;
    }
}
