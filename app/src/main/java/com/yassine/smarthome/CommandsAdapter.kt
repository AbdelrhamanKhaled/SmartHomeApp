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

class CommandsAdapter(
    val context: Activity,
    private val arrayListCommands: ArrayList<String>
) :
    RecyclerView.Adapter<CommandsAdapter.CommandsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandsHolder {
        val binding: CommandItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.command_item_view, parent, false
        )
        return CommandsHolder(binding)
    }


    override fun onBindViewHolder(holder: CommandsHolder, position: Int) {
        Speech.init(context, context.packageName)
        val favoriteDatabase = FavoriteDatabase(context)
        val command: String = arrayListCommands[position]
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

        if (favoriteDatabase.checkIfExist(command.trim { it <= ' ' })) {
            holder.binding.addToFavouriteImgView.setImageResource(R.drawable.star_filled)
        } else {
            holder.binding.addToFavouriteImgView.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
        holder.binding.addToFavouriteImgView.setOnClickListener {
            if (!favoriteDatabase.checkIfExist(command.trim { it <= ' ' })) {
                favoriteDatabase.insertFavorite(command.trim { it <= ' ' })
                holder.binding.addToFavouriteImgView.setImageResource(R.drawable.star_filled)
            }
            else {
                favoriteDatabase.deleteFromFavorite(command.trim { it <= ' ' })
                holder.binding.addToFavouriteImgView.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }

        holder.binding.root.isFocusable = false
    }

    override fun getItemCount(): Int = arrayListCommands.size

    class CommandsHolder(binding: CommandItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: CommandItemViewBinding

        init {
            this.binding = binding
        }
    }
}