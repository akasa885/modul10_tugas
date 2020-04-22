package android.latihan.modul10_tugas.kontak

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kontak_table")
data class Kontak (
    var name: String,
    var noTelp: String,
    var email: String,
    var kantor: String,
    var grup: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 }