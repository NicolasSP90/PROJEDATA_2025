package projedata.desafio.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioSalvarDTO(
        @NotBlank
        String nome,

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @JsonAlias("data_nascimento")
        LocalDate dataNascimento,

        @NotNull
        BigDecimal salario,

        @NotBlank
        String funcao ) {
}
