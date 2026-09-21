package  com.example.tp.models.validators.dateRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.start();
        this.endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object startValue = getFieldValue(value, startFieldName);
            Object endValue = getFieldValue(value, endFieldName);

            if (startValue == null || endValue == null) {
                return true;
            }

            LocalDateTime start = toLocalDateTime(startValue);
            LocalDateTime end = toLocalDateTime(endValue);

            if (start == null || end == null) {
                return true;
            }

            return start.isBefore(end) || start.isEqual(end);

        } catch (Exception e) {
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof LocalDateTime ldt) {
            return ldt;
        }
        if (value instanceof LocalDate ld) {
            return ld.atStartOfDay();
        }
        return null;
    }
}
