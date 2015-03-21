package com.yueyin.mymusicplayer;

import com.yueyin.mymusicplayer.MusicplayerData;
import com.yueyin.mymusicplayer.MusicplayerControl;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.yueyin.mymusicplayer.Musicinfo;

public class Song_list extends Activity {
	private ListView listview;
	private ImageButton more;
	private PopupWindow popupWindow;
	private List<HashMap<String, Object>> data;
	private Context context;
	private String musiclist_path;
	private TextView songname,singername;
	private ImageButton pre_song,play,next_song;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_list);
		
		songname=(TextView) findViewById(R.id.song_name_list);
        singername=(TextView) findViewById(R.id.singer_name_list);
        pre_song=(ImageButton) findViewById(R.id.pre_song_list);
        play=(ImageButton) findViewById(R.id.play_list);
        next_song =(ImageButton) findViewById(R.id.next_song_list);
		
		musiclist_path=MusicplayerData.All_music_info_filepath;
		context=this.getApplicationContext();
		more=(ImageButton) findViewById(R.id.more);
		listview=(ListView) findViewById(R.id.listView_song_name);
		data=new ArrayList<HashMap<String,Object>>();
		
		read_last_list();
		getmusiclist();
		if (data.size()==0){
			System.out.println("data.size==0");
			Toast.makeText(this, "musicall", 2000).show();
			musiclist_all();
		}
		
		SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.itemlist,new String[]{"song_name","singer_name"},new int[]{R.id.song_name,R.id.singer_name});
	    listview.setAdapter(adapter);  
	    listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (!MusicplayerData.currentmusicfile.equals(MusicplayerData.musicfile)){
					MusicplayerData.musicfile=MusicplayerData.currentmusicfile;
				}
				MusicplayerData.currentMusiclist_filename=musiclist_path;
				MusicplayerControl.playmusic(context,position,play);
			}
	    });
	    more.setOnClickListener(new ImageButton.OnClickListener(){   
            public void onClick(View v) {    
            	getPopupWindow();  
                popupWindow.setFocusable(true);
                ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
                popupWindow.setBackgroundDrawable(dw);
                popupWindow.showAsDropDown(v);  
            }
        });
	}
	private void getmusiclist(){
		try{
			FileInputStream fin = openFileInput(MusicplayerData.All_music_info_filepath);  
			DataInputStream dataIO = new DataInputStream(fin);
			String strline;
			while ((strline =  dataIO.readLine()) != null)
			{
				MusicplayerData.currentmusicfile.add(strline);
				Musicinfo musicinfo=Musicinfo.getMetaData(strline);
				HashMap<String, Object> nowMap = new HashMap<String, Object>();
				nowMap.put("song_name", musicinfo.title);
				nowMap.put("singer_name",musicinfo.artist);
				data.add(nowMap);
			}
		}catch(Exception e){   
		        e.printStackTrace();   
		}   
	}
	
	
	private void musiclist_all()
	{
		Cursor mAudioCursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null,// 字段　没有字段　就是查询所有信息　相当于SQL语句中的　“ * ”
				null, // 查询条件
				null, // 条件的对应?的参数
				MediaStore.Audio.AudioColumns.TITLE);// 排序方式
		// 循环输出歌曲的信息
		for (int i = 0; i < mAudioCursor.getCount(); i++) {
			mAudioCursor.moveToNext();
			// 找到歌曲标题和总时间对应的列索引
			int indexDuration = mAudioCursor
					.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
			int indexTitle = mAudioCursor
					.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
			int indexARTIST = mAudioCursor
					.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
			int indexSRC = mAudioCursor
					.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
			
			String strTitle = mAudioCursor.getString(indexTitle);
			String strARTIST = mAudioCursor.getString(indexARTIST);
			String strSRC=mAudioCursor.getString(indexSRC);
			int intDuration=mAudioCursor.getInt(indexDuration);
			MusicplayerData.musicfile.add(strSRC);
			
			if (intDuration<40000){
				continue;
			}
			HashMap<String, Object> nowMap = new HashMap<String, Object>();
			nowMap.put("song_name", strTitle );
			nowMap.put("singer_name",strARTIST);
			data.add(nowMap);
		}
	}
	
	private void read_last_list(){
		try{ 
			File file=new File(MusicplayerData.Play_song_and_list_info_filepath);
			if (!file.exists()){
				MusicplayerControl.save_music_list(context,MusicplayerData.All_music_info_filepath, 0);
			}
			
	         FileInputStream fin = openFileInput(MusicplayerData.Play_song_and_list_info_filepath);
	         DataInputStream dataIO = new DataInputStream(fin);
			 String filepath =  dataIO.readLine();
			 int position = Integer.parseInt(dataIO.readLine());
			 MusicplayerData.currentMusiclist_filename=filepath;
			 MusicplayerData.currentPosition=position;
			 Read_musicfile_list(filepath);
			 System.out.println(filepath+"   "+position);
	         fin.close();     
	        } 
	        catch(Exception e){ 
	         e.printStackTrace(); 
	        } 
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
	private void scan_music_fuction()
	{
		Intent it = new Intent(Song_list.this, Scan_music_activity.class);
        startActivity(it);   
	}
	private void activity_exit()
	{
		;
	}
	private void initPopuptWindow(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.title_popupwindow, null,false);
		popupWindow = new PopupWindow(popupWindow_view, (int)(MusicplayerData.screen_width*0.25), 80, true);
		
		Button scan_music=(Button)popupWindow_view.findViewById(R.id.scan_music);
		Button exit=(Button) popupWindow_view.findViewById(R.id.title_exit);
		scan_music.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {    
                scan_music_fuction();
            } 
         });
		exit.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {    
                activity_exit();
            } 
         });
		
	}
	private void getPopupWindow() {  
        
        if(null != popupWindow) {  
            popupWindow.dismiss();  
            return;  
        }else {  
            initPopuptWindow();  
        }  
    }  
	
	
}

