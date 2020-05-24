package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeRepository;
import com.example.demo.exception.EmployeeNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeRepository emplRepository;
	
	@GetMapping("/employees")
	List<Employee> getAll()
	{
		return emplRepository.findAll();
	}
	
	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee employee)
	{
		return emplRepository.save(employee);
	}
	
	/*
	 * @GetMapping("/employees/{id}") Employee getEmployeeById(@PathVariable Long
	 * id) throws Exception { return emplRepository.findById(id).orElseThrow(()->new
	 * EmployeeNotFoundException(id)); }
	 */
	
	@PutMapping("/employees/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee,@PathVariable Long id)
	{

		return emplRepository.findById(id)
					   .map(emp -> {
						   			emp.setName(newEmployee.getName());
						   			return emplRepository.save(emp);
						   			
					   })
					   .orElseGet(()->
					   {
						   newEmployee.setId(id);
						   newEmployee.setRole("None");
						   return emplRepository.save(newEmployee);
					   });
		
	}
	
	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id)
	{
		emplRepository.deleteById(id);
	}
	
	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable Long id) throws EmployeeNotFoundException {

	  Employee employee = emplRepository.findById(id)
	    .orElseThrow(() -> new EmployeeNotFoundException(id));

	  return new EntityModel<>(employee,
	    linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
	    linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees"));
	}
	
}
