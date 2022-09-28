package com.example.tmdb_test_app.core.di

import dagger.Module


// The "subcomponents" attribute in the @Module annotation tells Dagger what
// Subcomponents are children of the Component this module is included in.

@Module(subcomponents = [FragmentComponent::class])
class SubcomponentsModule {}