package aimene.doex.ensembler_simple.service;

import aimene.doex.ensembler_simple.model.Ensemble;
import aimene.doex.ensembler_simple.model.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Profile("prod")
public class MailNotifierService implements NotifierService {

    private JavaMailSender mailSender;

    public MailNotifierService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void memberAccepted(Ensemble ensemble, Member member) {
        String banner = """               
                _______  _____  ______        _____   ______  _____   ______  ______ _______ _______ _______ _____ __   _  ______
                |  |  | |     | |_____]      |_____] |_____/ |     | |  ____ |_____/ |_____| |  |  | |  |  |   |   | \\  | |  ____
                |  |  | |_____| |_____]      |       |    \\_ |_____| |_____| |    \\_ |     | |  |  | |  |  | __|__ |  \\_| |_____|
                        """;

        String body = banner + "\n" +
                "Congratulations, " + member.getName() + "!\n" +
                "You have been accepted into the " + ensemble.getName() + ".\n";

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(member.getEmail().mailAddress());
        mail.setSubject("Accepted into " + ensemble.getName());
        mail.setText(body);

        mailSender.send(mail);
    }
}
