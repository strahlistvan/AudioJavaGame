# AudioJavaGame
It's a simple Music quiz about your own mp3 music files. 

Eclipse Java SE project. Plain Java Classes with Java Swing GUI and external Java libraries: 
JAudioTagger -to extract audio files metadata (http://www.jthink.net/jaudiotagger/) 
JLayer - to play mp3 music files (http://www.javazoom.net/javalayer/javalayer.html).

<strong> audio package: </strong> <br />

<strong> AudioExtracter </strong> - contains some static methods to get some metadata from audio files. It uses JAudioTagger library. <br />
<strong> MusicFiles </strong> - More static methods to get all music file in a folder (recursive backtrack search), and get the list of Music, artists etc. <br />
<strong> Music </strong> - Music entity object. Contains the details of a music track. Also contains 'play' method (used JLayer in there). <br />

<strong> main package: </strong> <br />

<strong> Question </strong> - Question entity with answers. Generate a random question from the audioFiles list. (in constructor) <br />
<strong> GameSettings  </strong>  - general static variables of the game <br />
<strong> Main </strong> - main class with main method <br />

<strong> gui package: </strong> <br />

<strong> MainWindow - Main window GUI with file browser </strong> <br /> 
<strong> GameWindow - Game window GUI with the Question and answers </strong> <br />
<strong> HelpWindow </strong> - Short description (HTML content) <br />
<strong> InfoPanel </strong>  - Information panel about the score and the lifes. (Used in <b> MainWindow </b> and <b> GameWindow </b>) <br />

To directly download the executable Jar file: http://audiojavagame.pe.hu/AudioJavaGame_v0.1_beta.jar

For more information, visit http://audiojavagame.pe.hu/ (Hungarian language)
