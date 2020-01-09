package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;


public class MealTo {
    private  LocalDateTime dateTime;

    private  String description;

    private  int calories;

    private  boolean excess;

    private int id;



    public MealTo( LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
        this.id=id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime=dateTime;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}