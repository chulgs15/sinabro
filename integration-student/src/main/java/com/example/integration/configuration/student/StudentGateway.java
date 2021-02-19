package com.example.integration.configuration.student;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessagingException;

@MessagingGateway(errorChannel = "student.channel.error")
public interface StudentGateway {

  @Gateway(requestChannel = "students.channel.splitter")
  public void registerStudents(String value) throws MessagingException;

}
