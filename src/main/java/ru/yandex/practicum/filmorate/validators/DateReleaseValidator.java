package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateReleaseValidator implements ConstraintValidator<DateRelease, LocalDate> {

    private static final LocalDate DATE_LIMIT = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        return localDate.isAfter(DATE_LIMIT) || localDate.isEqual(DATE_LIMIT);
    }
}
