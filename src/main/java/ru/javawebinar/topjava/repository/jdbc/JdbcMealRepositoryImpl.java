package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {


    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER= BeanPropertyRowMapper.newInstance(Meal.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertMeal ;
@Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                               .withTableName("Meals")
                               .usingGeneratedKeyColumns("id");

    }

    @Override
    public Meal save(Meal meal, int userId) {



    String sqlStr="UPDATE meals SET description=:description, date_time=:date_time," +
            "calories=:calories where user_id=:user_id AND id=:id";
        MapSqlParameterSource mapSqlParameterSource=new MapSqlParameterSource()
                .addValue("user_id",userId)
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("date_time",meal.getDateTime())
                .addValue("calories",meal.getCalories());
    if (meal.isNew()){

                Number intKey=insertMeal.executeAndReturnKey(mapSqlParameterSource);
                meal.setId(intKey.intValue());

        }
else
   if ( namedParameterJdbcTemplate.update(sqlStr,mapSqlParameterSource)==0){
       return null;
   }


      return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
   return( jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?",id, userId)!=0);
        
    }

    @Override
    public Meal get(int id, int userId) {
    String sqlStr="SELECT*FROM meals WHERE id=? AND user_id=?";
    List<Meal> result=jdbcTemplate.query(sqlStr,ROW_MAPPER,id,userId);
        return  DataAccessUtils.singleResult(result );

    }

    @Override
    public List<Meal> getAll(int userId){
       return jdbcTemplate.query("SELECT*FROM meals WHERE user_id=? ORDER BY date_time DESC, description DESC ",
               ROW_MAPPER, userId);

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT*FROM meals WHERE user_id=? AND ((date_time>=?)AND(date_time<=?))" +
                        "ORDER BY date_time, description DESC ",
                ROW_MAPPER, userId, startDate, endDate);

    }
}
