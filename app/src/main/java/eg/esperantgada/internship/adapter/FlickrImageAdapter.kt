package eg.esperantgada.internship.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import eg.esperantgada.internship.R
import eg.esperantgada.internship.databinding.ImageListItemBinding
import eg.esperantgada.internship.room.entities.PhotoItem

class FlickrImageAdapter:
    PagingDataAdapter<PhotoItem, FlickrImageAdapter.ImagePagingViewHolder>(DiffCallback) {


    inner class ImagePagingViewHolder(
        private val binding: ImageListItemBinding
    ): RecyclerView.ViewHolder(binding.root){



        //Use Glide library to load photos from the API and bind views with photos details as needed
        fun bind(photoItem: PhotoItem){
            binding.apply {
                Glide.with(itemView)
                    .load(photoItem.getImageUrl())
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error_icon)
                    .into(imageView)

                title.text = photoItem.title
            }
        }

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ImagePagingViewHolder {
        val inflatedLayout = ImageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImagePagingViewHolder(inflatedLayout)
    }


    override fun onBindViewHolder(holder: ImagePagingViewHolder, position: Int) {
        val currentPhoto = getItem(position)

        if (currentPhoto != null){
            holder.bind(currentPhoto)
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<PhotoItem>(){
        override fun areItemsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean = oldItem == newItem

    }
}