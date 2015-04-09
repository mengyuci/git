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
		static public boolean play_flag=false;
		static public String Play_song_and_list_info_filepath="Playing_song_list_info.ini";
		static public String All_music_info_filepath="All_musiclist.ini";
		static public  int currentPosition=0;//正在播放的歌曲在musicfile列表中的下标
		static public String currentMusiclist_filename;//存储当前正在播放的歌曲所在列表的文件名
		static public int screen_height;
		static public int screen_width;
}
