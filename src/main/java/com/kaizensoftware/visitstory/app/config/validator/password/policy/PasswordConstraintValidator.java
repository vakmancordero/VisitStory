package com.kaizensoftware.visitstory.app.config.validator.password.policy;

import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordDTO;
import com.kaizensoftware.visitstory.app.dto.validation.InvalidPasswordOutDTO;
import com.kaizensoftware.visitstory.app.service.validation.PasswordService;
import org.passay.*;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    private PasswordService passwordService;

    private DictionaryRule dictionaryRule;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.dictionaryRule = dictionaryRule();
    }

    private DictionaryRule dictionaryRule() {

        DictionaryRule dictionaryRule = null;

        try {

            String words = passwordService.findAllInvalidPasswords().stream()
                    .map(InvalidPasswordOutDTO::getPassword)
                    .collect(Collectors.joining("\n"));

            dictionaryRule = new DictionaryRule(
                    new WordListDictionary(WordLists.createFromReader(
                            new BufferedReader[] {
                                    new BufferedReader(new StringReader(words))
                            },
                            false,
                            new ArraysSort()
                    ))
            );

        } catch (IOException ignored) { }

        return dictionaryRule;
    }

    private List<Rule> rules() {

        return Arrays.asList(

                // at least 8 characters
                new LengthRule(8, 30),

                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),

                // no whitespace
                new WhitespaceRule(),

                dictionaryRule
        );

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        List<Rule> rules = rules();

        PasswordValidator passwordValidator = new PasswordValidator(rules);

        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }

        List<String> messages = passwordValidator.getMessages(result);
        String messageTemplate = messages.stream().collect(Collectors.joining(","));

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
