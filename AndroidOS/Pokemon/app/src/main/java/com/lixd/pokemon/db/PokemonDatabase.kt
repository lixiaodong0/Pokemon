package com.lixd.pokemon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lixd.pokemon.db.entity.PokemonImage

@Database(entities = [PokemonImage::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonImageDao(): PokemonImageDao
}