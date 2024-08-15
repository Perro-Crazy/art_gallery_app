package com.mauri.movieapp.domain

import com.mauri.movieapp.data.ArtRepository
import com.mauri.movieapp.data.model.ArtContainerDM
import com.mauri.movieapp.data.model.PaginationDM
import com.mauri.movieapp.domain.entity.ArtContainerBM
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class ListInitialDataUseCaseTest {
    class WhenValidDataIsReturned {

        private val mockArtRepository = mockk<ArtRepository>()
        private val subject = ListInitialDataUseCase(mockArtRepository)
        private lateinit var result: ArtContainerBM

        @Before
        fun setUp() = runTest {
            coEvery { mockArtRepository.get() }.returns(
                ArtContainerDM(
                    data = emptyList(),
                    pagination = PaginationDM(
                        currentPage = 1,
                        totalPages = 12
                    )
                )
            )
            result = subject()
        }

        @Test
        fun `then has total pages`() = runTest {
            Assert.assertEquals(12, result.totalPages)
        }

        @Test
        fun `then has current page`() = runTest {
            Assert.assertEquals(1, result.currentPage)
        }
    }
}
