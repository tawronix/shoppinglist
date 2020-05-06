package com.javaguru.shoppinglist.controller;

import com.javaguru.shoppinglist.domain.Product;
import com.javaguru.shoppinglist.dto.ProductDTO;
import com.javaguru.shoppinglist.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController implements ErrorHandler {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable Long id) {
        return new ProductDTO(productService.findById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<ProductDTO> findByName(@RequestParam("name") String name) {
        Optional<Product> foundProduct = productService.findByName(name);
        return foundProduct.map(product -> ResponseEntity.ok(new ProductDTO(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Product product) {
        Long id = productService.save(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        productService.update(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
