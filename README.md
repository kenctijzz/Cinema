# CineRoom - Приложение позволяющее составлять коллекцию любимых фильмов, выставлять оценки, сохранять на телефон понравившиеся кадры и постеры. Так же имеется возможность мгновенно перейтиа к поиску фильма в Вк Видео
## [Скачать последнюю версию (APK)](https://github.com/kenctijzz/CineRoom/releases/download/v1.0.3/cineroom-release.apk)

Технологический стек

- **UI:** Jetpack Compose (Declarative UI)
- **Architecture:** Clean Architecture + MVVM
- **DI:** Hilt
-  **Network:** Retrofit
- **Paging:** Paging 3 (Remote Pagination)
- **Local DB:** Room (Offline-first caching)
- **Navigation:** Type-Safe Jetpack Navigation
- **Images:** Coil 3

<p align="center">
  <img src="https://github.com/user-attachments/assets/b02ec6f8-3392-48b4-ade7-3021292b20c8" alt="Страница информации о фильме(скрытое описание)" width="200""")/>
  <img src="https://github.com/user-attachments/assets/2a417330-f640-45d2-b470-afc4d96aaf63" alt="Страница информации о фильме" width="200""")/>
  <img src="https://github.com/user-attachments/assets/ff1089a9-f1e8-4175-9d42-ca2a9e4af8d2" alt="Главная страница с популярными фильмами" width="200""")/>
  <img src="https://github.com/user-attachments/assets/a7e7534e-65e0-4a4c-90c7-70a2dca4ba4f" alt="Меню навигации приложения" width="200""")/>
</p>

Ключевые особенности

- **Бесконечные списки:** Реализована пагинация через Paging 3 для оптимальной загрузки данных.
- **Caching & Persistence:** Реализовано сохранение данных в Room при первичной загрузке из сети.
  Это обеспечивает мгновенное открытие экрана деталей без повторных сетевых запросов.
- **Безопасная навигация:** Использование Kotlinx Serialization для передачи типизированных
  аргументов между экранами.
- **UI/UX:** Минималистичный дизайн в стиле Netflix, поддержка сетки (Vertical Grid), кастомные тени
  и эффекты для текста.
