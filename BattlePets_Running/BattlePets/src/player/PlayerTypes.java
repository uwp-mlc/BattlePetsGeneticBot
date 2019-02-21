package player;

import utils.Utils;

public enum PlayerTypes
{
	HUMAN,
	COMPUTER,
	JOHNS_GANG, // Team_01
	DYNAMIC_DEVELOPERS, // Team_02
	FND5A, // Team_03
	TEAMPERIWINKLE, // Team_04
	RIZATI, // Team_05
	ULTRON, // Team_06
	FURBIES_FIGHTERS, // Team_07
	TEAM_NAME, // Team_08
	DEVINS_CAT_IS_NAMED_JAVA, // Team_09
	TEAM10SMARTAI, //Team_10
	FIGHT_CLUB, // Team_11
	RHAPSODY, // Team_12
	ANOTHER_CUP_OF_JAVA, // Team_13
	RAWR_XD, // Team 14
	GOTTA_CATCH_EM_ALL, // Team_15
	RANGER_SQUAD, // Team_16
	WORK, // Team_17
	GIRAFFES, // Team_18
	PIONEER; // Team_19
	
	@Override
	public String toString()
	{		
		return Utils.convertEnumString(this.name());
	}
}
