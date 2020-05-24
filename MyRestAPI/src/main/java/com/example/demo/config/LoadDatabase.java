package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeRepository;

@Configuration
@Slf4j
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {
    return args -> {
      System.out.println("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
      System.out.println("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
  }
}