# Sistema de Parquímetro

Sistema de gerenciamento de parquímetro desenvolvido com Spring Boot, MongoDB e Redis.

## Pré-requisitos

- Git
- Docker
- Docker Compose

## Como executar

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/parquimetro.git
cd parquimetro
```
### 2. Execute com Docker Compose
```bash
docker-compose up -d
```
Este comando irá:

-   Construir a aplicação Spring Boot
-   Iniciar o MongoDB
-   Iniciar o Redis
-   Configurar a rede entre os serviços

### 3.  Comandos úteis

- Verificar status dos serviços:
```bash
docker-compose  ps
```
- Parar serviços: 
```bash
docker-compose down
```
- Ver logs da aplicação:
```
docker-compose logs app
```
- Ver logs do MongoDB:
```
docker-compose logs mongodb
```
- Ver logs do Redis:
 ```
docker-compose logs redis
```

### 4.  Variáveis de ambiente

- SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/parquimetrodb
- SPRING_REDIS_HOST=redis
- SPRING_REDIS_PORT=6379

### 5.   Documentação da API (Swagger)

Após iniciar a aplicação, a documentação Swagger está disponível em:
http://localhost:8080/swagger-ui/index.html

