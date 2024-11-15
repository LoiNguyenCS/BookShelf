    package com.example.bookshelf.ui

    import android.util.Log
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
    import androidx.lifecycle.viewModelScope
    import androidx.lifecycle.viewmodel.initializer
    import androidx.lifecycle.viewmodel.viewModelFactory
    import coil.network.HttpException
    import com.example.bookshelf.BookShelfApplication
    import com.example.bookshelf.data.BookShelfRepository
    import com.example.bookshelf.data.NetworkRepository
    import kotlinx.coroutines.Job
    import kotlinx.coroutines.launch
    import java.io.IOException

    sealed interface BookUIState {
        object Searching: BookUIState
        object Error: BookUIState
        data class ShowingResult(val result: List<String>): BookUIState

    }

    class BookShelfViewModel(
        val repository: BookShelfRepository
    ): ViewModel() {
        var bookUiState: BookUIState by mutableStateOf(BookUIState.Searching)
            private set


        fun updateSearchTerm(searchedTerm: String) {
            updateUIWithSearchResult(searchedTerm)
        }

        fun updateUIWithSearchResult(searchedTerm: String) {
            viewModelScope.launch {
                    bookUiState = try {
                        val searchResult = repository.getBookData(searchedTerm)
                            .items
                            .map { it.volumeInfo }
                            .mapNotNull { it.imageLinks }
                            .mapNotNull { it.thumbnail }
                        BookUIState.ShowingResult(searchResult)
                    } catch (e: IOException) {
                        BookUIState.Error
                    }
                    catch (e: HttpException) {
                        BookUIState.Error
                    }
            }
        }

        fun reset() {
            bookUiState = BookUIState.Searching
        }


        companion object {
            val Factory: ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BookShelfApplication)
                    val bookShelfRepository = application.container.bookShelfRepository
                    BookShelfViewModel(repository = bookShelfRepository)
                }
            }
        }
    }