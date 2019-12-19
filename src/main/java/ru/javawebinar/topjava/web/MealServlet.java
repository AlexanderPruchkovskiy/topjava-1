package ru.javawebinar.topjava.web;


import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import static ru.javawebinar.topjava.util.MealsUtil.MEALS;

public class
MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException{
        log.info("getAll List of Meals");
        request.setAttribute("meals", MealsUtil.getFilteredWithExcess(MEALS, LocalTime.MIN,
                LocalTime.MAX,2000));
        request.getRequestDispatcher("meals.jsp").forward(request,response);
    }
}
