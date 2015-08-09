package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListnameList extends Activity {
	private ListView listview;
	private ImageButton more;
	private List<HashMap<String, Object>> data;
	private TextView info;
	private List<String> listitemName;
	private String rowName,rowValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listname_list);
		SysApplication.getInstance().addActivity(this); 
		
		info=(TextView) findViewById(R.id.info);
		listview=(ListView) findViewById(R.id.listView_song_name);
		data=new ArrayList<HashMap<String,Object>>();
		listitemName=new ArrayList<String>();
		ArrayList<String> list=(ArrayList<String>)getIntent().getStringArrayListExtra("musicListtype");
		info.setText(list.get(0));
		
		rowName=list.get(1);
		getNamelist();
		getNamemap();
		
		SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.itemlist,new String[]{"itemname","number","hotnumber"},new int[]{R.id.song_name,R.id.singer_name,R.id.hotnumber});
	    listview.setAdapter(adapter);  
	    listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent it = new Intent(ListnameList.this, SongList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add(MusicplayerData.dbAllMusicTable);
                rowValue=listitemName.get(position);
                list.add(rowValue);
                list.add("singer&album&folder");
                list.add(rowValue);
                list.add(rowName);
                it.putStringArrayListExtra("musicListTableName", list);
                startActivity(it);   
			}
	    });
	}

	private void getNamelist(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select DISTINCT "+rowName+" from "+MusicplayerData.dbAllMusicTable, null);
		if (c.moveToFirst()){
			for(int i=0;i<c.getCount();i++){
				listitemName.add(c.getString(0));
		        c.moveToNext();
		    }
		}
	}
	private void getNamemap(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c;
		int number=0;
		Iterator<String> it=listitemName.iterator();
		while (it.hasNext()){
			rowValue=it.next();
			c=db.rawQuery("select count(*) from "+MusicplayerData.dbAllMusicTable+" where "+rowName+"= '"+rowValue+"'", null);
			if (c.moveToFirst()){
				number=c.getInt(0);
			}
			HashMap<String, Object> nowMap = new HashMap<String, Object>();
			nowMap.put("itemname", rowValue);
			nowMap.put("number",number+"首歌曲");
			nowMap.put("hotnumber","");
			data.add(nowMap);		        
		}
	}
}
