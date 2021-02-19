package com.example.integration.configuration.student;

import com.example.integration.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.ExecutorSubscribableChannel;

import javax.persistence.EntityManager;
import java.util.concurrent.Executors;

@MessageEndpoint
@RequiredArgsConstructor
public class StudentEndpoint {

  private final EntityManager entityManager;

  @Bean
  public IntegrationFlow registerStudentFlow() {
    return IntegrationFlows.from("students.channel.splitter")
        .split(String.class, s -> s.split("\n"))
        .transform(String.class, this::transformToStudent)
        .filter(filterAge())
        .log()
        .enrichHeaders(h -> {
          h.headerExpression(FileHeaders.FILENAME, "payload.name.concat('.txt')");
          h.header("directory", "d:/integration/");
        })
        .channel(new ExecutorSubscribableChannel())
        .publishSubscribeChannel(Executors.newCachedThreadPool(),
            pub -> {
              pub
                  .subscribe(sub -> {
                    sub.handle(studentJPAExecutor(),
                        e -> e.transactional());
                  })
                  .subscribe(sub -> {
                    sub.transform(new ObjectToJsonTransformer())
                        .handle(saveFiles());
                  });
            })
        .get();
  }

  private GenericSelector<Student> filterAge() {
    return source -> {
      if (!(source.getAge() < 30)) {
        System.out.printf("%s 의 나이(%d)가 너무 많습니다.%n",
            source.getName(), source.getAge());
      }
      return source.getAge() < 30;
    };
  }

  private Student transformToStudent(String text) {
    String[] tokens = text.split("\\|");
    String name = tokens[1].trim();
    Integer age = Integer.valueOf(tokens[2].trim());
    Student.Gender gender = Student.Gender.valueOf(tokens[3].trim());
    return new Student(name, age, gender);
  }


  private MessageHandler studentJPAExecutor() {
    return Jpa.outboundAdapter(this.entityManager)
        .entityClass(Student.class)
        .persistMode(PersistMode.PERSIST)
        .get();
  }


  private MessageHandler saveFiles() {
    return Files.outboundAdapter(m -> m.getHeaders().get("directory"))
        .autoCreateDirectory(true)
        .fileExistsMode(FileExistsMode.APPEND)
        .appendNewLine(true)
        .get();
  }

  // https://github.com/manojp1988/spring-integration/blob/master/javadsl/src/main/java/ErrorChannel/ErrorChannelExample.java
  @Bean
  public IntegrationFlow errorHandling() {
    return IntegrationFlows.from("student.channel.error")
        .handle(new GenericHandler<ErrorMessage>() {
          @Override
          public Object handle(ErrorMessage payload, MessageHeaders headers) {
            MessagingException exception = ((MessagingException) payload.getPayload());
            System.err.println(exception.getFailedMessage().getPayload());
            System.err.println(exception.getRootCause().getLocalizedMessage());
            return null;
          }
        })
        .get();
  }

}
