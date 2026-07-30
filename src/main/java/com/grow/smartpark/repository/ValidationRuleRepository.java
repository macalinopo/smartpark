package com.grow.smartpark.repository;

import com.grow.smartpark.model.ValidationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {
    Optional<ValidationRule> findByRuleName(String ruleName);

}
