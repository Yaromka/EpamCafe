package com.epam.cafe.entity;

import java.util.HashMap;
import java.util.Map;

public class ShopBasket extends AbstractEntity{
    private static final int MAX_BASKET_CAPACITY = 30;
    private Map<Dish, Integer> basket;
    private long basketPrice;
    private Integer basketCapacity;

    public ShopBasket() {
        basket = new HashMap<Dish, Integer>(MAX_BASKET_CAPACITY);
        basketCapacity = 0;
    }

    public Integer getBasketCapacity() {
        return basketCapacity;
    }

    public Map<Dish, Integer> getBasket() {
        return basket;
    }

    public long getBasketPrice() {
        return basketPrice;
    }

    public void addDish(Dish dish) {

        if (basketCapacity < MAX_BASKET_CAPACITY) {
            if (basket.containsKey(dish)) {
                Integer amount = basket.get(dish);
                basket.put(dish, amount + 1);
            } else {
                basket.put(dish, 1);
            }
            basketCapacity += 1;
            basketPrice = basketPrice + dish.getPrice();
        }
    }

    public void removeDish(Dish dish) {

        if (basket.containsKey(dish)) {
            if (basket.get(dish) > 1) {
                Integer amount = basket.get(dish);
                basket.put(dish, amount - 1);
            } else {
                basket.remove(dish);

            }
            basketCapacity -= 1;
            basketPrice = basketPrice - dish.getPrice();
        }
    }

    public void clean() {
        basket = new HashMap<Dish, Integer>(MAX_BASKET_CAPACITY);
        basketCapacity = 0;
        basketPrice = 0;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (!(anotherObject instanceof ShopBasket)) {
            return false;
        }

        ShopBasket another = (ShopBasket) anotherObject;

        return basketPrice == another.basketPrice &&
             (basket != null ? basket.equals(another.basket) : another.basket == null) &&
             (basketCapacity != null ? basketCapacity.equals(another.basketCapacity) : another.basketCapacity == null);
    }

    @Override
    public int hashCode() {
        int result = basket != null ? basket.hashCode() : 0;
        result = 31 * result + (int) (basketPrice ^ (basketPrice >>> 32));
        result = 31 * result + (basketCapacity != null ? basketCapacity.hashCode() : 0);
        return result;
    }
}

