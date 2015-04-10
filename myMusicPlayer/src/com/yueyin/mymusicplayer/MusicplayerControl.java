package com.yueyin.mymusicplayer;

import java.io.FileOutputStream;
import android.annotation.SuppressLint;
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
	public static  void playmusic(final Context context,final int position,final ImageButton playbutton)
	{
		try { 
			String src=MusicplayerData.musicfile.get(position);
			MusicplayerData.myMediaPlayer.reset();
			MusicplayerData.myMediaPlayer.setDataSource(src);
			MusicplayerData.myMediaPlayer.prepare();
			MusicplayerData.myMediaPlayer.start();
			
			if (MusicplayerData.play_flag==false){
				Resources r = context.getResources(); 
    			Drawable img=r.getDrawable(R.drawable.play);
    			playbutton.setImageDrawable(img);
    			MusicplayerData.play_flag=true;
			} 
			MusicplayerData.musiclist_lastplay.add(src);
			MusicplayerData.currentPosition=position;
			save_music_list(context,MusicplayerData.currentMusiclist_filename, position);
			MusicplayerData.myMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
	            
	            @Override
	            public void onCompletion(MediaPlayer mp) { 
	                nextmusic(context,position,playbutton);
	            }
	        });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	public  static void nextmusic(final Context context,int position,final ImageButton playbutton)
	{
		position++;
		if (position>=MusicplayerData.musicfile.size())
			position=0;
		playmusic(context,position,playbutton);
	}
	
	public  static void premusic(final Context context,int position,final ImageButton playbutton)
	{
		position--;
		if (position<0)
			position=MusicplayerData.musicfile.size()-1;
		playmusic(context,position,playbutton);
	}
	
	public static  void save_music_list(final Context context,String listPath,Integer pos)
	{
		SharedPreferences mSharedPreferences = context.getSharedPreferences("MainActivity.class", Context.MODE_PRIVATE);  
		SharedPreferences.Editor edit=mSharedPreferences.edit();
		
		edit.putString("listpath", listPath);
		edit.putInt("curPosition", pos);
		System.out.println("write: "+listPath+pos);
		
		try{
				FileOutputStream fout =
						context.openFileOutput(
						MusicplayerData.Play_song_and_list_info_filepath,Activity.MODE_PRIVATE);
		       byte [] bytes = listPath.getBytes(); 
		       fout.write(bytes);
	    	   String newLine = System.getProperty("line.separator");
	    	   fout.write(newLine.getBytes());
	    	   bytes=(pos.toString()).getBytes(); 	
	    	   fout.write(bytes);
		       fout.close();   
		     }catch(Exception e){   
		        e.printStackTrace();   
		     }   
	}
	
}
