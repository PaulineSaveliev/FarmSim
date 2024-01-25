# FarmSim
ASCII Farming Simulator

This is a simple ASCII farming simulator, built in Java Eclipse in 2022. It contains a simple storyline and 
is played via command line input. 

Gameplay:
The game is played by running the main method of the "test" class. The game is drawn and commands are issued via the console. Type anything and press enter to interact with the game, but only valid commands will be accepted. Valid commands are given, and the game will ignore all invalid commands. If there is game text and no valid commands are provided, any input is accepted. The command "stop" is never listed, but can be used to stop the game at any point.

Structure:
All related game files are in the src folder and default package. The 'test' class contains the main method used to run the game, and initializes all components of the gameplay. 
The 'FarmingGame' class contains the game logic. It is initialized with start-of-game stats (money, coins, etc), and the startSim method contains the loop where the game actually runs. A helper method is called based on player commands, which handle the behavior in question (opening shop, planting a seed, etc).
The 'Field' class is blank as it was intended for future encapsulation, which was not done.
The 'Plant' class represents a generic crop, which abstracts all crop behavior. 
All other classes represent crops that are growable in the game. All have their own growth pattern, cost, and value. Plants also have a boolean "bush" value, representing whether the crop disappears when harvested or resets to an earlier growth stage.
Note: 'Mushroom' exhibits a unique growth pattern, so that if it is not harvested when grown it will expand to all adjacent fields.
