package com.epam.maksim.katuranau.housing_utilities.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Message {
    private Integer userId;
    private BigDecimal amountOfResourcesSpent;
    private LocalDate date = LocalDate.now();

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getAmountOfResourcesSpent() {
        return amountOfResourcesSpent;
    }

    public LocalDate getDate() {
        return date;
    }

    public Message setUserId(final Integer userId) {
        this.userId = userId;
        return this;
    }

    public Message setAmountOfResourcesSpent(final BigDecimal amountOfResourcesSpent) {
        this.amountOfResourcesSpent = amountOfResourcesSpent;
        return this;
    }

    public Message setDate(final LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", amountOfResourcesSpent=" + amountOfResourcesSpent +
                ", date=" + date +
                '}';
    }
}
