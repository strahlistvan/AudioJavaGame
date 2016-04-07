# AudioJavaGame
It's a simple Music quiz about your own mp3 music files. 

Eclipse Java SE project. Plain Java Classes with Java Swing GUI and external Java libraries: 
JAudioTagger -to extract audio files metadata (http://www.jthink.net/jaudiotagger/) 
JLayer - to play mp3 music files (http://www.javazoom.net/javalayer/javalayer.html).

audio package:
AudioExtracter - contains some static methods to get some metadata from audio files. It uses JAudioTagger library.
MusicFiles - More static methods to get all music file in a folder (recursive backtrack search), and get the list of Music, artists etc.
Music - Music entity object. Contains the details of a music track. Also contains 'play' method (used JLayer in there).

main package:
Question - Question entity with answers. Generate a random question from the audioFiles list. (in constructor)
GameSettings - general static variables of the game
Main - main class with main method

gui package:
MainWindow - Main window GUI with file browser
GameWindow - Game window GUI with the Question and answers
HelpWindow - Short description (HTML content)
InfoPanel  - Information panel about the score and the lifes. (Used in MainWindow and GameWindow)

To directly download the executable Jar file: http://audiojavagame.pe.hu/AudioJavaGame_v0.1_beta.jar

For more information, visit http://audiojavagame.pe.hu/ (Hungarian language)
