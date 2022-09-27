package com.yassine.smarthome

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yassine.smarthome.databinding.AdsListItemTitlesCommandsBinding
import com.yassine.smarthome.databinding.ArticlesItemViewBinding
import com.yassine.smarthome.databinding.CommandsCategoryItemviewBinding

class ArticlesAdapter(
    val arrayListArticles: ArrayList<Any> ,
    val context: Activity

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var NATIVE_AD = 2
    var Article = 3


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == NATIVE_AD) {
            val binding: AdsListItemTitlesCommandsBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.ads_list_item_titles_commands, parent, false
            )
            NativeAdHolder(binding.root)
        } else {
            val binding: ArticlesItemViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.articles_item_view,
                parent,
                false
            )
            CategoryHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("ab_do", "onBindViewHolder $position")
        if (holder.itemViewType == NATIVE_AD) {
            Log.i("ab_do", "NATIVE_AD  $position")
            val nativeAdHolder = holder as NativeAdHolder
            //                Ads.loadNormalNative(context, nativeAdHolder.mBinding.nativeView1);
            val nativeAd = arrayListArticles[position] as TheNativeAd
            if (nativeAd.isLoaded) {
                Log.i("ab_do", "Show ad $position")
                nativeAd.tpNative.showAd(
                    nativeAdHolder.mBinding!!.nativeView1,
                    R.layout.tp_native_ad_list_item,
                    ""
                )
            } else {
                Log.i("ab_do", "not loaded $position")
                nativeAd.tpNative.loadAd()
            }
            //nativeAd.getTpNative().showAd(nativeAdHolder.mBinding.nativeView1, R.layout.tp_native_ad_list_item, "");
        } else {
            loadCommand(holder, position)
        }
    }

    private fun loadCommand(holder: RecyclerView.ViewHolder, position: Int) {
        val command = arrayListArticles[position] as String
        (holder as CategoryHolder).binding.textviewCategory.setText(command)
        if (command == "Favorites") {
            holder.binding.CategoryImgView.setImageResource(R.drawable.heart)
        }
        if (command == "Basic Grammar") {
            holder.binding.CategoryImgView.setImageResource(R.drawable.grammar)
        }
        if (command == "Article") {
            holder.binding.CategoryImgView.setImageResource(R.drawable.article)
        }
        if (command == "Sentence") {
            holder.binding.CategoryImgView.setImageResource(R.drawable.skill)
        }
//        holder.binding.root.setOnClickListener {
//            Ads.showInterstitialAd(context)
//            val bundle = Bundle()
//            bundle.putString("nameCategory", command)
//            val intent = Intent(context, CommandsActivity::class.java)
//            intent.putExtra("bundle", bundle)
//            context.startActivity(intent)
//            context.finish()
//        }
    }

    override fun getItemCount(): Int {
        return arrayListArticles.size
    }


    override fun getItemViewType(position: Int): Int {
        val spanNum =
            (MainActivity.numberOFItemsInRow * MainActivity.displayAdAfterNumberOfRows) + 1
        return if ((position + 1) % spanNum == 0) {
            NATIVE_AD
        } else {
            Article
        }
    }

    class CategoryHolder(binding: ArticlesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ArticlesItemViewBinding

        init {
            this.binding = binding
        }
    }

    class NativeAdHolder internal constructor(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {
        var mBinding: AdsListItemTitlesCommandsBinding?

        init {
            mBinding = DataBindingUtil.bind(itemView!!)
        }
    }
}
