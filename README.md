# CineRoom
## [Скачать последнюю версию (APK)](https://github.com/kenctijzz/CineRoom/releases/download/v1.0.2/cineroom-release.apk)

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
  <img src="https://github.com/user-attachments/assets/6583a9af-c098-47a0-88c5-8cd1ef12cde7" alt="Страница информации о фильме(скрытое описание)" width="200""")/>
  <img src="https://github.com/user-attachments/assets/c553f76f-e8d0-43b8-acb5-4be9a27b6d80" alt="Страница информации о фильме" width="200""")/>
  <img src="https://github.com/user-attachments/assets/f307c9ff-3fe6-4980-8d9e-02c675aea0f5" alt="Главная страница с популярными фильмами" width="200""")/>
  <img src="https://github.com/user-attachments/assets/0adee199-dd73-4c40-8bab-97096951becb" alt="Меню навигации приложения" width="200""")/>
</p>

Ключевые особенности

- **Бесконечные списки:** Реализована пагинация через Paging 3 для оптимальной загрузки данных.
- **Caching & Persistence:** Реализовано сохранение данных в Room при первичной загрузке из сети.
  Это обеспечивает мгновенное открытие экрана деталей без повторных сетевых запросов.
- **Безопасная навигация:** Использование Kotlinx Serialization для передачи типизированных
  аргументов между экранами.
- **UI/UX:** Минималистичный дизайн в стиле Netflix, поддержка сетки (Vertical Grid), кастомные тени
  и эффекты для текста.
