package aimene.doex.bestelling;

import aimene.doex.bestelling.model.Bestelling;
import aimene.doex.bestelling.model.Geld;
import aimene.doex.bestelling.model.Product;
import aimene.doex.bestelling.model.Valuta;
import aimene.doex.bestelling.repository.BestellingRepository;
import aimene.doex.bestelling.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@SpringBootApplication
public class BestellingApp {

	public static void main(String[] args) {
		SpringApplication.run(BestellingApp.class, args);
	}

	@Bean
	CommandLineRunner run(ProductRepository productRepository,
						  BestellingRepository bestellingRepository) {
		return args -> {
			/*
			 * Deze code kan efficiënter, maar ik vind het zelf wel lekker om
			 * bij code die data in de database zet vrij expliciet te zijn zodat
			 * ik in één keer zie welke data erin zit.
			 */

			Product tafel = new Product("tafel", new Geld(25000, Valuta.EUR));
			Product stoel = new Product("stoel", new Geld(7500, Valuta.EUR));
			Product bank = new Product("bank", new Geld(50000, Valuta.EUR));

			productRepository.saveAll(java.util.List.of(tafel, stoel, bank));

			Product tafelStored = productRepository.findById(1).orElseThrow();
			Product stoelStored = productRepository.findById(2).orElseThrow();
			Product bankStored = productRepository.findById(3).orElseThrow();

			AggregateReference<Product, Integer> tafelRef = AggregateReference.to(tafelStored.getId());
			AggregateReference<Product, Integer> stoelRef = AggregateReference.to(stoelStored.getId());
			AggregateReference<Product, Integer> bankRef = AggregateReference.to(bankStored.getId());

			Bestelling bestelling1 = new Bestelling();
			Bestelling bestelling2 = new Bestelling();

			bestelling1.voegProductToe(tafelRef, 1, tafelStored.getPrijs());
			bestelling1.voegProductToe(stoelRef, 4, stoelStored.getPrijs());

			bestelling2.voegProductToe(tafelRef, 1, tafelStored.getPrijs());
			bestelling2.voegProductToe(bankRef, 1, bankStored.getPrijs());

			bestellingRepository.save(bestelling1);
			bestellingRepository.save(bestelling2);

			System.out.println("Klaar met het 'seeden' van de database.");

		};
	}

}
