package edu.dselent.player;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import java.util.Set;

import edu.dselent.skill.Skills;

public interface Playable extends Observer
{
	// HACK ALERT
	// TODO add get skill set
	// TODO add starting HP
	//Set<Skills> getSkillSet();
	//double getStartingHp();
	
	default Set<Skills> getSkillSet()
	{
		List<Skills> skillList = Arrays.asList(Skills.values());
		Set<Skills> skillSet = new HashSet<>(skillList);
        return skillSet;
    }
	
	default double getStartingHp()
	{
        return 100;
    }
		

	
	///////////////////////////////////////////
	
	
	String getPlayerName();
	String getPetName();
	PlayerTypes getPlayerType();
	PetTypes getPetType();
	double getCurrentHp();
	Skills chooseSkill();
	void updateHp(double hp);
	void resetHp(); // TODO may not need this method in interface?
	void setCurrentHp(double currentHp);
	boolean isAwake();
	Skills getSkillPrediction();
	int getSkillRechargeTime(Skills skill);
	double calculateHpPercent();
	void reset();
	void decrementRechargeTimes();
	void setRechargeTime(Skills skill, int rechargeTime);

	
	////////////////////////////////////////////////////////////////////
	
	
}
