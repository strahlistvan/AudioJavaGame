package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import audio.Music;

public class Question 
{
	/* Constants for the question type property */
	public final int TITLE  = 0;
	public final int ARTIST = 1;
	public final int ALBUM  = 2;
	public final int YEAR   = 3;
	
	public final int MODE_COUNT = 4;

	//maximal limit of cycles at random selection
	private final int MAX_CYCLES = 5000; 
	
	private int type;
	private String questionLine;
	private ArrayList<String> answers = new ArrayList<String>();
	private String goodAnswer;
	private Music currentMusic;
	
	/** Generate a random Question */
	public Question() 
	{
		int musicCount = GameSettings.audioFiles.size();
		int randIndex = (int) (Math.random()*musicCount)%musicCount;
		currentMusic = GameSettings.audioFiles.get(randIndex);
	
		this.type = (int) (MODE_COUNT*Math.random());
			
		/* Type 0: 'What is the title of the music' */
		if (type==TITLE)
		{		
			makeTitleQuestion();
		}
		/* Type 1: 'Who is the artist of the music' */
		else if (type==ARTIST)
		{	
			if (GameSettings.artists.size() >= 4)
			{
				boolean success = makeArtistQuestion();
				if (!success)
					makeTitleQuestion();
			}
			else 
				makeTitleQuestion();	
		}
		else if (type==ALBUM)
		{
			System.out.println("Album! SIZE:"+GameSettings.albums.size());
			if (GameSettings.albums.size() >= 4)
			{
				boolean success = makeAlbumQuestion();
				if (!success)
					makeTitleQuestion();
			}
			else
				makeTitleQuestion();
		}
		else if (type==YEAR)
		{
			boolean success = makeYearQuestion();
			if (!success)
				makeTitleQuestion();
		}
		else 
			System.out.println("Type: "+type+" Not found.");
		
		//shuffle the arraylist
		if (!answers.isEmpty())
			Collections.shuffle(answers);
	}

	/** Make a "Who is the title of the song?" type question. */	
	private void makeTitleQuestion()
	{
		int musicCount = GameSettings.audioFiles.size();
		
		questionLine = "Mi a jelenleg szóló zene címe?";
		answers = new ArrayList<String>();
		
		goodAnswer = currentMusic.getTitle();
		answers.add(goodAnswer);
		
		int cycles = 0;
		while (answers.size()!=GameSettings.answersCount && cycles < MAX_CYCLES)
		{
			//read bad answers from the all music
			String answer = "";
			int randIndex = (int) (Math.random()*musicCount)%musicCount;
			if (GameSettings.audioFiles.get(randIndex)!=null)
				answer = GameSettings.audioFiles.get(randIndex).getTitle();
				
			if (!answers.contains(answer) && !answer.toLowerCase().equals(goodAnswer.toLowerCase()) 
			&& !answer.matches("\\s*"))
					answers.add(answer);
			++cycles;
		}
		if (cycles >= MAX_CYCLES)
		{
			System.out.println("Max cycles reached: "+MAX_CYCLES);
			answers.add(GameSettings.audioFiles.get(1).getTitle());
			answers.add(GameSettings.audioFiles.get(2).getTitle());
			answers.add(GameSettings.audioFiles.get(3).getTitle());
		}
	}
	
	
	/** Make a "Who is the artist of the song?" type question. **/
	private boolean makeArtistQuestion()
	{
		questionLine = "Ki az elõadója a hallott zeneszámnak?";
		answers = new ArrayList<String>();
	
		goodAnswer = currentMusic.getArtist();
		
		if (goodAnswer.matches("\\s*"))
			return false;
		
		answers.add(goodAnswer);
		
		int artistCount = GameSettings.artists.size();
			
		int cycles = 0;
		while (answers.size()!=GameSettings.answersCount && cycles < MAX_CYCLES)
		{
			//read bad answers from the all music
			String answer = "";
		
			int randIndex = (int) (Math.random()*artistCount)%artistCount;
			answer = GameSettings.artists.get(randIndex);
				
			if (!answers.contains(answer) && answer!=null)
				answers.add(answer);
			++cycles;
		}
		if (cycles >= MAX_CYCLES)
		{
			System.out.println("Max cycles reached: "+MAX_CYCLES);
			answers.add(GameSettings.artists.get(1));
			answers.add(GameSettings.artists.get(2));
			answers.add(GameSettings.artists.get(3));
		}
		
		return true;
	}
	
	/** Make a "This song is in which album?" type question.  **/
	private boolean makeAlbumQuestion()
	{
		questionLine = "Melyik albumon hallható ez a zeneszám?";
		answers = new ArrayList<String>();
		
		goodAnswer = currentMusic.getAlbum();
		if (goodAnswer.matches("\\s*"))
			return false; 
		
		answers.add(goodAnswer);
		
		int albumCount = GameSettings.albums.size();
			
		int cycles = 0;
		while (answers.size()!=GameSettings.answersCount && cycles < MAX_CYCLES)
		{
			//read bad answers from the all music
			String answer = "";
		
			int randIndex = (int) (Math.random()*albumCount)%albumCount;
			answer = GameSettings.albums.get(randIndex);
				
			if (!answers.contains(answer) && answer!=null)
				answers.add(answer);
			++cycles;
		}
		if (cycles >= MAX_CYCLES)
		{
			System.out.println("Max cycles reached: "+MAX_CYCLES);
			answers.add(GameSettings.albums.get(1));
			answers.add(GameSettings.albums.get(2));
			answers.add(GameSettings.albums.get(3));
		}

		return true;
	}
		
	/** Make a "When the song released?" type random question.
	 *  If it not successful, return false.
	 *  @return boolean success
	 **/
	private boolean makeYearQuestion()
	{
		questionLine = "Mikor jelent meg a hallott zeneszám?";
		answers = new ArrayList<String>();
		
		Integer goodAnswerInt = currentMusic.getYear();
		goodAnswer = currentMusic.getYear().toString();
        
		if (goodAnswerInt == 0)
			return false;
		
		answers.add(goodAnswer);
		int currentYear =  Calendar.getInstance().get(Calendar.YEAR);
		
		for (int k=0; k<3; ++k)
		{
			int randNum = (int) (40*Math.random()-20);  //random number between -20 and 20
			randNum = (randNum==0)?randNum+1:randNum;	// it can not be zero
			Integer answer = goodAnswerInt + randNum;
			//it can not greater than the current year
			answer = (answer > currentYear)?1984+randNum:answer; 
			answers.add(answer.toString());
		}
		
		return true;
	}
	
	public int getType() {
		return type;
	}
		
	public void setType(int type) {
		this.type = type;
	}
	
	public int getGoodAnswerIndex()
	{
		for (int i=0; i<answers.size(); ++i)
		{
			if (answers.get(i).equals(goodAnswer))
				return i;
		}
		return -1;
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}
	
	public String getGoodAnswer() {
		return goodAnswer;
	}
	
	public String getQuestionLine() {
		return questionLine;
	}

	public void setQuestionLine(String questionLine) {
		this.questionLine = questionLine;
	}
	
	public Music getCurrentMusic(){
		return currentMusic;
	}
}
