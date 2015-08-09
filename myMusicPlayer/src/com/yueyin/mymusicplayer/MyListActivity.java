package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyListActivity extends Activity {
	private ListView listview;
	private List<HashMap<String, Object>> data;
	private TextView info;
	private Button addList;
	private PopupWindow popupWindow;
	private List<String> listitemName;
	private String rowValue;
	private String newListName;
	private int pos;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylist);
		SysApplication.getInstance().addActivity(this); 
		
		context=this;
		info=(TextView) findViewById(R.id.info);
		listview=(ListView) findViewById(R.id.listView_song_name);
		addList=(Button) findViewById(R.id.addlist);
		ArrayList<String> list=(ArrayList<String>)getIntent().getStringArrayListExtra("musicListtype");
		data=new ArrayList<HashMap<String,Object>>();
		listitemName=new ArrayList<String>();
		info.setText(list.get(0));
		
		getNamelist();
		if (listitemName.size()!=0){
			getNamemap();
			SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.itemlist,new String[]{"itemname","number","hotnumber"},new int[]{R.id.song_name,R.id.singer_name,R.id.hotnumber});
		    listview.setAdapter(adapter);  
		    listview.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					Intent it = new Intent(MyListActivity.this, SongList.class);
	                ArrayList<String> list=new ArrayList<String>();
	                rowValue=listitemName.get(position);
	                list.add(rowValue);
	                list.add(info.getText().toString());
	                it.putStringArrayListExtra("musicListTableName", list);
	                startActivity(it);   
				}
		    });
		    listview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					pos=position;
					getPopupWindow();
					popupWindow.setFocusable(true);
	                ColorDrawable dw = new ColorDrawable(0x00FF00FF);
	                popupWindow.setBackgroundDrawable(dw);
	                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
					return true;
				}
			});
		}
		addList.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputTitleDialog();
			}
		});
		
	}
	
	 private void inputTitleDialog() {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加列表").setView(inputServer).setNegativeButton(
                "取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    	newListName = inputServer.getText().toString();
                    	if (newListName.charAt(0)>='0'&&newListName.charAt(0)<='9'){
                    		Toast toast = Toast.makeText(MyListActivity.this, "列表名格式错误！", Toast.LENGTH_SHORT);                    
        			        toast.show();
        			        return ;
                    	}
                    	if (checkNewListName()){
        					MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
        					SQLiteDatabase db=dbHelp.getWritableDatabase();
        					db.execSQL("insert into "+MusicplayerData.dbCustomMusicListNameTable+" (listname) values('"+newListName+"')"); 
        					db.execSQL("create table "+newListName+" (filepath text not null, primary key(filepath),foreign key(filepath) " +
        	        		"references "+MusicplayerData.dbAllMusicTable+"(filepath) on delete cascade on update cascade);");
        					Toast toast = Toast.makeText(MyListActivity.this, "添加成功", Toast.LENGTH_SHORT);                    
        			        toast.show();
        			        onCreate(null);
        				}else{
        					Toast toast = Toast.makeText(MyListActivity.this, "列表已存在", Toast.LENGTH_SHORT);                    
        			        toast.show();
        				}
                    }
                });
        builder.show();
	 }
	 
	 private boolean checkNewListName(){
		 if (newListName.equals(MusicplayerData.dbAllMusicTable)||newListName.equals(MusicplayerData.dbCustomMusicListNameTable)
				 ||newListName.equals(MusicplayerData.dbLastMusicListTable)||newListName.equals(MusicplayerData.dbNextMusicListTable)
				 ||newListName.equals(MusicplayerData.dbPreMusicListTable))
			 return false;
		 MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		 SQLiteDatabase db=dbHelp.getWritableDatabase();
		 Cursor c=db.rawQuery("select listname from "+MusicplayerData.dbCustomMusicListNameTable, null);
		 if (c.moveToFirst()){
			 for(int i=0;i<c.getCount();i++){
				 if (newListName.equals(c.getString(0)))
					 return false;
				 c.moveToNext();
			 }
		 }
		 return true;
	 }
	
	private void getNamelist(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select listname from "+MusicplayerData.dbCustomMusicListNameTable, null);
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
			c=db.rawQuery("select count(*) from "+rowValue, null);
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
	
	private void initPopuptWindow(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.mylist_popupwindow, null,false);
		popupWindow = new PopupWindow(popupWindow_view);
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,  
	            ViewGroup.LayoutParams.WRAP_CONTENT);  
		ListView listView=(ListView)popupWindow_view.findViewById(R.id.listView_mylist_popupwindow);
		ArrayList<String> name=new ArrayList<String>();
		name.add("添加音乐");
		name.add("删除列表");
		
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, name));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (position==0){
					
					Intent it = new Intent(MyListActivity.this, AddMusicActivity.class);
	                ArrayList<String> list=new ArrayList<String>();
	                list.add(listitemName.get(pos));
	                it.putStringArrayListExtra("musicListTableName", list);
	                startActivity(it);   
	                ((Activity) context).finish();
	            	popupWindow.dismiss();
					onCreate(null);
				}else if (position==1){
	            	String listName=listitemName.get(pos);
	            	MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
	            	SQLiteDatabase db=dbHelp.getWritableDatabase();
	            	db.execSQL("delete from "+MusicplayerData.dbCustomMusicListNameTable+" where listname='"+listName+"'");
	            	db.execSQL("drop table "+listName);
	            	popupWindow.dismiss();
			        onCreate(null);
				}
				
			}
		});
		
//		Button addMusic=(Button)popupWindow_view.findViewById(R.id.addmusic);
//		Button deleteList=(Button) popupWindow_view.findViewById(R.id.deletemylist);
//		addMusic.setOnClickListener(new Button.OnClickListener(){    
//            public void onClick(View v) {  
//
//		        onCreate(null);
//            } 
//         });
//		deleteList.setOnClickListener(new Button.OnClickListener(){    
//            public void onClick(View v) {   
//            	String listName=listitemName.get(pos);
//            	MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
//            	SQLiteDatabase db=dbHelp.getWritableDatabase();
//            	db.execSQL("delete from "+MusicplayerData.dbCustomMusicListNameTable+" where listname='"+listName+"'");
//            	db.execSQL("drop table "+listName);
//            	popupWindow.dismiss();
//		        onCreate(null);
//            } 
//         });
		
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
