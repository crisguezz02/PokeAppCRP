package com.example.pokeappcrp.domain

import com.example.pokeappcrp.domain.PokemonRepository1
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository1
}

