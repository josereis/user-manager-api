package com.josereis.usermanagerapi.domain.entity;

import com.josereis.usermanagerapi.domain.enums.UserSituationEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "tb_users")
public class User extends GenericEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(columnDefinition = "TEXT")
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 50, columnDefinition = "varchar(50) default 'PENDING_ACTIVATION'")
    private UserSituationEnum situation = UserSituationEnum.PENDING_ACTIVATION;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRole> roles;
}
