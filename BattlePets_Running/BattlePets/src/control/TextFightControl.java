package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.ApplicationConfiguration;
import config.StupidObservable;
import domain.Fight;
import domain.PlayerRoundData;
import domain.RngHolder;
import domain.Round;
import event.FightStartEvent;
import event.FightStartEvent.FightStartEventBuilder;
import event.PlayerEventInfo;
import event.PlayerEventInfo.PlayerEventInfoBuilder;
import io.IoManager;
import player.Playable;

public class TextFightControl
{
	private TextBattleControl textBattleControl;
	private TextRoundControl textRoundControl;
	
	public TextFightControl(TextBattleControl textBattleControl)
	{
		this.textBattleControl = textBattleControl;
		textRoundControl = new TextRoundControl(this);
	}
	
	public void runFight(Fight fight)
	{
		List<Playable> playerList = fight.getBattle().getPlayableList();
		
		for(Playable currentPlayable : playerList)
		{
			currentPlayable.reset();
		}
		
		IoManager ioManager = ApplicationConfiguration.INSTANCE.getIoManager();
		ioManager.getOutputSender().outputString("Fight " + (fight.getFightIndex()+1) + " Started");
		ioManager.getOutputSender().outputString("\n");
		ioManager.getOutputSender().outputString("Players");
		ioManager.getOutputSender().outputString("\n");
				
		for(int i=0; i<playerList.size(); i++)
		{
			Playable currentPlayer = playerList.get(i);
			ioManager.getOutputSender().outputString("Player " + (i+1));
			ioManager.getOutputSender().outputString("Player Name: " + currentPlayer.getPlayerName());
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Starting HP: " + currentPlayer.getStartingHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		StupidObservable observable = ApplicationConfiguration.INSTANCE.getObservable();
		
		List<PlayerEventInfo> playerEventInfoList = new ArrayList<>();
		
		for(Playable currentPlayable : playerList)
		{
			PlayerEventInfoBuilder peib = new PlayerEventInfoBuilder();
			
			peib.withPetName(currentPlayable.getPetName());
			peib.withPetType(currentPlayable.getPetType());
			peib.withPlayerType(currentPlayable.getPlayerType());
			peib.withSkillSet(currentPlayable.getSkillSet());
			peib.withStartingHp(currentPlayable.getCurrentHp());
			
			playerEventInfoList.add(peib.build());
		}
		
		FightStartEventBuilder fseb = new FightStartEventBuilder();
		fseb.withPlayerEventInfo(playerEventInfoList);
		fseb.withFightNumber(fight.getFightIndex());
		FightStartEvent fightStartEvent = fseb.Build();
		
		observable.fireEvent(fightStartEvent);
		
		int roundIndex = 0;
		while(!isFightOver(fight))
		{
			Round round = new Round(fight, roundIndex);
			fight.getRoundList().add(round);
			textRoundControl.runRound(round);
		
			roundIndex++;
		}
		
		ioManager.getOutputSender().outputString("Pets");
		ioManager.getOutputSender().outputString("\n");
		
		for(int i=0; i<playerList.size(); i++)
		{
			Playable currentPlayer = playerList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		
		ioManager.getOutputSender().outputString("Sorted by HP");
		ioManager.getOutputSender().outputString("\n");
		
		List<Playable> sortedPlayableList = new ArrayList<>(playerList);
		Collections.sort(sortedPlayableList, (p1, p2) -> Double.valueOf(p2.getCurrentHp()).compareTo(p1.getCurrentHp()));
		
		for(int i=0; i<sortedPlayableList.size(); i++)
		{
			Playable currentPlayer = sortedPlayableList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		List<Playable> winnerList = calculateWinnerList(fight);
		
		ioManager.getOutputSender().outputString("Fight " + (fight.getFightIndex()+1) + " Over");
		ioManager.getOutputSender().outputString("Fight Winner(s)");
		
		for(Playable playable : winnerList)
		{
			ioManager.getOutputSender().outputString(playable.getPetName());
			fight.setWinnerList(winnerList);
		}
		
		ioManager.getOutputSender().outputString("\n");		
	}
	

	public RngHolder getRngHolder()
	{
		return textBattleControl.getRngHolder();
	}
	
	private boolean isFightOver(Fight fight)
	{
		int awakeCount = 0;
		List<Playable> playerList = fight.getBattle().getPlayableList();
		
		for(int i=0; i<playerList.size(); i++)
		{
			Playable player = playerList.get(i);
			
			if(player.isAwake())
			{
				awakeCount++;
			}
		}
		
		return awakeCount<=1;
	}
	
	/**
	 * Assumes fight is over
	 * 
	 * @param round
	 * @return
	 */
	private List<Playable> calculateWinnerList(Fight fight)
	{
		List<Playable> playerList = fight.getBattle().getPlayableList();
		List<Playable> winnerList = new ArrayList<>();
		List<Round> roundList = fight.getRoundList();
		List<PlayerRoundData> lastRoundDataList = roundList.get(roundList.size()-1).getPlayerRoundDataList();
		
		// extract pets who were awake on the last turn
		// sort by their current hp
		
		for(int i=0; i<lastRoundDataList.size(); i++)
		{
			PlayerRoundData lastRoundData = lastRoundDataList.get(i);
			
			// Were awake at the start of last turn
			if(!lastRoundData.isSleeping())
			{
				winnerList.add(playerList.get(i));
			}
		}
		
		Collections.sort(winnerList, (p1, p2)->Double.valueOf(p2.getCurrentHp()).compareTo(p1.getCurrentHp()));
		
		for(int i=1; i<winnerList.size(); i++)
		{
			if(winnerList.get(i).getCurrentHp() < winnerList.get(i-1).getCurrentHp())
			{
				winnerList.remove(i);
				i--;
			}
		}
		
		return winnerList;
	}
}
