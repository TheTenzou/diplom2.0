# Дипломный проект геоинформационные системы

## Требования

- [Docker](https://www.docker.com/)
- Docker-compose

В случае windows лучше запускать из под wsl

## Запуск
```bash
docker-compose up
```

- Приложение запускается на 8080 порте
- Web ui для mongo на ходится на 8081 порте
- web ui для Redis на 8082 порте
- web ui для Postgres на 16543 порте
- [swagger ui](http://localhost:8080/api/tsodd/swagger-ui/) для tsodd-servise на `http://localhost:8080/api/tsodd/swagger-ui/`

- [swagger ui](http://localhost:8080/api/uds/swagger-ui/) для uds-servise на `http://localhost:8080/api/uds/swagger-ui/`
