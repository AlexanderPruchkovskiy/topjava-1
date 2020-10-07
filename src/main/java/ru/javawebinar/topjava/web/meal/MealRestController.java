package ru.javawebinar.topjava.web.meal;



import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;


import java.util.List;
@Controller
public class MealRestController {

    private final MealService service;




    public MealRestController(MealService service) {

        this.service = service;
    }



    public MealTo create(MealTo mealTo)
    {
        int userId= SecurityUtil.authUserId();
  return   service.create(mealTo,userId);
}

public MealTo get (int id){
       int userId= SecurityUtil.authUserId();
    return service.get(id,userId);
}
public void delete (int id)
{
    int userId= SecurityUtil.authUserId();
    service.delete(id, userId);
}

public void update(MealTo mealTo){
    int userId= SecurityUtil.authUserId();
    service.update(mealTo,userId);
}
public List<MealTo> getAll(){
    int userId= SecurityUtil.authUserId();
        return service.getAll(userId);
}
}