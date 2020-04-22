package android.latihan.modul10_tugas

import androidx.recyclerview.widget.RecyclerView
import android.latihan.modul10_tugas.kontak.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.kontak_item.view.*

class KontakAdapter : ListAdapter<Kontak, KontakAdapter.KontakHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Kontak>() {
            override fun areItemsTheSame(oldItem: Kontak, newItem: Kontak): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Kontak, newItem: Kontak): Boolean {
                return oldItem.name == newItem.name && oldItem.noTelp == newItem.noTelp
                        && oldItem.grup == newItem.grup && oldItem.email == newItem.email
                        && oldItem.kantor == newItem.kantor
            } } }
    private var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KontakHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.kontak_item, parent, false)
        return KontakHolder(itemView)
    }
    override fun onBindViewHolder(holder: KontakHolder, position: Int) {
        val currentKontak: Kontak = getItem(position)
        holder.textViewNama.text = currentKontak.name
        if (currentKontak.grup == 1){
            holder.textViewPriority.text = "Keluarga"
        }else if(currentKontak.grup == 2){
            holder.textViewPriority.text = "Kerabat"
        }else if(currentKontak.grup == 3){
            holder.textViewPriority.text = "Client"
        }else if(currentKontak.grup == 4){
            holder.textViewPriority.text = "Teman"
        }else{
            //do nothing
        }
        holder.textViewNo.text = currentKontak.noTelp
        holder.textViewEmail.text = currentKontak.email
    }
    fun getKontakAt(position: Int): Kontak {
        return getItem(position)
    }
    inner class KontakHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                } } }
        var textViewNama: TextView = itemView.text_view_name
        var textViewPriority: TextView = itemView.text_view_priority
        var textViewNo: TextView = itemView.text_view_noTelp
        var textViewEmail: TextView = itemView.text_view_email
    }
    interface OnItemClickListener {
        fun onItemClick(kontak: Kontak)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}