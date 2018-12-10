<h1>OOP Ex3</h1>
Created during a computer communication course during the second year at Ariel University 
in the Department of Computer Science, 2018
<br>
<b>Project site:</b> https://zvimints.github.io/OOP_2/ *********** NEED TO UPDATE <br>
<b>Made by: </b><a href="https://github.com/ZviMints">Zvi Mints</a> and <a href="https://github.com/orabu103">Or Abuhazira</a>
<h1>About The Project</h1>
<b>Problem:</b> Given a .csv file with pecans and fruits 
find a minimal optimal greedy path by time such that the Pacmans will pass through all the fruits in the map
This project represents a greedy solution to the <b>problem.</b>
<h1>Project Diagram:</h1>
שים פה תמונה של uml
<h1>Class Hierarchy:</h1>
שים פה תמונה של class Hierarchy
<h1>Packages:</h1>
שים פה תמונה של packages
So Lets start!
<h1>Coords</h1>
שים פה תמונה של מה יש ב MyCoords כלומר אינטרפס 
This Class is responsible for actions between Objects of the kind Point3D.
The Class is used to Provide a solution for elementary actions between vectors and points in R^3 Vector space.
<h1>File_format</h1>
שים פה תמונה של מה יש ב File_format
This package is divided into 2 classes.
<list>
<li><b>CSVToMatrix: </b> This class is responsible to make Dynamic matrix[][]. </li>
<li><b>Game2KML: </b> This Class Game Object into KML file.</li>
</list>
<h1>Fruit <img src="./img/Fruit.png"></h1>
שים פה תמונה של מה יש ב Fruit
This Class represent Fruit that implements GIS_Fruit.
Every Fruit have FruitData which include relevant information about the Fruit
such that ID, Also Geom Element which represent the location of the Fruit in [Lat,Lon,Alt] coords.
Each Fruit was created through Runtime of <b>Game</b> Class.
<h1>Pacman <img src="./img/Pacman.png"></h1>
שים פה תמונה של מה יש ב Pacman
This Class represent Pacman that implements GIS_Pacman.
Every Pacman have PacmanData which include relevant information about the Pacman
such that ID,Speed,Radius,Color and Time.
<list><li><b>Note: </b>Time used to represent The total time of the walkman's walk in the chosen route by the algorithm.</li></list>
Also Geom Element which represent the location of the Fruit in [Lat,Lon,Alt] coords.
Each Pacman was created through Runtime of <b>Game</b> Class.


