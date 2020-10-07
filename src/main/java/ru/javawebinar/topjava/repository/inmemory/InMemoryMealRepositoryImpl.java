package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.security.Security;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Integer userId=SecurityUtil.authUserId() ;
    private Map<Integer,HashMap<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal,userId));
    }



    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
             }
        if (repository.get(userId)==null){
            HashMap<Integer,Meal> mapOfMeal=new HashMap<Integer, Meal>();
            mapOfMeal.put(meal.getId(),meal);
            repository.put(userId,mapOfMeal);
        }
        else{
            HashMap<Integer,Meal> hashMap=repository.get(userId);
            hashMap.put(meal.getId(),meal);
        }
        // treat case: update, but absent in storage
        return repository.get(userId).get(meal.getId());
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId)  {
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getFilterByDate(Integer userId, LocalDate startDate, LocalDate finishDate) {
        return this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(),startDate,finishDate))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<Meal> getFilterByTime(Integer userId, LocalTime startTime, LocalTime finishTime) {
      return   this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(),startTime,finishTime))
                .collect(Collectors.toList());
    }
    @Override
    public Collection<Meal> getAll(Integer userId) {

      Collection<Meal> list= repository.get(userId).values().stream()
                .sorted(Comparator.comparing(meal->meal.getDateTime())) //check order
                .collect(Collectors.toList());

            return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

    public Integer getUserId() {
        return userId;
    }
}

