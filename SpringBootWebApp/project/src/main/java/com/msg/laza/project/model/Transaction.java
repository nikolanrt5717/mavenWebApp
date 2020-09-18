package com.msg.laza.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
    @JsonProperty("id")
    private int id;
    @JsonProperty("senderAccountId")
    private int senderAccountId;
    @JsonProperty("receiverAccountId")
    private int receiverAccountId;
    @JsonProperty("transactionTimestamp")
    private String transactionTimestamp;
    @JsonProperty("funds")
    private double funds;

    public Transaction(int id, int senderAccountId, int receiverAccountId, String transactionTimestamp, double funds) {
        this.id = id;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.transactionTimestamp = transactionTimestamp;
        this.funds = funds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public int getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(int receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", transactionTimestamp='" + transactionTimestamp + '\'' +
                ", funds=" + funds +
                '}';
    }
}
