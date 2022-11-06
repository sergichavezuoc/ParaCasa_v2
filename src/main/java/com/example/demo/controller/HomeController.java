package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/")
public class HomeController {


  @GetMapping("/")
  public String home() {
      return "home";
  }
}