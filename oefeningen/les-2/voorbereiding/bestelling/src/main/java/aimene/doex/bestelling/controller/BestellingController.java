package aimene.doex.bestelling.controller;

import aimene.doex.bestelling.model.Bestelling;
import aimene.doex.bestelling.model.Product;
import aimene.doex.bestelling.repository.BestellingRepository;
import aimene.doex.bestelling.repository.ProductRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bestellingen")
public class BestellingController {

    private final ProductRepository productRepository;
    private final BestellingRepository bestellingRepository;

    public BestellingController(BestellingRepository bestellingRepository, ProductRepository productRepository) {
        this.bestellingRepository = bestellingRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Bestelling> findAll() {
        return bestellingRepository.findAll();
    }

    @GetMapping("{id}")
    public Bestelling findById(@PathVariable("id") Bestelling bestelling) {
        return bestelling;
    }

    @PatchMapping("{id}/plaats")
    public void plaatstBestelling(@PathVariable("id") Bestelling bestelling) {
        bestelling.plaatsBestelling();
        bestellingRepository.save(bestelling);
    }

    @PostMapping("{id}/producten")
    public void voegProductToe(@PathVariable("id") Bestelling bestelling,
                           @RequestBody Map<String, Object> requestBody) {

        Product product = productRepository.findById((Integer) requestBody.get("productId")).orElseThrow();
        int aantal = (int) requestBody.get("aantal");

        AggregateReference<Product, Integer> productRef = AggregateReference.to(product.getId());

        bestelling.voegProductToe(productRef, aantal, product.getPrijs());

        bestellingRepository.save(bestelling);
    }
}
