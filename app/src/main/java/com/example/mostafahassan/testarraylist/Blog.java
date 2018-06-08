package com.example.mostafahassan.testarraylist;

public class Blog {

    private String childName, date, weight, gender, idNum, birthDay, IncNum;

    private String title;

    public Blog() {
    }

    public Blog(String title) {
        this.title = title;
    }

    public Blog(String childName, String date, String weight,
                String gender, String idNum, String birthDay, String incNum) {
        this.childName = childName;
        this.date = date;
        this.weight = weight;
        this.gender = gender;
        this.idNum = idNum;
        this.birthDay = birthDay;
        this.IncNum = incNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIncNum() {
        return IncNum;
    }

    public void setIncNum(String incNum) {
        this.IncNum = incNum;
    }
}
