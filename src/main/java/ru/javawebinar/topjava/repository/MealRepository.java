package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {

    Meal save(Meal meal, Integer userId);

    // false if not found
    boolean delete(int id, Integer userId);

    // null if not found
    Meal get(int id, Integer userId);

    Collection<Meal> getAll(Integer userId);

    Integer getUserId();

    Collection<Meal> getFilterByDate (Integer userId, LocalDate startDate, LocalDate finishDate );
    Collection<Meal> getFilterByTime (Integer userId, LocalTime startTime, LocalTime finishTime );
}
