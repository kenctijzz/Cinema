<div align="center">
  <a href="https://www.rustore.ru/catalog/app/com.timur.cineroomapp" target="_blank">
    <img 
      src="https://github.com/user-attachments/assets/4dbb4bd4-9a0d-49b7-acaa-921251e3324b" 
      width="280" 
      alt="Скачать CineRoom в RuStore"
    >
  </a>
  <br>
</div>

<h1 align="center">CineRoom</h1>

<p align="center">
  <strong>Приложение для составления коллекции любимых фильмов, выставления оценок и сохранения кадров</strong>
</p>

<p align="center">
  <a href="https://github.com/kenctijzz/CineRoom/releases/download/v1.0.5/cineroom_v1.0.5.apk">
    <img src="https://img.shields.io/badge/Скачать_APK-v1.0.5-0055FF?style=for-the-badge&logo=android" alt="Скачать APK">
  </a>
  <a href="https://github.com/kenctijzz/CineRoom/releases">
    <img src="https://img.shields.io/badge/Все_релизы-GitHub-181717?style=for-the-badge&logo=github" alt="Все релизы">
  </a>
</p>

## Технологический стек

- **UI:** Jetpack Compose (Declarative UI)
- **Архитектура:** Clean Architecture + MVVM
- **Внедрение зависимостей:** Hilt
- **Сетевые запросы:** Retrofit
- **Пагинация:** Paging 3 (Remote Pagination)
- **Локальная база данных:** Room (Offline-first caching)
- **Навигация:** Type-Safe Jetpack Navigation
- **Загрузка изображений:** Coil 3

## Скриншоты приложения

<p align="center">
  <img src="https://github.com/user-attachments/assets/b02ec6f8-3392-48b4-ade7-3021292b20c8" alt="Страница информации о фильме (детальное описание)" width="200"/>
  <img src="https://github.com/user-attachments/assets/2a417330-f640-45d2-b470-afc4d96aaf63" alt="Страница информации о фильме" width="200"/>
  <img src="https://github.com/user-attachments/assets/ff1089a9-f1e8-4175-9d42-ca2a9e4af8d2" alt="Главная страница с популярными фильмами" width="200"/>
  <img src="https://github.com/user-attachments/assets/a7e7534e-65e0-4a4c-90c7-70a2dca4ba4f" alt="Меню навигации приложения" width="200"/>
</p>

## Ключевые особенности

- **Бесконечные списки:** Реализована пагинация через Paging 3 для оптимальной загрузки данных
- **Кэширование и сохранение данных:** Данные сохраняются в Room при первичной загрузке из сети, что обеспечивает мгновенное открытие экрана деталей без повторных сетевых запросов
- **Безопасная навигация:** Использование Kotlinx Serialization для передачи типизированных аргументов между экранами
- **Пользовательский интерфейс:** Минималистичный дизайн в стиле Netflix, поддержка сетки (Vertical Grid), кастомные тени и эффекты для текста
