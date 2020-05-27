package ua.turskyi.searchviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

class ExampleAdapter : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>(), Filterable {
    private val exampleList: MutableList<ExampleItem> = mutableListOf()
    private var exampleListFull: MutableList<ExampleItem> = mutableListOf()
    fun setData(exampleList: MutableList<ExampleItem>) {
        this.exampleList.clear()
        this.exampleList.addAll(exampleList)
        this.exampleListFull.addAll(exampleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ExampleViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.example_item,
            parent, false
        ))

    override fun onBindViewHolder(
        holder: ExampleViewHolder,
        position: Int
    ) {
        val (imageResource, text1, text2) = exampleList[position]
        holder.imageView.setImageResource(imageResource)
        holder.textView1.text = text1
        holder.textView2.text = text2
    }

    override fun getItemCount() = exampleList.size

    override fun getFilter()= exampleFilter

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ExampleItem> = mutableListOf()
            if (constraint.isEmpty()) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern =
                    constraint.toString().toLowerCase(Locale.getDefault()).trim()
                for (item in exampleListFull) {
                    if (item.text1.toLowerCase(Locale.getDefault()).contains(filterPattern) ||
                        item.text2.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            exampleList.clear()
            exampleList.addAll(results.values as MutableList<ExampleItem>)
            notifyDataSetChanged()
        }
    }

    inner class ExampleViewHolder(itemView: View) :
        ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image_view)
        var textView1: TextView = itemView.findViewById(R.id.text_view1)
        var textView2: TextView = itemView.findViewById(R.id.text_view2)
    }
}