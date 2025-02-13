package projedata.desafio.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import projedata.desafio.serializer.BigDecimalCustomSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioExibirDTO (
        Long id,
        String nome,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @JsonSerialize(using = BigDecimalCustomSerializer.class)
        BigDecimal salario,
        String funcao
){
    public FuncionarioExibirDTO (Funcionario funcionario) {
        this(funcionario.getId(),
                funcionario.getNome(),
                funcionario.getDataNascimento(),
                funcionario.getSalario(),
                funcionario.getFuncao());
    }
}
