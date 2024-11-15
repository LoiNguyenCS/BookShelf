package com.example.bookshelf.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * This class sets the thread that runs the test to be different from the default thread (UI thread).
 */
class TestDispatcherRule(
    private val testDispatcherRule: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcherRule)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }

}