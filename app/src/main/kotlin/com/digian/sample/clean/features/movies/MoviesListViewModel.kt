package com.digian.sample.clean.features.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.digian.sample.clean.core.domain.exception.Failure
import com.digian.sample.clean.core.domain.usecases.BaseUseCase
import com.digian.sample.clean.features.movies.data.PopularMoviesRepositoryImpl
import com.digian.sample.clean.features.movies.domain.PopularMoviesRepository
import com.digian.sample.clean.features.movies.domain.entities.MovieEntity
import com.digian.sample.clean.features.movies.domain.usecases.GetMoviesUseCase


/**
 * Created by Alex Forrester on 23/04/20
 * 19.
 */
open class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val getMoviesUseCase: GetMoviesUseCase = GetMoviesUseCase(getRepository())
    val failure: MutableLiveData<Failure> = MutableLiveData()
    val movies: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    internal open fun getRepository(): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(getApplication())
    }

    fun loadMovies() {
        getMoviesUseCase(BaseUseCase.None()).successOrError(::handleFailure, ::handleSuccess)
    }

    private fun handleFailure(failure: Failure) {
        //TODO Add Error handling
        Log.d(this.javaClass.name, failure.toString())

    }

    private fun handleSuccess(movies: List<MovieEntity>) {
        movies.sortedByDescending {
            it.voteCount
        }

        this.movies.value = movies
    }


}