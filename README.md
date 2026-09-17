# Yandex Disk API Tests

Автотесты для REST API Яндекс.Диска.

## Стек
- Java 17
- JUnit 5
- RestAssured
- Maven

## Требования
- Java 17+
- Maven 3.8+

## Настройка
1. Получите OAuth-токен: https://yandex.ru/dev/disk/poligon/
2. Установите переменную окружения:
    - Windows: `set YANDEX_DISK_TOKEN=ваш_токен`
    - Linux/Mac: `export YANDEX_DISK_TOKEN=ваш_токен`

## Запуск
```bash
mvn clean test