package com.yueyin.mymusicplayer;

import java.io.File;

import android.media.MediaMetadataRetriever;

public class MusicInfo {
	String title, album,artist;
	int hotnum=0;
	public void setHotnum(int num){
		hotnum=num;
	}
	public int getHotnum(){
		return hotnum;
	}
	
	public static MusicInfo getMetaData(String filePath) {
		File file=new File(filePath);
		if (!file.exists()){
			return null;
		}
		MusicInfo musicinfo=new MusicInfo();
	       MediaMetadataRetriever retriever = new MediaMetadataRetriever ();
	     retriever.setDataSource(filePath);
	      musicinfo.title =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_TITLE );
	      musicinfo.album =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ALBUM );
	      musicinfo.artist =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ARTIST );
	      //composer =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_COMPOSER );
	      //genre =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_GENRE );
	      //mime =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_MIMETYPE );
	      //System.out.println( "title=" +title + ",album=" + album + ",artist=" + artist + ",composer=" + composer +",genre=" + genre + ",mime=" + mime);
	      if (musicinfo.title==null){
	    	  musicinfo.title=file.getName();
	      }
	      if (musicinfo.album==null){
	    	  musicinfo.album="未知专辑";
	      }
	      if (musicinfo.artist==null){
	    	  musicinfo.artist="未知歌手";
	      }
	      return musicinfo;
	}
	
}
