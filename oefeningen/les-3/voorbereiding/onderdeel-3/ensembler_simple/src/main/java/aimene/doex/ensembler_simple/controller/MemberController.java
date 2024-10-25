package aimene.doex.ensembler_simple.controller;

import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;
import aimene.doex.ensembler_simple.service.EnsembleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
public class MemberController {

    private final EnsembleService ensembleService;

    public MemberController(EnsembleService ensembleService) {
        this.ensembleService = ensembleService;
    }

    @GetMapping("{id}")
    public Member findById(@PathVariable("id") Member member) {
        return member;
    }

    @PostMapping("{id}/ensembles/{ensemble_id}/join")
    public void joinEnsemble(@PathVariable("id") Member member,
                             @PathVariable("ensemble_id")Ensemble ensemble) {

       ensembleService.joinAsParticipant(ensemble, member);
    }
}
