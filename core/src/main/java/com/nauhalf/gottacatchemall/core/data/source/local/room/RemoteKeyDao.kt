package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nauhalf.gottacatchemall.core.data.source.local.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE pokemonId= :pokemonId")
    suspend fun remoteKeyPokemonId(pokemonId: Int): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}