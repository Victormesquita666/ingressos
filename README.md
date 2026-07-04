

Esse é o projeto final que a gente fez. A proposta era montar um sistema completo, então escolhemos fazer algo de venda de ingressos pra eventos, tipo um Sympla mais simples. Tem cadastro de usuário, cadastro de local, criação de evento e a compra do ingresso em si, com algumas regras.

Fizemos em Spring Boot + Java, com PostgreSQL de banco.

Quem fez


Glauco Lucio Melo de Menezes Filho - RGM 42811155
Victor Gabriel Mesquita Muniz Farias - RGM 42888671


Tecnologias

Usamos:


Java 21
Spring Boot (Web, JPA, Security e Validation)
Gradle com Kotlin DSL
PostgreSQL
JWT pra autenticação (biblioteca io.jsonwebtoken)
Lombok pra não ficar escrevendo getter e setter à mão o tempo todo
Swagger pra documentar e poder testar os endpoints sem precisar abrir Postman toda hora


Como o projeto tá organizado

Separamos os pacotes assim:

com.example.ingressos
├── config       (configuração do Swagger)
├── controllers  (os endpoints da API)
├── exceptions   (tratamento de erro, tipo 400 e 404)
├── models
│   ├── dto       (o que entra e sai da API)
│   └── entities  (as tabelas do banco)
├── repositories (JPA)
├── security     (toda a parte de JWT)
└── services     (onde ficam as regras de negócio de fato)

As entidades


Usuario: quem compra o ingresso. Cada usuário tem um endereço vinculado (relação um-pra-um).
Endereco: endereço do usuário, separamos numa tabela à parte.
Local: onde o evento vai acontecer, tipo um teatro ou um estádio.
Evento: fica ligado a um Local, tem data, capacidade máxima e o preço do ingresso.
Ingresso: essa é a tabela que representa a venda em si, liga um Usuario a um Evento, com a data que foi comprado e quanto foi pago.


Regras que a gente implementou

No começo a gente ia deixar bem simples, só criar e listar, mas o professor pediu regra de negócio de verdade então colocamos essas três:


Se o evento já vendeu o total de ingressos que cabe no local, não deixa comprar mais. Dá erro 400.
Cada usuário só pode comprar até 5 ingressos do mesmo evento (pra evitar cambista kk). Se tentar o sexto, também dá 400.
Não pode cadastrar dois usuários com o mesmo email, senão também retorna 400.


Sobre a parte de segurança

A autenticação é via JWT e não guarda sessão no servidor, é tudo stateless mesmo.

As únicas rotas que não pedem token são o cadastro de usuário e o login, além da própria página do Swagger. Todo o resto precisa mandar o token no header assim:

Authorization: Bearer seu_token_aqui

E a senha do usuário nunca fica salva como veio, ela passa por hash BCrypt antes de ir pro banco.

Estatísticas

Tem um endpoint só pra isso, /ingressos/resumo, que mostra:


quantos ingressos já foram vendidos no total
quanto isso deu em dinheiro
a taxa de ocupação média dos eventos
e qual foi o evento que mais vendeu


Esses cálculos são feitos na hora, não fica salvo em nenhuma tabela separada.

Rodando na sua máquina

Precisa ter instalado: Java 21 (ou mais novo), PostgreSQL rodando e o Git.

Primeiro clona o repositório:

git clone https://github.com/Victormesquita666/ingressos.git
cd ingressos

Depois cria o banco no Postgres (pode ser pelo terminal ou pgAdmin, o que for mais fácil pra você):

CREATE DATABASE ingressos_db;

Aí abre o arquivo application.properties, que fica em src/main/resources, e troca usuário e senha pelos que você usa no seu Postgres local:

spring.datasource.url=jdbc:postgresql://localhost:5432/ingressos_db
spring.datasource.username=postgres
spring.datasource.password=coloque_sua_senha_aqui

Depois é só rodar:

./gradlew bootRun

(se for Windows, usa .\gradlew bootRun)

Quando subir, entra em http://localhost:8080/swagger-ui/index.html pra ver e testar os endpoints. As tabelas sobem sozinhas na primeira vez que roda, não precisa criar nada manualmente.

Endpoints principais

MétodoRotaO que fazPrecisa de token?POST/usuariosCadastra um usuário novoNãoPOST/auth/loginFaz login e devolve o JWTNãoGET / POST/locaisLista ou cria locaisSimGET / POST/eventosLista ou cria eventosSimGET / POST/ingressosLista ou compra ingressosSimGET/ingressos/resumoMostra as estatísticasSim

Tem mais rota além dessas, mas essas são as principais. O resto (com exemplo de request e response de cada uma) dá pra ver direto no Swagger depois que a aplicação estiver rodando.
