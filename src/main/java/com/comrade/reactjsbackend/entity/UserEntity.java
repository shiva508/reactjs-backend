package com.comrade.reactjsbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_PROFILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @ManyToMany(fetch =  FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "USER_ROLE",
              joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "userId"),
              inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId"))
    private Set<RoleEntity> roles = new HashSet<>();
}
