package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.Map;

public interface MealRepository {
   Meal save (Meal meal);
    Meal get(int id);
    Collection<MealTo> getAll();
    void delete(int id);
}
