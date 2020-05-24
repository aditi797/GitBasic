package com.example.demo.exception;

public class EmployeeNotFoundException extends Exception {
	
	public EmployeeNotFoundException(Long id)
	{
		super("could not find emp with id "+ id);
	}

}
