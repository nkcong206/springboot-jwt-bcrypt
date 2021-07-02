package com.example.JWT.repositories;

import com.example.JWT.entities.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<MyUser, Integer> {
    MyUser findByUsername(String usernname);
    //MyUser findByUsernameAndPassword(String usernname, String password);
}
