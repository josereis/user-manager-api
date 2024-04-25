package com.josereis.usermanagerapi.persistence.specification;

import com.josereis.usermanagerapi.domain.dto.filter.SpecificationFilter;
import com.josereis.usermanagerapi.domain.enums.QueryOperatorEnum;
import com.josereis.usermanagerapi.shared.utils.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class GenericSpecification<T> {
    public static SpecificationFilter builderSpecificationFilter(QueryOperatorEnum operator, String key, List<Object> values) {
        return SpecificationFilter.builder()
                .key(key)
                .values(values)
                .operator(operator)
                .build();
    }

    public static SpecificationFilter builderSpecificationFilter(QueryOperatorEnum operator, String key, Object value) {
        return SpecificationFilter.builder()
                .operator(operator)
                .key(key)
                .value(value)
                .build();
    }

    public static <T> Specification<T> checkSpecification(Specification<T> current, Specification<T> recent, String condition) {
        if(current != null) {
            if(condition.equals("and")) {
                return current.and(recent);
            } else {
                return current.or(recent);
            }
        } else {
            return recent;
        }
    }

    protected static <T> Specification<T> createSpecification(SpecificationFilter filter) {
        switch (filter.getOperator()) {
            case IN:
                return in(filter.getKey(), filter.getValues());
            case EQUAL:
                return equals(filter.getKey(), filter.getValue());
            case NOT_EQUAL:
                return notEquals(filter.getKey(), filter.getValue());
            case IS_NOT_NULL:
                return isNotNull(filter.getKey());
            case LESS_THAN:
                return lessThan(filter.getKey(), filter.getValue());
            case GREATER_THAN:
                return greaterThan(filter.getKey(), filter.getValue());
            case LESS_THAN_OR_EQUAL:
                return lessThanOrEqualTo(filter.getKey(), filter.getValue());
            case GREATER_THAN_OR_EQUAL:
                return greaterThanOrEqualTo(filter.getKey(), filter.getValue());
            case LIKE:
                return like(filter.getKey(), filter.getValue());
            case IS_NULL:
                return isNull(filter.getKey());
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private static <T> Specification<T> isNotNull(String key) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.isNotNull(root.get(key));
        };
    }

    protected static <T> Specification<T> between(String key, Object startOfValue, Object endOfValue) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startOfValue instanceof LocalDate && endOfValue instanceof LocalDate) {
                return builder.between(root.get(key), (LocalDate) startOfValue, (LocalDate) endOfValue);
            } else if (startOfValue instanceof LocalDateTime && endOfValue instanceof LocalDateTime) {
                return builder.between(root.get(key), (LocalDateTime) startOfValue, (LocalDateTime) endOfValue);
            } else {
                throw new IllegalArgumentException("{errors.jpa.different-type-objects}");
            }
        };
    }

    private static <T> Specification<T> like(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like(
                    builder.function("translate", String.class,
                        builder.lower(root.get(key)), builder.literal("ÁÀÂÃÄáàâãäÉÈÊËéèêëÍÌÎÏíìîïÓÒÕÔÖóòôõöÚÙÛÜúùûüÇç"),
                        builder.literal("AAAAAaaaaaEEEEeeeeIIIIiiiiOOOOOoooooUUUUuuuuCc")),
                    "%"+ StringUtils.removeAccent(value.toString().toLowerCase(Locale.ROOT))+"%"
            );
        };
    }

    private static <T> Specification<T> equals(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get(key), value);
        };
    }

    private static <T> Specification<T> notEquals(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.notEqual(root.get(key), value);
        };
    }

    private static <T> Specification<T> in(String key, Collection<?> values) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.in(root.get(key)).value(values);
        };
    }

    private static <T> Specification<T> greaterThan(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if(value instanceof LocalDate) {
                return builder.greaterThan(root.get(key), (LocalDate) value);
            }

            return ((Specification<T>) gt(key, value)).toPredicate(root, query, builder);
        };
    }

    public static <T> Specification<T> greaterThanOrEqualTo(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return ((Specification<T>) greaterThan(key, value).or(equals(key, value))).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> lessThan(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if(value instanceof LocalDate) {
                return builder.lessThan(root.get(key), (LocalDate) value);
            }

            return ((Specification<T>) lt(key, value)).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> lessThanOrEqualTo(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return ((Specification<T>) lessThan(key, value).or(equals(key, value))).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> gt(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.gt(root.get(key), (Number) value);
        };
    }

    private static <T> Specification<T> lt(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.lt(root.get(key), (Number) value);
        };
    }

    private static <T> Specification<T> isNull(String key) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.isNull(root.get(key));
        };
    }

    protected static <T> Specification<T> startsWith(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like( builder.lower(root.get(key).as(String.class)), value.toString().toLowerCase(Locale.ROOT)+"%");
        };
    }

    protected static <T> Specification<T> startsWithCpfCnpj(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            String val = value.toString().replace(".", "").replace("-", "").replace("/", "").toLowerCase(Locale.ROOT);
            var replacePonto = builder.function("replace", String.class, builder.lower(root.get(key).as(String.class)), builder.literal("."),
                    builder.literal(""));
            var replaceTraco = builder.function("replace", String.class, replacePonto, builder.literal("-"),
                    builder.literal(""));
            var replaceBarra = builder.function("replace", String.class, replaceTraco, builder.literal("/"),
                    builder.literal(""));

            return builder.like(replaceBarra,val+"%");
        };
    }

    protected static <T> Specification<T> cpfCnpjWithoutLike(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            String val = value.toString().replace(".", "").replace("-", "").replace("/", "").toLowerCase(Locale.ROOT);
            var replacePonto = builder.function("replace", String.class, builder.lower(root.get(key).as(String.class)), builder.literal("."),
                    builder.literal(""));
            var replaceTraco = builder.function("replace", String.class, replacePonto, builder.literal("-"),
                    builder.literal(""));
            var replaceBarra = builder.function("replace", String.class, replaceTraco, builder.literal("/"),
                    builder.literal(""));

            return builder.equal(replaceBarra,val);
        };
    }
}
