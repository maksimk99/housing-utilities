package com.epam.maksim.katuranau.housingutilities.model;

public class CustomPrincipal {
    private Integer userId;
    private String userName;

    public CustomPrincipal(final Integer userId, final String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public CustomPrincipal setUserId(final Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CustomPrincipal setUserName(final String userName) {
        this.userName = userName;
        return this;
    }
}
