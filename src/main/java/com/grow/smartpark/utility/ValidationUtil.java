package com.grow.smartpark.utility;

import com.grow.smartpark.model.ValidationRule;
import com.grow.smartpark.repository.ValidationRuleRepository;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    private final ValidationRuleRepository ruleRepository;

    public ValidationUtil(ValidationRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public boolean isValidLicensePlate(String plate) {
        String regex = ruleRepository.findByRuleName("LICENSE_PLATE_REGEX")
                .map(ValidationRule::getRuleValue)
                .orElse("^[A-Z0-9-]+$");
        return Pattern.matches(regex, plate);
    }

    public boolean isValidOwnerName(String name) {
        String regex = ruleRepository.findByRuleName("OWNER_NAME_REGEX")
                .map(ValidationRule::getRuleValue)
                .orElse("^[A-Za-z ]+$");
        return Pattern.matches(regex, name);
    }

    public boolean isValidLotId(String lotId) {
        int maxLength = ruleRepository.findByRuleName("LOT_ID_MAX_LENGTH")
                .map(rule -> Integer.parseInt(rule.getRuleValue()))
                .orElse(50);
        return lotId != null && lotId.length() <= maxLength;
    }
}
