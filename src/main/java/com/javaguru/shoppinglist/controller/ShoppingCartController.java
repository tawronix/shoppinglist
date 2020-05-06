package com.javaguru.shoppinglist.controller;

import com.javaguru.shoppinglist.domain.ShoppingCart;
import com.javaguru.shoppinglist.dto.ShoppingCartDTO;
import com.javaguru.shoppinglist.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController implements ErrorHandler {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/{id}")
    public ShoppingCartDTO findById(@PathVariable Long id) {
        return new ShoppingCartDTO(shoppingCartService.findById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<ShoppingCartDTO> findByName(@RequestParam("name") String name) {
        Optional<ShoppingCart> foundShoppingCart = shoppingCartService.findByName(name);
        return foundShoppingCart.map(shoppingCart -> ResponseEntity.ok(new ShoppingCartDTO(shoppingCart)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ShoppingCartDTO> findAll() {
        return shoppingCartService.findAll().stream().map(ShoppingCartDTO::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ShoppingCart shoppingCart) {
        Long id = shoppingCartService.save(shoppingCart);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setId(id);
        shoppingCartService.update(shoppingCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shoppingCartService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
