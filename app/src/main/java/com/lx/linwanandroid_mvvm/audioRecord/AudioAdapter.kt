package com.lx.linwanandroid_mvvm.audioRecord

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lx.linwanandroid_mvvm.R
import com.lx.linwanandroid_mvvm.audioRecord.audioRecord.MusicPlayer
import com.lx.linwanandroid_mvvm.databinding.ItemNormalAudioBinding
import com.lx.linwanandroid_mvvm.databinding.ItemSelectAudioBinding
import com.lx.linwanandroid_mvvm.ext.getTime
import com.lx.linwanandroid_mvvm.utils.context

/**
 * @title：AudioAdapter
 * @projectName LinWanAndroid-mvvm
 * @description: <Description>
 * @author linxiao
 * @data Created in 2021/05/04
 */
class AudioAdapter(private var datas: MutableList<AudioBean>,val mContext: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var isClick = false

    override fun getItemViewType(position: Int): Int {
        val audioBean = datas[position]
        return if (!audioBean.isSelect) {
            0
        } else {
            1
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_normal_audio, parent, false)
            ViewHolderNormal(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_select_audio, parent, false)
            ViewHolderSelect(view)
        }
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderNormal) {
            holder.binding.data = datas[position]
            holder.binding.root.setOnClickListener {
                for (audioData in datas) {
                    audioData.isSelect = false
                }
                datas[position].isSelect = true
                notifyDataSetChanged()
            }
        } else if (holder is ViewHolderSelect) {
            holder.binding.run {
                val currentData = datas[position]
//                if (currentData.isPlaying) {
//                    ivPlay.setImageResource(R.drawable.ic_pause)
//                } else {
//                    ivPlay.setImageResource(R.drawable.ic_resume)
//                }
                val musicPlayer = MusicPlayer(context())
                currentData.let {
                    totalTime.text = currentData.audioDuration.getTime()
                }
                root.setOnClickListener {
                    currentData.isSelect = false
                    notifyItemChanged(position)
                }

                ivPlay.setOnTouchListener { v, event ->
                    when(event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isClick = true
                            Handler().postDelayed(Runnable {
                                isClick = false
                            }, 500)
                            false
                        }
                        MotionEvent.ACTION_UP -> {
                            isClick = false
                            false
                        }
                        else -> {
                            false
                        }

                    }
                }

                ivPlay.setOnClickListener {
                    if (ivPlay.drawable.current.constantState ==
                        mContext.resources.getDrawable(R.drawable.ic_pause).constantState) {
                        musicPlayer.pause()
                    } else {
                        if (currentData.progress == 0) {
                            musicPlayer.play(currentData.filePath)
                        } else {
                            musicPlayer.resume()
                        }

                    }
                }

                musicPlayer.setOnSeekListener {
                    currentData.progress = it
                    progressBar.progress = it
//                    notifyItemChanged(position)
                    Log.d("progress11", it.toString())
                }
                musicPlayer.setOnStateListener {
                    if (!it) {
                        ivPlay.setImageResource(R.drawable.ic_pause)
                    } else {
                        ivPlay.setImageResource(R.drawable.ic_resume)
                    }
//                    currentData.isPlaying = !it
                }
            }
        }
    }

    override fun getItemCount(): Int = datas.size

    class ViewHolderNormal(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemNormalAudioBinding>(itemView)!!
    }

    class ViewHolderSelect(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<ItemSelectAudioBinding>(itemView)!!
    }

    fun setData(datas: MutableList<AudioBean>) {
        this.datas = datas
        notifyDataSetChanged()
    }
}