package com.intuitiondecidetraining.ui.main_flow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.intuitiondecidetraining.R
import com.intuitiondecidetraining.data.db.test.Test
import com.intuitiondecidetraining.setDebouncedOnClickListener

class TestListAdapter(
    private val onClickListener: (test: Test) -> Unit
) : ListAdapter<Test, TestListAdapter.TestViewHolder>(TestsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.apply {
            getItem(position).apply {
                bind(test = this)

                startButtonItemView.setDebouncedOnClickListener {
                    onClickListener(this)
                }
            }
        }
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.item_title)
        val startButtonItemView: MaterialButton = itemView.findViewById(R.id.start_button)

        fun bind(test: Test) {
            titleItemView.text = test.title
        }

        companion object {
            fun create(parent: ViewGroup): TestViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_list_item, parent, false)
                return TestViewHolder(view)
            }
        }
    }

    class TestsComparator : DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem == newItem
        }
    }
}