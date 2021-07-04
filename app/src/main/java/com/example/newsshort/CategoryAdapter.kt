package com.example.newsshort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cat_item.view.*
import java.util.ArrayList

class CategoryAdapter(private var array:ArrayList<cat_data>,private val cat_listener:CatItemClicked):RecyclerView.Adapter<catHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): catHolder {
      val itemView=LayoutInflater.from(parent.context).inflate(R.layout.cat_item,parent,
          false
      )
        val mxHolder=catHolder(itemView)
        itemView.setOnClickListener {
            cat_listener.onItemClicked(array[mxHolder.adapterPosition])
        }
        return  mxHolder
    }

    override fun onBindViewHolder(holder: catHolder, position: Int) {
            holder.itemView.cat_img.setImageResource(array[position].img)
            holder.itemView.cat_text.text=array[position].name
    }

    override fun getItemCount(): Int {
        return array.size
    }
}
class catHolder(itemView: View):RecyclerView.ViewHolder(itemView){

}
interface CatItemClicked{
    fun onItemClicked(item: cat_data)
}