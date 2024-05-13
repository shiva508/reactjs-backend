package com.comrade.reactjsbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long roleId;

    @Column(updatable = false, unique = true, nullable = false)
    private String name;

}
