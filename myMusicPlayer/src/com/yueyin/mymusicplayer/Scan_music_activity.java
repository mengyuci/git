package com.yueyin.mymusicplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.yueyin.mymusicplayer.MusicplayerData;


public class Scan_music_activity extends Activity {
	private TextView find_music_number,find_music_filepath_show;
	private Button find_music_complete;
	private File scan_file;
	private String file;
	private int find_music_complete_flag;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_music_xml);
		find_music_number=(TextView) findViewById(R.id.find_music_number);
		find_music_filepath_show=(TextView) findViewById(R.id.find_music_filepath_show);
		find_music_complete=(Button) findViewById(R.id.find_music_complete);
		MusicplayerData.All_musicfile.clear();
		find_music_complete_flag=0;
		file="storage";
		scan_file=new File(file);
		find_music_complete.setText("开始扫描");
		find_music_filepath_show.setText(file);
		find_music_complete.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {    
				if (find_music_complete_flag==0){
					scan_music_fuction(scan_file);
					save_music_list();
					find_music_complete_flag=1;
					find_music_complete.setText("返回");
					find_music_filepath_show.setText("扫描完成");
				}
				else if (find_music_complete_flag==1)
				{
					Intent it = new Intent(Scan_music_activity.this, MainActivity.class);
			        startActivity(it);   
				}
            } 
		});
	}
	
	private boolean isaccord(String str)
	{
		if (str.equals(".mp3"))
			return true;
		return false;
	}
	private void scan_music_fuction(File scan_file)
	{
		String text="正在扫描：";
		text=text+scan_file.getAbsolutePath();
		find_music_filepath_show.setText(text);
		if (scan_file.isDirectory())
		{
			File[] files = scan_file.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					scan_music_fuction(f);
				}
			}
		}
		else if (scan_file.isFile())
		{
			int dot = scan_file.getName().lastIndexOf(".");
			if (dot > -1 && dot < scan_file.getName().length()) {
				String extriName = scan_file.getName().substring(dot,scan_file.getName().length());
				if (isaccord(extriName))
				{
					MusicplayerData.All_musicfile.add(scan_file.getAbsoluteFile().getPath());
					String number="已扫描到";
					number=number+MusicplayerData.All_musicfile.size()+"首歌曲";
					find_music_number.setText(number);
				}
			}
		}
	}
	
	private void save_music_list()
	{
		try{
		       FileOutputStream fout = openFileOutput(MusicplayerData.allMusiclistFilepath,Activity.MODE_PRIVATE);   
		       for (Iterator<String> it=MusicplayerData.All_musicfile.iterator();it.hasNext();)
		       {
		    	   String src=it.next();
		    	   byte [] bytes = src.getBytes();   
		    	   fout.write(bytes);
		    	   String newLine = System.getProperty("line.separator");
		    	   fout.write(newLine.getBytes());
		       }
		       fout.close();   
		     }catch(Exception e){   
		        e.printStackTrace();   
		     }   
	}
}
