package com.demo;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ItemController {

    private final ItemRepository repository;
    private final ItemModelAssembler assembler;

    ItemController(ItemRepository repository, ItemModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/items")
    public CollectionModel<EntityModel<Item>> all() {

        List<EntityModel<Item>> items = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(items, linkTo(methodOn(ItemController.class).all()).withSelfRel());
    }

    @PostMapping("/items")
    public Item newItem(@RequestBody Item item) {
        return repository.save(item);
    }

    @GetMapping("/items/{id}")
    EntityModel<Item> one(@PathVariable Long id) {

        Item item = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));

        return assembler.toModel(item);
    }

    @PutMapping("/items/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Long id) {

        return repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setPrice(newItem.getPrice());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/items/{id}")
    void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }

}