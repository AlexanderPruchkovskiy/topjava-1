package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    MealTo create(MealTo mealTo, int userId);

    void delete(int id, int userId) throws NotFoundException;

    MealTo get(int id, int userId) throws NotFoundException;

    void update(MealTo mealTo, int userId)throws NotFoundException;

    List<MealTo> getAll(int userId)throws NotFoundException;
}