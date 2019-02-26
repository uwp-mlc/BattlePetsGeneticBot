package team_mlc;

import java.util.Observable;

import player.PetInstance;
import python.PythonCommunicator;
import settings.PlayerInfo;
import skill.Skills;

public class DavePetInstance extends PetInstance {
	
	private PythonCommunicator pyCom;
	
	public DavePetInstance(PlayerInfo playerInfo)
	{
		super(playerInfo);
		
	}

	@Override
	public Skills chooseSkill() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		
	}
}
