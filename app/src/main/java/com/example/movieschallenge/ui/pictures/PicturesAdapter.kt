package com.example.movieschallenge.ui.pictures

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieschallenge.R
import com.example.movieschallenge.databinding.ItemImagesBinding

class PicturesAdapter() : RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder>() {
    private val items: MutableList<Uri> = arrayListOf()
    private val itemsPair: MutableList<Pair<Uri,Uri?>> = arrayListOf()

    fun putPictures(itemsToPut: List<Uri>){
        items.clear()
        itemsPair.clear()
        items.addAll(itemsToPut.filter { items.contains(it).not() })
        var pair:Pair<Uri,Uri?>?=null
        items.forEach{
            if(pair == null){
                pair = Pair(it,null)
            } else {
                pair = Pair(pair!!.first,it)
                itemsPair.add(pair!!)
                pair = null
            }
        }
        if(pair!=null){
            itemsPair.add(pair!!)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemImagesBinding.inflate(layoutInflater, parent, false)
        return PicturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {

        holder.bind(itemsPair[position])
    }

    override fun getItemCount() = itemsPair.size

    class PicturesViewHolder(val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: Pair<Uri,Uri?>) {
            binding.apply {
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_movie)
                    .error(R.drawable.ic_error)

                Glide.with(binding.root.context).clear(binding.iv1)
                Glide.with(binding.root.context)
                    .load(data.first)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(options).into(binding.iv1)
                Glide.with(binding.root.context).clear(binding.iv2)
                if(data.second!=null){
                    Glide.with(binding.root.context)
                        .load(data.second)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .apply(options).into(binding.iv2)
                }
            }
        }
    }
}