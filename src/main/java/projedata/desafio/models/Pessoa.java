package projedata.desafio.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Pessoa {

    @NotBlank
    @Column(name = "nome")
    protected String nome;

    @NotNull
    @Column(name = "data_nascimento")
    protected LocalDate dataNascimento;

}
