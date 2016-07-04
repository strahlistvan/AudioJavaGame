# AudioJavaGame
It's a simple Music quiz about your own mp3 music files. 

Eclipse Java SE project. Plain Java Classes with Java Swing GUI and external Java libraries: 
<ul>
<li> <a href="http://www.jthink.net/jaudiotagger/" target="_blank"> JAudioTagger </a> -to extract audio files metadata
<li> <a href="http://www.javazoom.net/javalayer/javalayer.html" target="_blank"> JLayer </a> - to play mp3 music files
</ul>

<strong> audio package: </strong> <br />

<ul>
<li> <strong> AudioExtracter </strong> - contains some static methods to get some metadata from audio files. It uses JAudioTagger library. <br />
<li> <strong> MusicFiles </strong> - More static methods to get all music file in a folder (recursive backtrack search), and get the list of Music, artists etc. <br />
<li> <strong> Music </strong> - Music entity object. Contains the details of a music track. Also contains 'play' method (used JLayer in there). <br />
</ul>

<strong> main package: </strong> <br />

<ul>
<li> <strong> Question </strong> - Question entity with answers. Generate a random question from the audioFiles list. (in constructor) <br />
<li> <strong> GameSettings  </strong>  - general static variables of the game <br />
<li> <strong> Main </strong> - main class with main method <br />
</ul>

<strong> gui package: </strong> <br />

<ul>
<li> <strong> MainWindow - Main window GUI with file browser </strong> <br /> 
<li> <strong> GameWindow </strong> - Game window GUI with the Question and answers <br />
<li> <strong> HelpWindow </strong> - Short description (HTML content) <br />
<li> <strong> InfoPanel </strong>  - Information panel about the score and the lifes. (Used in <b> MainWindow </b> and <b> GameWindow </b>) <br />
</ul>

To directly download the executable Jar file: http://audiojavagame.pe.hu/AudioJavaGame_v0.2_beta.jar

For more information, visit http://audiojavagame.pe.hu/ (Hungarian language)
