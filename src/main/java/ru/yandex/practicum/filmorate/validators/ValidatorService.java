package ru.yandex.practicum.filmorate.validators;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Entity;

import javax.validation.*;
import java.util.Set;

@Service
public class ValidatorService {

    public boolean isValid(Entity entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return true;
    }

}
