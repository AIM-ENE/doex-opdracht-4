package aimene.doex.bestelling.controller;

import aimene.doex.bestelling.model.Geld;
import aimene.doex.bestelling.model.Product;
import aimene.doex.bestelling.repository.ProductRepository;
import aimene.doex.bestelling.repository.BestellingRepository;
import aimene.doex.bestelling.service.ProductService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producten")
public class ProductController {

    private final ProductRepository productRepository;
    private final BestellingRepository bestellingRepository;

    public ProductController(ProductRepository productRepository,
                             BestellingRepository bestellingRepository) {
        this.productRepository = productRepository;
        this.bestellingRepository = bestellingRepository;

    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable("id") Product product) {
        return product;
    }

//    @GetMapping("{id}/projection")
//    public ProductNaamDTO findProductNaamById(@PathVariable("id") Integer productId) {
//
//    }

    @PatchMapping("{id}/prijs")
    public void veranderPrijs(@PathVariable("id") Product product, @RequestBody Geld nieuwePrijs) {
        product.setPrijs(nieuwePrijs);
        productRepository.save(product);

        bestellingRepository.findAll().forEach(bestelling -> {
            bestelling.veranderStukPrijs(AggregateReference.to(product.getId()), nieuwePrijs);
            bestellingRepository.save(bestelling);
        });

    }
}