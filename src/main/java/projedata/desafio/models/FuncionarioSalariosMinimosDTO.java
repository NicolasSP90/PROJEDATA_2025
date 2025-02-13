package projedata.desafio.models;

import java.math.BigDecimal;

public record FuncionarioSalariosMinimosDTO(
        String nome,
        BigDecimal salariosMinimos
) {

    public FuncionarioSalariosMinimosDTO(Funcionario funcionario, BigDecimal valorSalarioMinimo) {
        this(funcionario.getNome(), funcionario.salariosMinimos(valorSalarioMinimo));
    }
}
