package com.josereis.usermanagerapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "tb_roles")
public class Role extends GenericEntity {
    private String name;

    @Builder.Default
    @Column(columnDefinition = "boolean default true")
    private Boolean active = Boolean.TRUE;

    private String prettyName;

    private String description;
}
