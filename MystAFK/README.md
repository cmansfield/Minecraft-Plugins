## mystAFK (Freezing) - plugin
### Created by AirsoftingFox
### Created for http://mystserver.enjin.com/

### *** Requirements ***
	Event Cancelling:
		Recognize when someone is AFK
			When an AFK player is messaged, the player who sent the message
			will receive a personal message telling them they are AFK
			
			Have some visual representation that a player is AFK
		Tablist
			Change the player's prefix to indicate they are AFK
			Respect player rank - matches the prefix color
			
		The player who is AFK:
			Gravity doesn't affect them
			Don't change their position in the world
			Can't be killed by mobs or other players
			Player should be frozen 
				cannot drop stuff
				cannot pick stuff up
				Cannot interact with the world
				Cannot fish
				cannot move
				Water shouldn't move them
				Pistons shouldn't be able to move them
				cannot send messages
				Prevent other players from moving an AFK player
					fishing rods can do this
					pushing players
					removing blocks around the player
				The only thing they can do is go un-AFK
				
		System will automatically place a player into AFK
			If a player is inactive for 10min then a captcha will pop-up
				Have a screen pop-up and prompt the player
					Random button location for prompt to prevent the macro
					Green glass pane?
				If no interaction, then automatically place the player in AFK
		
			Rather than a captcha pop-up use an in-chat AFK check
				after about 2 hours have an AFK check
					have a few chat warnings
					In chat prompt for AFK
						Include a warning in the Minecraft action bar
						Have a random prompt "click-able" location within a string for
						the user to click to indicate the user isn’t AFK
					Check to see if their health has been impacted, don't make them go AFK
					Place the Player into AFK if they don't do anything for 5min
					Include a sound prompt
		
		onDisable:
			Me sure to remove all players from AFK before closing 
		
		Commands:
			/AFK
			
		Permissions:
			bypass
			reload
		
		Remove entity tracking from mobs to prevent AFK players from being surrounded
			
				
	Include a message.yml file to allow the server owner to change the messaging of the plugin

<hr />

<p><strong>Milestones:</strong></p>
<ol>
<li>[x] Set up Git Repo for plugin</li>
<li>[x] Add plugin commands</li>
<li>[x] Toggle player AFK</li>
<li>[x] Only allow commands to be executed by players</li>
<li>[x] Notify all players that a given player is AFK</li>
<li>[x] Add AFK player to an AFK list</li>
<li>[x] Update tab-list to show they are AFK</li>
<li>[x] Update chat to show they are AFK</li>
<li>[x] Add color to AFK prefix</li>
<li>[x] Check to ensure the player has the right permissions for commands</li>
<li>[x] Prevent AFK players from sending messages</li>
<li>[x] Prevent messages to AFK players</li>
<li>[x] Prevent AFK players from sending personal messages</li>
<li>[x] Prevent personal messages to AFK players</li>
<li>[x] Block all commands from afk players except for AFK</li>
<li>[x] Refactor the AFKplayers into their own class</li>
<li>[x] Create armor stand with player's head</li>
<li>[x] Remove players from the AFK list when they log-off
<li>[x] Add an AFK tag to the player's NameTag</li>
<li>[x] Prevent World interaction with player</li>
<li>[x] Prevent Player interaction with world</li>
<li>[x] Prevent Player interaction with player</li>
<li>[x] Permissions</li>
<li>[x] Remove the ability to remove armor from armor stand</li>
<li>[x] Add AFK timer to the server</li>
<li>[x] Automatically add players to AFK if timed out</li>
<li>[x] Fix move listener
<li>[x] Add listener for flying
<li>[x] Create a /NoAFK command that only chat click events can use</li>
<li>[x] Create a randomly generated key that only the plugin knows for /NoAFK command use</li>
<li>[x] Send players a clickable link in chat to indicate they are not AFK</li>
<li>[x] Reset AFK timer when the player clicks the chat link provided</li>
<li>[x] Complete the bypassAFK permission</li>
<li>[x] Add a config.yml file</li>
<li>[x] Add sound to notify players they are about to go AFK</li>
<li>[x] Add and actionbar to visual show players they are about to go AFK</li>
<li>[x] Add message customization to the config.yml file</li>
<li>[x] auto kick a player who has been AFK for too long</li>
<li>[x] Plugin documentation</li>
<li>[x] Prevent AFK players from using the AFK teleport commands</li>
<li>[ ] Be able to specify the number of actionbar warnings</li>
<li>[ ] Actionbar messages also print the number of minutes until action</li>
<li>[ ] Change sound from world sound to personal sound</li>
<li>[ ] Actionbar message telling AFK players they are AFK</li>
<li>[ ] Add a list of commands in config.yml when a player is kicked</li>
<li>[ ] Prevent players from moving AFK players with pistons
<li>[ ] Add config settings for sounds</li>
<li>[ ] Check to see if spectator mode can be set to 3rd person</li>
<li>[ ] Discuss adding a AFK command whitelist</li>
</ol>