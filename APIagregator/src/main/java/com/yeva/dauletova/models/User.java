package com.yeva.dauletova.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
    @OneToOne(mappedBy = "user")
    private EmailConfirmationToken verificationToken;
    @Column(columnDefinition = "boolean default false")
    private boolean verified;
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

}
