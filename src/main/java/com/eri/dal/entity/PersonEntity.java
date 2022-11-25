package com.eri.dal.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersonEntity extends BaseEntity {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
