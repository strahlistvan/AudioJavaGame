package main;

import java.util.ArrayList;
import java.util.Collections;

import audio.Music;

public class Question 
{
	/* Codes for the question type property */
	public final int TITLE = 0;
	public final int ARTIST = 1;
	
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
	
		this.type = (int) (2*Math.random());
		
		/* Type 1: 'What is the title of the music' */
		if (type==TITLE)
		{		
			questionLine = "Mi a zene címe?";
			answers = new ArrayList<String>();
			
			goodAnswer = "";
			while (goodAnswer.matches("\\s*"))
			{
				goodAnswer = currentMusic.getTitle();
			}
			answers.add(goodAnswer);
			
			int cycles = 0;
			while (answers.size()!=GameSettings.answersCount && cycles < MAX_CYCLES)
			{
				//read bad answers from the all music
				String answer = "";
				randIndex = (int) (Math.random()*musicCount)%musicCount;
				if (GameSettings.audioFiles.get(randIndex)!=null)
					answer = GameSettings.audioFiles.get(randIndex).getTitle();
					
				if (!answers.contains(answer) && !answer.toLowerCase().equals(goodAnswer.toLowerCase()) 
				&& !answer.matches("\\s*"))
						answers.add(answer);
				++cycles;
			}
		}
		/* Type 2: 'Who is the artist of the music' */
		else if (type==ARTIST)
		{	
			questionLine = "Ki az elõadó?";
			answers = new ArrayList<String>();
			
			goodAnswer = "";
			while (goodAnswer.matches("\\s*"))
			{
				goodAnswer = currentMusic.getArtist();
			}
			answers.add(goodAnswer);
			
			int artistCount = GameSettings.artists.size();
				
			int cycles = 0;
			while (answers.size()!=GameSettings.answersCount && cycles < MAX_CYCLES)
			{
				//read bad answers from the all music
				String answer = "";
			
				randIndex = (int) (Math.random()*artistCount)%artistCount;
				answer = GameSettings.artists.get(randIndex);
					
				if (!answers.contains(answer) && answer!=null)
					answers.add(answer);
				++cycles;
			}
		}
		else 
			System.out.println("Type: "+type+" Not found.");
		
		//shuffle the arraylist
		if (!answers.isEmpty())
			Collections.shuffle(answers);
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
