package com.epam.cafe.entity;

public class SignUpBean {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String passport;

    public SignUpBean(String name, String surname, String email, String phone, String passport) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SignUpBean)) {
            return false;
        }

        SignUpBean another = (SignUpBean) object;

        return (name != null ? name.equals(another.name) : another.name == null) &&
               (surname != null ? surname.equals(another.surname) : another.surname == null) &&
               (email != null ? email.equals(another.email) : another.email == null) &&
               (phone != null ? phone.equals(another.phone) : another.phone == null) &&
               (passport != null ? passport.equals(another.passport) : another.passport == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        return result;
    }
}
