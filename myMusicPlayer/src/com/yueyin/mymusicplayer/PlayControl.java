package com.yueyin.mymusicplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayControl extends RelativeLayout {
	public static Context contextForWrite;
	public SeekBar seekbar;
	public ImageView singerPhoto;
	public TextView singerName,songName,hotText;
	public ImageButton preSong,play,nextSong;
	private PlayControl playControl;
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
				System.out.println("in");
        		MusicplayerControl.playOrPause(contextForWrite,playControl);
			}
			
		});
		nextSong.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MusicplayerControl.nextmusic(contextForWrite, MusicplayerData.currentPosition,playControl);
			}
		});
	}
	
	void setSingerSongName(String singerName,String songName){
		this.singerName.setText(singerName);
		this.songName.setText(songName);
	}

}
