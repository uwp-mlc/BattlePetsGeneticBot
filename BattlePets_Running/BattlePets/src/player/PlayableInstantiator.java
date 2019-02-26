package player;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import settings.PlayerInfo;
/*
import skill.Skills;
import edu.team01.JohnsGangPetInstance;
import edu.team02.DynamicDevelopers;
import edu.team02.PetSkill;
import edu.team03.FND5AInstance;
import edu.team04.DougTrioPetInstance;
import edu.team05.RiZaTi;
import edu.team06.Team06_ULTRON;
import edu.team07.JarvisPlayer;
import edu.team08.TeamNamePetInstance;
import edu.team09.AIDevinsCat;
import edu.team10.SmartAIPet;
import edu.team11.Winston;
import edu.team12.Rhapsody;
import edu.team14.RawrXdPlayer;
import edu.team15.GottaCatchEmAllInstance;
import edu.team16.RangerInstance;
import edu.team17.WorkAI;
import edu.team18.GiraffesAI;
import edu.team19.PioneerPetInstance;
*/
import team_mlc.DavePetInstance;

public class PlayableInstantiator
{
	public static List<Playable> instantiatePlayables(List<PlayerInfo> playerInfoList)
	{
		List<Playable> playableList = new ArrayList<>();
		
		for(PlayerInfo playerInfo : playerInfoList)
		{
			Playable playable = instantiatePlayable(playerInfo);
			playableList.add(playable);
		}
		
		return playableList;
	}
	
	public static Playable instantiatePlayable(PlayerInfo playerInfo)
	{
		Playable thePlayable = null;
		
		PlayerTypes playerType = playerInfo.getPlayerType();
		
		
		if(playerType == PlayerTypes.HUMAN)
		{
			thePlayable = new HumanPetInstance(playerInfo);
		}
		
		
		/*
		else if(playerType == PlayerTypes.JOHNS_GANG) // Team_01
		{
			thePlayable = new JohnsGangPetInstance(playerInfo);
		}
		else if(playerType == PlayerTypes.DYNAMIC_DEVELOPERS) // Team_02
		{
			Map<Skills,PetSkill> playerSkills = new HashMap<Skills, PetSkill>();
			for(Skills skill: Skills.values())
			{
				playerSkills.put(skill, new PetSkill(skill));
			}
			
			thePlayable = new DynamicDevelopers(playerInfo.getPlayerName(), playerSkills, 500);
		}
		else if(playerType == PlayerTypes.FND5A) // Team_03
		{
			thePlayable = new FND5AInstance(playerInfo);
		}
		else if(playerType == PlayerTypes.TEAMPERIWINKLE) // Team_04
		{
			thePlayable = new DougTrioPetInstance(playerInfo);
		}
		else if(playerType == PlayerTypes.RIZATI) // Team_05
		{
			thePlayable = new RiZaTi();
		}
		else if(playerType == PlayerTypes.ULTRON) // Team_06
		{
			thePlayable = new Team06_ULTRON(playerInfo.getPlayerName(), null);
		}
		else if (playerType == PlayerTypes.FURBIES_FIGHTERS)
		{
			thePlayable = new JarvisPlayer(playerInfo.getStartingHp(), playerInfo.getPlayerName(), playerInfo.getPetName(), PetTypes.POWER, playerInfo);
		}
		else if(playerType == PlayerTypes.TEAM_NAME) // Team_08
		{
			thePlayable = new TeamNamePetInstance(playerInfo);
		}
		else if(playerType == PlayerTypes.DEVINS_CAT_IS_NAMED_JAVA) // Team_09
		{
			thePlayable = new AIDevinsCat(playerInfo);
		}
		else if(playerType == PlayerTypes.TEAM10SMARTAI) // Team_10
		{
			String playerName = playerInfo.getPlayerName();
			String petName = playerInfo.getPetName();
			PetTypes petType = playerInfo.getPetType();
			thePlayable = new SmartAIPet(petType, playerName, petName);
		}
		else if(playerType == PlayerTypes.FIGHT_CLUB) // Team_11
		{
			thePlayable = new Winston(playerInfo.getStartingHp(), playerInfo.getPetName(), PetTypes.POWER);
		}
		else if(playerType == PlayerTypes.RHAPSODY) // Team_12
		{
			thePlayable = new Rhapsody(playerInfo.getPetName(),playerInfo.getStartingHp(), PetTypes.POWER, playerInfo.getPlayerType());
		}
		else if(playerType == PlayerTypes.ANOTHER_CUP_OF_JAVA) // Team_13
		{
			//
		}
		else if (playerType == PlayerTypes.RAWR_XD)
	    {
	      // thePlayable = new RawrXdPlayer(playerInfo, new AiBrain(playerInfo));
	    }
		else if(playerType == PlayerTypes.GOTTA_CATCH_EM_ALL) // Team_15
		{
			// have to do this if we want to override to always be Speed type.
			// can't change the Pet's type from our instance, as it's private in PetInstance
			// and therefore not inherited.
			PlayerInfo.PlayerInfoBuilder infoBuilder = new PlayerInfo.PlayerInfoBuilder();
			PlayerInfo speedInfo = infoBuilder.withPetName(playerInfo.getPetName())
					.withPetType(PetTypes.SPEED)
					.withPlayerName(playerInfo.getPlayerName())
					.withPlayerType(playerInfo.getPlayerType())
					.withSkillSet(playerInfo.getSkillSet())
					.withStartingHp(playerInfo.getStartingHp())
					.build();
			thePlayable = new GottaCatchEmAllInstance(speedInfo);
		}
		else if(playerType == PlayerTypes.RANGER_SQUAD) // Team_16
		{
			// Doug's default AI
			thePlayable = new RangerInstance(playerInfo);
		}
		else if(playerType == PlayerTypes.WORK) // Team_17
		{
			thePlayable = new WorkAI(playerInfo);
		}
		else if(playerType == PlayerTypes.GIRAFFES) // Team_18
		{
			thePlayable = new GiraffesAI(playerInfo);
		}
		else if(playerType == PlayerTypes.PIONEER) // Team_19
		{
			thePlayable = new PioneerPetInstance(playerInfo);
		}*/
		else if(playerType == PlayerTypes.COMPUTER)
		{
			// Doug's default AI
			thePlayable = new ComputerPetInstance(playerInfo);
		}
		
		else if(playerType == PlayerTypes.DAVE)
		{
			thePlayable = new DavePetInstance(playerInfo);
		}
		
		else
		{
			// TODO make custom exception
			throw new RuntimeException("Invalid playerType: " + playerType);
		}
		
		
		return thePlayable;
	}

}
