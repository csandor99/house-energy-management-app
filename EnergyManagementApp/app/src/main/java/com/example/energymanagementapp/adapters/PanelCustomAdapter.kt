package com.example.energymanagementapp.adapters

import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.energymanagementapp.R
import com.example.energymanagementapp.models.PanelViewModel

class PanelCustomAdapter(private val mList: List<PanelViewModel>

) : RecyclerView.Adapter<PanelCustomAdapter.ViewHolder>() {

    var listener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.panel_card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        // holder.imageView.setImageResource(ItemsViewModel.image)
        // sets the text to the textview from our itemHolder class

        if(itemViewModel.auto) {
            holder.autoIconView.visibility = VISIBLE
        }else {
            holder.autoIconView.visibility = INVISIBLE
        }

        holder.textView.text = (itemViewModel.text  + " slope: " + String.format("%.2f", itemViewModel.slope)  )
        if(itemViewModel.selected){

            holder.cardView.setCardBackgroundColor(Color.CYAN)
        }

        holder.itemView.setOnClickListener { listener?.let { it1 -> it1(position) } }
    }



    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val cardView: CardView = itemView.findViewById(R.id.panelCardView)
        val autoIconView:ImageView = itemView.findViewById(R.id.autoIcon)


    }
}


