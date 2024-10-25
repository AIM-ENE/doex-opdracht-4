package aimene.doex.ensembler_simple.service;

import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;

public interface NotifierService {

    void memberAccepted(Ensemble ensemble, Member member);
}
