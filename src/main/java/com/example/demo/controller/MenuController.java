package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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

import com.example.demo.model.Menu;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.RestaurantRepository;


@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")



public class MenuController {

  @Autowired
  MenuRepository menuRepository;
  @Autowired
  RestaurantRepository restaurantRepository;

  @GetMapping("/menus")
  public String getAllTutorials(@RequestParam(required = false) String title, Model model) {
    try {
      List<Menu> menus = new ArrayList<Menu>();

      if (title == null)
        menuRepository.findAll().forEach(menus::add);
      else
        menuRepository.findByTitleContaining(title).forEach(menus::add);

      if (menus.isEmpty()) {
        return "menus";
      }
      model.addAttribute("menus", menus);
      return "menus";
    } catch (Exception e) {
      return "error";
    }
  }
  @GetMapping("/restaurants/{restaurantId}/menus")
  public String getAllCommentsByTutorialId(@PathVariable(value = "restaurantId") Long restaurantId, Model model) {
 
    List<Menu> menus = menuRepository.findByRestaurantId(restaurantId);
    model.addAttribute("menus", menus);
    return "menus";
  }
  @GetMapping("/menus/{id}")
  public ResponseEntity<Menu> getTutorialById(@PathVariable("id") long id) {
    Optional<Menu> menuData = menuRepository.findById(id);

    if (menuData.isPresent()) {
      return new ResponseEntity<>(menuData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping("/menus/add")
  public String addMenu(Model model) {
    List<Restaurant> restaurants = new ArrayList<Restaurant>();
    restaurantRepository.findAll().forEach(restaurants::add);
    model.addAttribute("restaurants", restaurants);
      return "nuevo_menu";
  }

  @PostMapping("/menus")
  public String createMenu(@RequestParam String title, @RequestParam String description, @RequestParam long tutorialsid) {
    System.out.println("identificador de menu");

    Optional<Restaurant> menu = restaurantRepository.findById(tutorialsid);
    
    if (menu.isPresent()) {
      Menu menuRequest = new Menu(title, description, false, menu.get());
      menuRepository.save(menuRequest);
      return "creado";
    } else {
      return "error";
    }
    
    
  }
  @GetMapping("/menus/{id}/edit")
  public String getMenuById(@PathVariable("id") long id, Model model) {
    Optional<Menu> menuData = menuRepository.findById(id);

    if (menuData.isPresent()) {
      List<Restaurant> restaurants = restaurantRepository.findAll();
      model.addAttribute("restaurants", restaurants);
      model.addAttribute("menu", menuData.get());
      return "modificar_menu";
    } else {
      return "noencontrado";
    }
  }

  @PutMapping("/menus/{id}")
  public String updateTutorial(@PathVariable("id") long id, @RequestParam String title, @RequestParam String description, @RequestParam long restaurant) {
    Optional<Menu> menuData = menuRepository.findById(id);
Optional<Restaurant> restaurantData =restaurantRepository.findById(restaurant);
    if (menuData.isPresent() && restaurantData.isPresent()) {
      Menu _menu = menuData.get();
      _menu.setTitle(title);
      _menu.setDescription(description);
      _menu.setTutorial(restaurantData.get());
      menuRepository.save(_menu);
      return "modificado";
    } else {
      return "id"+id;
    }
  }
  

  @DeleteMapping("/menus/del/{id}")
  public String deleteMenu(@PathVariable("id") long id) {
    try {
      menuRepository.deleteById(id);
      return "borrado";
    } catch (Exception e) {
      return "error";
    }
  }

  @DeleteMapping("/menus")
  public ResponseEntity<HttpStatus> deleteAllMenus() {
    try {
      menuRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/menus/published")
  public ResponseEntity<List<Menu>> findByPublished() {
    try {
      List<Menu> menus = menuRepository.findByPublished(true);

      if (menus.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(menus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}