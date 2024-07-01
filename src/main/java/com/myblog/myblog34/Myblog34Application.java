package com.myblog.myblog34;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class Myblog34Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog34Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return  new ModelMapper();
	}

}
