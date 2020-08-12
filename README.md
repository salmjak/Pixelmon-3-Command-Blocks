# Pixelmon <3 Command Blocks / Pixelmon Commandblock Utilities
#Pixelmon 8.0.2 Minecraft 1.12.2 Sponge API 7.2.0

Add scoreboard objectives for Pixelmon-related variables.

The scores are continously updated (10 players at a time every 500 ms (i.e. it would take 5000 ms (or 5 seconds) to update all of 100 players) and are meant to be accessed from command blocks, e.g. to open an iron door if a player has 8 badges or more.


Objective names (case sensitive):
- badges: The number of different pixelmon badges in a players inventory.
- money: The amount of pokedollars a player has.
- partySize: The number of pokemon in a players team.
- isFainted: Checks if all the pokemon in a players team are fainted (0 = false, 1 = true).
- avgLvl: The average level of a players team (sum of levels / 6, even if there is just one pokemon in the team).
- minLvl: The lowest level of the pokemon in a players team.
- maxLvl: The highest level of the pokemon in a players team.
- hasEgg: Checks if the player has an egg in its party (0 = false, 1 = true).
- knows[HM]: Checks if the player has a pokemon that knows HM Fly/Cut/Strength/Surf/Rock Smash (0 = false, 1 = true).
-caughtCount: The number of pokemon in the pokedex the player has caught.
-seenCount: The number of pokemon in the pokedex the player has seen.

How-to:
- Use like any other score with commands block. E.g. '/tellraw @p[score_minLvl_min=10] ["Hello World"]' to send the message "Hello World" to the nearest player with a team in which the lowest level is greater or equal to 10.

![Screenshot](http://i.imgur.com/09mS8gG.png)
