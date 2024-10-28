package aimene.doex.ensembler_simple.service;

import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;
import aimene.doex.ensembler_simple.repository.EnsembleRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class EnsembleService {

    private final EnsembleRepository ensembleRepository;
    private final NotifierService notifierService;

    public EnsembleService(EnsembleRepository ensembleRepository,
                           NotifierService notifierService) {
        this.ensembleRepository = ensembleRepository;
        this.notifierService = notifierService;

    }

    public void joinAsParticipant(Ensemble ensemble, Member member) {
        ensemble.joinAsParticipant(AggregateReference.to(member.getId()));
        ensembleRepository.save(ensemble);
        notifierService.memberAccepted(ensemble, member);

    }
}
