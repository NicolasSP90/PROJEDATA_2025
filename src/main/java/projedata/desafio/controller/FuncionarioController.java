package projedata.desafio.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projedata.desafio.manager.FuncionarioManager;
import projedata.desafio.models.FuncionarioSalvarDTO;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioManager gerenciador;

    // Adicionar por Lote
    @PostMapping("/batch_import")
    @Transactional
    public ResponseEntity importarLotes(@RequestBody @Valid List<FuncionarioSalvarDTO> listaFuncionarios) {
        gerenciador.salvarLista(listaFuncionarios);
        return ResponseEntity.ok().build();
    }

    // GET - Visualizar Um Funcionario (ID / Nome)
    // POST - Criar um Funcionario
    // PUT - Atualizar um Funcionário

    // Desativar por ID
    @DeleteMapping("/deactivate/id/{id}")
    @Transactional
    public ResponseEntity desativarFuncionarioID(@PathVariable int id) {
        return gerenciador.desativarID(id);
    }

    // Desativar por Nome
    @DeleteMapping("/deactivate/nome/{nome}")
    @Transactional
    public ResponseEntity desativarFuncionarioNome(@PathVariable String nome) {
        return gerenciador.desativarNome(nome);
    }

    // Ativar por ID
    @PutMapping("/activate/id/{id}")
    @Transactional
    public ResponseEntity ativarFuncionarioID(@PathVariable int id) {
        return gerenciador.ativarID(id);
    }

    // Ativar por Nome
    @PutMapping("/activate/nome/{nome}")
    @Transactional
    public ResponseEntity ativarFuncionarioNome(@PathVariable String nome) {
        return gerenciador.ativarNome(nome);
    }

    // Retornar todos os clientes ativos
    @GetMapping("/all")
    public ResponseEntity getAllFuncionarios(
            @RequestParam(required = false) String agrupar,
            @RequestParam(required = false) List<Integer> meses,
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) String ordem) {
        return gerenciador.todosFuncionarios(agrupar, meses, filtro, ordem);
    }

    // Reajuste Salários
    @PostMapping("/reajuste/{porcentagem}")
    @Transactional
    public ResponseEntity reajusteSalarios(@PathVariable BigDecimal porcentagem) {
        return gerenciador.reajusteSalarios(porcentagem);
    }


}
