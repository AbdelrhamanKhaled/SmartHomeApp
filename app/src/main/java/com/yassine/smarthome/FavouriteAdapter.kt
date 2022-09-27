package com.yassine.smarthome

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yassine.smarthome.databinding.CommandItemViewBinding
import net.gotev.speech.Speech
import net.gotev.speech.TextToSpeechCallback

class FavouriteAdapter(
    val context: Activity,
    private val favouriteCommands: ArrayList<String>
) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val binding: CommandItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.command_item_view, parent, false
        )
        return FavouriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        Speech.init(context, context.packageName)
        val favoriteDatabase = FavoriteDatabase(context)
        val command: String = favouriteCommands[position]
        holder.binding.textviewCommand.text = (command.trim { it <= ' ' })
        holder.binding.PlaySoundImageview.setOnClickListener {
            Speech.getInstance().say(command, object : TextToSpeechCallback {
                override fun onStart() {
                    Log.i("speak", "TTS onStart")
                }

                override fun onCompleted() {
                    Log.i("speak", "TTS onCompleted")
                }

                override fun onError() {
                    Log.i("speak", "TTS onError")
                }
            })
        }
        holder.binding.addToFavouriteImgView.setImageResource(R.drawable.star_filled)
        holder.binding.addToFavouriteImgView.setOnClickListener {
            // remove from favourite
            favoriteDatabase.deleteFromFavorite(command.trim { it <= ' ' })
            favouriteCommands.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position , favouriteCommands.size)
        }

        holder.binding.root.isFocusable = false
    }

    override fun getItemCount(): Int {
        return favouriteCommands.size
    }

    inner class FavouriteHolder(binding: CommandItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: CommandItemViewBinding

        init {
            this.binding = binding
        }
    }


}
