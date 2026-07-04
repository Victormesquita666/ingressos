Projeto final da disciplina de Banco de Dados, desenvolvido com Spring Boot, Java e PostgreSQL. O sistema permite o cadastro de usuários, gerenciamento de locais e eventos, e a venda de ingressos com controle de capacidade máxima e limite de compras por usuário.

## Equipe

  Nome completo | RGM |

| Glauco Lucio Melo de Menezes Filho | 42811155 |
| Victor Gabriel Mesquita Muniz Farias | 42888671 |

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot** (Web, Data JPA, Security, Validation)
- **Gradle** (Kotlin DSL)
- **PostgreSQL**
- **JWT** (io.jsonwebtoken) — autenticação stateless
- **Lombok** — redução de boilerplate
- **Swagger / OpenAPI** — documentação interativa da API

## Estrutura do projeto

```
src/main/java/com/example/ingressos/
├── config/          → configuração do Swagger/OpenAPI
├── controllers/      → endpoints REST (Auth, Usuário, Local, Evento, Ingresso)
├── exceptions/        → tratamento global de erros (400, 404)
├── models/
│   ├── dto/           → objetos de entrada (FormDTO) e saída (DTO)
│   └── entities/       → entidades JPA mapeadas para o banco
├── repositories/      → interfaces Spring Data JPA
├── security/           → JWT (TokenService, SecurityFilter, SecurityConfig)
└── services/            → regras de negócio
```

## Modelo de dados

- **Usuario** — dados do cliente/comprador (relação `@OneToOne` com Endereco)
- **Endereco** — endereço do usuário
- **Local** — estabelecimento onde ocorrem os eventos (teatros, estádios, etc.)
- **Evento** — evento cultural vinculado a um Local (`@ManyToOne`), com data, capacidade máxima e preço do ingresso
- **Ingresso** — entidade de transação, vinculando Usuario e Evento (`@ManyToOne` para ambos), com data da compra e valor pago

## Regras de negócio implementadas

1. **Capacidade máxima do evento**: não é possível emitir novos ingressos quando a capacidade máxima do local/evento já foi atingida (retorna status `400`).
2. **Limite por usuário**: cada usuário pode comprar no máximo **5 ingressos** para o mesmo evento (retorna status `400`).
3. **Email único**: não é possível cadastrar dois usuários com o mesmo email (retorna status `400`).

## Segurança

- Autenticação via **JWT** (stateless, sem sessão no servidor).
- Rotas públicas: `POST /usuarios` (cadastro), `POST /auth/login` (login) e documentação Swagger.
- Todas as demais rotas exigem o cabeçalho `Authorization: Bearer <token>`.
- Senhas armazenadas com hash **BCrypt**, nunca em texto puro.

## Endpoint de estatísticas

`GET /ingressos/resumo` retorna, calculado em memória:
- Total de ingressos vendidos
- Receita total arrecadada
- Taxa de ocupação média dos eventos
- Evento com maior número de vendas

## Como rodar o projeto localmente

### Pré-requisitos
- Java 21 ou superior
- PostgreSQL instalado e rodando
- Git

### Passo a passo

**1.** Clone o repositório:
```bash
git clone https://github.com/Victormesquita666/ingressos.git
cd ingressos
```

**2.** Crie o banco de dados no PostgreSQL (via pgAdmin ou terminal):
```sql
CREATE DATABASE ingressos_db;
```

**3.** Configure a conexão em `src/main/resources/application.properties` com o usuário e senha do **seu** PostgreSQL local:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ingressos_db
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

**4.** Rode a aplicação:
```bash
./gradlew bootRun
```
*(No Windows: `.\gradlew bootRun`)*

**5.** Acesse a documentação interativa (Swagger) para testar os endpoints:
```
http://localhost:8080/swagger-ui/index.html
```

As tabelas do banco são criadas automaticamente na primeira execução (via Hibernate `ddl-auto=update`).

## Principais endpoints

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| POST | `/usuarios` | Cadastrar usuário | Pública |
| POST | `/auth/login` | Login (retorna JWT) | Pública |
| GET/POST | `/locais` | Listar / criar locais | Requer token |
| GET/POST | `/eventos` | Listar / criar eventos | Requer token |
| GET/POST | `/ingressos` | Listar / comprar ingressos | Requer token |
| GET | `/ingressos/resumo` | Estatísticas de vendas | Requer token |

A lista completa de endpoints, com exemplos de requisição e resposta, está disponível no Swagger UI.
