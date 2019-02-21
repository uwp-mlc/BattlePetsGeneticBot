package battlepets;

import java.util.List;

import config.ApplicationConfiguration;
import config.StupidObservable;
import control.TextBattleControl;
import control.GameSettingsControl;
import control.PlayerSettingsControl;
import io.TextInputGetter;
import io.TextOutputSender;
import player.Playable;
import player.PlayableInstantiator;
import io.Inputtable;
import io.IoManager;
import io.Outputtable;
import settings.GameSettings;
import settings.PlayerInfo;

public class TextGameRunner implements GameRunner
{
	@Override
	public void runGame()
	{
		Inputtable textInputtable = new TextInputGetter();
		Outputtable textOutputtable = new TextOutputSender();
		IoManager textIoManager = new IoManager(textInputtable, textOutputtable);
		
		// TODO
		// Think more about if this should really be a Singleton
		ApplicationConfiguration.INSTANCE.setIoManager(textIoManager);
		
		GameSettingsControl gameSettingsControl = new GameSettingsControl();
		GameSettings gameSettings = gameSettingsControl.retrieveGameSettings();
	
		PlayerSettingsControl playerSettingsControl = new PlayerSettingsControl(gameSettings);
		List<PlayerInfo> playerInfoList = playerSettingsControl.retrievePlayerInfoList();
		
		List<Playable> playableList = PlayableInstantiator.instantiatePlayables(playerInfoList);

		StupidObservable observable = new StupidObservable();
		ApplicationConfiguration.INSTANCE.setObservable(observable);
		TextBattleControl battleControl = new TextBattleControl(gameSettings, playableList);
		
		battleControl.runBattle();
	}
	
}
