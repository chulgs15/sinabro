package com.example.integration.controller;

import com.example.integration.configuration.student.StudentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

  private final StudentGateway studentGateway;

  @PostMapping(value = {"/students", "/student"},
      consumes = {MediaType.TEXT_PLAIN_VALUE})
  public void registerStudents(@RequestBody String text) {
    studentGateway.registerStudents(text);
  }

}
