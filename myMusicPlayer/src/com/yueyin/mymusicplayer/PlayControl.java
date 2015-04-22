package com.yueyin.mymusicplayer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayControl extends RelativeLayout {
	public static Context contextForWrite;
	public SeekBar seekbar;
	public ImageView singerPhoto;
	public TextView singerName,songName,hotText;
	public ImageButton preSong,play,nextSong;
	private PlayControl playControl;
	private Button addHotnum,subHotnum;
	public boolean isStartTrackingTouch=false;
	public PlayControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		View view = View.inflate(context, R.layout.play_control, this);
		seekbar=(SeekBar) view.findViewById(R.id.songs_process);
		singerPhoto=(ImageView) view.findViewById(R.id.singerphoto);
		singerName=(TextView) view.findViewById(R.id.singer_name);
		songName=(TextView) view.findViewById(R.id.song_name);
		hotText=(TextView) view.findViewById(R.id.hot_text);
		preSong=(ImageButton) view.findViewById(R.id.pre_song);
		play=(ImageButton) view.findViewById(R.id.play);
		nextSong=(ImageButton) view.findViewById(R.id.next_song);
		addHotnum=(Button) view.findViewById(R.id.addHotnum);
		subHotnum=(Button) view.findViewById(R.id.subHotnum);
		playControl=this;
		contextForWrite=this.getContext();
		
		
		preSong.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MusicplayerControl.premusic(contextForWrite, MusicplayerData.currentPosition,playControl);
			}
		});
		play.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View v) {
        		MusicplayerControl.playOrPause(contextForWrite,playControl);
			}
			
		});
		nextSong.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MusicplayerControl.nextmusic(contextForWrite, MusicplayerData.currentPosition,playControl);
			}
		});
		addHotnum.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MusicplayerControl c=new MusicplayerControl();
				c.addHotNumber(playControl);
			}
		});
		subHotnum.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MusicplayerControl c=new MusicplayerControl();
				c.subHotNumber(playControl);
			}
		});
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				MusicplayerData.myMediaPlayer.seekTo(seekBar.getProgress());  
				if (!MusicplayerData.playStatus)
					MusicplayerControl.playOrPause(contextForWrite, playControl);
	            isStartTrackingTouch = false;  
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				isStartTrackingTouch = true;
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
				
			}
		});
		
	}
	
	void setSingerSongName(String singerName,String songName,String hotText){
		this.singerName.setText(singerName);
		this.songName.setText(songName);
		this.hotText.setText(hotText);
	}
	void setHotnum(String hotText){
		this.hotText.setText(hotText);
	}
	
	void setPlayControl(PlayControl a){
		this.hotText.setText(a.hotText.getText());
		this.singerName.setText(a.singerName.getText());
		this.songName.setText(a.songName.getText());
		if (!MusicplayerData.myMediaPlayer.isPlaying()){
			Resources r = MusicplayerData.context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.play);
			this.play.setImageDrawable(img);
		}else{
			Resources r = MusicplayerData.context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.pause);
			playControl.play.setImageDrawable(img);
		}
		this.singerPhoto.setImageDrawable(a.singerPhoto.getDrawable());
		this.isStartTrackingTouch=a.isStartTrackingTouch;
		this.seekbar.setProgress(a.seekbar.getProgress());
		this.seekbar.setMax(a.seekbar.getMax());
	}
}
