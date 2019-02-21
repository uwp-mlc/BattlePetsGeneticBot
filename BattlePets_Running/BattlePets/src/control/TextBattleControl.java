package control;

import java.util.List;

import config.ApplicationConfiguration;
import config.StupidObservable;
import domain.Battle;
import domain.Fight;
import domain.RngHolder;
import io.IoManager;
import player.Playable;
import settings.GameSettings;

public class TextBattleControl
{
	private TextFightControl textFightControl;
	private Battle battle;
	private RngHolder rngHolder;

	public TextBattleControl(GameSettings gameSettings, List<Playable> playableList)
	{
		textFightControl = new TextFightControl(this);
		battle = new Battle(gameSettings.getNumberOfFights(), playableList);
		
		rngHolder = new RngHolder(gameSettings.getRandomSeed());
		
		StupidObservable observable = ApplicationConfiguration.INSTANCE.getObservable();
		
		for(Playable playable : playableList)
		{
			observable.addObserver(playable);
		}
	}
	
	/**
	 * Playables do not have a reference to control classes therefore cannot obtain
	 * this rng by normal means.
	 * Need this to be public to share with the damage package
	 * @return
	 */
	public RngHolder getRngHolder()
	{
		return rngHolder;
	}
	
	public void runBattle()
	{
		List<Playable> playerList = battle.getPlayableList();
		
		IoManager ioManager = ApplicationConfiguration.INSTANCE.getIoManager();
		ioManager.getOutputSender().outputString("Battle Started");
		ioManager.getOutputSender().outputString("Number of Fights = " + battle.getNumberOfFights());
		ioManager.getOutputSender().outputString("\n");
		
		int numberOfFights = battle.getNumberOfFights();
		
		int[] winCounts = new int[playerList.size()];
		
		for(int i=0; i<numberOfFights; i++)
		{
			Fight fight = new Fight(battle, i);
			battle.getFightList().add(fight);
			textFightControl.runFight(fight);
			
			
			List<Playable> winnerList = fight.getWinnerList();
			
			for(Playable winner : winnerList)
			{
				int winnerIndex = playerList.indexOf(winner);
				winCounts[winnerIndex]++;
			}
			
			ioManager.getOutputSender().outputString("Win Counts ");

			for(int j=0; j<playerList.size(); j++)
			{
				Playable player = playerList.get(j);
				ioManager.getOutputSender().outputString(player.getPetName() + ": " + winCounts[j]);
			}
			
			ioManager.getOutputSender().outputString("\n");
		}
		
		ioManager.getOutputSender().outputString("\n");
		ioManager.getOutputSender().outputString("Battle Over!");
		ioManager.getOutputSender().outputString("\n");
		
		ioManager.getOutputSender().outputString("Winner(s)");
		
		int max = 0;
		for(int i=0; i<winCounts.length; i++)
		{
			if(winCounts[i] > max)
			{
				max = winCounts[i];
			}
		}
		
		for(int i=0; i<winCounts.length; i++)
		{
			if(winCounts[i] == max)
			{
				ioManager.getOutputSender().outputString(playerList.get(i).getPetName());
			}
		}
		
	}
}
