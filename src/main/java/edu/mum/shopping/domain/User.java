package edu.mum.shopping.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    @NotBlank
    @Email(message="{errors.invalid_email}")
    private String email;
    @NotBlank
    @Size(min=4)
    private String password;
    private String phone;
    private String address;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages;
}
