import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class user {
    private String userId;
    private String userName;
    private String surname;
    private Address address;
    private String role;
    private double balance;

    public user(String userId, String userName, String surname, Address address, double balance, String role) {
        this.userId = userId;
        this.userName = userName;
        this.surname = surname;
        this.address = address;
        this.role = role;
        this.balance = balance;
    }

    // getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    // In the user class:



    public void deductBalance(double amount) {
        this.balance -= amount;
    }

}

