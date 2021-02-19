package com.example.integration.domain;


import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Student implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private String name;
  private Integer age;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  public Student() {

  }

  public Student(String name, Integer age, Gender gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }

  public enum Gender {
    MALE, FEMALE;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", sex=" + gender +
        '}';
  }
}
