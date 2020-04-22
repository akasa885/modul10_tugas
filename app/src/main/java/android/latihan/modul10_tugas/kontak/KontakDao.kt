package android.latihan.modul10_tugas.kontak

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KontakDao {
    @Insert
    fun insert(kontak: Kontak)
    @Update
    fun update(kontak: Kontak)
    @Delete
    fun delete(kontak: Kontak)
    @Query("DELETE FROM kontak_table")
    fun deleteAllKontak()
    @Query("SELECT * FROM kontak_table ORDER BY name ASC")
    fun getAllKontak(): LiveData<List<Kontak>>
}