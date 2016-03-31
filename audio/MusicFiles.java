package audio;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class MusicFiles 
{
	private static ArrayList<Music> allMusicObjects = new ArrayList<Music>();
	
	 /** FileFilter: filters only the acceptable music files **/
	 public static FileFilter filter = new FileFilter() 
     {
        @Override
        public boolean accept(File path) 
        {
			  if (path.isDirectory())
				return true;
            String[] okExt = {"mp3", "wmw"}; 
            for (int i=0; i<okExt.length; ++i)
			{
				if (path.getName().toLowerCase().endsWith(okExt[i]))
					return true;
			}	
           return false;
        }
     };
	
     
    /** 
     * List recursively the the given directory, and Fill the allMusicFileNames list
     * @param String dirName - Name of the root directory
     * @return void
     **/
	protected static void listDirectory(String dirName)
	{
	  File fileDir = null;
      File[] allFiles;
      
      //System.out.println(Files.probeContentType("filename")); //bad...
      
      try
      {      
		 fileDir = new File(dirName);
		 //if not directory, then return (recursion end)
		 if (!fileDir.isDirectory())
			return;
		 
         allFiles = fileDir.listFiles(filter);
         
         // for each file in a directory
         for(File file: allFiles)
         {
            //if it is a real file, then add filename to list
        	 if (file.isFile())
        	 {
        		 Music music = AudioExtracter.extractMusicData(file.toString());
        		 allMusicObjects.add(music);
        	 }
            //if directory, then open and list it
        	 else if (file.isDirectory())
				listDirectory(file.toString());
         }
      }
      catch(Exception e)
      {
         // if any error occurs
    	  System.out.println("Error when listing the directory");
         e.printStackTrace();
      }
	}
	
	/**
	 * @param String dirName - Name of the root directory
	 * @return ArrayList<String> - the name of the music files in the given directory
	 **/
	public static ArrayList<Music> getallMusicObjects(String dirName)
	{
		allMusicObjects.clear();
		listDirectory(dirName);
		allMusicObjects.removeAll(Collections.singleton(null)); //remove all null values (for...)
		return allMusicObjects;
	}
	
	/** Return the list of the artist who play soung in the given folder's music files
	 *  @param String dirName - Path of the audio files
	 *  @return ArrayList<String> list of the artists
	 **/
	public static ArrayList<String> getallArtists(String dirName)
	{
		ArrayList<String> allArtists = new ArrayList<String>();
		getallMusicObjects(dirName);
		for (Music music: allMusicObjects)
		{
			String artist = music.getArtist();
			boolean inArray = false;
			for (int i=0; i<allArtists.size(); ++i)
			{
				if (allArtists.get(i).toLowerCase().equals(artist.toLowerCase()))
					inArray = true;
			}
			if (!inArray && !artist.matches("\\s*"))
				allArtists.add(artist);
		}
		return allArtists;
	}
	
	/** Return the list of the albums of the music files
	 *  @param String dirName - Path of the audio files
	 *  @return ArrayList<String> list of the albums
	 **/
	public static ArrayList<String> getAllAlbum(String dirName)
	{
		ArrayList<String> allAlbums = new ArrayList<String>();
		getallMusicObjects(dirName);
		for (Music music: allMusicObjects)
		{
			String album = music.getAlbum();
			boolean inArray = false;
			for (int i=0; i<allAlbums.size(); ++i)
			{
				if (allAlbums.get(i).toLowerCase().equals(album.toLowerCase()))
					inArray = true;
			}
			if (!inArray && !album.matches("\\s*"))
				allAlbums.add(album);
		}
		return allAlbums;
	}
}
