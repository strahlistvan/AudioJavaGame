package audio;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.images.Artwork;

public class AudioExtracter 
{
	/** Extract information from an audio file.
	 *  @param String fileName - path and name of the audio file.
     *  @return Music object, with the properties.
	 */
	public static Music extractMusicData(String fileName)
	{	
		try 
		{
			File file = new File(fileName);
			AudioFile audioFile = AudioFileIO.read(file);

			Tag tag = audioFile.getTag();
			AudioHeader header = audioFile.getAudioHeader();
			
			int length = header.getTrackLength();
			long samples = header.getNoOfSamples();
			String artist = tag.getFirst(FieldKey.ARTIST).trim();
			String title = tag.getFirst(FieldKey.TITLE).trim();
			String album = tag.getFirst(FieldKey.ALBUM).trim();
			int year = Integer.parseInt("0"+tag.getFirst(FieldKey.YEAR));
			
			Music music = new Music(artist, title, file, album, length, year, samples);
			music.setImage(AudioExtracter.getCoverImage(fileName));
			return music;
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("File not found: "+fileName);
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/** Give the artist of the given audio file from metadata.
	 *  @param String fileName - path and name of the audio file.
     *  @return String - name of the artist.
	 */
	public static String getMusicArtist(String fileName)
	{
		String artist = "";
		try 
		{
			File file = new File(fileName);
			AudioFile audioFile = AudioFileIO.read(file);
			artist = audioFile.getTag().getFirst(FieldKey.ARTIST).trim();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("File not found: "+ex);
			ex.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return artist;
	}
		
	/** Give the cover image of the given audio file from metadata, if exists.
	 *  @param String fileName - path and name of the audio file.
     *  @return BufferedImage - the cover image from metadata.
	 */
	public static BufferedImage getCoverImage(String fileName)
	{
		try 
		{
			File file = new File(fileName);
			AudioFile audioFile = AudioFileIO.read(file);
			Tag audioTag = audioFile.getTag();
			Artwork art = audioTag.getFirstArtwork();
			if (art!=null)
			{
				byte[] binArt = art.getBinaryData();
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(binArt));
				image = scaleImage(image, 200, 200);
				return image;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	   /** Resizes an image using a Graphics2D object backed by a BufferedImage.
		*  @param srcImg - source image to scale
		*  @param width - desired width
		*  @param height - desired height
		*  @return - the new resized image
		*/
		public static BufferedImage scaleImage(BufferedImage image, int width, int height) 
		{
	       int type = (image.getType() == 0)? BufferedImage.TYPE_INT_ARGB : image.getType();
	       BufferedImage resizedImage = new BufferedImage(width, height, type);
	       Graphics2D graphics = resizedImage.createGraphics();
	       graphics.drawImage(image, 0, 0, width, height, null);
	       graphics.dispose();
	       return resizedImage;
	    }	
}
