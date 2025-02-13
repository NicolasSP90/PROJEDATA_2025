package projedata.desafio.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Funcionario getFuncionarioById(int id);

    Funcionario getFuncionarioByNome(String nome);

    @Query("SELECT f FROM Funcionario f WHERE f.is_active = true ORDER BY f.id ASC")
    List<Funcionario> getAllActive();

    @Query("SELECT SUM(f.salario) FROM Funcionario f")
    BigDecimal totalSalarios();
}
