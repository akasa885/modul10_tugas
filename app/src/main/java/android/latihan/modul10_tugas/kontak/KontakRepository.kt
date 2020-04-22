package android.latihan.modul10_tugas.kontak

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class KontakRepository (application: Application) {
    private var kontakDao: KontakDao
    private var allKontaks: LiveData<List<Kontak>>
    init {
        val database: KontakDatabase = KontakDatabase.getInstance(
            application.applicationContext
        )!!
        kontakDao = database.kontakDao()
        allKontaks = kontakDao.getAllKontak()
    }
    fun insert(kontak: Kontak) {
        val insertKontakAsyncTask = InsertKontakAsyncTask(kontakDao).execute(kontak)
    }
    fun update(kontak: Kontak) {
        val updateKontakAsyncTask = UpdateKontakAsyncTask(kontakDao).execute(kontak)
    }
    fun delete(kontak: Kontak) {
        val deleteKontakAsyncTask = DeleteKontakAsyncTask(kontakDao).execute(kontak)
    }
    fun deleteAllKontak() {
        val deleteAllKontaksAsyncTask = DeleteAllKontaksAsyncTask(
            kontakDao
        ).execute()
    }
    fun getAllKontak(): LiveData<List<Kontak>> {
        return allKontaks
    }
    companion object {
        private class InsertKontakAsyncTask(KontakDao: KontakDao) : AsyncTask<Kontak, Unit, Unit>() {
            val KontakDao = KontakDao
            override fun doInBackground(vararg p0: Kontak?) {
                KontakDao.insert(p0[0]!!)
            }
        }
        private class UpdateKontakAsyncTask(KontakDao: KontakDao) : AsyncTask<Kontak, Unit, Unit>() {
            val KontakDao = KontakDao
            override fun doInBackground(vararg p0: Kontak?) {
                KontakDao.update(p0[0]!!)
            }
        }
        private class DeleteKontakAsyncTask(KontakDao: KontakDao) : AsyncTask<Kontak, Unit, Unit>() {
            val KontakDao = KontakDao
            override fun doInBackground(vararg p0: Kontak?) {
                KontakDao.delete(p0[0]!!)
            }
        }
        private class DeleteAllKontaksAsyncTask(KontakDao: KontakDao) : AsyncTask<Unit, Unit, Unit>() {
            val KontakDao = KontakDao
            override fun doInBackground(vararg p0: Unit?) {
                KontakDao.deleteAllKontak()
            }
        }
    }
}