package com.example.demo.controllers;

import java.util.List;

import com.example.demo.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return new ResponseEntity<>(itemService.getItems(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		Item item = itemService.getItemById(id);
		return new ResponseEntity<>(item, item != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		return new ResponseEntity<>(itemService.getItemsByName(name), HttpStatus.OK);
	}
	
}
