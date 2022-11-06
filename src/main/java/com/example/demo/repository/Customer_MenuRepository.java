package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Customer_Menu;

public interface Customer_MenuRepository extends JpaRepository<Customer_Menu, Long> {
  List<Customer_Menu> findByOrderid(long orderid);
@Query(value="select * from customer_menu GROUP BY orderid", nativeQuery=true)
List<Customer_Menu> findAllGrouped();
@Query(value="select * from customer_menu WHERE customer_menu.customer_id= :customerid GROUP BY orderid", nativeQuery=true)
List<Customer_Menu> findAllGrouped(Long customerid);
@Modifying
@Query(value = "UPDATE customer_menu c set c.customer_id =:customer_id where c.id = :id",nativeQuery = true)
void updateCustomerMenu(@Param("customer_id") long customer_id, @Param("id") long id);
  @Transactional
  void deleteById(long customerId);
}
