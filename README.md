# Enunciado

## TESTE PRÁTICO PROGRAMAÇÃO.

Considerando que uma indústria possui as pessoas/funcionários abaixo:

![Tabela](assets/tabela.png)

Diante disso, você deve desenvolver um projeto java, com os seguintes requisitos:

1– Classe Pessoa com os atributos: nome (String) e data nascimento (LocalDate).

2 – Classe Funcionário que estenda a classe Pessoa, com os atributos: salário (BigDecimal) e função (String).

3 – Deve conter uma classe Principal para executar as seguintes ações:

3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.

3.2 – Remover o funcionário “João” da lista.

3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:

* informação de data deve ser exibido no formato dd/mm/aaaa;
* informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.

3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.

3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.

3.6 – Imprimir os funcionários, agrupados por função.

3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.

3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.

3.10 – Imprimir a lista de funcionários por ordem alfabética.

3.11 – Imprimir o total dos salários dos funcionários.

3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.

### Orientações gerais:
* você poderá utilizar a ferramenta que tem maior domínio (exemplos: eclipse, netbeans etc);
* após finalizado o desenvolvimento, exportar o projeto e encaminhar o link do seu teste aqui mesmo na etapa Mão na Massa 🖐.

Basta Colar o link ainda aqui nessa etapa.
• Assim que recebermos seu projeto desenvolvido, será agendada uma entrevista com nosso time técnico para avaliação.

Esperamos que você use todo seu conhecimento e criatividade nesse teste.

Caso você não souber resolver determinado requisito comente no código que aquele item você não sabe como desenvolver, e vá para o próximo. Avaliaremos o que você conseguiu desenvolver e como foi desenvolvido.

Boa sorte!

# Desenvolvimento

A idéia geral do projeto é a implementação de CRUD além do retorno de algumas informações específicas. Dessa forma, decidi realizar o projeto como uma API REST, com integração com banco de dados MySQL.
O projeto foi inicializado com o [Spring Initializr](https://start.spring.io), utilizando as seguintes dependências:
* Spring Boot 3.4.2
* JDK 23 - Como não foram especificadas versões, foi utilizada a versão mais recente
* Maven - gerenciamento de pacotes para auxiliar no desafio
* Spring Web - Para criação das requisições em aplicação REST
* PostgreSQL Driver - Dependência para conexão com banco de dados PostgreSQL
* Flyway Migration - Versionamento do banco de dados
* Spring Data JPA - API de persistência de dados
* Lombok - Biblioteca de Anotações
* Spring Boot DevTools - Auxilia nas inicilizações da aplicação
* Jackson - Lidar com arquivos no formato JSON

O IntelliJ utiliza configurações de região. Para evitar problemas as configurações a seguir foram adicionadas à VM:
* -Duser.language=en
* -Duser.country=US, 
* -Duser.variant=US

* Para o Lombok, alterar a configuração de *Build, Execution, Deployment* > *Compiler* > *Annotation Processors* > selecionar o item *Obtain processors from project classpath*

* Como o projeto vai ser desenvolvido como uma API REST, será utilizado o Postman para realização das requisições.

## Projeto

### Models

O projeto foi iniciado com a criação dos modelos *Funcionario* e *Pessoa*, além da modelagem dos atributos e tipos de dados de cada um. 

Nesse ponto foi criado um atributo *is_active* em *Funcionário* para que o DELETE seja lógico (o registro vai ser mantido no banco de dados, mas não vai ser exibido).

Além do modelo, foram criados:
* FuncionarioRepository - Interface para JPA e integração com Banco de Dados
* FuncionarioController - Especificações das requisições e as respostas
* FuncionarioManager - Funções e métodos relacionados às requisições

### Migrations

Primeiro foi criado o database db_projedata

````
CREATE DATABASE db_projedata
````

Após isso a primeira migration é realizada para criação de tabela e tipos de dados: *V1__create-table.sql*.

A segunda migration está relacionada ao preenchimento do banco de dados com os dados do enunciado: *V2__fill-db.sql* 

A terceira migration irá remover os dados do banco de dados (segunda migration é apenas para teste dos dados). Os dados serão reinseridos a partir de requisições.

### Resolução dos Itens Propostos

#### Classe Pessoa

Criação da classe com os atributos necessários e anotações para facilitar a manutenção de código. 

Foi necessário adicionar a anotação *@MappedSuperclass* além do *@Column* de cada atributo por se tratar de uma classe que será herdada pela *Funcionario*, que é efetivamente a classe que será a entidade do banco de dados.

#### Classe Funcionario

Criação da classe com os atributos necessários e anotações para facilitar a manutenção de código.

Foi adicionado um atributo *is_active* para que o DELETE do banco seja LÓGICO, ou seja, o registro será mantido mas não será possível acessar.

#### Inserir Todos os Funcionários

Foi realizado de duas formas:
* Migration - A primeira forma foi a partir de migration (*V2__fill-db.sql*) onde os registros foram adicionados. Foi adicionada uma nova migration para exclusão dos registros realizar a mesma operação utilizando requisições. 
* Requisição em Lote - Foi utilizada uma requisição POST a partir de *localhost:8080/funcionatios/batch_import* com JSON de todos os funcionários

Endpoints:
```
localhost:8080/funcionarios/batch_import
```

#### Remover o Funcionario João

Realizado o DELETE Lógico do João. Pode ser feito com o nome ou id

Endpoints:
```
localhost:8080/funcionarios/deactivate/id/{id}

localhost:8080/funcionarios/deactivate/nome/{nome}
```

Também está configurado a ativação de um funcionario desativado:

```
localhost:8080/funcionarios/activate/id/{id}

localhost:8080/funcionarios/activate/nome/{nome}
```

#### Exibir Todos os Funcionários e Informações

Necessário utiliza anotações e um Serializer personalizado para as configurações específicas de formatação.

Endpoint:
```
localhost:8080/funcionarios/all
```

#### Reajuste Saários

Utiliza uma porcentagem para realização do ajuste. Ex: reajuste de 10%.

Endpoint:
```
localhost:8080/funcionarios/reajuste/{porcentagem}
```

#### Agrupar por Função

Retorna todos os usuários e suas informações, agrupados por função. Feito como parâmetro opcional do Endpoint *```funcionarios/all```*

Endpoint:
```
localhost:8080/funcionarios/all?agrupar=funcao
```

#### Aniversário em meses específicos

No projeto pede especificamente 10 e 12, mas foi realizado de modo que uma lista de meses deve ser informada. Feito como parâmetro opcional do Endpoint *```funcionarios/all```*

Endpoint
```
localhost:8080/funcionarios/all?meses=10,12
```

#### Maior Idade

Retorna o funcionário com maior idade com o método de cálculo especificado na classe *Pessoa*

Endpoint
```
localhost:8080/funcionarios/all?filtro=maiorIdade
```

#### Ordem Alfabética

Retorna a lista de funcionários em ordem alfabética.

```
localhost:8080/funcionarios/all?ordem=alfabetica
```

#### Total dos Salários

Cálculo do valor total dos saários dos funcionários.

```
localhost:8080/funcionarios/totalsalarios
```

#### Quantidade de Salários Mínimos

Cálculo da quantidade de salários mínimos. O valor do salário mínimo é informado como path variable.

```
localhost:8080/funcionarios/salariosminimos/1212.00
```

### Execução do Endpoint ```/all```

O endpoint ```localhost:8080/funcionarios/all``` possui a seguinte ordem de execução dos parâmetros opcionais:
* filtro (maiorIdade) - é independente e retorna um valor sem considerar os demais parâmetros
* meses - Filtra a lista de funcionarios pelos meses de nascimento especificados. Funciona em conjunto com outros parâmetros. 
* ordem - Filtra a lista de funcionários de acordo com a ordem alfabética. Funciona em conjunto com outros parâmetros.
* agrupar - Agrupa a lista por função. Funciona em conjunto com outros parâmetros.


### Documentação Endpoints

Os endpoints podem ser visualizados em ambiente local com swagger em
* Requisições - http://localhost:8080/swagger-ui/index.html#/
* Endpoints - http://localhost:8080/v3/api-docs

Os JSON podem ser encontrados em:
* [Swagger](swagger)
* [Postman](postman)