# Reading Management API 📚

Este projeto é um sistema back-end para gerenciamento de obras literárias, organização de bibliotecas pessoais, planos de leitura e anotações baseadas no método **Zettelkasten**. Desenvolvido inicialmente como Trabalho de Conclusão de Curso (TCC) em Análise e Desenvolvimento de Sistemas, o sistema visa otimizar a retenção de conhecimento e a organização de leituras complexas, como literatura clássica e filosofia.

Esta API foi projetada para alimentar um aplicativo front-end mobile desenvolvido em React Native.

## 🚀 Funcionalidades Principais

- **Gestão de Usuários e Autenticação:** Sistema seguro de login e controle de acesso com Spring Security, JWT e Oauth2.
- **Organização de Biblioteca:** Cadastro e gerenciamento de livros, categorização e acompanhamento de status e progresso de leitura.
- **Planos de Leitura:** Criação de planos de leitura por meio da base de templates, os planos podem ser compartilhados e é possível criar uma nova biblioteca com as obras presentes no planejamento.
- **Anotações Avançadas (Zettelkasten):** Criação, categorização e interligação de notas (utilizando Markdown) para melhor retenção e associação de ideias literárias ao longo do tempo.
- **Estatísticas e Sugestões:** Geração de estatísticas de leitura e sistema de sugestão de obras baseando-se nos templates e suas respectivas categorias.
- **Documentação Interativa:** API documentada com Swagger/OpenAPI.

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot (Web, Security, Data JPA)
- **Banco de Dados:** PostgreSQL
- **Migrações de Banco:** Flyway (com versionamento completo na pasta `db/migration`)
- **Infraestrutura:** Docker e Docker Compose
- **Gerenciador de Dependências:** Maven

## ⚙️ Arquitetura e Padrões de Projeto

O projeto segue os princípios de uma API RESTful robusta, com separação de responsabilidades bem definida:
- **Controllers:** Endpoints da API isolados por domínio lógico (`AuthController`, `BookController`, `NoteController`, etc.).
- **Services:** Regras de negócio encapsuladas e tratamento de exceções customizadas (ex: `BookException`, `UserException`).
- **Repositories & Projections:** Consultas otimizadas utilizando Spring Data JPA e *Specifications* para filtros dinâmicos.
- **Padrão DTO e Builders:** Transferência de dados segura e padronizada entre as camadas, utilizando o padrão Builder para montagem limpa e testável de objetos complexos e respostas.

## 💻 Como Executar o Projeto Localmente

### Pré-requisitos
- [Java JDK](https://adoptium.net/) (versão 21+)
- [Maven](https://maven.apache.org/)
- [Docker e Docker Compose](https://www.docker.com/)

### Passos para rodar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/raaphaelgomess/reading-management.git](https://github.com/raaphaelgomess/reading-management.git)
   cd reading-management
