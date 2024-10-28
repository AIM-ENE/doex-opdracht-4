package aimene.doex.ensembler_simple.model;

import org.apache.commons.validator.routines.EmailValidator;

public record Email(String mailAddress) {
    public Email {
        if (!EmailValidator.getInstance().isValid(mailAddress)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
