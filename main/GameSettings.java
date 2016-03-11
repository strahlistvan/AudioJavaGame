package main;

import java.io.File;
import java.util.ArrayList;

import audio.Music;

public class GameSettings 
{
	public static ArrayList<Music> audioFiles = new ArrayList<Music>();
	public static ArrayList<String> artists = new ArrayList<String>();
	public static String folderName = new File(".").toString();
	public static Integer answersCount = 4;
	public static Integer starterLifeCount = 3;
	public static Integer lifeCount = 3;
	public static Integer score = 0;
}
