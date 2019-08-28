package com.perezm27.CodeFellowship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@SpringBootApplication
public class CodeFellowshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeFellowshipApplication.class, args);
	}

}

