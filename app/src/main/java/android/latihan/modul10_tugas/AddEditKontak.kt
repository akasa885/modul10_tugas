package android.latihan.modul10_tugas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_kontak.*

class AddEditKontak : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.piusanggoro.notesapp.EXTRA_ID"
        const val EXTRA_NAMA = "com.piusanggoro.notesapp.EXTRA_NAMA"
        const val EXTRA_NOTELP = "com.piusanggoro.notesapp.EXTRA_NOTELP"
        const val EXTRA_EMAIL = "com.piusanggoro.notesapp.EXTRA_EMAIL"
        const val EXTRA_INSTANSI = "com.piusanggoro.notesapp.EXTRA_INSTANSI"
        const val EXTRA_GRUP = "com.piusanggoro.notesapp.EXTRA_GRUP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kontak)
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 4
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Kontak"
            edit_text_nama.setText(intent.getStringExtra(EXTRA_NAMA))
            edit_text_no.setText(intent.getStringExtra(EXTRA_NOTELP))
            edit_text_email.setText(intent.getStringExtra(EXTRA_EMAIL))
            edit_text_instansi.setText(intent.getStringExtra(EXTRA_INSTANSI))
            number_picker_priority.value = intent.getIntExtra(EXTRA_GRUP, 1)
        } else {
            title = "Tambah Kontak Baru"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_kontak_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveKontak()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun saveKontak() {
        if (edit_text_nama.text.toString().trim().isBlank() || edit_text_no.text.toString().trim().isBlank() || edit_text_email.text.toString().trim().isBlank() ) {
            Toast.makeText(this, "Kontak kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_NAMA, edit_text_nama.text.toString())
            putExtra(EXTRA_NOTELP, edit_text_no.text.toString())
            putExtra(EXTRA_EMAIL, edit_text_email.text.toString())
            putExtra(EXTRA_INSTANSI, edit_text_instansi.text.toString())
            putExtra(EXTRA_GRUP, number_picker_priority.value)
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            } }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}