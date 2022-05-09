package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table (name = "roles")
@Getter
@RequiredArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String rolename;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> user;



    public Role(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String getAuthority() {
        return rolename;
    }

    @Override
    public String toString() {
        return rolename;
    }
}
