package edu.dselent.control;

import edu.dselent.io.IoManager;
import edu.dselent.config.ApplicationConfiguration;
import edu.dselent.io.Inputtable;
import edu.dselent.io.Outputtable;
import edu.dselent.settings.GameSettings;
import edu.dselent.utils.StringConstants;
import edu.dselent.utils.StringConstants.StringKeys;

// TODO missing some input validation for invalid ranges
public class GameSettingsControl
{
	private IoManager ioManager;
	private Outputtable output;
	private Inputtable input;
	
	private GameSettings gameSettings;
	
	public GameSettingsControl()
	{		
		ioManager = ApplicationConfiguration.INSTANCE.getIoManager();
		output = ioManager.getOutputSender();
		input = ioManager.getInputGetter();
	}

	public GameSettings getGameSettings()
	{
		return gameSettings;
	}

	/**
	 * Facade for simple call to get all game settings
	 * 
	 * @return All necessary settings to run the game
	 */
	public GameSettings retrieveGameSettings()
	{
		long randonSeed = retrieveRandomSeed();
		int numberOfPlayers = retrieveNumberOfPlayers();
		int numberOfFights = retrieveNumberOfFights();
	
		gameSettings = new GameSettings(randonSeed, numberOfPlayers, numberOfFights);

		return gameSettings;
	}
	
	private Long retrieveRandomSeed()
	{		
		Long randomSeed = null;
		
		while(randomSeed == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_RANDOM_SEED_KEY);
			output.outputString(outputMessage);
			
			String randomSeedString = input.getString();
			
			try
			{
				randomSeed = Long.parseLong(randomSeedString);
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_FIGHTS_KEY, randomSeedString);
				output.outputString(errorMessage);
			}
		}
		
		return randomSeed;
	}
	
	private Integer retrieveNumberOfPlayers()
	{	
		Integer numberOfPlayers = null;
		
		while(numberOfPlayers == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_NUMBER_OF_PLAYERS_KEY);
			output.outputString(outputMessage);
			
			String numberOfPlayersString = input.getString();
			
			try
			{
				numberOfPlayers = Integer.parseInt(numberOfPlayersString);
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_PLAYERS_KEY, numberOfPlayersString);
				output.outputString(errorMessage);
			}
		}
		
		return numberOfPlayers;
	}

	private Integer retrieveNumberOfFights()
	{	
		Integer numberOfFights = null;
		
		while(numberOfFights == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_NUMBER_OF_FIGHTS_KEY);
			output.outputString(outputMessage);
			
			String numberOfFightsString = input.getString();
			
			try
			{
				numberOfFights = Integer.parseInt(numberOfFightsString);
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_NUMBER_OF_FIGHTS_KEY, numberOfFightsString);
				output.outputString(errorMessage);
			}
		}
		
		return numberOfFights;
	}
	
}
