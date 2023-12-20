package com.example.expensetrackersystem.model;

public class FriendExpense {

    private int id;
    private String friendName;
    private double expenseAmount;

    public FriendExpense(int id, String friendName, double expenseAmount) {
        this.id= id;
        this.friendName = friendName;
        this.expenseAmount = expenseAmount;
    }

    public FriendExpense(String friendName, double expenseAmount) {
        this.friendName = friendName;
        this.expenseAmount = expenseAmount;
    }

    public int getId(){
        return id;
    }

    public String getFriendName() {
        return friendName;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }
}

