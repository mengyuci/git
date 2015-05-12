package com.yueyin.mymusicplayer;

import java.util.Iterator;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class MusicplayerControl  {
	private static String filepath;
	static Random random=new Random();
	public static  void playmusic(final Context context,final int position,final PlayControl playControl)
	{
		try {
			String src=MusicplayerData.musicfile.get(position);
	    	MusicInfo musicinfo=MusicInfo.getMetaData(src);
	    	MusicplayDBHelper dbHelp=new MusicplayDBHelper(context);
			SQLiteDatabase db=dbHelp.getWritableDatabase();
	    	int hotnum=MusicplayerDBOperate.getHotnum(MusicplayerData.currentMusiclistFilename, src, db);
	    	musicinfo.setHotnum(hotnum);
	    	MusicplayerData.musicInfoAll=musicinfo;
	    	MusicplayerDBOperate.insertPreMusicList(src,db);
	    	
			MusicplayerData.myMediaPlayer.reset();
			MusicplayerData.myMediaPlayer.setDataSource(src);
			MusicplayerData.myMediaPlayer.prepare();
			MusicplayerData.myMediaPlayer.start();
			
			
			
			if (MusicplayerData.playStatus==false){
				Resources r = context.getResources(); 
    			Drawable img=r.getDrawable(R.drawable.pause);
    			playControl.play.setImageDrawable(img);
    			MusicplayerData.playStatus=true;
			} 
			MusicplayerData.musiclist_lastplay.add(src);
			MusicplayerData.currentPosition=position;
			saveMusicList(context);
			playControl.seekbar.setMax(MusicplayerData.myMediaPlayer.getDuration()); 
			
			MusicplayerData.myMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
	            
	            @Override
	            public void onCompletion(MediaPlayer mp) { 
	            	if (MusicplayerData.currentMusiclistFilename==MusicplayerData.dbNextMusicListTable){
	        			String filepath=MusicplayerData.musicfile.get(MusicplayerData.currentPosition);
	        			MusicplayDBHelper dbHelp=new MusicplayDBHelper(context);
	        			SQLiteDatabase db=dbHelp.getWritableDatabase();
	        			db.execSQL("delete from "+MusicplayerData.dbNextMusicListTable+" where filepath='"+filepath+"'");
	        		}
	            	if (MusicplayerData.exitFlag){
	            		MusicplayerData.exitSongNumber--;
	            		if (MusicplayerData.exitSongNumber<=0){
	            			SysApplication.getInstance().exit();
	            		}
	            	}
	                nextmusic(context,position,playControl);
	            }
	        });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	public static  void playOrPause(final Context context,final PlayControl playControl)
	{
		if (MusicplayerData.playStatus){
			MusicplayerData.myMediaPlayer.pause();
			Resources r = context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.play);
			playControl.play.setImageDrawable(img);
		}else{
			MusicplayerData.myMediaPlayer.start();
			Resources r = context.getResources(); 
			Drawable img=r.getDrawable(R.drawable.pause);
			playControl.play.setImageDrawable(img);
		}
		MusicplayerData.playStatus=!MusicplayerData.playStatus;
	}
	public static  void nextmusic(final Context context,int position,final PlayControl playControl)
	{
		
		if (MusicplayerData.playMode==0){
			position++;
			if (position>=MusicplayerData.musicfile.size())
				position=0;
		}else if(MusicplayerData.playMode==1){
			filepath=MusicplayerData.musicfile.get(position);
			position=getRandom();
		}
		playmusic(context,position,playControl);
		playControl.seekbar.setProgress(0);
	}
	
	public  static void premusic(final Context context,int position,final PlayControl playControl)
	{
		position--;
		if (position<0)
			position=MusicplayerData.musicfile.size()-1;
		playmusic(context,position,playControl);
		playControl.seekbar.setProgress(0);
	}
	
	public static  void saveMusicList(final Context context)
	{
		SharedPreferences mSharedPreferences = context.getSharedPreferences("MainActivity", Context.MODE_PRIVATE);  
		SharedPreferences.Editor edit=mSharedPreferences.edit();
		
		edit.putString("listpath", MusicplayerData.currentMusiclistFilename);
		edit.putInt("curPosition", MusicplayerData.currentPosition);
		edit.commit();
	}
	
	public void addHotNumber(final PlayControl playControl){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		String filepath=MusicplayerData.musicfile.get(MusicplayerData.currentPosition%MusicplayerData.musicfile.size());
		db.execSQL("update "+MusicplayerData.dbAllMusicTable+" set hotnum=hotnum+10 where filepath='"+filepath+"'");
		int hotnum=MusicplayerDBOperate.getHotnum(MusicplayerData.currentMusiclistFilename, filepath, db);
		playControl.setHotnum("hot:"+hotnum);
		MusicplayerData.musicInfoAll.setHotnum(hotnum);
	}
	public void subHotNumber(final PlayControl playControl){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		String filepath=MusicplayerData.musicfile.get(MusicplayerData.currentPosition%MusicplayerData.musicfile.size());
		Cursor c=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+filepath+"'", null);
		if (c.moveToFirst()){
			int t=c.getInt(0);
			if (t>=10){
				db.execSQL("update "+MusicplayerData.dbAllMusicTable+" set hotnum=hotnum-10 where filepath='"+filepath+"'");
			}
		}
		int hotnum=MusicplayerDBOperate.getHotnum(MusicplayerData.currentMusiclistFilename, filepath, db);
		playControl.setHotnum("hot:"+hotnum);
		MusicplayerData.musicInfoAll.setHotnum(hotnum);
	}

	public static int getRandom(){
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		Iterator<String> it=MusicplayerData.musicfile.iterator();
		int x=random.nextInt(MusicplayerData.totHotnum);
		System.out.println("random"+x);
		int sum=0;
		while (it.hasNext()){
			filepath=it.next();
			sum+=MusicplayerDBOperate.getHotnum(MusicplayerData.dbAllMusicTable, filepath, db);
			if (sum>=x)
				break;
		}
		return MusicplayerData.musicfile.indexOf(filepath);
	}
	
}
