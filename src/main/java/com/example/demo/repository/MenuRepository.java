package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  List<Menu> findByPublished(boolean published);
  List<Menu> findByTitleContaining(String title);
  List<Menu> findByRestaurantId(Long postId);
  @Transactional
  void deleteByRestaurantId(long restuarantlId);
}
