package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer,User> repaUsers=new ConcurrentHashMap<>();
    private AtomicInteger counter=new AtomicInteger();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repaUsers.remove(id)!=null;

    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        return repaUsers.put(user.getId(),user);

    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repaUsers.get(id);

    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = new ArrayList<>();
        userList.addAll(repaUsers.values());
        Collections.sort(userList, Comparator.comparing(User::getName)
                .thenComparing(User::getId));

        return userList;
    }



    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
          return  repaUsers.values().stream()
            .filter(user -> email.equals(user.getEmail()))
            .findAny()
            .orElse(null);
    }
}
