package projedata.desafio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
// @EqualsAndHashCode(of = "id")
public class Funcionario extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal salario;

    @NotBlank
    private String funcao;

    @NotNull
    private boolean is_active;

    public Funcionario(FuncionarioSalvarDTO funcionario) {
        super(funcionario.nome(), funcionario.dataNascimento());
        this.id = null;
        this.salario = funcionario.salario();
        this.funcao = funcionario.funcao();
        this.is_active = true;
    }

    public void deletarFucionario() {
        this.is_active = false;
    }

    public void ativarFuncionario() {
        this.is_active = true;
    }

    public void reajusteSalario(BigDecimal porcentagem) {
        var multiplicador = porcentagem.divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
        this.salario = this.salario.multiply(multiplicador);
    }

    public BigDecimal salariosMinimos(BigDecimal valorSalarioMinimo) {
        return this.salario.divide(valorSalarioMinimo, 2, RoundingMode.HALF_UP);
    }
}
