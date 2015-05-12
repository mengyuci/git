package com.yueyin.mymusicplayer;

import com.yueyin.mymusicplayer.MusicplayerData;
import com.yueyin.mymusicplayer.MusicplayerControl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yueyin.mymusicplayer.MusicInfo;

public class SongList extends Activity {
	private ListView listview;
	private ImageButton more,pre;
	private PopupWindow popupWindow,popupWindowLong,popupWindowAdd,popupWindowMenu;
	private List<HashMap<String, Object>> data;
	private ArrayList<String> listname=new ArrayList<String>();//用于添加到 列表功能
	private Context context;
	private String tableName;
	private PlayControl playControl;
	private TextView info;
	private String currentFilepath;//表示 选中的音乐的全路径
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Handler handler=new Handler();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.song_list);
		SysApplication.getInstance().addActivity(this); 
		
		info=(TextView) findViewById(R.id.info);
		MusicplayerData.playControlSongList=(PlayControl) findViewById(R.id.playcontrol_songlist);
		context=this.getApplicationContext();
		pre=(ImageButton) findViewById(R.id.fanhui);
		more=(ImageButton) findViewById(R.id.more);
		listview=(ListView) findViewById(R.id.listView_song_name);
		data=new ArrayList<HashMap<String,Object>>();
		context=this;
		dealInput();
		
		handler.post(new Runnable() {  
            public void run() {  
            	handler.postDelayed(this, 100);  
                if (!MusicplayerData.playControlSongList.isStartTrackingTouch)  
                {
                	if (!MusicplayerData.myMediaPlayer.isPlaying()){
            			Resources r = MusicplayerData.context.getResources(); 
            			Drawable img=r.getDrawable(R.drawable.play);
            			MusicplayerData.playControlSongList.play.setImageDrawable(img);
            		}else{
            			Resources r = MusicplayerData.context.getResources(); 
            			Drawable img=r.getDrawable(R.drawable.pause);
            			MusicplayerData.playControlSongList.play.setImageDrawable(img);
            		}
        	    	MusicplayerData.playControlSongList.setSingerSongName(
        	    			MusicplayerData.musicInfoAll
        	    			.title, 
        	    			MusicplayerData.musicInfoAll.artist,"hot:"+MusicplayerData.musicInfoAll.hotnum);
                    MusicplayerData.playControlSongList.seekbar.setMax(MusicplayerData.myMediaPlayer.getDuration()); 
                	MusicplayerData.playControlSongList.seekbar.setProgress(MusicplayerData.myMediaPlayer.getCurrentPosition());  
                }
            }  
        });  
		
		SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.itemlist,new String[]{"song_name","singer_name","hotnumber"},new int[]{R.id.song_name,R.id.singer_name,R.id.hotnumber});
	    listview.setAdapter(adapter);  
	    listview.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				MusicplayerData.currentMusiclistFilename=tableName;
				if (!MusicplayerData.currentmusicfile.equals(MusicplayerData.musicfile)){
					MusicplayerData.musicfile=MusicplayerData.currentmusicfile;
					MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
					SQLiteDatabase db=dbHelp.getWritableDatabase();
					MusicplayerDBOperate.insertLastMusicList(db);
					MusicplayerData.totHotnum=MusicplayerDBOperate.getTotHotnum();
				}
				MusicplayerControl.playmusic(context,position,playControl);
			}
	    });
	    listview.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {

						currentFilepath=MusicplayerData.currentmusicfile.get(position);
						getPopupWindowLong();
						popupWindowLong.setFocusable(true);
		                ColorDrawable dw = new ColorDrawable(0x00FF00FF);
		                popupWindowLong.setBackgroundDrawable(dw);
		                popupWindowLong.showAtLocation(view, Gravity.CENTER, 0, 0);
						
						return true;
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
	    pre.setOnClickListener(new ImageButton.OnClickListener(){   
            public void onClick(View v) {    
            	((Activity) context).finish();
            }
        });
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode==KeyEvent.KEYCODE_MENU){
			getPopupWindowMenu();
			popupWindowMenu.setFocusable(true);
			View view=getLayoutInflater().inflate(R.layout.menu, null); 	
            ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
            popupWindowMenu.setBackgroundDrawable(dw);
            popupWindowMenu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			System.out.println("Menu");
		}
		
		
		return super.onKeyDown(keyCode, event);
	}

	
	
	private void dealInput(){
		ArrayList<String> list=(ArrayList<String>)getIntent().getStringArrayListExtra("musicListTableName");
		tableName=list.get(0);
		info.setText(list.get(1));
		if (list.size()>=3){
			String type=list.get(2);
			if (type.equals("hotRank")){
				getMusicListHotRank();
			}else if (type.equals("singer&album&folder")){
				String rowValue=list.get(3);
				String rowName=list.get(4);
				getMusicListSingerAlbumFolder(rowValue,rowName);
			}
		}else{
			getMusicList();
		}
	}
	
	private void getMusicListSingerAlbumFolder(String rowValue,String rowName){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select sum(hotnum) from "+MusicplayerData.dbAllMusicTable+" where "+rowName+"='"+rowValue+"'", null);
		int totHotNumber=0;
		if (c.moveToFirst()){
			totHotNumber=c.getInt(0);
		}
		c = db.rawQuery("select filepath from "+tableName+" where "+rowName+"='"+rowValue+"'",null);
		makeData(totHotNumber,c,db);
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
	
	private void getMusicList(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select sum(hotnum) from "+MusicplayerData.dbAllMusicTable, null);
		int totHotNumber=0;
		if (c.moveToFirst()){
			totHotNumber=c.getInt(0);
		}
		c = db.rawQuery("select filepath from "+tableName,null);
		tableName=MusicplayerData.dbAllMusicTable;
		makeData(totHotNumber,c,db);
	}
	
	private void makeData(int totHotNumber,Cursor c,SQLiteDatabase db){
		String filepath;
		if(c.moveToFirst()){
			MusicplayerData.currentmusicfile.clear();
		    for(int i=0;i<c.getCount();i++){
		        filepath = c.getString(c.getColumnIndex("filepath"));
		        MusicInfo musicinfo=MusicInfo.getMetaData(filepath);
		        if (musicinfo==null){
		        	c.moveToNext();
		        	continue;
		        }
		        Cursor cu=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+filepath+"'", null);
				int hotnum=0;
				if (cu.moveToFirst()){
					hotnum=cu.getInt(0);
				}
		        HashMap<String, Object> nowMap = new HashMap<String, Object>();
				nowMap.put("song_name", musicinfo.title);
				nowMap.put("singer_name",musicinfo.artist);
				nowMap.put("hotnumber","hot："+hotnum+"/"+totHotNumber);
				data.add(nowMap);		        
		        MusicplayerData.currentmusicfile.add(filepath);
		        c.moveToNext();
		    }
		}
	}
	
	private void scan_music_fuction()
	{
		Intent it = new Intent(SongList.this, Scan_music_activity.class);
        startActivity(it);   
        System.exit(0);
	}
	private void activity_exit()
	{
		SysApplication.getInstance().exit();
	}
	private void initPopuptWindow(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.title_popupwindow, null,false);
		popupWindow = new PopupWindow(popupWindow_view, (int)(MusicplayerData.screenWidth*0.25), 80, true);
		
		Button scan_music=(Button)popupWindow_view.findViewById(R.id.scan_music);
		Button exit=(Button) popupWindow_view.findViewById(R.id.title_exit);
		scan_music.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {    
                scan_music_fuction();
                popupWindow.dismiss();
            } 
         });
		exit.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {    
                activity_exit();
                popupWindow.dismiss();
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
	
	private void initPopuptWindowLong(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.mylist_popupwindow, null,false);
		popupWindowLong = new PopupWindow(popupWindow_view);
		popupWindowLong.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,  
	            ViewGroup.LayoutParams.WRAP_CONTENT);  
		ListView listView=(ListView)popupWindow_view.findViewById(R.id.listView_mylist_popupwindow);
		ArrayList<String> name=new ArrayList<String>();
		name.add("添加到");
		name.add("删除音乐");
		
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, name));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (position==0){
					popupWindowLong.dismiss();
					getPopupWindowAdd();
					popupWindowAdd.setFocusable(true);
	                ColorDrawable dw = new ColorDrawable(0x00FF00FF);
	                popupWindowAdd.setBackgroundDrawable(dw);
	                popupWindowAdd.showAtLocation(view, Gravity.CENTER, 0, 0);
	                
					
					onCreate(null);
				}else if (position==1){
					String filepath=MusicplayerData.currentmusicfile.get(position);
					MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
					SQLiteDatabase db=dbHelp.getWritableDatabase();
					db.execSQL("delete from "+tableName+" where filepath='"+filepath+"'");
					popupWindowLong.dismiss();
			        onCreate(null);
				}	
			}
		});
	}
	private void getPopupWindowLong() {  
        
        if(null != popupWindowLong) {  
        	popupWindowLong.dismiss();  
            return;  
        }else {  
            initPopuptWindowLong();  
        }  
    }  
	
	private void initPopuptWindowAdd(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.mylist_popupwindow, null,false);
		popupWindowAdd = new PopupWindow(popupWindow_view);
		popupWindowAdd.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,  
	            ViewGroup.LayoutParams.WRAP_CONTENT);  
		ListView listView=(ListView)popupWindow_view.findViewById(R.id.listView_mylist_popupwindow);
		
		listname.add("待播列表");
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c=db.rawQuery("select listname from "+MusicplayerData.dbCustomMusicListNameTable, null);
		if (c.moveToFirst()){
			for(int i=0;i<c.getCount();i++){
				listname.add(c.getString(0));
		        c.moveToNext();
		    }
		}
		
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listname));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String tableName=listname.get(position);
				
				if (position==0){
					tableName=MusicplayerData.dbNextMusicListTable;
				}
				MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
				SQLiteDatabase db=dbHelp.getWritableDatabase();
				Cursor cu=db.rawQuery("select count(*) from "+tableName+" where filepath='"+currentFilepath+"'", null);
				if (cu.moveToFirst()){
					if (cu.getInt(0)!=0){
						popupWindowAdd.dismiss();
						System.out.println("return"+currentFilepath);
						return;
					}
				}
				cu=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+currentFilepath+"'", null);
				int hotnum=0;
				if (cu.moveToFirst()){
					hotnum=cu.getInt(0);
				}
				if (position!=0)
					db.execSQL("insert into "+tableName+"(filepath) values('"+currentFilepath+"'"+")");
				db.execSQL("updata into "+MusicplayerData.dbAllMusicTable+" set hotnum="+hotnum+" where filepath='"+currentFilepath+"'");
				
				popupWindowAdd.dismiss();
			}
		});
	}
	private void getPopupWindowAdd() {  
        
        if(null != popupWindowAdd) {  
        	popupWindowAdd.dismiss();  
            return;  
        }else {  
            initPopuptWindowAdd();  
        }  
    }  
	

	private void initPopuptWindowMenu(){
		View popupWindow_view = getLayoutInflater().inflate(R.layout.menu, null,false);
		popupWindowMenu = new PopupWindow(popupWindow_view);
		popupWindowMenu.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT,  
	            ViewGroup.LayoutParams.WRAP_CONTENT);  
		
		Button playmode=(Button)popupWindow_view.findViewById(R.id.playmode);
		Button timingexit=(Button) popupWindow_view.findViewById(R.id.timingexit);
		Button exit=(Button) popupWindow_view.findViewById(R.id.exit);
		playmode.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {  
            	
            	playModeDialog();
            	
            	popupWindowMenu.dismiss();
            } 
         });
		timingexit.setOnClickListener(new Button.OnClickListener(){    
            public void onClick(View v) {   
            	
            	inputTitleDialog();
            	popupWindowMenu.dismiss();
            } 
         });
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SysApplication.getInstance().exit();
			}
		});
		
	}
	private void getPopupWindowMenu() {  
        
        if(null != popupWindowMenu) {  
        	popupWindowMenu.dismiss();  
            return;  
        }else {  
            initPopuptWindowMenu();  
        }  
    } 
	
	private void playModeDialog(){
		AlertDialog builder=new AlertDialog.Builder(context)  
		.setTitle("播放模式")   
	    .setIcon(android.R.drawable.ic_dialog_info)                  
	    .setSingleChoiceItems(new String[] {"顺序播放","随机播放","单曲循环"}, MusicplayerData.playMode,   
	      new DialogInterface.OnClickListener() {  
	                                  
	         public void onClick(DialogInterface dialog, int which) {  
	        	if (which==0){
	        		MusicplayerData.playMode=0;
	        		Toast toast = Toast.makeText(SongList.this,"顺序播放", Toast.LENGTH_SHORT);                    
    			    toast.show();
	        	}else if (which==1){
	        		MusicplayerData.playMode=1;
	        		Toast toast = Toast.makeText(SongList.this, "随机播放", Toast.LENGTH_SHORT);                    
    			    toast.show();
	        	}else if (which==2){
	        		MusicplayerData.playMode=2;
	        		Toast toast = Toast.makeText(SongList.this, "单曲循环", Toast.LENGTH_SHORT);                    
    			    toast.show();
	        	}
	            dialog.dismiss();  
	         }  
	      }  
	    )  
	    .setNegativeButton("取消", null)  
	    .show();  
	}
	
	private void inputTitleDialog() {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
        //inputServer.setText(MusicplayerData.exitSongNumber);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("歌曲数目").setView(inputServer).setNegativeButton(
                "取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MusicplayerData.exitFlag=false;
						Toast toast = Toast.makeText(SongList.this, "定时退出已关闭", Toast.LENGTH_SHORT);                    
        			    toast.show();
					}
				});
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
        				String str=inputServer.getText().toString();
        				int num=0;
        				try{
        					num=Integer.parseInt(str);
        				}catch(Exception e){
        					Toast toast = Toast.makeText(SongList.this, "请输入数字", Toast.LENGTH_SHORT);                    
            			    toast.show();
        				}
        				MusicplayerData.exitFlag=true;
        				MusicplayerData.exitSongNumber=num;
                    	Toast toast = Toast.makeText(SongList.this, num+"首歌曲后退出", Toast.LENGTH_SHORT);                    
        			    toast.show();
                    }
                });
        builder.show();
	 }
	
}

