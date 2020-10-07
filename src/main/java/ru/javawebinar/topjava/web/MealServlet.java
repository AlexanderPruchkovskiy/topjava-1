package ru.javawebinar.topjava.web;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext applicationContext=   new ClassPathXmlApplicationContext("spring/spring-app.xml");
        MealRestController mealRestController=applicationContext.getBean(MealRestController.class);
        mealRestController.getAll();
        repository = applicationContext.getBean(InMemoryMealRepositoryImpl.class);
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");


        Meal meal = new Meal(Strings.isNullOrEmpty(id) ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal,repository.getUserId());


        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String startDateStr=request.getParameter("startDate");
        String startTimeStr=request.getParameter("startTime");
        switch (action == null ? "all" : action) {
            case "filteringByDate" :
                request.setAttribute("meals", MealsUtil.getWithExcess(
                        repository.getFilterByDate(repository.getUserId(), LocalDate.parse(request.getParameter("startDate")),
                                LocalDate.parse(request.getParameter("finishDate"))),MealsUtil.DEFAULT_CALORIES_PER_DAY));
                break;
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                repository.delete(id,repository.getUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request),repository.getUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");

                     if ((startDateStr!=null)||(startTimeStr!=null)){
                         if (startDateStr!=null)
                         request.setAttribute("meals", MealsUtil.getWithExcess(
                                 repository.getFilterByDate(repository.getUserId(), LocalDate.parse(request.getParameter("startDate")),
                                         LocalDate.parse(request.getParameter("finishDate"))),MealsUtil.DEFAULT_CALORIES_PER_DAY));
                         if (startTimeStr!=null)
                         request.setAttribute("meals", MealsUtil.getWithExcess(
                                 repository.getFilterByTime(repository.getUserId(), LocalTime.parse(request.getParameter("startTime")),
                                         LocalTime.parse(request.getParameter("finishTime"))),MealsUtil.DEFAULT_CALORIES_PER_DAY));
           }
                     else {
                request.setAttribute("meals",
                        MealsUtil.getWithExcess(repository.getAll(repository.getUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY));}
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
