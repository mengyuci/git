package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AddMusicActivity extends Activity {
	private Button commit;
	private ListView listView;
	private List<HashMap<String, Object>> data;
	private List<String> musicList=new ArrayList<String>();
	private List<Boolean> checkList=new ArrayList<Boolean>();
	private String tableName;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addmusiclist);
		SysApplication.getInstance().addActivity(this); 
		
		context=this;
		commit=(Button) findViewById(R.id.completeaddmusic);
		listView=(ListView) findViewById(R.id.listviewaddmusic);
		data=new ArrayList<HashMap<String,Object>>();
		
		ArrayList<String> list=(ArrayList<String>)getIntent().getStringArrayListExtra("musicListTableName");
		tableName=list.get(0);
		
		getMusicListHotRank();
		
		SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.addmusicitemlist,new String[]{"song_name","singer_name"},new int[]{R.id.song_name,R.id.singer_name});
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				CheckBox checkBox=(CheckBox) view.findViewById(R.id.select);
				checkBox.setChecked(!checkBox.isChecked());
				checkList.set(position, !checkList.get(position));
			}
		});
	    commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Iterator<Boolean> it1=checkList.iterator();
				Iterator<String> it2=musicList.iterator();
				MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
				SQLiteDatabase db=dbHelp.getWritableDatabase();
				boolean temp;
				String filepath;
				int hotnum=0;
				while (it1.hasNext()&&it2.hasNext()){
					temp=it1.next();
					filepath=it2.next();
					if (temp){
						
						Cursor cu=db.rawQuery("select count(*) from "+tableName+" where filepath='"+filepath+"'", null);
						if (cu.moveToFirst()){
							if (cu.getInt(0)!=0){
								continue;
							}
						}
						
						cu=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+filepath+"'", null);
						hotnum=0;
						if (cu.moveToFirst()){
							hotnum=cu.getInt(0);
						}
						db.execSQL("insert into "+tableName+"(filepath) values('"+filepath+"'"+")");
					}
				}
				Intent it = new Intent(AddMusicActivity.this, MyListActivity.class);
	            ArrayList<String> list=new ArrayList<String>();
                list.add("我的列表");
                list.add("mylist");
                it.putStringArrayListExtra("musicListtype", list);
	            startActivity(it);
	            ((Activity) context).finish();
			}
		});
		
	}
	private void getMusicListHotRank(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select sum(hotnum) from "+MusicplayerData.dbAllMusicTable, null);
		int totHotNumber=0;
		if (c.moveToFirst()){
			totHotNumber=c.getInt(0);
		}
		c = db.rawQuery("select filepath from "+MusicplayerData.dbAllMusicTable+" order by hotnum desc",null);
		makeData(totHotNumber,c,db);
	}
	private void makeData(int totHotNumber,Cursor c,SQLiteDatabase db){
		String filepath;
		if(c.moveToFirst()){
		    for(int i=0;i<c.getCount();i++){
		        filepath = c.getString(c.getColumnIndex("filepath"));
		        MusicInfo musicinfo=MusicInfo.getMetaData(filepath);
		        if (musicinfo==null){
		        	c.moveToNext();
		        	continue;
		        }
		        HashMap<String, Object> nowMap = new HashMap<String, Object>();
				nowMap.put("song_name", musicinfo.title);
				nowMap.put("singer_name",musicinfo.artist);
				data.add(nowMap);		        
				musicList.add(filepath);
				checkList.add(false);
		        c.moveToNext();
		    }
		}
	}
}
