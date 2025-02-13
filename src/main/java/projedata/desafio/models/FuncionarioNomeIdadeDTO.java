package projedata.desafio.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import projedata.desafio.serializer.BigDecimalCustomSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioNomeIdadeDTO(
        String nome,
        Integer idade
) {

    public FuncionarioNomeIdadeDTO (Funcionario funcionario) {
        this(funcionario.getNome(), funcionario.calcularIdade());
    }
}
