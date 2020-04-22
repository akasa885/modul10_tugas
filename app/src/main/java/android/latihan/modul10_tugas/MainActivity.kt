package android.latihan.modul10_tugas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.latihan.modul10_tugas.kontak.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2 }
    private lateinit var noteViewModel: KontakViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditKontak::class.java),
                ADD_NOTE_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = KontakAdapter()
        recycler_view.adapter = adapter
        noteViewModel = ViewModelProviders.of(this).get(KontakViewModel::class.java)
        noteViewModel.getAllKontak().observe(this, Observer<List<Kontak>> {
            adapter.submitList(it) })
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getKontakAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Kontak dihapus!", Toast.LENGTH_SHORT).show()
            } }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : KontakAdapter.OnItemClickListener {
            override fun onItemClick(kontak: Kontak) {
                val intent = Intent(baseContext, AddEditKontak::class.java)
                intent.putExtra(AddEditKontak.EXTRA_ID, kontak.id)
                intent.putExtra(AddEditKontak.EXTRA_NAMA, kontak.name)
                intent.putExtra(AddEditKontak.EXTRA_NOTELP, kontak.noTelp)
                intent.putExtra(AddEditKontak.EXTRA_EMAIL, kontak.email)
                intent.putExtra(AddEditKontak.EXTRA_INSTANSI, kontak.kantor)
                intent.putExtra(AddEditKontak.EXTRA_GRUP, kontak.grup)
                startActivityForResult(intent, EDIT_NOTE_REQUEST) }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllKontak()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            } }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newKontak = Kontak(
                data!!.getStringExtra(AddEditKontak.EXTRA_NAMA),
                data.getStringExtra(AddEditKontak.EXTRA_NOTELP),
                data.getStringExtra(AddEditKontak.EXTRA_EMAIL),
                data.getStringExtra(AddEditKontak.EXTRA_INSTANSI),
                data.getIntExtra(AddEditKontak.EXTRA_GRUP, 1) )
            noteViewModel.insert(newKontak)
            Toast.makeText(this, "Kontak disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditKontak.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }
            val updateKontak = Kontak(
                data!!.getStringExtra(AddEditKontak.EXTRA_NAMA),
                data.getStringExtra(AddEditKontak.EXTRA_NOTELP),
                data.getStringExtra(AddEditKontak.EXTRA_EMAIL),
                data.getStringExtra(AddEditKontak.EXTRA_INSTANSI),
                data.getIntExtra(AddEditKontak.EXTRA_GRUP, 1) )
            updateKontak.id = data.getIntExtra(AddEditKontak.EXTRA_ID, -1)
            noteViewModel.update(updateKontak)
        } else {
            Toast.makeText(this, "Kontak tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}
