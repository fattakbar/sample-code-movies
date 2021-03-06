package io.petros.movies.presentation.feature.movies

import androidx.lifecycle.MutableLiveData
import io.petros.movies.domain.interactor.movie.LoadMoviesUseCase
import io.petros.movies.domain.model.common.PaginationData
import io.petros.movies.domain.model.movie.Movie
import io.petros.movies.domain.model.movie.MoviesResultPage
import io.petros.movies.presentation.feature.BaseViewModel
import io.petros.movies.presentation.feature.common.list.adapter.AdapterStatus
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesActivityViewModel constructor(
    private val loadMoviesUseCase: LoadMoviesUseCase
) : BaseViewModel() {

    val statusObservable = MutableLiveData<AdapterStatus>()
    val moviesObservable = MutableLiveData<PaginationData<Movie>>()

    val paginationData = PaginationData<Movie>()

    fun loadMoviesOrRestore(year: Int? = null, month: Int? = null) {
        if (paginationData.isEmpty()) loadMovies(year, month) else moviesObservable.postValue(paginationData)
    }

    fun loadMovies(year: Int? = null, month: Int? = null, page: Int? = null) = uiScope.launch {
        statusObservable.postValue(AdapterStatus.LOADING)
        try {
            val movies = loadMoviesUseCase.execute(LoadMoviesUseCase.Params(year, month, page))
            onLoadMoviesSuccess(movies)
        } catch (error: LoadMoviesUseCase.Error) {
            onLoadMoviesError(error)
        }
    }

    private fun onLoadMoviesSuccess(movies: MoviesResultPage) {
        Timber.d("Load movies success. [Movies: $movies]")
        statusObservable.postValue(AdapterStatus.IDLE)
        moviesObservable.postValue(paginationData.addPage(movies))
    }

    private fun onLoadMoviesError(error: LoadMoviesUseCase.Error) {
        Timber.w(error, "Load movies error.")
        statusObservable.postValue(AdapterStatus.ERROR)
        moviesObservable.postValue(null)
    }

    fun reloadMovies(year: Int? = null, month: Int? = null) {
        paginationData.clear()
        loadMovies(year, month)
    }

}
