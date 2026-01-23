Современное Android-приложение для просмотра информации о популярных фильмах. Возможность оценки и сохранения любимых фильмов в избранное. (Приложение находится в стадии разработки)

Технологический стек

- **UI:** Jetpack Compose (Declarative UI)
- **Architecture:** Clean Architecture + MVVM
- **DI:** Hilt
-  **Network:** Retrofit
- **Paging:** Paging 3 (Remote Pagination)
- **Local DB:** Room (Offline-first caching)
- **Navigation:** Type-Safe Jetpack Navigation
- **Images:** Coil 3

Скриншоты приложения
![Страница информации о фильме](https://github.com/user-attachments/assets/36e68cbd-7f60-444e-8441-f6ad56a3a73e)
![Страница информации о фильме(скрытое описание)](https://github.com/user-attachments/assets/a42b9e0b-6d62-4a67-8a7d-9c28dd4f7900)
![Главная страница с популярными фильмами](https://github.com/user-attachments/assets/eea8d288-004b-40d3-9816-769774c4947d)
![Меню навигации приложения](https://github.com/user-attachments/assets/a2b9fe05-9ddc-426c-8425-52b4bb166dbe)

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

