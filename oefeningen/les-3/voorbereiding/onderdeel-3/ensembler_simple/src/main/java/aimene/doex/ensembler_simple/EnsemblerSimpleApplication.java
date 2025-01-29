package aimene.doex.ensembler_simple;

import aimene.doex.ensembler_simple.model.Email;
import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;
import aimene.doex.ensembler_simple.repository.EnsembleRepository;
import aimene.doex.ensembler_simple.repository.MemberRepository;
import aimene.doex.ensembler_simple.service.EnsembleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class EnsemblerSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsemblerSimpleApplication.class, args);
	}

	@Bean
	CommandLineRunner run(EnsembleRepository ensembleRepository,
						  EnsembleService ensembleService,
						  MemberRepository memberRepository) {
		return args -> {
			memberRepository.saveAll(List.of(
					new Member("Jane", new Email("jane@jane.com")),
					new Member("John", new Email("john@john.com")),
					new Member("Jack", new Email("jack@jack.com")),
					new Member("Jill", new Email("jill@jill.com"))
			));


			ensembleRepository.saveAll(List.of(
					new Ensemble("Ensemble 1"),
					new Ensemble("Ensemble 2")
		   	));


			Ensemble ensemble1 = ensembleRepository.findById(1).orElseThrow();

			ensembleService.joinAsParticipant(ensemble1, memberRepository.findByName("John").orElseThrow());
			ensembleService.joinAsParticipant(ensemble1, memberRepository.findByName("Jane").orElseThrow());

			ensembleRepository.save(ensemble1);

		};
	}
}
