package com.epam.cafe.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order extends AbstractEntity{
    private int id;
    private OrderStatus orderStatus;
    private Date date;
    private PaymentMethod paymentMethod;
    private OrderRating rating;
    private String review;
    private User user;
    private Map<Dish, Integer> dishes;

    public Order() {
        dishes = new HashMap<>();
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date = data;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setRating(OrderRating rating) {
        this.rating = rating;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public long getTotalPrice() {
        long total = 0;
        for (Map.Entry<Dish, Integer> pair: dishes.entrySet()) {
            Dish dish = pair.getKey();
            int amount = pair.getValue();
            total += dish.getPrice() * amount;
        }
        return total;
    }

    public OrderRating getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject){
            return true;
        }
        if (!(anotherObject instanceof Order)){
            return false;
        }

        Order anotherOrder = (Order) anotherObject;

        return id == anotherOrder.id && orderStatus == anotherOrder.orderStatus &&
               (date != null ? date.equals(anotherOrder.date) : anotherOrder.date == null) &&
               paymentMethod == anotherOrder.paymentMethod && rating == anotherOrder.rating &&
               (review != null ? review.equals(anotherOrder.review) : anotherOrder.review == null) &&
               (user != null ? user.equals(anotherOrder.user) : anotherOrder.user == null) &&
               (dishes != null ? dishes.equals(anotherOrder.dishes) : anotherOrder.dishes == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        return result;
    }
}
