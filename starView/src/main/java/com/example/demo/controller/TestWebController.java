package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/test")
public class TestWebController{
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/subin")
	public String subin() {
		return "subinTest";
	}
}