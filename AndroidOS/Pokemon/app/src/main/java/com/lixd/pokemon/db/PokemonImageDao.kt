package com.lixd.pokemon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lixd.pokemon.db.entity.PokemonImage

@Dao
interface PokemonImageDao {
    @Query("SELECT * FROM pokemonImage WHERE id = :id")
    fun queryImage(id: Int): PokemonImage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(data: PokemonImage)
}