package model;

public class User {

    private String userName;
    private  int userID;

    public User(String userName, int userID) {

        this.userName = userName;
        this.userID = userID;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public int getUserID() {
        return userID;
    }

    // Setters

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
