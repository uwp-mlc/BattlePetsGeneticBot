package edu.dselent.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.dselent.io.IoManager;
import edu.dselent.config.ApplicationConfiguration;
import edu.dselent.io.Inputtable;
import edu.dselent.io.Outputtable;
import edu.dselent.player.PetTypes;
import edu.dselent.player.PlayerTypes;
import edu.dselent.settings.GameSettings;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.settings.PlayerInfo.PlayerInfoBuilder;
import edu.dselent.skill.Skills;
import edu.dselent.utils.StringConstants;
import edu.dselent.utils.StringConstants.StringKeys;

// TODO missing some input validation for invalid ranges
public class PlayerSettingsControl
{
	private IoManager ioManager;
	private Outputtable output;
	private Inputtable input;
	
	private GameSettings gameSettings;
	private List<PlayerInfo> playerInfoList;
	
	public PlayerSettingsControl(GameSettings gameSettings)
	{		
		this.ioManager = ApplicationConfiguration.INSTANCE.getIoManager();
		this.output = ioManager.getOutputSender();
		this.input = ioManager.getInputGetter();
		
		this.gameSettings = gameSettings;
		this.playerInfoList = new ArrayList<>();
	}

	public List<PlayerInfo> getPlayerInfoList()
	{
		return playerInfoList;
	}

	
	public List<PlayerInfo> retrievePlayerInfoList()
	{
		int numberOfPlayers = gameSettings.getNumberOfPlayers();
		
		List<PlayerInfo> playerInfoList = new ArrayList<>();
		
		for(int i=0; i<numberOfPlayers; i++)
		{
			PlayerInfoBuilder playerInfoBuilder = new PlayerInfoBuilder();
			
			PlayerTypes playerType = retrievePlayerType(i);
			PetTypes petType = retrievePetType(i);
			String playerName = retrievePlayerName(i);
			String petName = retrievePetName(playerName);
			double startingHp = retrieveStartingHp(petName);
			Set<Skills> skillSet = getSkillSet();
			
			playerInfoBuilder.withPlayerType(playerType);
			playerInfoBuilder.withPetType(petType);
			playerInfoBuilder.withPlayerName(playerName);
			playerInfoBuilder.withPetName(petName);
			playerInfoBuilder.withStartingHp(startingHp);
			playerInfoBuilder.withSkillSet(skillSet);
			
			PlayerInfo playerInfo = playerInfoBuilder.build();
			
			playerInfoList.add(playerInfo);
		}
		
		return playerInfoList;
	}

	private PlayerTypes retrievePlayerType(int playerIndex)
	{
		PlayerTypes playerType = null;
		int playerTypeInt = -1;
		
		while(playerType == null)
		{
			String outputMessage1 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_TYPE_KEY, playerIndex+1);
			output.outputString(outputMessage1);
			
			String outputMessage2 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_TYPE_CHOICES_KEY);
			output.outputString(outputMessage2);
				
			String playerTypeString = input.getString();
			
			try
			{
				playerTypeInt = Integer.parseInt(playerTypeString);
				playerType = PlayerTypes.values()[playerTypeInt-1];
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_PLAYER_TYPE_KEY, playerTypeString);
				output.outputString(errorMessage);
			}
		}
		
		return playerType;
	}
	
	private PetTypes retrievePetType(int playerIndex)
	{
		PetTypes petType = null;
		int petTypeInt = -1;
		
		while(petType == null)
		{
			
			String outputMessage1 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_TYPE_KEY, (playerIndex+1));
			output.outputString(outputMessage1);
			
			String outputMessage2 = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_TYPE_CHOICES_KEY);
			output.outputString(outputMessage2);
						
			String petTypeString = input.getString();
			
			try
			{
				petTypeInt = Integer.parseInt(petTypeString);
				petType = PetTypes.values()[petTypeInt-1];
			}
			catch(Exception e)
			{				
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_PET_TYPE_KEY, petTypeString);
				output.outputString(errorMessage);
			}
		}
		
		return petType;
	}
	
	private String retrievePlayerName(int playerIndex)
	{	
		String playerName = null;
		
		while(playerName == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PLAYER_NAME_KEY, (playerIndex+1));
			output.outputString(outputMessage);
			
			playerName = input.getString();
		}
		
		return playerName;
	}
	
	private String retrievePetName(String playerName)
	{	
		String petName = null;
		
		while(petName == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_PET_NAME_KEY, playerName);
			output.outputString(outputMessage);

			petName = input.getString();
		}
		
		return petName;
	}
	
	private Double retrieveStartingHp(String petName)
	{	
		Double startingHp = null;
		
		while(startingHp == null)
		{
			String outputMessage = StringConstants.getFormattedString(StringConstants.StringKeys.ENTER_STARTING_HP_KEY, petName);
			output.outputString(outputMessage);
			
			String startingHpString = input.getString();
			
			try
			{
				startingHp = Double.parseDouble(startingHpString);
			}
			catch(Exception e)
			{
				String errorMessage = StringConstants.getFormattedString(StringKeys.INVALID_STARTING_HP_KEY, startingHpString);
				output.outputString(errorMessage);
			}
		}
		
		return startingHp;
	}
	
	// TODO hard-coded skill choice for now
	// Future work to let users choose skills from the list of all possible skills
	public Set<Skills> getSkillSet()
	{
		Set<Skills> skillSet = new HashSet<>();
		
		skillSet.add(Skills.ROCK_THROW);
		skillSet.add(Skills.SCISSORS_POKE);
		skillSet.add(Skills.PAPER_CUT);
		skillSet.add(Skills.SHOOT_THE_MOON);
		skillSet.add(Skills.REVERSAL_OF_FORTUNE);
		
		return skillSet;
	}
}
