package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final  int MEAL_ID=START_SEQ+2;
    public static final LocalDateTime TIME=LocalDateTime.now();
    public static final Meal MEAL=new Meal(MEAL_ID,TIME,"Завтрак",300);
    public static final LocalDate STARTDATE=LocalDate.parse("2020-02-15");
    public static final LocalDate ENDDATE=LocalDate.parse("2020-02-17");
    public static final LocalTime STARTTIME= LocalTime.parse("10:10");
    public static final LocalTime ENDTIME= LocalTime.parse("14:10");

    public static final List<Meal> MEALS1 =new ArrayList<>( Arrays.asList(
            new Meal (100002, DateTimeUtil.parseLocalDateTime("2020-02-15 10:10"),"Завтрак",300),
            new Meal (100003,DateTimeUtil.parseLocalDateTime("2020-02-15 14:10"),"Обед",330),
            new Meal (100004, DateTimeUtil.parseLocalDateTime("2020-02-15 18:10"),"Ужин",290)));
    public static final List<Meal> MEALS2 = new ArrayList<>(Arrays.asList(
            new Meal (100005, DateTimeUtil.parseLocalDateTime("2020-02-15 10:10"),"Завтрак",400),
            new Meal (100006, DateTimeUtil.parseLocalDateTime("2020-02-15 14:10"),"Обед",390),
            new Meal (100007, DateTimeUtil.parseLocalDateTime("2020-02-15 18:10"),"Ужин",295),
            new Meal (100008, DateTimeUtil.parseLocalDateTime("2021-03-17 09:35"),"Завтрак",500),
            new Meal (100009, DateTimeUtil.parseLocalDateTime("2021-03-17 12:34"),"Обед2",1000)));
    public static final Map<Integer,List<Meal>> MEALSMap=new ConcurrentHashMap<>();

    static {MEALSMap.put(100000,MEALS1);
            MEALSMap.put(100001,MEALS2);}
}
