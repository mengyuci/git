package com.yueyin.mymusicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.widget.ImageButton;

public class MusicplayerControl extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public static  void playmusic(final Context context,final int position,final PlayControl playControl)
	{
		try { 
			String src=MusicplayerData.musicfile.get(position);
	    	Musicinfo musicinfo=Musicinfo.getMetaData(src);
	    	playControl.setSingerSongName(musicinfo.title, musicinfo.artist);
			MusicplayerData.myMediaPlayer.reset();
			MusicplayerData.myMediaPlayer.setDataSource(src);
			MusicplayerData.myMediaPlayer.prepare();
			MusicplayerData.myMediaPlayer.start();
			
			if (MusicplayerData.playStatus==false){
				Resources r = context.getResources(); 
    			Drawable img=r.getDrawable(R.drawable.pause);
    			playControl.play.setImageDrawable(img);
    			MusicplayerData.playStatus=true;
			} 
			MusicplayerData.musiclist_lastplay.add(src);
			MusicplayerData.currentPosition=position;
			save_music_list(context);
			MusicplayerData.myMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
	            
	            @Override
	            public void onCompletion(MediaPlayer mp) { 
	                nextmusic(context,position,playControl);
	            }
	        });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	public static  void playOrPause(final Context context,final PlayControl playControl)
	{
		if (MusicplayerData.playStatus){
			MusicplayerData.myMediaPlayer.pause();
			Resources r = context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.play);
			playControl.play.setImageDrawable(img);
		}else{
			MusicplayerData.myMediaPlayer.start();
			Resources r = context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.pause);
			playControl.play.setImageDrawable(img);
		}
		MusicplayerData.playStatus=!MusicplayerData.playStatus;
	}
	public  static void nextmusic(final Context context,int position,final PlayControl playControl)
	{
		position++;
		if (position>=MusicplayerData.musicfile.size())
			position=0;
		playmusic(context,position,playControl);
	}
	
	public  static void premusic(final Context context,int position,final PlayControl playControl)
	{
		position--;
		if (position<0)
			position=MusicplayerData.musicfile.size()-1;
		playmusic(context,position,playControl);
	}
	
	public static  void save_music_list(final Context context)
	{
		SharedPreferences mSharedPreferences = context.getSharedPreferences("MainActivity", Context.MODE_PRIVATE);  
		SharedPreferences.Editor edit=mSharedPreferences.edit();
		
		edit.putString("listpath", MusicplayerData.currentMusiclistFilename);
		edit.putInt("curPosition", MusicplayerData.currentPosition);
		edit.commit();
	}
	
}
