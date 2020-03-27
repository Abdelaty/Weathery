package com.kmsoftapp.weathery.ui.camera

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmsoftapp.weathery.R
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import kotlinx.android.synthetic.main.item_thumbnail.view.*


class ThumbnailAdapter(
    private val items: ArrayList<ImageEntry>,
    val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_thumbnail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageEntry: ImageEntry = items[position]
        holder.thumbnailSrc.setImageURI(Uri.parse(imageEntry.uri))
        holder.bindView(imageEntry, itemClickListener)
    }


}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val thumbnailSrc = view.thumbnail_src!!
    override fun onClick(p0: View?) {}
    fun bindView(imageEntry: ImageEntry, clickListener: OnItemClickListener) {
        itemView.setOnClickListener {
            clickListener.onItemClicked(imageEntry.uri)
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(imageUri: String)
}