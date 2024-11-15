package com.example.bookshelf

import com.example.bookshelf.fake.FakeDataSource
import com.example.bookshelf.fake.FakeNetworkBookRepository
import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.BookShelfViewModel
import com.example.bookshelf.ui.BookUIState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BookShelfViewModelTest {
    @get: Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun BookShelfViewModel_getBookPhoto_verifyBookUIStateSuccess() {
        runTest {
            val viewModel: BookShelfViewModel = BookShelfViewModel(repository = FakeNetworkBookRepository())
            // note that the value of the search term does not matter here. The way we constructs the fake classes make sure that the return result is consistent. The reason for this choice is that we do not want to test the searching feature, which is something belongs to GoogleBookAPI itsel.
            viewModel.updateSearchTerm("Random")

            // BookShelfViewModel supposes to transform the APIResponce into a list of non-null image links before passing the data to the UI state.
            val listOfImageLink = viewModel.repository
                .getBookData("Random")
                .items
                .map { it.volumeInfo }
                .mapNotNull { it.imageLinks }
                .mapNotNull { it.thumbnail }

            assertEquals (
                BookUIState.ShowingResult(listOfImageLink),
                viewModel.bookUiState
            )

        }
    }
}