# CineRoom
[Скачать последнюю версию (APK)](https://github.com/kenctijzz/Cinema/releases/download/v1.0.1/CineRoom_1.0.1.apk)

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
  <img src="https://github.com/user-attachments/assets/fd4235df-1dfa-441e-af4e-52f93585565b" alt="Страница информации о фильме(скрытое описание)" width="200""")/>
  <img src="https://github.com/user-attachments/assets/7a9e510e-18c5-43e4-9cea-b0d2a78080c7" alt="Страница информации о фильме" width="200""")/>
  <img src="https://github.com/user-attachments/assets/6c0c62c3-02ae-4f4a-9a2d-e49eb664c42f" alt="Главная страница с популярными фильмами" width="200""")/>
  <img src="https://github.com/user-attachments/assets/a2b9fe05-9ddc-426c-8425-52b4bb166dbe" alt="Меню навигации приложения" width="200""")/>
</p>

Ключевые особенности

- **Бесконечные списки:** Реализована пагинация через Paging 3 для оптимальной загрузки данных.
- **Caching & Persistence:** Реализовано сохранение данных в Room при первичной загрузке из сети.
  Это обеспечивает мгновенное открытие экрана деталей без повторных сетевых запросов.
- **Безопасная навигация:** Использование Kotlinx Serialization для передачи типизированных
  аргументов между экранами.
- **UI/UX:** Минималистичный дизайн в стиле Netflix, поддержка сетки (Vertical Grid), кастомные тени
  и эффекты для текста.

## Как запустить

1. Получите API-ключ на [TMDB](https://www.themoviedb.org).
2. Вставьте ваш ключ в  `di-NetworkModule-ProvideKey()`.
3. Соберите и запустите проект в Android Studio.

