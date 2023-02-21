package com.epam.ld.module2.testing.annotations;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(DisableOnWeekends.DisableOnWeekendsImpl.class)
public @interface DisableOnWeekends {
    class DisableOnWeekendsImpl implements ExecutionCondition {
        @Override
        public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
            ConditionEvaluationResult result;
            DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
            return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                    ? ConditionEvaluationResult.disabled("Tests is disabled. Today is weekend")
                    : ConditionEvaluationResult.enabled("Tests is enabled. Today is working day");
        }
    }
}
