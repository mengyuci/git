package com.yueyin.mymusicplayer;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import com.yueyin.mymusicplayer.MusicplayerData;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends Activity {
	public static Context context;
	private ImageButton allMusic,hotRank,singer,album,playlist,waitplaylist,mylist,folder;
    private PlayControl playControl;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double height_rate=0.17;
        setContentView(R.layout.activity_main);
        
        context=this.getApplicationContext();
        allMusic=(ImageButton) findViewById(R.id.all_music);
        hotRank=(ImageButton) findViewById(R.id.hot_rank);
        singer=(ImageButton) findViewById(R.id.singer);
        album=(ImageButton) findViewById(R.id.album);
        playlist=(ImageButton) findViewById(R.id.playlist);
        waitplaylist=(ImageButton) findViewById(R.id.waitplaylist);
        mylist=(ImageButton) findViewById(R.id.mylist);
        folder=(ImageButton) findViewById(R.id.folder);
        playControl=(PlayControl) findViewById(R.id.playcontrol);
        Display display = this.getWindowManager().getDefaultDisplay();
        MusicplayerData.screenWidth=display.getWidth();
        MusicplayerData.screenHeight=display.getHeight();
        int width = display.getWidth();
        int height=display.getHeight();
        allMusic.setMinimumHeight((int) ((height)*height_rate));
        hotRank.setMinimumHeight((int) ((height)*height_rate));
        singer.setMinimumHeight((int) ((height)*height_rate));
        album.setMinimumHeight((int) ((height)*height_rate));
        playlist.setMinimumHeight((int) ((height)*height_rate));
        waitplaylist.setMinimumHeight((int) ((height)*height_rate));
        mylist.setMinimumHeight((int) ((height)*height_rate));
        folder.setMinimumHeight((int) ((height)*height_rate));
        
        allMusic.setMinimumWidth((int) (width*0.45));
        hotRank.setMinimumWidth((int) (width*0.45));
        singer.setMinimumWidth((int) (width*0.45));
        album.setMinimumWidth((int) (width*0.45));
        playlist.setMinimumWidth((int) (width*0.45));
        waitplaylist.setMinimumWidth((int) (width*0.45));
        mylist.setMinimumWidth((int) (width*0.45));
        folder.setMinimumWidth((int) (width*0.45));
        
//        songname=(TextView) findViewById(R.id.song_name);
//        singername=(TextView) findViewById(R.id.singer_name);
//        pre_song=(ImageButton) findViewById(R.id.pre_song);
//        play=(ImageButton) findViewById(R.id.play);
//        next_song =(ImageButton) findViewById(R.id.next_song);
        
        
        
        
        read_last_list();
        Read_musicfile_list(MusicplayerData.currentMusiclistFilename);
        System.out.println(MusicplayerData.currentPosition+"  "+MusicplayerData.musicfile.size());
        initMediaPlayer();
        
//        pre_song.setOnClickListener(new Button.OnClickListener(){
//        	public void onClick(View v){
//        		MusicplayerControl.premusic(context, MusicplayerData.currentPosition,play);
//        	}
//        });
//        next_song.setOnClickListener(new Button.OnClickListener(){
//        	public void onClick(View v){
//        		MusicplayerControl.nextmusic(context, MusicplayerData.currentPosition,play);
//        	}
//        });
//       play.setOnClickListener(new Button.OnClickListener(){
//        	public void onClick(View v){
//        		if (MusicplayerData.play_flag){
//        			MusicplayerData.myMediaPlayer.pause();
//        			Resources r = getResources(); 
//        			Drawable img=r.getDrawable(R.drawable.pause);
//        			play.setImageDrawable(img);
//        		}else{
//        			MusicplayerData.myMediaPlayer.start();
//        			Resources r = getResources(); 
//        			Drawable img=r.getDrawable(R.drawable.play);
//        			play.setImageDrawable(img);
//        		}
//        		MusicplayerData.play_flag=!MusicplayerData.play_flag;
//        	}
//        }); 
        
        allMusic.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
                String strTmp = "点击Button01";    
                System.out.println(strTmp);
                Intent it = new Intent(MainActivity.this, Song_list.class);
                startActivity(it);   
            }
        });
    }


    private void read_last_list(){
		SharedPreferences mSharedPreferences = getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
		String filepath="All_musiclist.ini";
		int position=0;
		filepath=mSharedPreferences.getString("listpath", filepath);
		position=mSharedPreferences.getInt("curPosition",position);
		MusicplayerData.currentMusiclistFilename=filepath;
		MusicplayerData.currentPosition=position;
	}
    private void Read_musicfile_list(String filepath) 
	{
		try{
			FileInputStream fin = openFileInput(filepath);  
			DataInputStream dataIO = new DataInputStream(fin);
			String strline;
			while ((strline =  dataIO.readLine()) != null)
			{ 
				MusicplayerData.musicfile.add(strline);
			}
		}catch(Exception e){   
		        e.printStackTrace();   
		}
	}
    
    private void initMediaPlayer(){
    	if (MusicplayerData.playStatus)
    		return ; 
    	String src=MusicplayerData.musicfile.get((MusicplayerData.currentPosition)%MusicplayerData.musicfile.size());
    	Musicinfo musicinfo=Musicinfo.getMetaData(src);
    	playControl.setSingerSongName(musicinfo.title, musicinfo.artist);
    	System.out.println("init MediaPlayer:"+musicinfo.title+musicinfo.artist);
//    	songname.setText(musicinfo.title);
//    	singername.setText(musicinfo.artist);
    	try{
	    	MusicplayerData.myMediaPlayer.reset(); 
			MusicplayerData.myMediaPlayer.setDataSource(src);
			MusicplayerData.myMediaPlayer.prepare();
    	}catch (Exception e) 
    	{ 
    		e.printStackTrace();
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
