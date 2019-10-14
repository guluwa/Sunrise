package com.guluwa.sunrise.mapper;

import com.guluwa.sunrise.model.People;

import java.util.List;

public interface UserMapper {

    List<People> getAll();

    People getUser(String id);

    Long insertUser(People user);

    Long updateUser(People user);

    Long deleteUser(String id);
}
