package com.epam.cafe.entity;

public class Dish extends AbstractEntity{
    private int id;
    private String name;
    private String description;
    private int weight;
    private long price;
    private Category category;
    private String picture;
    private boolean isEnable;

    public Dish() {}

    public Dish(String name, String description, int weight, long price, Category category) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.category = category;
        this.picture = "";
        this.isEnable = true;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (!(anotherObject instanceof Dish)) {
            return false;
        }

        Dish another = (Dish) anotherObject;

        return id == another.id && weight == another.weight &&
               price == another.price && isEnable == another.isEnable &&
               (name != null ? name.equals(another.name) : another.name == null) &&
               (description != null ? description.equals(another.description) : another.description == null) &&
               (category != null ? category.equals(another.category) : another.category == null) &&
               (picture != null ? picture.equals(another.picture) : another.picture == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (isEnable ? 1 : 0);
        return result;
    }
}

