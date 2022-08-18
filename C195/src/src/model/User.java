package model;

public class User
{
    public int userId;
    public String userName;
    public String password;


    public User (int userId, String userName, String password)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }


    public int getUserId() {
        return userId;
    }


    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString()
    {
        return "[" + userId + "] " + userName;
    }
}