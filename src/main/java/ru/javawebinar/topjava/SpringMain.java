package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MockRepository;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;



public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
      /*  try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealServiceImpl mealService=appCtx.getBean(MealServiceImpl.class);
           // MealRepository mealRepository=appCtx.getBean(MockRepository.class);
           // System.out.println(mealService.get(1,1));
         //   System.out.println(mealRepository.getClass().getSimpleName());
            MealRestController mealRestController=appCtx.getBean(MealRestController.class);
          List<MealTo> list= mealRestController.getAll();
            list.stream().forEach(mealTo -> System.out.println(mealTo.toString()));*/

         //   AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
          //  adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
       // }
    }
}
