# Controle de Gastos - Backend

API REST desenvolvida com **Java + Spring Boot**, utilizando **PostgreSQL** como banco de dados e **Docker** para facilitar o ambiente de desenvolvimento e implantação.

## 🚀 Funcionalidades

- 🔐 Autenticação via JWT
- 👥 Atribuição de papéis com base na idade (Admin / Usuário)
- 📊 Gerenciamento de despesas por usuário
- 🛡️ Proteção de rotas com base em autenticação e papel
- 📦 Integração com frontend via JSON

## 🧰 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok**

## 🗂️ Estrutura do Projeto

```
src/
├── main/          
│   ├── java/   
│   │   ├── controle-de-gastos/
│   │       ├── controllers/
│   │       ├── domain/
│   │       ├── infra.security/
│   │       ├── repositories/
│   │       ├── services/
│   │       └── ControleDeGastosBackendApplication           
│   └── resources/                
└── test/      
```

## 🐳 Executando com Docker

Certifique-se de ter o Docker e Docker Compose instalados.

```bash
# Subir os containers (API + PostgreSQL)
docker-compose up --build

O backend estará disponível em: http://localhost:8080

## 🔐 Endpoints de Autenticação
POST /auth/login – Autentica o usuário e retorna o token JWT
BODY:{
  "login":"teste",
  "password": "123456789"
}

RESPONSE:
status code: 200 OK
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjb250cm9sZS1nYXN0b3MtYXBpIiwic3ViIjoidGVzdGUtMDEiLCJleHAiOjE3NTY1ODg2Mzh9.orOey9880cuJlwXA_3EmRd-HJQAfAl97rYTH4ZnJRBQ"
}

POST /auth/register – Registra novo usuário com papel baseado na idade
BODY:{
    "login": "teste-01",
    "password": "123456789",
    "age": "25",
    "role": "ADMIN"
}
RESPONSE:
status 201 Created

## 📦 Endpoints de Despesas (exemplos) - falta ser implementado 
GET /expenses – Lista despesas do usuário autenticado

POST /expenses – Cria nova despesa

PUT /expenses/{id} – Atualiza despesa existente

DELETE /expenses/{id} – Remove despesa
```

## ⚙️ Configuração Local (sem Docker)
Pré-requisitos
Java 21+

Maven

PostgreSQL rodando localmente

Variáveis de ambiente ou application.yml configurado com credenciais do banco

Executando