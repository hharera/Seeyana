package com.example.n_one.objects;

import java.util.ArrayList;
import java.util.List;

public class Provider {
    private String firstName;
    private String lastName;
    private List<String> mainExpertises;
    private String country;
    private String city;
    private boolean willingTravel;
    private boolean verified;
    private String shortDescription;
    private String overview;
    private float rating;
    private int earnedMoney;
    private double balance;
    private List<String> skills;
    private String phoneNumber;

    public Provider() {
        mainExpertises = new ArrayList<>();
        skills = new ArrayList<>();
        willingTravel = false;
        country = "";

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMainExpertises() {
        return mainExpertises;
    }

    public void setMainExpertises(List<String> mainExpertises) {
        this.mainExpertises = mainExpertises;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isWillingTravel() {
        return willingTravel;
    }

    public void setWillingTravel(boolean willingTravel) {
        this.willingTravel = willingTravel;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setEarnedMoney(int earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getEarnedMoney() {
        return earnedMoney;
    }
}
