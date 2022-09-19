package com.intuitiondecidetraining.ui.main_flow.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.intuitiondecidetraining.R
import com.intuitiondecidetraining.setDebouncedOnClickListener

class TestAdapter(
    private val resources: Resources,
    private val onClickListener: (id: Int) -> Boolean
) : ListAdapter<Int, TestAdapter.TestItemViewHolder>(TestItemsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestItemViewHolder {
        return TestItemViewHolder.create(parent)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TestItemViewHolder, position: Int) {
        holder.apply {
            getItem(position).apply {

                testButtonItemView.setDebouncedOnClickListener {
                    when (onClickListener(this)) {
                        true -> {
                            testButtonItemView.backgroundTintList =
                                ColorStateList.valueOf(resources.getColor(R.color.green))
                        }
                        false -> {
                            testButtonItemView.backgroundTintList =
                                ColorStateList.valueOf(resources.getColor(R.color.red))
                        }
                    }
                }
            }
        }
    }

    class TestItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val testButtonItemView: MaterialButton = itemView.findViewById(R.id.test_button)

        companion object {
            fun create(parent: ViewGroup): TestItemViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_item, parent, false)
                return TestItemViewHolder(view)
            }
        }
    }

    class TestItemsComparator : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}