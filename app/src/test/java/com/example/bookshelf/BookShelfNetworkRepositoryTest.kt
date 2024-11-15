package com.example.bookshelf

import com.example.bookshelf.data.NetworkRepository
import com.example.bookshelf.fake.FakeDataSource
import com.example.bookshelf.fake.FakeGoogleBookAPIService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BookShelfNetworkRepositoryTest {
    @Test
    fun NetworkRepository_getBookData_verifyBookData() {
        runTest {
            val repository: NetworkRepository = NetworkRepository(FakeGoogleBookAPIService())
            assertEquals(
                repository.getBookData("Random"),
                FakeDataSource.googleBookAPIService
            )
        }

    }
}