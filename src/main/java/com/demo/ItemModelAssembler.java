package com.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

    @Override
    public EntityModel<Item> toModel(Item entity) {
        return EntityModel.of(entity, //
                linkTo(methodOn(ItemController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).all()).withRel("employees"));
    }
}
