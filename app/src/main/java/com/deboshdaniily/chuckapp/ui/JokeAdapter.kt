package com.deboshdaniily.chuckapp.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deboshdaniily.chuckapp.R
import com.deboshdaniily.chuckapp.data.JokeModel
import io.vavr.control.Try
import kotlinx.android.synthetic.main.joke_item.view.*

class JokeAdapter(
    private val count: Int,
    private val supplier: (position: Int, callback: (Try<JokeModel>) -> Unit) -> Unit
) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)
        )
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {

        Log.e("JokeAdapter", "onBind")
        supplier.invoke(position) {
            Log.e("JokeAdapter", "supplier.invoke, it = $it")
            holder.itemView.apply {
                if (it.isSuccess) {
                    val model = it.get()
                    joke_text.text = model.joke
                    joke_category.text = "Category: " + (model.categories?.joinToString(", ") ?: "none")
                    joke_share_button.setOnClickListener {
                        val shareIntent = Intent()
                        shareIntent.action = Intent.ACTION_SEND
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            model.joke + resources.getString(R.string.share_text)
                        )
                        context.startActivity(shareIntent)
                    }
                } else {
                    joke_text.text = "Error downloading joke: ${it.cause}"
                    joke_category.text = "???"
                }

            }
        }
    }

    class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
