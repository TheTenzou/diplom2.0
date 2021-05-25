# Дипломный проект геоинформационные системы

## Требования

- [Docker](https://www.docker.com/)
- Docker-compose

В случае windows лучше запускать из под wsl

## Запуск
```bash
docker-compose up
```

- Приложение запускается на порте 8080
- Web ui для mongo на ходится на порту 8081
- web ui для Redis на 8082
- web ui для Postgres на 16543
- [swagger ui](http://localhost:8080/api/tsodd/swagger-ui/) для tsodd-servise на `http://localhost:8080/api/tsodd/swagger-ui/`
