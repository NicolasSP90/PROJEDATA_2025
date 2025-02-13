package projedata.desafio.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projedata.desafio.models.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioManager {

    @Autowired
    private FuncionarioRepository repositorio;

    public void salvarLista(List<FuncionarioSalvarDTO> listaFuncionarios) {
        listaFuncionarios.forEach(t -> repositorio.save(new Funcionario(t)));
    }

    public ResponseEntity desativarID(int id) {
        var funcionario = repositorio.getFuncionarioById(id);
        if (funcionario.is_active()) {
            funcionario.deletarFucionario();
            repositorio.save(funcionario);
            return ResponseEntity.ok("Funcionário id: %s desativado com sucesso!".formatted(funcionario.getId()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity desativarNome(String nome) {
        var funcionario = repositorio.getFuncionarioByNome(nome);
        if (funcionario.is_active()) {
            funcionario.deletarFucionario();
            repositorio.save(funcionario);
            return ResponseEntity.ok("Funcionário %s desativado com sucesso!".formatted(funcionario.getNome()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity ativarID(int id) {
        var funcionario = repositorio.getFuncionarioById(id);
        if (funcionario.is_active()) {
            return ResponseEntity.ok("Funcionário id:%s já está ativo!".formatted(funcionario.getId()));
        } else {
            funcionario.ativarFuncionario();
            repositorio.save(funcionario);
            return ResponseEntity.ok("Funcionário id:%s reativado com sucesso!".formatted(funcionario.getId()));
        }
    }

    public ResponseEntity ativarNome(String nome) {
        var funcionario = repositorio.getFuncionarioByNome(nome);
        if (funcionario.is_active()) {
            return ResponseEntity.ok("Funcionário %s já está ativo!".formatted(funcionario.getNome()));
        } else {
            funcionario.ativarFuncionario();
            repositorio.save(funcionario);
            return ResponseEntity.ok("Funcionário %s reativado com sucesso!".formatted(funcionario.getNome()));
        }
    }

    public ResponseEntity todosFuncionarios(String agrupar, List<Integer> meses, String filtro, String ordem) {
        var listaFuncionarios = repositorio.getAllActive();

        if ("maiorIdade".equalsIgnoreCase(filtro)) {
            Optional<Funcionario> maiorIdade = listaFuncionarios.stream()
                    .max(Comparator.comparingInt(Funcionario::calcularIdade));

            if (maiorIdade.isPresent()) {
                return ResponseEntity.ok(new FuncionarioNomeIdadeDTO(maiorIdade.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        if (meses != null && !meses.isEmpty()) {
            listaFuncionarios = listaFuncionarios.stream()
                    .filter(f -> meses.contains(f.getDataNascimento().getMonthValue()))
                    .toList();
        }


        if ("alfabetica".equalsIgnoreCase(ordem)) {
            listaFuncionarios.sort(Comparator.comparing(Funcionario::getNome));
        }

        if ("funcao".equalsIgnoreCase(agrupar)) {
            Map<String, List<FuncionarioExibirDTO>> funcionarioFuncao = listaFuncionarios.stream()
                    .collect(Collectors.groupingBy(Funcionario::getFuncao,
                            Collectors.mapping(FuncionarioExibirDTO::new, Collectors.toList())));

            return ResponseEntity.ok(funcionarioFuncao);
        }

        List<FuncionarioExibirDTO> listaFuncionariosDTO = new ArrayList<>();
        listaFuncionarios.forEach(f -> listaFuncionariosDTO.add(new FuncionarioExibirDTO(f)));
        return ResponseEntity.ok(listaFuncionariosDTO);
    }

    public ResponseEntity reajusteSalarios(BigDecimal porcentagem) {
        var listaFuncionarios = repositorio.getAllActive();
        listaFuncionarios.forEach(f -> f.reajusteSalario(porcentagem));
        repositorio.saveAll(listaFuncionarios);

        return ResponseEntity.ok("Salarios reajustados em %s%% !".formatted(porcentagem));
    }

    public ResponseEntity totalSalarios() {
        BigDecimal total = repositorio.totalSalarios();
        return ResponseEntity.ok(total);
    }

    public ResponseEntity salariosMinimos(BigDecimal valorSalarioMinimo) {
        var listaFuncionarios = repositorio.getAllActive();

        List<FuncionarioSalariosMinimosDTO> listaFuncionariosSalariosMinimos = new ArrayList<>();

        listaFuncionarios.forEach(f -> listaFuncionariosSalariosMinimos.add(new FuncionarioSalariosMinimosDTO(f, valorSalarioMinimo)));
        return ResponseEntity.ok(listaFuncionariosSalariosMinimos);
    }
}