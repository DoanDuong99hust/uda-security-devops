package com.example.demo.service;

import com.example.demo.model.persistence.Item;

import java.util.List;

public interface ItemService {

     List<Item> getItems();
     Item getItemById(Long id);
     List<Item> getItemsByName(String name);
}
