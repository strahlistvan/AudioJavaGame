package audio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

public class Music 
{
	private String artist, album, title;
	private int year, length;
	private long framesCount;
	private File audioFile;
	private BufferedImage image;
	
	public Music(){ super(); }
	
	public Music(String artist, String title, File audioFile, int length, long framesCount)
	{
		this.artist = artist;
		this.title = title;
		this.audioFile = audioFile;
		this.length = length;
		this.framesCount = framesCount;
	}
	
	public Music(String artist, String title, File audioFile, String album, int length, long framesCount)
	{
		this.artist = artist;
		this.title = title;
		this.audioFile = audioFile;
		this.album = album;
		this.length = length;
		this.framesCount = framesCount;
	}
	
	public Music(String artist, String title, File audioFile, String album, int length, int year, long framesCount)
	{
		this.artist = artist;
		this.title = title;
		this.audioFile = audioFile;
		this.album = album;
		this.length = length;
		this.year = year;
		this.framesCount = framesCount;
	}
	
	/** Play the music from the beginning to the end.
	 * @return boolean success
	 **/
	public boolean play()
	{
		try
		{
		    FileInputStream fis = new FileInputStream(audioFile);
		    AdvancedPlayer playMP3 = new AdvancedPlayer(fis);
		    playMP3.play();
		}
		catch(Exception ex)
		{
			System.out.println("Failed to play the file.");
		    ex.printStackTrace();
		    return false;
		}
		return true;
	}
	
	/** Play the music from a random part, for some seconds.
	 *  @param int seconds - length of the play time in seconds. Must less then the music length
	 *  @return boolean success
	 **/
	public boolean playRandomPart(int seconds)
	{
		double ratio = (double)seconds/length;
		if (ratio >= 1.0)
		{
			System.out.println("Too long parameter:"+seconds);
			System.out.println(audioFile+"'s length:"+length);
			return false;
		}
		
		int frameLength = (int)(ratio*framesCount);
		int rand = (int) (Math.random()*framesCount)% (int)(framesCount-frameLength);
		
		try
		{
		    FileInputStream fis = new FileInputStream(audioFile);
		    AdvancedPlayer playMP3 = new AdvancedPlayer(fis);
		    playMP3.play(rand, rand+frameLength);
		}
		catch(Exception ex)
		{
			System.out.println("Failed to play the file:"+audioFile);
		    ex.printStackTrace();
		    return false;
		}	
		return true;
	}
	

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public long getframesCount() {
		return framesCount;
	}
	
	public void setframesCount(long framesCount){
		this.framesCount = framesCount;
	}
	
	public File getAudioFile(){
		return audioFile;
	}
	
	public void setAudioFile(File audioFile) {
		if (audioFile.exists() && audioFile.isFile())
			this.audioFile = audioFile;
	}

	@Override
	public String toString() 
	{
		return "Music [artist=" + artist + ", album=" + album + ", title=" + title + ", year=" + year + ", length="
				+ length + "]";
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
}
