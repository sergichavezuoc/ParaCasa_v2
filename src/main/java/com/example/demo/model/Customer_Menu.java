package com.example.demo.model;


import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "customer_menu")
public class Customer_Menu implements Serializable{
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu_id;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer_id;
    
    @Column(name = "quantity")
    private int quantity;
    public Customer_Menu() {
	}
    @Column(name = "orderid")
    private long orderid;
    public Customer_Menu(Customer customer, Menu menu, int quantity, long orderid) {
    this.customer_id=customer;
    this.menu_id=menu;
    this.quantity=quantity;     
    this.orderid=orderid;
	}

    public long getId() {
            return id;
        }
    public long getQuantity() {
            return quantity;
        }
    public String getCustomer() {
            return customer_id.getName();
        }
   public long getCustomerid() {
            return customer_id.getId();
        }
        public long getMenuid() {
            return menu_id.getId();
        }
    public String getMenu() {
            return menu_id.getDescription();
        }
        public long getOrderid() {
            return orderid;
        }
        public void setOrderid(long orderid) {
            this.orderid=orderid;
        }
        @Override
        public String toString() {
            return "Customer_Menu [id=" + id + ", customer=" + customer_id + ", menu=" + menu_id + ", quantity=" + quantity + ", orderid=" + orderid + "]";
        }   
}
