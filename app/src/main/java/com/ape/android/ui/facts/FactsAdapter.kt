package com.ape.android.ui.facts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ape.R
import com.ape.android.datamodel.Row
import com.ape.android.imageloader.ImageLoader
import com.ape.android.util.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_facts.*

class FactsAdapter(private val onItemClick: (item: Row?) -> Unit) :
    RecyclerView.Adapter<FactsAdapter.FactsViewHolder>() {

    var list: List<Row>? = null

    fun addList(list: List<Row>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): FactsAdapter.FactsViewHolder =
        FactsViewHolder(parent.inflate(R.layout.item_facts))

    override fun getItemCount(): Int = if (list != null && list!!.isNotEmpty()) list!!.size else 0

    override fun onBindViewHolder(viewHolder: FactsAdapter.FactsViewHolder, position: Int) =
        viewHolder.bind(list!![position])

    inner class FactsViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!),
        LayoutContainer {

        fun bind(row: Row) {
            tv_title.text = row.title
            tv_desc.text = row.description
            ImageLoader.load(iv_pic, row.imageHref)

            itemView.setOnClickListener { onItemClick(row) }
        }
    }
}