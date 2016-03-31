package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import audio.Music;

public class Question 
{
	/* Codes for the question type property */
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
				makeArtistQuestion();
			else 
				makeTitleQuestion();	
		}
		else if (type==ALBUM)
		{
			System.out.println("Album! SIZE:"+GameSettings.albums.size());
			if (GameSettings.albums.size() >= 4)
				makeAlbumQuestion();
			else
				makeTitleQuestion();
		}
		else if (type==YEAR)
		{
			makeYearQuestion();
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
		int cycles = 0;
		
		questionLine = "Mi a jelenleg szóló zene címe?";
		answers = new ArrayList<String>();
		
		goodAnswer = "";
		
		while (goodAnswer.matches("\\s*") && cycles < MAX_CYCLES)
		{
			goodAnswer = currentMusic.getTitle();
			++cycles;
		}
		answers.add(goodAnswer);
		
		cycles = 0;
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
	
	
	/** Make a "Who is the artist of the song?" type question.
	 *  If the user don't have enough artist, then "What is the title?" question 
	 **/
	private void makeArtistQuestion()
	{
		questionLine = "Ki az elõadója a hallott zeneszámnak?";
		answers = new ArrayList<String>();
		int cycles = 0;
		
		goodAnswer = "";
		while (goodAnswer.matches("\\s*") && cycles < MAX_CYCLES)
		{
			goodAnswer = currentMusic.getArtist();
			++cycles;
		}
		answers.add(goodAnswer);
		
		int artistCount = GameSettings.artists.size();
			
		cycles = 0;
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
	}
	
	/** Make a "This song is in whitch album?" type question.
	 *  If the user don't have enough artist, then "What is the title?" question 
	 **/
	private void makeAlbumQuestion()
	{
		questionLine = "Melyik albumon hallható ez a zeneszám?";
		answers = new ArrayList<String>();
		int cycles = 0;
		
		goodAnswer = "";
		
		while (goodAnswer.matches("\\s*") && cycles < MAX_CYCLES)
		{
			goodAnswer = currentMusic.getAlbum();
			++cycles;
		}
		answers.add(goodAnswer);
		
		int albumCount = GameSettings.albums.size();
			
		cycles = 0;
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

	}
	
	private void makeYearQuestion()
	{
		questionLine = "Mikor jelent meg a hallott zeneszám?";
		answers = new ArrayList<String>();
		int cycles = 0;
		
		goodAnswer = "";
		Integer goodAnswerInt = 0;
		while (goodAnswer.matches("\\s*") && cycles < MAX_CYCLES)
		{
			goodAnswerInt = currentMusic.getYear();
			goodAnswer = currentMusic.getYear().toString();
		}
		
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
