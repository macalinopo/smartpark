package com.grow.smartpark.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "validation_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", unique = true, nullable = false, length = 100)
    private String ruleName;

    @Column(name = "rule_value", nullable = false, length = 255)
    private String ruleValue;

    @Column(name = "description", length = 255)
    private String description;
}
