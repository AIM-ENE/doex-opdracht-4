package aimene.doex.ensembler_simple.service;

import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class DummyNotifierService implements NotifierService {
    @Override
    public void memberAccepted(Ensemble ensemble, Member member) {
        String banner = """               
                _______  _____  ______        _____   ______  _____   ______  ______ _______ _______ _______ _____ __   _  ______
                |  |  | |     | |_____]      |_____] |_____/ |     | |  ____ |_____/ |_____| |  |  | |  |  |   |   | \\  | |  ____
                |  |  | |_____| |_____]      |       |    \\_ |_____| |_____| |    \\_ |     | |  |  | |  |  | __|__ |  \\_| |_____|
                        """;

        String message = banner + "\n" +
                "Congratulations, " + member.getName() + "!\n" +
                "You have been accepted into the " + ensemble.getName() + ".\n";

        System.out.println(message);
    }
}
