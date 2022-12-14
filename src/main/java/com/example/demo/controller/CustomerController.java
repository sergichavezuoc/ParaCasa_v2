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
import com.example.demo.model.Customer;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.CustomerRepository;


@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")



public class CustomerController {


  @Autowired
  CustomerRepository customerRepository;

  @GetMapping("/customers")
  public String getAllCustomers(@RequestParam(required = false) String title, Model model) {
    try {
      List<Customer> customers = new ArrayList<Customer>();

      if (title == null)
        customerRepository.findAll().forEach(customers::add);
      else
       customerRepository.findByName(title).forEach(customers::add);

      if (customers.isEmpty()) {
        return "customers";
      }
      model.addAttribute("customers", customers);
      return "customers";
    } catch (Exception e) {
      return "error";
    }
  }
 
  @GetMapping("/customers/{id}")
  public ResponseEntity<Customer> getTutorialById(@PathVariable("id") long id) {
    Optional<Customer> customerData = customerRepository.findById(id);

    if (customerData.isPresent()) {
      return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping("/customers/add")
  public String addCustomer(Model model) {
 
      return "nuevo_customer";
  }

  @PostMapping("/customers")
  public String createCustomer(@RequestParam String name, @RequestParam String surname, @RequestParam String email) {
    System.out.println("identificador de customer");

  
      Customer customerRequest = new Customer(name, surname, email);
      customerRepository.save(customerRequest);
      return "creado";  
    
  }
  @GetMapping("/customers/{id}/edit")
  public String getCustomerById(@PathVariable("id") long id, Model model) {
    Optional<Customer> customerData = customerRepository.findById(id);

    if (customerData.isPresent()) {
      model.addAttribute("customer", customerData.get());
      return "modificar_customer";
    } else {
      return "noencontrado";
    }
  }

  @PutMapping("/customers/{id}")
  public String updateTutorial(@PathVariable("id") long id, @RequestParam String name, @RequestParam String surname, @RequestParam String email) {
    Optional<Customer> customerData = customerRepository.findById(id);

    if (customerData.isPresent()) {
      Customer _customer = customerData.get();
      _customer.setName(name);
      _customer.setSurname(surname);
      _customer.setEmail(email);
      customerRepository.save(_customer);
      return "modificado";
    } else {
      return "id"+id;
    }
  }
  

  @DeleteMapping("/customers/del/{id}")
  public String deleteCustomer(@PathVariable("id") long id) {
    try {
      customerRepository.deleteById(id);
      return "borrado";
    } catch (Exception e) {
      return "error";
    }
  }

  @DeleteMapping("/customers")
  public ResponseEntity<HttpStatus> deleteAllCustomers() {
    try {
      customerRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }


}