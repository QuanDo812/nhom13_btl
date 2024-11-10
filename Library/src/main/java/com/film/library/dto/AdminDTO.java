package com.film.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AdminDTO {

    private String username;
    @Size(min=3, max = 15, message="Invalid!(3 - 10 characters)")
    private String password;
    @Size(min=3, max = 10, message="Invalid!(3 - 10 characters)")
    private String firstName;
    @Size(min=3, max = 10, message="Invalid!(3 - 10 characters)")
    private String lastName;
    private String repeatPassword;
}
