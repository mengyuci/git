package com.yueyin.mymusicplayer;

import java.io.File;
import java.io.UnsupportedEncodingException;

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
	     String temp;
			try {
				temp=retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_TITLE );
				if (temp==null||temp.equals("")){
					temp=file.getName();
			      }
				musicinfo.title =new String(temp.getBytes("utf-8")).trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				temp=retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ALBUM );
				if (temp==null||temp.equals("")){
					temp="未知专辑";
			      }
				musicinfo.album =new String(temp.getBytes("utf-8")).trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				temp=retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ARTIST );
				if (temp==null||temp.equals("")){
					temp="未知歌手";
			      }
				musicinfo.artist =new String(temp.getBytes("utf-8")).trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//	      musicinfo.album =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ALBUM );
//	      musicinfo.artist =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ARTIST );
//	      //composer =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_COMPOSER );
//	      //genre =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_GENRE );
//	      //mime =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_MIMETYPE );
//	      //System.out.println( "title=" +title + ",album=" + album + ",artist=" + artist + ",composer=" + composer +",genre=" + genre + ",mime=" + mime);
//	      
//	      if (musicinfo.album==null||musicinfo.album.equals("")){
//	    	  musicinfo.album="未知专辑";
//	      }
//	      if (musicinfo.artist==null||musicinfo.artist.equals("")){
//	    	  musicinfo.artist="未知歌手";
//	      }
	      return musicinfo;
	}
	
}
