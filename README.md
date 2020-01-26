# VisualMazeSolver (by Sebastiaan and Jeffrey)


## Assignment for Graphical applications in Java

This This project is part of the Graphical applications in Java class.
It is lectured by [Kris Aerts](https://github.com/krisaerts). The goal of this project
is to create a graphical and interactive application. 
A game is allowed but is not required. Simulations, procesvisualisations,... are perfectly allowed.

## Minimum requirements
<details>
<summary>Click to see the minimum requirements</summary>

* Interactive: The user has to be able to do something.

* Graphical elements: Visualisation or user drawn.

* JavaFX: User interface has to be built with JavaFX (No AWT or Swing).

* Model-View-Controller design: Has to be used, classes have to be documented (javadoc).

* Level of difficulty: Has to be adequate, not to easy, not to difficult.

</details>

## VisualMazeSolver

As the name suggests this is a visual maze solver. It shows how different algorithmes find (or do not find) their the end of the maze.

This application allows the user to select a start node and an end node. You can change the location of the start and end node by dragging 
then over to the desired spot with your mouse. After selecting these nodes it is possible to place solid nodes. 
These solid nodes are not traversable by the algorithmes. This enables the user to draw a maze to see how each algoritm solves
the task at hand. This task is reaching the end node.

When you press iterate it is possible to see the selected algorithm work its way to the endpoint. For a fast way of seeing the result, 
just press finish. It will do all the calculations and draw the path instead of showing each iteration.
 
<details>
<summary>Click to see tile selection</summary>

![Tiletypes](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/tileType.png)

</details>

<details>
<summary>Click to see start and end node in the view</summary>

![view1](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/View1.png)

</details>

## Different algorithmes

* Always right
	- Always tries to go to the right.
	- <details>
	  <summary>Click to see the "Always right" algorithm</summary>
	  ![AlwaysRight](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/AlwaysRight.gif)
	  </details>
* Drunk
	- Uses a randomized direction each iteration.
	- <details>
	  <summary>Click to see the "Drunk" algorithm</summary>
	  ![Drunk](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/Drunk.gif)
	  </details>	
* Turn clockwise
	- Always takes a clockwise turn if possible.
	- <details>
	  <summary>Click to see the "Turn Clockwise" algorithm</summary>
	  ![ClockWise](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/ClockWise.gif)
	  </details>		
* A Star
	- The star of them all, takes the shortest path possible.
	- <details>
	  <summary>Click to see the "A Star" algorithm</summary>
	  ![AStar](https://github.com/J3G0/VisualMazeSolver/blob/master/msc/AStar.gif)
	  </details>		

## Future work

* Maps; Load a maze from pre-defined mazes, save a drawn maze for later use.
* Synchronous execution; Split screen to see each algoritm execute simultaneously.
* UI improvements; E.G. make solid with left mouse button, make basic (delete solid) with right mouse button


## Authors

* *Kris Aerts*   	    - Lecturer  - [GitHub](https://github.com/krisaerts)
* *Jeffrey Gorissen*        - Student   - [GitHub](https://github.com/J3G0)
* *Sebastiaan Vanspauwen*   - Student   - [GitHub](https://github.com/SebastiaanVanspauwen)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments