package com.epam.cafe.builder;

import com.epam.cafe.entity.AbstractEntity;
import com.epam.cafe.entity.Role;
import com.epam.cafe.entity.User;
import com.epam.cafe.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements EntityBuilder {
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

    @Override
    public User createEntity(ResultSet resultSet) throws DAOException{
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
}
