package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {



    private final MealRepository repository;



    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }



    @Override
    public MealTo create(MealTo mealTo, int userId) {
        Meal meal = new Meal(mealTo.getDateTime(),mealTo.getDescription(),mealTo.getCalories());
         repository.save(meal, userId);
         return mealTo;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
     repository.delete(id, userId);
    }

    @Override
    public MealTo get(int id, int userId) throws NotFoundException {
        List<MealTo> mealToList= MealsUtil.getWithExcess(
                repository.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY);
         return mealToList.stream()
                 .filter(mealTo -> mealTo.getId().equals(id))
                 .findFirst()
                 .orElse(null);
    }

    @Override
    public void update(MealTo mealTo, int userId)throws NotFoundException {
        Meal meal=new Meal(mealTo.getDateTime(),mealTo.getDescription(),mealTo.getCalories());
      repository.save(meal,userId);
    }

    @Override
    public List<MealTo> getAll(int userId) throws NotFoundException {

        return MealsUtil.getWithExcess(repository.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY);


    }
}