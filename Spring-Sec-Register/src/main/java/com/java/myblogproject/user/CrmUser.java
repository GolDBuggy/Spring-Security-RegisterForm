package com.java.myblogproject.user;

import com.java.myblogproject.valid.FieldMatch;
import com.java.myblogproject.valid.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({@FieldMatch(first = "password",second = "matchingPassword",message = "The password field must match!")})
public class CrmUser {

    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String userName;


    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String password;

    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String matchingPassword;


    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String firstName;

    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String lastName;

    @ValidEmail
    @NotNull(message = "is required!")
    @Size(min = 1,message = "is required!")
    private String email;

    public CrmUser(String userName, String password, String matchingPassword, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CrmUser() {
    }


}
