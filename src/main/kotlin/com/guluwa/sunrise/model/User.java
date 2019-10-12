package com.guluwa.sunrise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 662692455422902539L;
    public Long id;
    public String name;
    public int age;

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
