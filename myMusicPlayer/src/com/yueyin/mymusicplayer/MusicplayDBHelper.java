package com.yueyin.mymusicplayer;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicplayDBHelper extends SQLiteOpenHelper {

	public MusicplayDBHelper(Context context) {
		super(context, MusicplayerData.dbName, null, MusicplayerData.dbVersion);

	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql;
		sql = "create table "+MusicplayerData.dbAllMusicTable+"(filepath text not null primary key , singer text,album text ,folder text,hotnum int unsigned default 100,flag int unsigned default 0);";          
        db.execSQL(sql);
        db.execSQL("create table "+MusicplayerData.dbCustomMusicListNameTable+" (listname text primary key);");
        db.execSQL("create table "+MusicplayerData.dbPreMusicListTable+" (filepath text not null, primary key(filepath),foreign key(filepath) " +
        		"references "+MusicplayerData.dbAllMusicTable+"(filepath) on delete cascade on update cascade);");
        db.execSQL("create table "+MusicplayerData.dbNextMusicListTable+" (filepath text not null, primary key(filepath),foreign key(filepath) " +
        		"references "+MusicplayerData.dbAllMusicTable+"(filepath) on delete cascade on update cascade);");
        db.execSQL("create table "+MusicplayerData.dbLastMusicListTable+" (filepath text not null, primary key(filepath),foreign key(filepath) " +
        		"references "+MusicplayerData.dbAllMusicTable+"(filepath) on delete cascade on update cascade);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
