package com.epam.cafe.entity;

public class User extends AbstractEntity{
    private int id;
    private Role role;
    private String name;
    private String surname;
    private String phone;
    private String passport;
    private String password;
    private String mail;
    private long loyaltyPoints;
    private long money;
    private ShopBasket shopBasket;

    public User() {
        shopBasket = new ShopBasket();
    }

    public User(String name, String surname, String phone, String passport, String password, String mail) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.passport = passport;
        this.password = password;
        this.mail = mail;
        shopBasket = new ShopBasket();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public ShopBasket getShopBasket() {
        return shopBasket;
    }

    public void setShopBasket(ShopBasket shopBasket) {
        this.shopBasket = shopBasket;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject){
            return true;
        }
        if (!(anotherObject instanceof User)){
            return false;
        }

        User anotherUser = (User) anotherObject;

        return id == anotherUser.id &&
               loyaltyPoints == anotherUser.loyaltyPoints &&
               money == anotherUser.money &&
               role == anotherUser.role &&
               (name != null ? name.equals(anotherUser.name) : anotherUser.name == null) &&
               (surname != null ? surname.equals(anotherUser.surname) : anotherUser.surname == null) &&
               (phone != null ? phone.equals(anotherUser.phone) : anotherUser.phone == null) &&
               (passport != null ? passport.equals(anotherUser.passport) : anotherUser.passport == null) &&
               (password != null ? password.equals(anotherUser.password) : anotherUser.password == null) &&
               (mail != null ? mail.equals(anotherUser.mail) : anotherUser.mail == null) &&
               (shopBasket != null ? shopBasket.equals(anotherUser.shopBasket) : anotherUser.shopBasket == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (int) (loyaltyPoints ^ (loyaltyPoints >>> 32));
        result = 31 * result + (int) (money ^ (money >>> 32));
        result = 31 * result + (shopBasket != null ? shopBasket.hashCode() : 0);
        return result;
    }
}