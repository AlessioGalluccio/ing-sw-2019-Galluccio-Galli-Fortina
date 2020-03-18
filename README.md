# Adrenalina

This project was developed as part of the Software Engineering course at Politecnico di Milano in 2019.  
The aim of the project is to implement the board game _Adrenalina_ in Java language, using the OOP and Software Engineering principles.

Collaborators (in alphabetical order):

- [Fortina Valeria](https://github.com/valeriafortina "Valeria's GitHub")
- [Galli Davide](https://github.com/dade145 "Davide's GitHub")
- [Galluccio Alessio](https://github.com/AlessioGalluccio "Alessio's GitHub")

## Structure

The repository is organized as follow:  

- **Deliveries**:  
This folder contains files requested by the university laboratory. They are divided into subforforlder based on the date of the delivery.  
Those files consist of UML diagrams, the screenshot of analysis made by SonareQube, JAR, JavaDocs and other documentation files.
- **src**:  
This folder contains the Java code and some tests.

## Specs

The project consists of developing a board game such that it is possible to play with multi-players and multi-match.
This requires the implementation of a distributed system consisting of a single server containing the game logic and multiple clients (one per player) that can participate in only one game at a time.  
The client-server connection must be developed both via RMI and socket, and the player must be able to choose which type of connection to use when logging in.

The client-side UI must be developed both as CLI and GUI (Swing or JavaFX) and, again, the player must be able to choose which UI to use.

The MVC pattern is required for the whole game structure.

> For more details see the [Specification Document](https://github.com/AlessioGalluccio/ing-sw-2019-Galluccio-Galli-Fortina/blob/master/Specification%20Document%20(Italian).pdf) file (in Italian)
