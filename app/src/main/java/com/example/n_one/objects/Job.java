package com.example.n_one.objects;

import java.util.List;

public class Job implements Cloneable{

    public static final int NO_PROVIDER = 0x00000000;
    public static final int WORK_ON_PROGRESS = 0x00000001;
    public static final int COMPLETED = 0x00000002;

    private String providerKey;
    private String customerKey;
    private String title;
    private String details;
    private String requiredExpertise;
    private String city;
    private String country;
    private List<String> requiredSkills;
    private List<String> photoKeys;
    private String invitedCustomerKey;
    private Price price;
    private int status;

    public Job() {

    }

    public String getProviderKey() {
        return providerKey;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRequiredExpertise() {
        return requiredExpertise;
    }

    public void setRequiredExpertise(String requiredExpertise) {
        this.requiredExpertise = requiredExpertise;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getPhotoKeys() {
        return photoKeys;
    }

    public void setPhotoKeys(List<String> photoKeys) {
        this.photoKeys = photoKeys;
    }

    public String getInvitedCustomerKey() {
        return invitedCustomerKey;
    }

    public void setInvitedCustomerKey(String invitedCustomerKey) {
        this.invitedCustomerKey = invitedCustomerKey;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
