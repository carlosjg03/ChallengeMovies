package com.example.movieschallenge.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieschallenge.R
import com.example.movieschallenge.databinding.HorizontalMovieItemBinding
import com.example.movieschallenge.models.RatedMovieModel


class AdapterMovieAdapter(val onClick: (movie: RatedMovieModel)->Unit,val loadMoreItems: ()->Unit) :
    RecyclerView.Adapter<AdapterMovieAdapter.MovieViewHolder>() {
    private val items: MutableList<RatedMovieModel> = arrayListOf()

    fun putMovies(itemsToPut: List<RatedMovieModel>){
        val totalAntes = items.size
        items.addAll(itemsToPut.filter { items.contains(it).not() })
        notifyItemRangeInserted(if(totalAntes>0)totalAntes-1 else 0,items.size-totalAntes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HorizontalMovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if(position == items.size-1){
            loadMoreItems.invoke()
        }
        holder.bind(items[position],onClick)
    }

    override fun getItemCount() = items.size

    class MovieViewHolder(val binding: HorizontalMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: RatedMovieModel,  onClick: (movie: RatedMovieModel) -> Unit) {
            binding.apply {
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_error)

                Glide.with(binding.root.context).clear(binding.ivMovie)
                Glide.with(binding.root.context)
                    .load("https://image.tmdb.org/t/p/original"+data.poster_path)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(options).into(binding.ivMovie)
            }
        }
    }
}