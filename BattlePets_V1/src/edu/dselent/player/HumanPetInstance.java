package edu.dselent.player;

import java.util.Observable;

import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;

public class HumanPetInstance extends PetInstance
{
	public HumanPetInstance(PlayerInfo playerInfo)
	{
		super(playerInfo);
	}

	@Override
	public Skills chooseSkill()
	{
		// TODO
		return null;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}
	
}
