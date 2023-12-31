package com.example.movieschallenge.provider

import com.example.movieschallenge.services.API
import com.example.movieschallenge.services.best_movies.BestMoviesContract
import com.example.movieschallenge.services.best_movies.BestMoviesImp
import com.example.movieschallenge.services.best_movies.BestMoviesServices
import com.example.movieschallenge.services.locations.LocationsContract
import com.example.movieschallenge.services.locations.LocationsImp
import com.example.movieschallenge.services.pictures.PicturesContract
import com.example.movieschallenge.services.pictures.PicturesImp
import com.example.movieschallenge.services.rated.RatedContract
import com.example.movieschallenge.services.rated.RatedImp
import com.example.movieschallenge.services.rated.RatedServices
import org.koin.dsl.module

val providerModule = module {
    factory { API.getServices<RatedServices>() }
    factory { API.getServices<BestMoviesServices>() }
    single<RatedContract> { RatedImp(get(),get()) }
    single<BestMoviesContract> { BestMoviesImp(get(),get()) }
    single<LocationsContract> { LocationsImp(get()) }
    single<PicturesContract> { PicturesImp() }
    factory { ViewModelFactory(get(),get(),get(),get()) }
}