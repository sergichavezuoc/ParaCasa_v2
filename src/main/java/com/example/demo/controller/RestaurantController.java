package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepository;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")
public class RestaurantController {

  @Autowired
  RestaurantRepository tutorialRepository;

  @GetMapping("/restaurants")
  public String getAllTutorials(@RequestParam(required = false) String title, Model model) {
    try {
      List<Restaurant> restaurants = new ArrayList<Restaurant>();

      if (title == null)
        tutorialRepository.findAll().forEach(restaurants::add);
      else
        tutorialRepository.findByTitleContaining(title).forEach(restaurants::add);

      if (restaurants.isEmpty()) {
        return "greeting";
      }
      model.addAttribute("restaurants", restaurants);
      return "restaurants";
    } catch (Exception e) {
      return "greeting";
    }
  }

  @GetMapping("/restaurants/{id}")
  public ResponseEntity<Restaurant> getTutorialById(@PathVariable("id") long id) {
    Optional<Restaurant> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping("/restaurants/{id}/edit")
  public String getRestaurantById(@PathVariable("id") long id, Model model) {
    Optional<Restaurant> restaurantData = tutorialRepository.findById(id);

    if (restaurantData.isPresent()) {
      model.addAttribute("restaurant", restaurantData.get());
      return "modificar_restaurante";
    } else {
      return "noencontrado";
    }
  }
  @GetMapping("/restaurants/add")
  public String addRestaurant() {
      return "nuevo_restaurante";
  }
  @PostMapping("/restaurants")
  public String createRestaurant(@RequestParam String title, @RequestParam String description) {
    try {
      tutorialRepository.save(new Restaurant(title, description, false));
      return "creado";
    } catch (Exception e) {
      return "error";
    }
  }

  @PutMapping("/restaurants/{id}")
  public String updateTutorial(@PathVariable("id") long id, @RequestParam String title, @RequestParam String description) {
    Optional<Restaurant> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      Restaurant _tutorial = tutorialData.get();
      _tutorial.setTitle(title);
      _tutorial.setDescription(description);
      tutorialRepository.save(_tutorial);
      return "modificado";
    } else {
      return "id"+id;
    }
  }

  @DeleteMapping("/restaurants/del/{id}")
  public String deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialRepository.deleteById(id);
      return "borrado";
    } catch (Exception e) {
      return "error";
    }
  }

  @DeleteMapping("/restaurants")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/restaurants/published")
  public ResponseEntity<List<Restaurant>> findByPublished() {
    try {
      List<Restaurant> restaurants = tutorialRepository.findByPublished(true);

      if (restaurants.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(restaurants, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}