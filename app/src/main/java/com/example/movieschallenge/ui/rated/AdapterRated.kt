package com.example.movieschallenge.ui.rated

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieschallenge.R
import com.example.movieschallenge.databinding.ItemRatedBinding
import com.example.movieschallenge.models.RatedMovieModel

class AdapterRated(val loadMoreItems: ()->Unit) :
    RecyclerView.Adapter<AdapterRated.MovieViewHolder>() {
    private val items: MutableList<RatedMovieModel> = arrayListOf()

    fun putMovies(itemsToPut: List<RatedMovieModel>){
        val totalAntes = items.size
        items.addAll(itemsToPut.filter { items.contains(it).not() })
        notifyItemRangeInserted(if(totalAntes>0)totalAntes-1 else 0,items.size-totalAntes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRatedBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if(position == items.size-1){
            loadMoreItems.invoke()
        }
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class MovieViewHolder(val binding: ItemRatedBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: RatedMovieModel) {
            binding.apply {
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_error)

                Glide.with(binding.root.context).clear(binding.includeMovie.ivMovie)
                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/original"+data.poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(options).into(binding.includeMovie.ivMovie)
                tvTitle.text = data.title
                tvDescription.text = data.overview
                ratingBar.rating = data.rating.toFloat()/2
            }
        }
    }
}