<h1>OOP Ex3</h1>
<p>Created during a computer communication course during the second year at Ariel University in the Department of Computer Science, 2018 <br /> <strong>Project site:</strong> https://zvimints.github.io/OOP_2/ *********** <strong>N</strong>EED TO UPDATE <br /> <strong>Made by: </strong><a href="https://github.com/ZviMints">Zvi Mints</a> and <a href="https://github.com/orabu103">Or Abuhazira</a></p>
<h1>About The Project</h1>
<table style="height: 206px;" width="599">
<tbody>
<tr>
<td style="width: 290.667px;"><img src="./img/100Plz.jpg" alt="" width="25%" height="25%" /></td>
<td style="width: 292.667px;"><img src="./img/AfterRunning.jpg" alt="" width="25%" height="25%" /></td>
</tr>
</tbody>
</table>
<p><strong>Problem:</strong> Given a .csv file with pecans and fruits find a minimal optimal greedy path by the time such that the Pacmans will pass through all the fruits in the map This project represents a greedy solution to the <strong>problem.</strong></p>
<h1>Project Diagram:</h1>
<p>&lt;img src="./ClassDiagram.jpg"&gt;</p>
<p>&lt;br&gt;</p>
<h1>Class Hierarchy:</h1>
<p><img src="./Hierarchy.jpg" /></p>
<h1>Packages:</h1>
<p>שים פה תמונה של packages So Lets start!</p>
<h1>Coords</h1>
<p>שים פה תמונה של מה יש ב MyCoords כלומר אינטרפס This Class is responsible for actions between Objects of the kind Point3D. The Class is used to Provide a solution for elementary actions between vectors and points in R^3 Vector space.</p>
<h1>File_format</h1>
<p>שים פה תמונה של מה יש ב File_format This package is divided into 2 classes.</p>
<ul>
<li><strong>CSVToMatrix: </strong> This class is responsible to make Dynamic matrix[][].</li>
<li><strong>Game2KML: </strong> This Class Game Object into KML file.</li>
</ul>
<h1>Fruit <img src="./img/Fruit.png" /></h1>
<p>שים פה תמונה של מה יש ב Fruit This Class represent Fruit that implements GIS_Fruit. Every Fruit has FruitData which include relevant information about the Fruit such that ID, Also Geom Element which represent the location of the Fruit in [Lat, Lon, Alt] coords. Each Fruit was created through Runtime of <strong>Game</strong> Class.</p>
<h1>Pacman <img src="./img/Pacman.png" /></h1>
<p>שים פה תמונה של מה יש ב Pacman This Class represent Pacman that implements GIS_Pacman. Every Pacman has PacmanData which include relevant information about the Pacman such that ID, Speed, Radius, Color and Time.</p>
<ul>
<li><strong>Note: </strong>Time used to represent The total time of the Pacman's walk in the chosen route by the Algorithm.</li>
</ul>
<p>Also, Geom Element which represents the location of the Fruit in [Lat, Lon, Alt] coords. Each Pacman was created through Runtime of <strong>Game</strong> Class.</p>
<h1>Path</h1>
<ul>
<li><strong>Game2KML:&nbsp;</strong>This Class Converting Game into KML file.</li>
</ul>
<h1>Path2KML</h1>
<p>&nbsp;This package is responsible to convert from a game that has been runned&nbsp;by the algorithm and make a valid .KML file that show the path's of each Pacman</p>
<p><strong>Example:</strong></p>
<p><img src="./img/KML.jpg" alt="" /></p>
