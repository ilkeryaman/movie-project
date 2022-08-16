package com.eri.model;

import javax.validation.constraints.NotBlank;

public abstract class Person {
    @NotBlank(message = "name is mandatory!")
    private String name;

    @NotBlank(message = "surname is mandatory!")
    private String surname;

    public Person(){

    }

    public Person(String name, String surname){
        setName(name);
        setSurname(surname);
    }

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
