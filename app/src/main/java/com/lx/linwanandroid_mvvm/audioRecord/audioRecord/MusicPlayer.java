package com.lx.linwanandroid_mvvm.audioRecord.audioRecord;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer {
    private MediaPlayer mPlayer;
    private Context mContext;

    private String path;
    private Thread initThread;

    private Timer mTimer;
    private Handler mHandler;

    public MusicPlayer(Context context) {
        mContext = context;
        //构造函数中
        mTimer = new Timer();//创建Timer
        mHandler = new Handler();//创建Handler
        mPlayer = new MediaPlayer();
        setListen();
    }

    /**
     * 播放
     */
    public void play(String path) throws IOException {
        this.path = path;
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.reset();
        Uri uri = Uri.fromFile(new File(path));
        mPlayer.setDataSource(mContext, uri);
        mPlayer.prepare();
    }
    /**
     * 是否正在播放
     */
    public boolean isPlaying() {
        if (mPlayer == null) {
            return false;
        }
        return mPlayer.isPlaying();
    }

    /**
     * 销毁播放器
     */
    public void onDestroyed() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();//释放资源
            mPlayer = null;
        }
    }

    /**
     * 停止播放器
     */
    public void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        if (mOnSeekListener != null) {
            mOnSeekListener.OnSeek(0);
        }
        if (mOnSeekListener != null) {
            mOnStateListener.isCanPlay(true);
        }
    }


    /**
     * 暂停播放器
     */
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            mOnStateListener.isCanPlay(true);
        }
    }

    public void resume(){
        if (mPlayer != null) {
            mPlayer.start();
            mOnStateListener.isCanPlay(false);
        }
    }

    public void seekTo(int progress){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mPlayer.seekTo(progress, MediaPlayer.SEEK_CLOSEST);
        } else {
            mPlayer.seekTo(progress);
        }
    }

    private void setListen(){
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.start();
                mOnStateListener.isCanPlay(false);
                //开始方法中
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (isPlaying()) {
                            int pos = mPlayer.getCurrentPosition();
                            int duration = mPlayer.getDuration();
                            mHandler.post(() -> {
                                if (mOnSeekListener != null) {
                                    mOnSeekListener.OnSeek((int) (pos * 1.f / duration * 100));
                                }
                            });
                        }
                    }
                }, 0, 1000);
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mOnSeekListener.OnSeek(100);
                mOnSeekListener.OnSeek(0);
                mOnStateListener.isCanPlay(true);
                stop();
                onDestroyed();
            }
        });
    }

    //------------设置进度监听-----------
    public interface OnSeekListener {
        void OnSeek(int per_100);
    }
    private OnSeekListener mOnSeekListener;
    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }

    public interface OnStateListener {
        //true表示可以播放
        //false表示可以暂停
        void isCanPlay(boolean isCanPlay);
    }
    private OnStateListener mOnStateListener;
    public void setOnStateListener(OnStateListener onStateListener) {
         mOnStateListener = onStateListener;
    }

}
