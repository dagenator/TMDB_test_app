package com.example.tmdb_test_app.core.di

import com.example.tmdb_test_app.*
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface FragmentComponent {

    // This tells Dagger that mainActivity requests injection from FragmentComponent
    // so that this subcomponent graph needs to satisfy all the dependencies of the
    // fields that MainActivity is injecting


    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }

    // All LoginActivity, LoginUsernameFragment and LoginPasswordFragment
    // request injection from LoginComponent. The graph needs to satisfy
    // all the dependencies of the fields those classes are injecting
    fun inject(mainActivity: MainActivity)
    fun inject(movieFragment: MovieFragment)
    fun inject(popularFragment: PopularFragment)
    fun inject(popularFragment: FavouriteMoviesListFragment)
    fun inject(randomFragment: RandomFragment)

}