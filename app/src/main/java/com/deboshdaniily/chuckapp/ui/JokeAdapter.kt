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
    private val supplier: (position: Int, callback: (Try<JokeModel>) -> Unit) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    @Volatile
    private var downloadedJokes: MutableList<JokeModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)
        )
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {

        holder.itemView.apply {
            joke_text.text = context.getString(R.string.loading)
            joke_category.text = context.getString(R.string.loading)
        }

        synchronized(this) {
            if (downloadedJokes.size <= position) {
                supplier.invoke(position) {
                    Log.e("JokeAdapter", "supplier.invoke, it = $it")
                    holder.itemView.apply {
                        if (it.isSuccess) {
                            val model = it.get()
                            joke_text.text = model.joke
                            joke_category.text = String.format(
                                resources.getString(
                                    R.string.adapter_categories,
                                    (model.categories?.joinToString(", "))
                                        ?: resources.getString(R.string.adapter_categories_none)
                                )
                            )
                            downloadedJokes.add(model)
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
                            joke_text.text = resources.getString(R.string.err_connection_msg)
                            joke_category.text = "???"
                        }
                    }
                }
            } else {
                val model = downloadedJokes[position]
                holder.itemView.apply {
                    joke_text.text = model.joke
                    joke_category.text = String.format(
                        resources.getString(R.string.adapter_categories),
                        model.categories?.joinToString(", ") ?: resources.getString(R.string.adapter_categories_none)
                    )
                    downloadedJokes.add(model)
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
                }
            }
        }
    }

    fun setJokesList(jokes: Collection<JokeModel>) {
        downloadedJokes.clear()
        downloadedJokes.addAll(jokes)
    }

    class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
