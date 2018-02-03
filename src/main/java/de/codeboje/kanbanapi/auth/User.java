package de.codeboje.kanbanapi.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable{
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column(length=50, nullable = false, unique = true)
    @NotEmpty
    @Size(min=4, max=50, message="length must be between {min} and {max} characters")
    private String username;
 
    @Column(length=100)
    @NotEmpty
    @Size(groups={UserCreateValidationGroup.class}, min=6, max=40, message="length must be between {min} and {max} characters")
    private String password;

}
