Современное Android-приложение для просмотра информации о популярных фильмах.

Технологический стек

- **UI:** Jetpack Compose (Declarative UI)
- **Architecture:** Clean Architecture + MVVM + UDF (Unidirectional Data Flow)
- **DI:** Hilt
- **Network:** Retrofit
- **Paging:** Paging 3 (Remote Pagination)
- **Local DB:** Room (Offline-first caching)
- **Navigation:** Type-Safe Jetpack Navigation
- **Images:** Coil 3

Ключевые особенности

- **Бесконечные списки:** Реализована пагинация через Paging 3 для оптимальной загрузки данных.
- **Caching & Persistence:** Реализовано сохранение данных в Room при первичной загрузке из сети.
  Это обеспечивает мгновенное открытие экрана деталей без повторных сетевых запросов и позволяет
  просматривать ранее загруженные фильмы при нестабильном соединении.
- **Безопасная навигация:** Использование Kotlinx Serialization для передачи типизированных
  аргументов между экранами.
- **UI/UX:** Минималистичный дизайн в стиле Netflix, поддержка сетки (Vertical Grid), кастомные тени
  и эффекты для текста.

## Как запустить

1. Получите API-ключ на [TMDB](https://www.themoviedb.org).
2. Вставьте ваш ключ в файл `data/remote/ApiConstants.kt`.
3. Соберите и запустите проект в Android Studio.