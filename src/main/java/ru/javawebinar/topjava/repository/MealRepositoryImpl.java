package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository {

    private static ConcurrentHashMap<Integer, Meal> repository=new ConcurrentHashMap();

    private AtomicInteger counter=new AtomicInteger(0);
    private static final Logger Loggg= LoggerFactory.getLogger(MealRepositoryImpl.class);
    {

        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }
    @Override
    public Meal save(Meal meal) {

        if (meal.isNew()) {
            Loggg.info("create element with new id");
            meal.setId(counter.incrementAndGet());

        }
       return repository.put(meal.getId(),meal);

    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<MealTo> getAll() {
        Collection<MealTo> resultListOfMealTo;
        resultListOfMealTo=MealsUtil.getFilteredWithExcess(new ArrayList<Meal>(repository.values()), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesMaxPerDay);
       return resultListOfMealTo;
    }

    @Override
    public void delete(int id) {
      repository.remove(id);
    }
}
