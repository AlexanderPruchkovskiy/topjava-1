package ru.javawebinar.topjava.web;


import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.TimeUtil;
import sun.rmi.runtime.Log;
import static org.slf4j.LoggerFactory.getLogger;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class
MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRepositoryImpl mealRepository;
    public MealServlet(){
        super();
        mealRepository=new MealRepositoryImpl();
    }

    @Override

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        log.info("getAll List of Meals");

   if (action==null){
       request.setAttribute("meals", mealRepository.getAll());
          }
   else{
   switch (action){
       case "delete":
                     mealRepository.delete(Integer.parseInt(request.getParameter("mealId")));
                   //  request.setAttribute("meals", mealRepository.getAll());

                     break;

       case "update":
           int idMeal=Integer.parseInt(request.getParameter("mealId"));
           Meal meal=mealRepository.get(idMeal);
           request.setAttribute("meal", meal);
           break;
   }
       request.setAttribute("meals", mealRepository.getAll());
   }

        RequestDispatcher view=request.getRequestDispatcher("/meals.jsp");
        view.forward(request,response);

    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String stringId=request.getParameter("id");
        if ((stringId==null)||stringId.isEmpty()){
            mealRepository.save(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),request.getParameter("description"),
                                Integer.parseInt(request.getParameter("calories"))));
        }

        else {
           Meal meal= mealRepository.get(Integer.parseInt(stringId));
           meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
           meal.setDescription(request.getParameter("description"));
           meal.setCalories(Integer.parseInt(request.getParameter("calories")));
           mealRepository.save(meal);
        }
        request.setAttribute("meals", mealRepository.getAll());
        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        view.forward(request,response);
    }}