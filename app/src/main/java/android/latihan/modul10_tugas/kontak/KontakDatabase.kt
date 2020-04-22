package android.latihan.modul10_tugas.kontak

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Kontak::class], version = 1)
abstract class KontakDatabase : RoomDatabase() {
    abstract fun kontakDao(): KontakDao
    companion object {
        private var instance: KontakDatabase? = null
        fun getInstance(context: Context): KontakDatabase? {
            if (instance == null) {
                synchronized(KontakDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KontakDatabase::class.java, "kontak_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                } }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: KontakDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val kontakDao = db?.kontakDao()
        override fun doInBackground(vararg p0: Unit?) {
            kontakDao?.insert(Kontak("Rizki Akbar", "085334727663", "akasa2444@gmail.com","UPN VETERAN JATIM",1))
        }
    }
}