package android.latihan.modul10_tugas

import android.app.Application
import android.latihan.modul10_tugas.kontak.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class KontakViewModel (application: Application) : AndroidViewModel(application)
{
    private var repository: KontakRepository =
        KontakRepository(application)
    private var allKontaks: LiveData<List<Kontak>> = repository.getAllKontak()
    fun insert(kontak: Kontak) {
        repository.insert(kontak)
    }
    fun update(kontak: Kontak) {
        repository.update(kontak)
    }
    fun delete(kontak: Kontak) {
        repository.delete(kontak)
    }
    fun deleteAllKontak() {
        repository.deleteAllKontak()
    }
    fun getAllKontak(): LiveData<List<Kontak>> {
        return allKontaks
    }
}