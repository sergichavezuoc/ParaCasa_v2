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
import com.example.demo.model.Customer_Menu;
import com.example.demo.model.Customer;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.Customer_MenuRepository;


@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")



public class Customer_MenuController {


  @Autowired
  Customer_MenuRepository customer_MenuRepository;
  @Autowired
  MenuRepository menuRepository;
  @Autowired
  CustomerRepository customerRepository;
  @GetMapping("/orders")
  public String getAllOrders( @RequestParam(required = false) Long customerid,Model model) {
 
      List<Customer_Menu> orders = new ArrayList<Customer_Menu>();
      if (customerid == null){
        customer_MenuRepository.findAllGrouped().forEach(orders::add);
      }
      else {
        customer_MenuRepository.findAllGrouped(customerid).forEach(orders::add);
      }
      if (orders.isEmpty()) {
        return "orders";
      }
      model.addAttribute("orders", orders);
      return "orders";
  }
 

  @GetMapping("/orders/add")
  public String addCustomer(Model model) {
    List<Menu> menus = new ArrayList<Menu>();
    menuRepository.findAll().forEach(menus::add);
    List<Customer> customers = new ArrayList<Customer>();
    customerRepository.findAll().forEach(customers::add);
    model.addAttribute("customers", customers);
    model.addAttribute("menus", menus);
      return "nuevo_order";
  }

   @PostMapping("/orders")
  public String createCustomer_Order(@RequestParam long customerid, @RequestParam long menuid,  @RequestParam int quantity,  @RequestParam long orderid) {
    Optional<Customer> customer = customerRepository.findById(customerid);
    Optional<Menu> menu = menuRepository.findById(menuid);
    if (menu.isPresent() && customer.isPresent()) {
      Customer_Menu customer_MenuRequest = new Customer_Menu(customer.get(), menu.get(), quantity, orderid);
      customer_MenuRepository.save(customer_MenuRequest);
    }
  

      return "creado";  
    
  }
  @GetMapping("/orders/{id}/edit")
  public String getCustomer_MenuById(@PathVariable("id") long id, Model model) {
    Optional<Customer_Menu> customer_MenuData = customer_MenuRepository.findById(id);

    if (customer_MenuData.isPresent()) {
      model.addAttribute("customer_Menu", customer_MenuData.get());
      List<Customer> customer = customerRepository.findAll();
      List<Menu> menu = menuRepository.findAll();
      Customer_Menu order = customer_MenuData.get();
      List<Customer_Menu> menusinorder = customer_MenuRepository.findByOrderid(order.getOrderid());
      model.addAttribute("customers", customer);
      model.addAttribute("menus", menu);
      model.addAttribute("menusinorder", menusinorder);
      return "modificar_order";
    } else {
      return "noencontrado";
    }
  }
  @DeleteMapping("/orderinmenu/del/{id}")
  public String deleteCustomer(@PathVariable("id") long id) {
    try {
      customer_MenuRepository.deleteById(id);
      return "borrado";
    } catch (Exception e) {
      return "error";
    }
  }
  @DeleteMapping("/order/del/{id}")
  public String deleteOrder(@PathVariable("id") long id) {
    try {
      customer_MenuRepository.deleteById(id);
      return "borrado";
    } catch (Exception e) {
      return "error";
    }
  }
/*
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

 
 */
}