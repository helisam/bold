# Bold - Sistema de Pagamentos

## Descrição

O Bold é um sistema de processamento de pagamentos composto por dois serviços principais:

1. **api-pagamento**: Serviço que recebe requisições de pagamento e as encaminha para o autorizador.
2. **autorizador**: Serviço que processa as requisições de pagamento e retorna a autorização.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Docker e Docker Compose
- Swagger/OpenAPI 3.0 (springdoc-openapi)
- JUnit 5 para testes unitários
- Mockito para mocks em testes
- Lombok para redução de boilerplate
- Spring WebFlux para comunicação entre serviços
- Gradle como ferramenta de build

## Documentação da API (Swagger)

O sistema possui documentação interativa das APIs através do Swagger UI:

- **API Pagamento**: [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)
- **Autorizador**: [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)

A documentação OpenAPI também está disponível em:

- **API Pagamento**: [http://localhost:8082/api-docs](http://localhost:8082/api-docs)
- **Autorizador**: [http://localhost:8083/api-docs](http://localhost:8083/api-docs)

## Tratamento de Exceções

O sistema implementa um tratamento centralizado de exceções utilizando o padrão GlobalExceptionHandler do Spring:

### Tipos de Exceções Tratadas

- **Exceções genéricas**: Tratadas com status 500 (Internal Server Error)
- **Erros de validação**: Tratados com status 400 (Bad Request)
- **Recursos não encontrados**: Tratados com status 404 (Not Found)
- **Erros de negócio**: Tratados com status 400 (Bad Request)
- **Erros de comunicação** (api-pagamento): Tratados com status 503 (Service Unavailable)
- **Erros de processamento de transação** (autorizador): Tratados com status 422 (Unprocessable Entity)

### Estrutura do Tratamento de Exceções

- **GlobalExceptionHandler**: Classe principal que centraliza o tratamento de exceções usando `@RestControllerAdvice`
- **ErrorResponse**: Modelo para padronização das respostas de erro
- **Exceções personalizadas**: Classes específicas para diferentes tipos de erros

### Exemplo de Resposta de Erro

```json
{
  "timestamp": "2025-07-10T17:30:45.123",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "O recurso solicitado não foi encontrado",
  "path": "/api/recurso/123"
}
```

## Como Executar

### Usando Docker Compose

A maneira mais simples de executar o sistema é utilizando o Docker Compose:

```bash
# Na raiz do projeto
docker-compose up -d
```

Este comando irá construir e iniciar os contêineres para ambos os serviços:
- api-pagamento: acessível na porta 8082
- autorizador: acessível na porta 8083

### Verificando os Logs

Para verificar os logs dos serviços:

```bash
# Logs do serviço api-pagamento
docker logs bold-api-pagamento-1

# Logs do serviço autorizador
docker logs bold-autorizador-1
```

### Configuração de Portas

O sistema utiliza o seguinte mapeamento de portas:

| Serviço | Porta no Container | Porta no Host |
|---------|-------------------|---------------|
| api-pagamento | 8080 | 8082 |
| autorizador | 8080 | 8083 |

Para verificar o status dos serviços, você pode acessar:

```bash
# Verificar status do api-pagamento
curl http://localhost:8082/api/v1/pagamentos/status

# Verificar status do autorizador
curl http://localhost:8083/api/v1/autorizador/status
```

## Estrutura do Projeto

- **api-pagamento/**
  - Controller para receber requisições HTTP
  - Cliente para comunicação com o autorizador
  - Serviço para processamento das requisições
  - DTOs para transferência de dados
  - Utilitários para conversão de formatos
  - **exception/**: Classes para tratamento centralizado de exceções
  - Testes unitários para cada componente

- **autorizador/**
  - Configuração de socket para comunicação
  - Serviço para processamento das autorizações
  - DTOs para transferência de dados
  - Utilitários para processamento de mensagens ISO8583
  - **exception/**: Classes para tratamento centralizado de exceções
  - Testes unitários para cada componente

## Testes Unitários

O projeto implementa testes unitários abrangentes utilizando JUnit 5 e Mockito:

### Tecnologias de Teste

- **JUnit 5**: Framework de testes
- **Mockito**: Framework para criação de mocks
- **MockitoExtension**: Integração entre JUnit 5 e Mockito
- **MockedStatic**: Para mockar métodos estáticos

### Estrutura dos Testes

- Anotações `@ExtendWith(MockitoExtension.class)` para integração com Mockito
- Uso de `@Mock` para criar mocks de dependências
- Uso de `@InjectMocks` para injetar mocks nas classes testadas
- Métodos `@BeforeEach` para configuração dos testes
- Asserções para validar resultados esperados

### Execução dos Testes

Para executar os testes unitários:

```bash
# Na raiz do projeto, para todos os módulos
./gradlew test

# Para um módulo específico
./gradlew :api-pagamento:test
./gradlew :autorizador:test
```

## Exemplos de Requisições para Testes no Postman

Abaixo estão exemplos de comandos curl que podem ser importados no Postman para testar os endpoints da aplicação:

### API Pagamento

#### Verificar Status do Serviço

```bash
curl --location 'http://localhost:8082/api/v1/pagamentos/status' \
--header 'Accept: application/json'
```

#### Autorizar Pagamento

```bash
curl --location 'http://localhost:8082/api/v1/pagamentos/authorization' \
--header 'x-identifier: 123456789' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "externalId": "PEDIDO123456",
    "value": 150.75,
    "cardNumber": "4111111111111111",
    "installments": 1,
    "cvv": "123",
    "expMonth": 12,
    "expYear": 2025,
    "holderName": "USUARIO TESTE"
}'
```

### Autorizador

#### Verificar Status do Serviço

```bash
curl --location 'http://localhost:8083/api/v1/autorizador/status' \
--header 'Accept: application/json'
```

### Importando no Postman

Para importar estes exemplos no Postman:

1. Copie o comando curl desejado
2. No Postman, clique em "Import" > "Raw text"
3. Cole o comando curl e clique em "Continue"
4. Revise os detalhes da requisição e clique em "Import"

Você também pode criar uma coleção para organizar todas as requisições relacionadas ao projeto Bold.