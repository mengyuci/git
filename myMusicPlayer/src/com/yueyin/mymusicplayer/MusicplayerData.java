package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicplayerData {
		static public  List<String> musicfile=new ArrayList<String>();
		static public  List<String> All_musicfile=new ArrayList<String>();
		static public  List<String> currentmusicfile=new ArrayList<String>();
		static public  List<String> musiclist_lastplay=new LinkedList<String>();//已播放列表
		static public  Context context;
		static public  MediaPlayer myMediaPlayer=new MediaPlayer();
		static public boolean playStatus=false;
		static public String Play_song_and_list_info_filepath="Playing_song_list_info.ini";
		static public String allMusiclistFilepath="allmusic";
		static public  int currentPosition=0;//正在播放的歌曲在musicfile列表中的下标
		static public String currentMusiclistFilename;//存储当前正在播放的歌曲所在列表的文件名
		static public int currentDuration=0;//当前播放歌曲的毫秒数，用于界面变换保证playControl的一致性
		static public int totHotnum=0;
		static public int screenHeight;
		static public int screenWidth;
		
		static public String dbName="yueyin.db";
		static public int dbVersion = 3;
		static public String dbAllMusicTable="allmusic";//全部音乐列表
		static public String dbCustomMusicListNameTable="musiclist";//存放用户自定义列表的名称的表
		static public String dbPreMusicListTable="premusiclist";//已播放列表 
		static public String dbNextMusicListTable="nextmusiclist";//待播放列表
		static public String dbLastMusicListTable="lastmusiclist";//待播放列表
		
		static public volatile PlayControl playControlMain,playControlSongList;
		static public MusicInfo musicInfoAll;
		
		static public int playMode=0;
		static public boolean exitFlag=false;
		static public int exitSongNumber=6;
}
