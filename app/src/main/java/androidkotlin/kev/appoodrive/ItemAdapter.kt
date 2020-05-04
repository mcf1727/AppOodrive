package androidkotlin.kev.appoodrive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(val items: List<Item>, val itemClickListener: View.OnClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.card_view.tag = position
        holder.itemView.card_view.setOnClickListener(itemClickListener)
        holder.itemView.item_view.text = item.itemName
    }

    override fun getItemCount(): Int {
        return items.size
    }
}