package com.yueyin.mymusicplayer;

import java.util.Iterator;
import java.util.Random;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MusicplayerDBOperate {
	public static void insert(String src,SQLiteDatabase db){
		MusicInfo info=MusicInfo.getMetaData(src);
		int i;
		for (i=src.length()-1;i>=0;--i){
			if (src.charAt(i)=='/')
				break;
		}
		String folder=src.substring(0, i);
		ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
		cv.put("filepath",src);
		cv.put("singer", info.artist);
		cv.put("album", info.album);
		cv.put("folder", folder);
		cv.put("hotnum", 100);
		db.insert(MusicplayerData.dbAllMusicTable,null,cv);
	}
	
	public static void insertPreMusicList(String src,SQLiteDatabase db){
		db.execSQL("delete from "+MusicplayerData.dbPreMusicListTable+" where filepath='"+src+"'");
		db.execSQL("insert into "+MusicplayerData.dbPreMusicListTable+" (filepath)values('"+src+"' )");
	}
	
	public static int getHotnumFromAllMusic(String src,SQLiteDatabase db){
		Cursor c=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+src+"'", null);
		if (c.moveToFirst()){
			return c.getInt(0);
		}
		return 0;
	}
	
	public static int getHotnum(String tableName,String src,SQLiteDatabase db){
		Cursor c=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+src+"'",null);
		if (c.moveToFirst()){
			return c.getInt(0);
		}
		return 0;
	}
	public static void insertLastMusicList(SQLiteDatabase db){
		Iterator<String> it=MusicplayerData.musicfile.iterator();
		db.execSQL("delete from "+MusicplayerData.dbLastMusicListTable);
		while(it.hasNext()){
			db.execSQL("insert into "+MusicplayerData.dbLastMusicListTable+"(filepath) values('"+it.next()+"')");
		}
	}
	public void getLastMusicList(SQLiteDatabase db){
		Cursor c=db.rawQuery("select filepath from "+MusicplayerData.dbLastMusicListTable, null);
		if (c.moveToFirst()){
			MusicplayerData.musicfile.clear();
		    for(int i=0;i<c.getCount();i++){
		        MusicplayerData.musicfile.add(c.getString(0));
		        c.moveToNext();
		    }
		}
	}
	
	public static int getTotHotnum(){
		Iterator<String> it=MusicplayerData.musicfile.iterator();
		String filepath;
		int sum=0;
		MusicplayDBHelper dbHelp=new MusicplayDBHelper(MusicplayerData.context);
		SQLiteDatabase db=dbHelp.getWritableDatabase();
		while (it.hasNext()){
			filepath=it.next();
			Cursor c=db.rawQuery("select hotnum from "+MusicplayerData.dbAllMusicTable+" where filepath='"+filepath+"'", null);
			if (c.moveToFirst()){
				sum+=c.getInt(0);
			}
		}
		return sum;
	}
	
}
