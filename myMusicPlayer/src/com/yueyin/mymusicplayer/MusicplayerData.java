package com.yueyin.mymusicplayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.media.MediaPlayer;

public class MusicplayerData {
		static public  List<String> musicfile=new ArrayList<String>();
		static public  List<String> All_musicfile=new ArrayList<String>();
		static public  List<String> currentmusicfile=new ArrayList<String>();
		static public  List<String> musiclist_lastplay=new LinkedList<String>();
		static public  MediaPlayer myMediaPlayer=new MediaPlayer();
		static public boolean playStatus=false;
		static public String Play_song_and_list_info_filepath="Playing_song_list_info.ini";
		static public String allMusiclistFilepath="All_musiclist.ini";
		static public  int currentPosition=0;//正在播放的歌曲在musicfile列表中的下标
		static public String currentMusiclistFilename;//存储当前正在播放的歌曲所在列表的文件名
		static public int screenHeight;
		static public int screenWidth;
}
