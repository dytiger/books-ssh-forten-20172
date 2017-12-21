package org.forten.books.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="users")
public class User implements Serializable{
    @Id
    private int id;
    @Column(name="login_name")
    private String loginName;
    @Column
    private String password;
    @Column
    private String name;
    @Column(name="library_card")
    private String libraryCard;
    @Column(name="user_level")
    private int userLevel;

    public User() {
        this.userLevel = 0;
    }

    public User(String loginName, String password, String name, String libraryCard) {
        this();
        this.loginName = loginName;
        this.password = password;
        this.name = name;
        this.libraryCard = libraryCard;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(String libraryCard) {
        this.libraryCard = libraryCard;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", libraryCard='" + libraryCard + '\'' +
                ", userLevel=" + userLevel +
                '}';
    }
}
