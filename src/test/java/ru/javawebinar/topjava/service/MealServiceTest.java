package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(
        {"classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml"}
)
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config=@SqlConfig(encoding="UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception{
        Meal meal= service.get(MEAL_ID,USER_ID);
       assertThat(meal).isEqualToIgnoringGivenFields(MEAL,"dateTime","id");
    }



    @Test
    public void getBetweenDates() throws Exception {

        List<Meal> mealListActual=service.getBetweenDates(STARTDATE,ENDDATE, USER_ID);
        Predicate<Meal> betweenDatesAfter=meal -> meal.getDate().isAfter(STARTDATE)||
                                             meal.getDate().isEqual(STARTDATE);
        Predicate<Meal> betweenDatesBefore=meal -> meal.getDate().isBefore(ENDDATE)||
                meal.getDate().isEqual(ENDDATE);
        Predicate<Meal> betweenDates=betweenDatesAfter.and(betweenDatesBefore);
       List<Meal> expectedList= MEALS1.stream().filter(betweenDates).collect(Collectors.toList());
       assertThat(mealListActual).hasSize(expectedList.size()).hasSameElementsAs(expectedList);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealListActual=service.getBetweenDateTimes(LocalDateTime.of(STARTDATE,STARTTIME),
                                  LocalDateTime.of(ENDDATE,ENDTIME),USER_ID);
        Predicate<Meal> betweenLDTAfter=meal -> meal.getDateTime().isAfter(LocalDateTime.of(STARTDATE,STARTTIME))||
                                        meal.getDateTime().isEqual(LocalDateTime.of(STARTDATE,STARTTIME));
        Predicate<Meal> betweenLDTBefore=meal -> meal.getDateTime().isBefore(LocalDateTime.of(ENDDATE,ENDTIME))||
                meal.getDateTime().isEqual(LocalDateTime.of(ENDDATE,ENDTIME));
        Predicate<Meal> betweenLocalDateTimes=betweenLDTAfter.and(betweenLDTBefore);
        List<Meal> expectedList=MEALS1.stream().filter(betweenLocalDateTimes).collect(Collectors.toList());
        assertThat(mealListActual).hasSize(expectedList.size()).hasSameElementsAs(expectedList);
    }


    @Test(expected = NotFoundException.class)
    public void deleteNotFoundMeal() throws Exception{

      service.delete(MEAL_ID,ADMIN_ID);}

   @Test(expected = NotFoundException.class)
   public void getNotFound () throws Exception{
        service.get(MEAL_ID, ADMIN_ID);
   }

    @Test(expected = NotFoundException.class)
    public void updateNotFound ()throws Exception{

        Meal meal=new Meal(MEAL);
        meal.setDescription("Обновленный завтрак");
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
      Map<Integer,List<Meal>> mp=MEALSMap.entrySet().stream()

              .collect(Collectors.toMap(Map.Entry::getKey, e->new ArrayList<Meal>(e.getValue())));

        List<Meal> mealList=mp.get(USER_ID);
        mealList.removeIf(m->m.getId()==MEAL_ID);
        service.delete(MEAL_ID, USER_ID);
        List<Meal> meals2=service.getAll(USER_ID);
        assertThat(meals2).hasSize(mealList.size()).hasSameElementsAs(mealList);

    }

    @Test
    public void getAll()throws Exception {
        List<Meal> mealList=service.getAll(USER_ID);
        assertThat(mealList).hasSize(MEALS1.size()).hasSameElementsAs(MEALS1);
    }

    @Test
    public void update() throws Exception {
        Meal mealUp=new Meal(MEAL);
        mealUp.setDescription("Завтрак_Новый");
        mealUp.setCalories(333);
        mealUp.setDateTime(DateTimeUtil.parseLocalDateTime("2021-03-19 19:19"));
        service.update(mealUp, USER_ID);
        Meal meal=service.get(MEAL_ID,USER_ID);

        assertThat(meal).isEqualToIgnoringGivenFields(mealUp, "id","date_time");
    }

    @Test
    public void create() throws Exception {


        service.create(MEAL,USER_ID);
        Meal meal=service.get(MEAL_ID,USER_ID);
        assertThat(meal).isEqualToIgnoringGivenFields(MEAL, "id","date_time");
    }
}