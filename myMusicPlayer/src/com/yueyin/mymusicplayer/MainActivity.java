package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yueyin.mymusicplayer.MusicplayerData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity {
	

	private static Handler handler=new Handler();
	private ImageButton allMusic,hotRank,singer,album,playlist,waitplaylist,mylist,folder;
	private PopupWindow popupWindowMenu;
	private Context context;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this); 
        
        initWidget();
        
        initDB();
        readLastList();
        readMusicFileList();
        initMediaPlayer();
        handler.post(new Runnable() {  
            public void run() {  
                // 更新进度条状态   
                handler.postDelayed(this, 100);  
                if (!MusicplayerData.playControlMain.isStartTrackingTouch)  
                {
                	if (!MusicplayerData.myMediaPlayer.isPlaying()){
            			Resources r = MusicplayerData.context.getResources(); 
            			Drawable img=r.getDrawable(R.drawable.play);
            			MusicplayerData.playControlMain.play.setImageDrawable(img);
            		}else{
            			Resources r = MusicplayerData.context.getResources(); 
            			Drawable img=r.getDrawable(R.drawable.pause);
            			MusicplayerData.playControlMain.play.setImageDrawable(img);
            		}
        	    	MusicplayerData.playControlMain.setSingerSongName(
        	    			MusicplayerData.musicInfoAll
        	    			.title, 
        	    			MusicplayerData.musicInfoAll.artist,"hot:"+MusicplayerData.musicInfoAll.hotnum);
                    MusicplayerData.playControlMain.seekbar.setMax(MusicplayerData.myMediaPlayer.getDuration()); 
                	MusicplayerData.playControlMain.seekbar.setProgress(MusicplayerData.myMediaPlayer.getCurrentPosition());  
                }
                // 1秒之后再次发送   
            }  
        });  
        allMusic.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, SongList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add(MusicplayerData.dbAllMusicTable);
                list.add("全部音乐");
                it.putStringArrayListExtra("musicListTableName", list);
                startActivity(it);   
            }
        });
        hotRank.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, SongList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add(MusicplayerData.dbAllMusicTable);
                list.add("热度榜");
                list.add("hotRank");
                it.putStringArrayListExtra("musicListTableName", list);
                startActivity(it);   
            }
        });
        playlist.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, SongList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add(MusicplayerData.dbPreMusicListTable);
                list.add("播放列表");
                it.putStringArrayListExtra("musicListTableName", list);
                startActivity(it);   
            }
        });
        singer.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, ListnameList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add("歌手列表");
                list.add("singer");
                it.putStringArrayListExtra("musicListtype", list);
                startActivity(it);   
            }
        });
        album.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, ListnameList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add("专辑列表");
                list.add("album");
                it.putStringArrayListExtra("musicListtype", list);
                startActivity(it);   
            }
        });
        mylist.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, MyListActivity.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add("我的列表");
                list.add("mylist");
                it.putStringArrayListExtra("musicListtype", list);
                startActivity(it);   
            }
        });
        folder.setOnClickListener(new ImageButton.OnClickListener(){//创建监听    
            public void onClick(View v) {    
            	
                Intent it = new Intent(MainActivity.this, ListnameList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add("文件夹");
                list.add("folder");
                it.putStringArrayListExtra("musicListtype", list);
                startActivity(it);   
            }
        });
        waitplaylist.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, SongList.class);
                ArrayList<String> list=new ArrayList<String>();
                list.add(MusicplayerData.dbNextMusicListTable);
                list.add("待播列表");
                it.putStringArrayListExtra("musicListTableName", list);
                startActivity(it);   
			}
        	
        });
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode==KeyEvent.KEYCODE_MENU){
			getPopupWindow();
			popupWindowMenu.setFocusable(true);
			View view=getLayoutInflater().inflate(R.layout.menu, null); 	
            ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
            popupWindowMenu.setBackgroundDrawable(dw);
            popupWindowMenu.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			System.out.println("Menu");
		}
		
		
		return super.onKeyDown(keyCode, event);
	}

	
	private void initDB(){
		
		SharedPreferences mSharedPreferences = getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
		if (mSharedPreferences.getInt("firstFlag", 1)==1){
			SharedPreferences.Editor edit=mSharedPreferences.edit();
			edit.putInt("firstFlag",2);
			edit.commit();
		}else{
			return;
		} 
		
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		List<String> list=musiclist_all();
		Iterator<String> it=list.iterator();
		String src;
		while (it.hasNext()){
			src=it.next();
			MusicplayerDBOperate.insert(src, db);
		}
	}

    private void readLastList(){
		SharedPreferences mSharedPreferences = getSharedPreferences("MainActivity", Activity.MODE_PRIVATE);
		String filepath=MusicplayerData.dbAllMusicTable;
		int position=0;
		filepath=mSharedPreferences.getString("listpath", filepath);
		position=mSharedPreferences.getInt("curPosition",position);
		MusicplayerData.currentMusiclistFilename=filepath;
		MusicplayerData.currentPosition=position;
		System.out.println(filepath+" "+position);
	}
    private void readMusicFileList(){
    	MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Cursor c = db.rawQuery("select filepath from "+MusicplayerData.currentMusiclistFilename,null);
		String filepath;
		if(c.moveToFirst()){
		    for(int i=0;i<c.getCount();i++){
		        filepath = c.getString(c.getColumnIndex("filepath"));
		        MusicplayerData.musicfile.add(filepath);
		        c.moveToNext();
		        System.out.println("A "+filepath);
		    }
		}
		MusicplayerData.totHotnum=MusicplayerDBOperate.getTotHotnum();
    }

    
    private List<String> musiclist_all()
	{
    	List<String>list=new ArrayList<String>();
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
			int indexSRC = mAudioCursor
					.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
			
			String strSRC=mAudioCursor.getString(indexSRC);
			int intDuration=mAudioCursor.getInt(indexDuration);
			
			if (intDuration<40000){
				continue;
			}
			MusicplayerData.musicfile.add(strSRC);
			list.add(strSRC);
		}
		return list;
	}
    
    private void initMediaPlayer(){
    	if (MusicplayerData.playStatus)
    		return ; 
    	if (MusicplayerData.musicfile.size()==0)
    		return;
    	String src=MusicplayerData.musicfile.get((MusicplayerData.currentPosition)%MusicplayerData.musicfile.size());
    	System.out.println("AA"+src);
    	MusicInfo musicinfo=MusicInfo.getMetaData(src);
    	if (musicinfo==null){
    		return ;
    	}
    	MusicplayDBHelper dbHelp=new MusicplayDBHelper(this);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		int hotnum=MusicplayerDBOperate.getHotnum(MusicplayerData.currentMusiclistFilename, src, db);
		musicinfo.setHotnum(hotnum);
		System.out.println(musicinfo);
    	MusicplayerData.musicInfoAll=musicinfo;
    	System.out.println(MusicplayerData.musicInfoAll);
		MusicplayerData.playControlMain.setSingerSongName(musicinfo.title, musicinfo.artist,"hot:"+hotnum);
		MusicplayerData.playControlMain.seekbar.setProgress(0);
    	try{
	    	MusicplayerData.myMediaPlayer.reset(); 
			MusicplayerData.myMediaPlayer.setDataSource(src);
			MusicplayerData.myMediaPlayer.prepare();
    	}catch (Exception e) 
    	{ 
    		e.printStackTrace();
    	}
    }
    
    private void initWidget(){
    	double height_rate=0.17;
        MusicplayerData.context=this;
        allMusic=(ImageButton) findViewById(R.id.all_music);
        hotRank=(ImageButton) findViewById(R.id.hot_rank);
        singer=(ImageButton) findViewById(R.id.singer);
        album=(ImageButton) findViewById(R.id.album);
        playlist=(ImageButton) findViewById(R.id.playlist);
        waitplaylist=(ImageButton) findViewById(R.id.waitplaylist);
        mylist=(ImageButton) findViewById(R.id.mylist);
        folder=(ImageButton) findViewById(R.id.folder);
        MusicplayerData.playControlMain=(PlayControl) findViewById(R.id.playcontrol);
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
        
        context=this;
    }

    private void initPopuptWindow(){
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
	private void getPopupWindow() {  
        
        if(null != popupWindowMenu) {  
        	popupWindowMenu.dismiss();  
            return;  
        }else {  
            initPopuptWindow();  
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
	        		Toast toast = Toast.makeText(MainActivity.this,"顺序播放", Toast.LENGTH_SHORT);                    
    			    toast.show();
	        	}else if (which==1){
	        		MusicplayerData.playMode=1;
	        		Toast toast = Toast.makeText(MainActivity.this, "随机播放", Toast.LENGTH_SHORT);                    
    			    toast.show();
	        	}else if (which==2){
	        		MusicplayerData.playMode=2;
	        		Toast toast = Toast.makeText(MainActivity.this, "单曲循环", Toast.LENGTH_SHORT);                    
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
						Toast toast = Toast.makeText(MainActivity.this, "定时退出已关闭", Toast.LENGTH_SHORT);                    
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
        					Toast toast = Toast.makeText(MainActivity.this, "请输入数字", Toast.LENGTH_SHORT);                    
            			    toast.show();
        				}
        				MusicplayerData.exitFlag=true;
        				MusicplayerData.exitSongNumber=num;
                    	Toast toast = Toast.makeText(MainActivity.this, num+"首歌曲后退出", Toast.LENGTH_SHORT);                    
        			    toast.show();
                    }
                });
        builder.show();
	 }
}
