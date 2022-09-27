package com.yassine.smarthome

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.base.bean.TPBaseAd
import com.tradplus.ads.open.LoadAdEveryLayerListener
import com.tradplus.ads.open.R
import com.tradplus.ads.open.banner.BannerAdListener
import com.tradplus.ads.open.banner.TPBanner
import com.tradplus.ads.open.interstitial.InterstitialAdListener
import com.tradplus.ads.open.interstitial.TPInterstitial
import com.tradplus.ads.open.nativead.NativeAdListener
import com.tradplus.ads.open.nativead.TPNative
import com.tradplus.ads.open.splash.SplashAdListener
import com.tradplus.ads.open.splash.TPSplash


object Ads {
    const val TAG = ""
    const val APPID = "44273068BFF4D8A8AFF3D5B11CBA3ADE"
    const val NATIVE_ADUNITID = "DDBF26FBDA47FBE2765F1A089F1356BF"
    const val INTERSTITIAL_ADUNITID = "E609A0A67AF53299F2176C3A7783C46D"
    const val INTERSTITIAL_ADUNITID2 = "E609A0A67AF53299F2176C3A7783C46D"
    const val BANNER_ADUNITID = "A24091715B4FCD50C0F2039A5AF7C4BB"
    const val SPLASH_ADUNITID = "D9118E91DD06DF6D322369455CAED618"
    const val ENTRY_AD_INTERSTITIAL = "01EAD2CCED1870"

    var mTPInterstitial: TPInterstitial? = null
    var tpBanner: TPBanner? = null
    fun initInterstitialAd(context: Context?) {
        mTPInterstitial = TPInterstitial(context, INTERSTITIAL_ADUNITID, false)
        mTPInterstitial?.entryAdScenario(ENTRY_AD_INTERSTITIAL)
        mTPInterstitial?.setAdListener(object : InterstitialAdListener {
            override fun onAdLoaded(tpAdInfo: TPAdInfo?) {
                Log.i(TAG, "onAdLoaded: ")
            }

            override fun onAdClicked(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClicked: add clicked " + tpAdInfo.adSourceName)
            }

            override fun onAdImpression(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdImpression: " + tpAdInfo.adSourceName)
            }

            override fun onAdFailed(tpAdError: TPAdError?) {
                Log.i(TAG, "onAdFailed: ")
            }

            override fun onAdClosed(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClosed: " + tpAdInfo.adSourceName)
            }

            override fun onAdVideoError(tpAdInfo: TPAdInfo, tpAdError: TPAdError?) {
                Log.i(TAG, "onAdClosed: " + tpAdInfo.adSourceName)
            }
        })
        mTPInterstitial?.setAllAdLoadListener(object : LoadAdEveryLayerListener {
            override fun onAdAllLoaded(b: Boolean) {
                Log.i(TAG, "onAdAllLoaded：$b")
            }

            override fun oneLayerLoadFailed(tpAdError: TPAdError, tpAdInfo: TPAdInfo) {
                Log.i(
                    TAG,
                    "oneLayerLoadFailed:  failed" + tpAdInfo.adSourceName.toString() + "code :: " +
                            tpAdError.getErrorCode()
                                .toString() + " , Msg :: " + tpAdError.getErrorMsg()
                )
            }

            override fun oneLayerLoaded(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "oneLayerLoaded: " + tpAdInfo.adSourceName)
            }

            override fun onLoadAdStart(tpAdInfo: TPAdInfo?) {}
            override fun onBiddingStart(tpAdInfo: TPAdInfo?) {}
            override fun onBiddingEnd(tpAdInfo: TPAdInfo?, tpAdError: TPAdError?) {}
        })
    }

    fun loadInterstitialAd() {
        mTPInterstitial?.loadAd()
    }

    fun showInterstitialAd(activity: Activity?) {
        mTPInterstitial?.showAd(activity, ENTRY_AD_INTERSTITIAL)
    }

    fun loadSplashAd(context: Activity) {
        val tpSplash = TPSplash(context, SPLASH_ADUNITID)
        tpSplash.setAdListener(object : SplashAdListener() {
            override fun onAdClicked(tpAdInfo: TPAdInfo?) {
                Log.i(TAG, "onAdClicked: ")
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
                context.finish()
            }

            override fun onAdImpression(tpAdInfo: TPAdInfo?) {
                Log.d(TAG, "onAdImpression: ")
            }

            override fun onAdClosed(tpAdInfo: TPAdInfo?) {
                Log.i(TAG, "onAdClosed: ")
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
                context.finish()
            }

            override fun onAdLoaded(tpAdInfo: TPAdInfo?, tpBaseAd: TPBaseAd?) {
                Log.i(TAG, "onAdLoaded: ")
                tpSplash.showAd()
            }

            override fun onAdLoadFailed(tpAdError: TPAdError?) {
                super.onAdLoadFailed(tpAdError)
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
                context.finish()
            }

            override fun onAdShowFailed(tpAdInfo: TPAdInfo?, tpAdError: TPAdError?) {
                super.onAdShowFailed(tpAdInfo, tpAdError)
                val i = Intent(context, MainActivity::class.java)
                context.startActivity(i)
                context.finish()
            }
        })
        tpSplash.loadAd(null)
    }

    fun loadBanner(context: Context?, adContainer: LinearLayout) {
        tpBanner = TPBanner(context)
        tpBanner?.setAdListener(object : BannerAdListener() {
            override fun onAdClicked(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClicked: " + tpAdInfo.adSourceName)
            }

            override fun onAdImpression(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdImpression: " + tpAdInfo.adSourceName)
            }

            override fun onAdLoaded(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdLoaded: " + tpAdInfo.adSourceName)
            }

            override fun onAdLoadFailed(error: TPAdError) {
                Log.i(
                    TAG,
                    "onAdLoadFailed:，code :" + error.getErrorCode()
                        .toString() + ", msg : " + error.getErrorMsg()
                )
            }

            override fun onAdClosed(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClosed: " + tpAdInfo.adSourceName)
            }
        })
        adContainer.addView(tpBanner)
        tpBanner?.loadAd(BANNER_ADUNITID)
    }

    fun destroyBanner() {
        tpBanner?.onDestroy()
    }

    fun loadNormalNative(context: Context?, adContainer: LinearLayout?) {
        val tpNative = TPNative(context, NATIVE_ADUNITID)
        tpNative.setAdListener(object : NativeAdListener() {
            override fun onAdLoaded(tpAdInfo: TPAdInfo, tpBaseAd: TPBaseAd?) {
                Log.i(TAG, "onAdLoaded: " + tpAdInfo.adSourceName)
                //tpNative.showAd(adContainer, R.layout.tp_native_ad_list_item, "");
            }

            override fun onAdClicked(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClicked: " + tpAdInfo.adSourceName)
            }

            override fun onAdImpression(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdImpression: " + tpAdInfo.adSourceName)
            }

            override fun onAdShowFailed(tpAdError: TPAdError?, tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdShowFailed: " + tpAdInfo.adSourceName)
            }

            override  fun onAdLoadFailed(tpAdError: TPAdError) {
                Log.i(
                    TAG,
                    "onAdLoadFailed: , code : " + tpAdError.getErrorCode()
                        .toString() + ", msg :" + tpAdError.getErrorMsg()
                )
            }

            override fun onAdClosed(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClosed: " + tpAdInfo.adSourceName)
            }
        })
        tpNative.loadAd()
    }

    fun createNativeAd(
        context: Context,
        commandsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
        pos: Int
    ): TheNativeAd {
        Log.i("ab_doooo" , "createNativeAd at " + pos)
        val tpNative = TPNative(context.applicationContext, NATIVE_ADUNITID)
        val nativeAd = TheNativeAd(tpNative)
        nativeAd.tpNative.setAdListener(object : NativeAdListener() {
            override fun onAdLoaded(tpAdInfo: TPAdInfo?, tpBaseAd: TPBaseAd?) {
                Log.i("ab_doooo", "createNativeAd loaded $pos")
                nativeAd.isLoaded = true
                commandsAdapter?.notifyItemChanged(pos)
            }

            override fun onAdClicked(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClicked: " + tpAdInfo.adSourceName)
            }

            override  fun onAdImpression(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdImpression: " + tpAdInfo.adSourceName)
            }

            override fun onAdShowFailed(tpAdError: TPAdError?, tpAdInfo: TPAdInfo) {
                //nativeAd.setLoaded(false);
                Log.i(TAG, "onAdShowFailed: " + tpAdInfo.adSourceName)
            }

            override  fun onAdLoadFailed(tpAdError: TPAdError?) {
                //nativeAd.setLoaded(false);
                Log.i("ab_doooo" , "createNativeAd failed $pos  ${tpAdError?.errorMsg}");
            }

            override fun onAdClosed(tpAdInfo: TPAdInfo) {
                Log.i(TAG, "onAdClosed: " + tpAdInfo.adSourceName)
            }
        })
        tpNative.loadAd()
        return nativeAd
    }
}