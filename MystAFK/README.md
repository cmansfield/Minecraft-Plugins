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
				Cannot interact with the world
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
			/reload
			/bypass
			
		Permissions:
			bypass
			reload
		
		Remove entity tracking from mobs to prevent AFK players from being surrounded
			
				
	Include a message.yml file to allow the server owner to change the messaging of the plugin

<hr />

<p><strong>Milestones:</strong></p>
<ol>
<li>[x] Hello World</li>
<li>[x] Set up Git Repo for plugin</li>
<li>[x] Add plugin commands</li>
<li>[x] Toggle player AFK</li>
<li>[x] Only allow commands to be executed by players</li>
<li>[x] Notify all players that a given player is AFK</li>
<li>[ ] Add AFK player to an AFK list</li>
<li>[ ] Update tab-list to show they are AFK</li>
<li>[ ] Prevent messages to AFK players</li>
<li>[ ] Prevent World interaction with player</li>
<li>[ ] Prevent Player interaction with world</li>
<li>[ ] Prevent Player interaction with player</li>
<li>[ ] Add AFK timer to the server</li>
<li>[ ] Automatically add players to AFK if timed out</li>
<li>[ ] In chat warnings that the player is about to go AFK</li>
<li>[ ] Capcha</li>
<li>[ ] Permissions</li>
<li>[ ] Reload command</li>
<li>[ ] message.yml</li>
<li>[ ] Plugin documentation</li>
</ol>