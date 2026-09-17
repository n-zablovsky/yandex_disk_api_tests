![Run Tests](https://github.com/n-zablovsky/yandex_disk_api_tests/actions/workflows/maven.yml/badge.svg)

# Yandex Disk API Tests

Автотесты для REST API Яндекс.Диска.

## Стек
- Java 17
- JUnit 5
- RestAssured
- Maven
- Allure
- GitHub Actions (CI)

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
```

## Allure-отчёт
```bash
mvn allure:serve
```

## Покрытие
- **GET**: информация о диске, метаданные, содержимое корзины, публичные ресурсы
- **PUT**: создание папки, публикация, восстановление из корзины
- **POST**: загрузка файла, копирование
- **DELETE**: удаление файла/папки, очистка корзины

## Структура тестов
- `DiskInfoTest` — информация о диске
- `FolderTest` — операции с папками
- `FileTest` — операции с файлами
- `TrashTest` — работа с корзиной
- `NegativeTest` — негативные сценарии
- `PublicResourceTest` — публичные ресурсы
- `ParameterizedPathTest` — параметризованные тесты

## CI
Проект использует GitHub Actions для автоматического запуска тестов при каждом пуше в `main`.

Токен хранится в секретах репозитория (`YANDEX_DISK_TOKEN`).

## Allure Report

![Allure Report](https://github.com/n-zablovsky/yandex_disk_api_tests/raw/main/screenshots/allure-report.png)
```