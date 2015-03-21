package com.yueyin.mymusicplayer;

import android.media.MediaMetadataRetriever;

public class Musicinfo {
	String title, album,artist;
	
	public static Musicinfo getMetaData(String filePath) {
		Musicinfo musicinfo=new Musicinfo();
	       MediaMetadataRetriever retriever = new MediaMetadataRetriever ();
	     retriever.setDataSource(filePath);
	      musicinfo.title =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_TITLE );
	      musicinfo.album =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ALBUM );
	      musicinfo.artist =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_ARTIST );
	      //composer =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_COMPOSER );
	      //genre =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_GENRE );
	      //mime =retriever.extractMetadata( MediaMetadataRetriever . METADATA_KEY_MIMETYPE );
	      //System.out.println( "title=" +title + ",album=" + album + ",artist=" + artist + ",composer=" + composer +",genre=" + genre + ",mime=" + mime);
	      return musicinfo;
	}
	
}
