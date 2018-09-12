package io.petros.movies.presentation.feature.movies.view

import io.petros.movies.presentation.PreconfiguredRobolectricTestRunner
import io.petros.movies.presentation.RobolectricTestProvider.Companion.provideContext
import io.petros.movies.test.domain.TestMoviesProvider.Companion.provideMovie
import kotlinx.android.synthetic.main.item_movie.view.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(PreconfiguredRobolectricTestRunner::class)
class MovieItemViewRobolectricTest {

    private val movie = provideMovie()

    private lateinit var testedClass: MovieItemView
    private val context = provideContext()

    @Before
    fun setUp() {
        testedClass = MovieItemView(context)
    }

    @Test
    fun `When movie is bind, then the movie backdrop is set`() {
        assertThat(testedClass.iv_movie_backdrop.drawable).isNull()

        testedClass.bind(movie)

        assertThat(testedClass.iv_movie_backdrop.drawable).isNotNull
    }

    @Test
    fun `When movie is bind, then the movie title is set`() {
        testedClass.bind(movie)

        assertThat(testedClass.tv_movie_title.text).isEqualTo(movie.title)
    }

    @Test
    fun `When movie is bind, then the movie release date is set`() {
        testedClass.bind(movie)

        assertThat(testedClass.tv_movie_release_date.text).isEqualTo(movie.releaseDate())
    }

    @Test
    fun `When movie is bind, then the movie vote is set`() {
        testedClass.bind(movie)

        assertThat(testedClass.tv_movie_vote.text).isEqualTo(movie.vote())
    }

}