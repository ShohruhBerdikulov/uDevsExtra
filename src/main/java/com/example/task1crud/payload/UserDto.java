package com.example.task1crud.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String phone;
}
