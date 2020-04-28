package com.javaguru.shoppinglist.controller;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.dto.ShoppingCartDTO;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController implements ErrorHandler {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    public ShoppingCartDTO getShoppingCart(@PathVariable Long id) {
        return new ShoppingCartDTO(shoppingCartService.findById(id));
    }

    @GetMapping
    public List<ShoppingCartDTO> getAllShoppingCarts() {
        List<ShoppingCartDTO> shoppingCartList = new ArrayList<>();
        shoppingCartService.findAll().forEach(shoppingCart -> shoppingCartList.add(new ShoppingCartDTO(shoppingCart)));
        return shoppingCartList;
    }

    @PostMapping
    public ResponseEntity<Void> saveShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        Long id = shoppingCartService.save(shoppingCart);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setId(id);
        shoppingCartService.update(shoppingCart);
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.delete(id);
    }
}
