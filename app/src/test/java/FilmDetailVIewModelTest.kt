import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.DeleteFilmUserRatingUseCase
import com.example.cinema.domain.usecases.films.GetFilmDetailsUseCase
import com.example.cinema.domain.usecases.films.GetFilmFlowUseCase
import com.example.cinema.domain.usecases.films.ToggleFilmLikeUseCase
import com.example.cinema.domain.usecases.films.UpdateFilmRatingUseCase
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class FilmDetailVIewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    //СОЗДАЕМ ПУСТЫШКИ ДЛЯ ЮЗКЕЙСОВ
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase = mockk()
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase = mockk()
    //RELAXED = TRUE (НЕ ПАДАТЬ С ОШИБКОЙ, А ВЕРНУТЬ ДЕФОЛТНОЕ ЗНАЧЕНИЕ)
    private val getFilmFlowUseCase: GetFilmFlowUseCase = mockk(relaxed = true)
    private val updateFilmRatingUseCase: UpdateFilmRatingUseCase = mockk()
    private val deleteFilmUserRatingUseCase: DeleteFilmUserRatingUseCase = mockk()
    private val id = 123
    //КЛАДЕМ ДАННЫЕ В BUNDLE ДЛЯ VIEWMODEL
    private val savedStateHandle = SavedStateHandle().apply {
        set("id", id)
    }
    private lateinit var viewModel: FilmDetailViewModel
    //ВЫПОЛНЯТЬ КОД ПЕРЕД КАЖДЫМ ТЕСТОМ
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    //ВЫПОЛНЯТЬ КОД ПОСЛЕ КАЖДОГО ТЕСТА
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load should update state to Success when useCase returns data`() = runTest {
        val mockFilm = Film(
            id = 123, title = "Inception",
            page = 0,
            image = "",
            releaseDate = "",
            overview = "",
            adult = false,
            isFavorite = false,
            rating = 5.0,
            popularity = 5.0,
            language = "ru",
            runtime = "123",
            video = "",
            photos = emptyList(),
            userRating = 4
        )
        coEvery { getFilmDetailsUseCase(123) } returns Result.success(mockFilm)

        //СОЗДАЕМ ТЕСТИРУЕМУЮ VIEWMODEL
        viewModel = FilmDetailViewModel(
            savedStateHandle = savedStateHandle, getFilmDetailsUseCase, toggleFilmLikeUseCase,
            getFilmFlowUseCase, updateFilmRatingUseCase, deleteFilmUserRatingUseCase
        )
        //МОТАЕМ ВРЕМЯ ПОКА ВСЕ КОРУТЫ НЕ ЗАКОНЧАТ РАБОТУ
        advanceUntilIdle()

        //ПОДКЛЮЧАЕМСЯ К FLOW
        viewModel.state.test {
            //ПОЛУЧАЕМ ПОСЛЕДНЕЕ СОСТОЯНИЕ ИЗ FLOW
            val item = expectMostRecentItem()
            //УКАЗЫВАЕМ ЧТО ДЛЯ ПРОХОЖДЕНИЯ ТЕСТА ДОЛЖНЫ ПОЛУЧИТЬ SUCCESS
            assert(item is UiState.Success)
            //ПРОВЕРЯЕМ ЧТО НАЗВАНИЕ ФИЛЬМА СОВПАДАЕТ С НАЗВАНИЕ MOCKFILM
            assertEquals("Inception", (item as UiState.Success).data.title)
        }
    }
    @Test
    fun `toggleFilmLike should show snackbar with added message`() = runTest {
        val film = Film(
            id = 1, title = "Matrix",
            page = 0,
            image = "",
            releaseDate = "",
            overview = "",
            adult = false,
            isFavorite = false,
            rating = 5.0,
            popularity = 5.0,
            language = "ru",
            runtime = "123",
            video = "",
            photos = emptyList(),
            userRating = 4
        )
        coEvery { getFilmDetailsUseCase(any()) } returns Result.success(film)
        coEvery { toggleFilmLikeUseCase(film) } returns true
        viewModel = FilmDetailViewModel(
            savedStateHandle = savedStateHandle, getFilmDetailsUseCase, toggleFilmLikeUseCase,
            getFilmFlowUseCase, updateFilmRatingUseCase, deleteFilmUserRatingUseCase
        )
        viewModel.toggleFilmLike(film)
        viewModel.snackBarEvent.test {
            val event = awaitItem()
            assertEquals("Matrix добавлен в избранное", event.message)
        }
    }
}