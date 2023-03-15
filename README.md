# League of Horrors Client <!-- Header -->
League of Horrors is a open source 2D MOBA game. This repository contains the client code for the game. The client is responsible for rendering the game to the player and sending user input to the game server.

The client communicates with the game server, which can be found in this repository: [Java Game Server](https://github.com/hmongkey12/league-of-horrors-server) <!-- Link -->

It also communicates with the database server for the game, which can be found in this repository: [League of Horrors Database Server](https://github.com/hmongkey12/league-of-horrors-database) <!-- Link -->


## Getting Started <!-- Subheader -->
### Prerequisites <!-- Sub-subheader -->
To build and run the Java game client, you need to have the following software installed:

- Java Development Kit (JDK) version 8 or higher <!-- Unordered list -->
- Gradle build tool version 6.0 or higher <!-- Unordered list -->

### Building the Game Client <!-- Sub-subheader -->
To build the Java game client, run the following command in your terminal:


``` 
  gradlew build
  
```


This will compile the Java source code and generate an executable JAR file in the `build/libs` directory. <!-- Inline code -->

### Running the Game Client <!-- Sub-subheader -->
To run the Java game client, navigate to the `build/libs` directory and run the following command in your terminal: <!-- Inline code -->


```
  java -jar java-game-client.jar
``` 

This will start the game client and connect to the game server.

## Architecture <!-- Subheader -->
The Java game client follows a client-server architecture, where the client is a renderer and the server is the game state manager. The client sends user input to the server, and the server updates the game state and sends the updated state back to the client for rendering.

## Network Communication <!-- Subheader -->
The Java game client uses UDP for communication between the client and server over the network. UDP is a lightweight protocol that is well-suited for real-time games, as it has lower latency than TCP (Transmission Control Protocol).

## Rendering <!-- Subheader -->
The Java game client is responsible for rendering the game state received from the server. It uses Libgdx framework to draw the game objects and UI elements on the screen. The client will handle animations and other visual elements of the game.

## Contributing <!-- Subheader -->
If you would like to contribute to the Java game client, please submit a pull request with your changes. Before submitting a pull request, please make sure to run the tests and verify that they pass.

## License <!-- Subheader -->
The Java game client is open-source software released under the MIT License. See the `LICENSE` file for details. <!-- Inline code -->
