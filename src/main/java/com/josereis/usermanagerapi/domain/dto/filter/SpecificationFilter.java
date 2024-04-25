package com.josereis.usermanagerapi.domain.dto.filter;

import com.josereis.usermanagerapi.domain.enums.QueryOperatorEnum;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SpecificationFilter {
    private String key;
    private Object value;
    private List<?> values;
    private QueryOperatorEnum operator;
}
