# Gerenciamento de filmes

Projeto Java para cadastro e consulta de filmes utilizando **Java, Maven, JDBC e MySQL**. \
O banco de dados pode ser executado localmente através do **Docker**, permitindo que o projeto funcione mesmo sem acesso ao banco utilizado durante as aulas.

## Tecnologias utilizadas

* Java 17
* Maven
* JDBC
* MySQL 8.4
* Docker
* HTML
* CSS

## Estrutura do projeto

```text
projeto-filme/
├── .env
├── .env_example
├── .gitignore
├── pom.xml
├── docker-compose.yml
├── docker/
│   └── init.sql
│
└── src/
    └── main/
        ├── java/
        │   ├── Conexao.java
        │   ├── Filme.java
        │   ├── Main.java
        │   ├── FilmeDAO.java
        │   └── Servidor.java
        │
        └── resources/
            └── web/
                ├── index.html
                └── style.css
```

## Pré-requisitos

Antes de executar o projeto pela primeira vez, é necessário ter instalado:

* Java JDK 17 ou superior
* Maven
* Docker
* Docker Compose

Verifique as instalações:

```bash
java -version
```

```bash
mvn -version
```

```bash
docker --version
```

```bash
docker compose version
```

## Configuração do banco

O projeto utiliza um MySQL executado através do Docker.
O arquivo `.env` contém as configurações utilizadas pelo banco:

```dotenv
DB_URL=jdbc:mysql://localhost:3306/filmes
DB_USER=root
DB_PASSWORD=root

MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=filmes
```
> Por ser de caráter acadêmico, disponibilizei o `.env_exemple`

## Iniciar o MySQL

Na raiz do projeto, execute:

```bash
docker compose up -d
```

Verifique se o container está em execução:

```bash
docker ps
```

O container deverá aparecer com o nome:

```text
projeto-filme-mysql
```

## Banco e tabela

O Docker cria automaticamente o banco:

```text
filmes
```

e executa o arquivo:

```text
docker/init.sql
```

Esse arquivo cria a tabela:

```text
poliana
```

com as colunas:

```text
id
nome_filme
```

Para verificar o banco manualmente:

```bash
docker exec -it projeto-filme-mysql mysql -u root -p
```

Digite a senha:
```text
root
```

Depois:
```sql
USE filmes;
```

```sql
SHOW TABLES;
```

Para visualizar os registros:

```sql
SELECT * FROM poliana;
```

Para sair:

```sql
exit;
```

## Configuração do Maven

O arquivo `pom.xml` contém a dependência do MySQL Connector/J utilizada pelo JDBC.

Para baixar as dependências e compilar o projeto:

```bash
mvn clean compile
```

Se aparecer:

```text
BUILD SUCCESS
```

o projeto foi compilado corretamente.

## 6. Executar a aplicação

Abra o projeto no IntelliJ IDEA.

Execute a classe:

```text
Servidor.java
```

O console deverá mostrar:

```text
Servidor iniciado!
Acesse: http://localhost:8080
```

Abra o navegador e acesse:

```text
http://localhost:8080
```

## Funcionalidades

Atualmente a aplicação possui:

### Cadastrar filme

Preencha o campo:

```text
Nome do filme
```

e clique em:

```text
Cadastrar
```

O formulário envia uma requisição `POST` para:

```text
/filmes/salvar
```

O Java recebe os dados e utiliza:

```java
FilmeDAO.salvar()
```

para inserir o filme no banco.

### Listar filmes

Ao acessar a página inicial, o sistema consulta os filmes através de:

```java
FilmeDAO.listarTodos();
```

e exibe os registros em uma tabela.

## Fluxo da aplicação

```text
Navegador
    │
    │ HTTP
    ▼
Servidor.java
    │
    ▼
FilmeDAO
    │
    │ JDBC
    ▼
MySQL
    │
    ▼
Docker
```

Para o cadastro:

```text
HTML
 ↓
POST /filmes/salvar
 ↓
Servidor.java
 ↓
FilmeDAO.salvar()
 ↓
MySQL
```

Para a listagem:

```text
HTML
 ↓
Servidor.java
 ↓
FilmeDAO.listarTodos()
 ↓
MySQL
 ↓
HTML
```

## Parar o banco

Para parar o container:

```bash
docker compose stop
```
Os dados continuam salvos no volume do Docker.

Para iniciar novamente:

```bash
docker compose up -d
```

## Apagar o banco e começar novamente

Se for necessário apagar completamente os dados do banco e recriá-lo:

```bash
docker compose down -v
```

Depois:

```bash
docker compose up -d
```

**Atenção:** o parâmetro `-v` remove o volume do banco. Todos os filmes cadastrados serão apagados.

## Executar novamente após a primeira configuração

Depois que tudo estiver configurado, o processo normal é:
1. Iniciar o banco

```bash
docker compose up -d
```

2. Abrir o projeto no IntelliJ

3. Executar

```text
Servidor.java
```

4. Acessar

```text
http://localhost:8080
```

E pronto. O MySQL fica no Docker e a aplicação Java conversa com ele através do JDBC.


## Executando sem Docker
Caso não queira utilizar o Docker, é necessário ter o MySQL instalado e rodando localmente.

1. Crie um banco chamado filmes. 
- Execute o `docker/init.sql` manualmente no MySQL para criar a tabela poliana.
- Configure no Conexao.java os dados do seu MySQL local:
  - URL: jdbc:mysql://localhost:3306/filmes
  - Usuário: seu usuário do MySQL
  - Senha: sua senha do MySQL
    - Depois, execute o Servidor.java normalmente e acesse http://localhost:8080.

O Docker só automatiza a criação e execução do MySQL. Sem ele, você precisa fazer essa parte manualmente. 