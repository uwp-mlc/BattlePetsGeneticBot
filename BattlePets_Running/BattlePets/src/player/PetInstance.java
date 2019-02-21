package player;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import settings.PlayerInfo;
import skill.Skill;
import skill.SkillRegistry;
import skill.Skills;
import skill.instances.PaperCutInstance;
import skill.instances.ReversalOfFortuneInstance;
import skill.instances.RockThrowInstance;
import skill.instances.ScissorsPokeInstance;
import skill.instances.ShootTheMoonInstance;
import skill.instances.SkillInstance;


public abstract class PetInstance implements Playable
{
	private Pet pet;
	private double maxHp;
	private double currentHp;
	private Map<Skills, SkillInstance> skillInstanceMap;
	
	public PetInstance(PlayerInfo playerInfo)
	{
		Player player = new Player(playerInfo.getPlayerName(), playerInfo.getPlayerType());
		pet = new Pet(player, playerInfo.getPetType(), playerInfo.getPetName());
		
		maxHp = playerInfo.getStartingHp();
		currentHp = maxHp;
		
		// Create skill map from skills
		skillInstanceMap = new EnumMap<>(Skills.class);
		List<Skill> allSkillList = SkillRegistry.INSTANCE.getSkillList();
		Set<Skills> skillSet = playerInfo.getSkillSet();
		
		// TODO better way to make skill instances? possibly not instantiating different classes based on enum
		for(Skills skill : skillSet)
		{
			Skill tempSkill = null;
			
			// Slightly inefficient
			for(Skill registrySkill : allSkillList)
			{
				if(registrySkill.getSkillEnum() == skill)
				{
					tempSkill = registrySkill;
				}
			}
			
			SkillInstance skillInstance;
			
			if(skill == Skills.ROCK_THROW)
			{
				skillInstance = new RockThrowInstance(tempSkill);
			}
			else if(skill == Skills.SCISSORS_POKE)
			{
				skillInstance = new ScissorsPokeInstance(tempSkill);
			}
			else if(skill == Skills.PAPER_CUT)
			{
				skillInstance = new PaperCutInstance(tempSkill);
			}
			else if(skill == Skills.SHOOT_THE_MOON)
			{
				skillInstance = new ShootTheMoonInstance(tempSkill);
			}
			else if(skill == Skills.REVERSAL_OF_FORTUNE)
			{
				skillInstance = new ReversalOfFortuneInstance(tempSkill);
			}
			else
			{
				// TODO custom exception, for invalid skill
				throw new RuntimeException("Invalid skill: " + skill);
			}
			
			skillInstanceMap.put(skill, skillInstance);
		}
	}
	
	@Override
	public String getPlayerName()
	{
		return pet.getPlayer().getName();
	}
	
	@Override
	public PlayerTypes getPlayerType()
	{
		return pet.getPlayer().getPlayerType();
	}
	
	@Override
	public PetTypes getPetType()
	{
		return pet.getPetType();
	}
	
	@Override
	public String getPetName()
	{
		return pet.getName();
	}

	@Override
	public void setCurrentHp(double currentHp)
	{
		this.currentHp = currentHp;
	}
	
	@Override
	public double getCurrentHp()
	{
		return currentHp;
	}
	
	@Override
	public void updateHp(double hp)
	{
		currentHp = currentHp - hp;
	}
	
	@Override
	public void setRechargeTime(Skills skill, int rechargeTime)
	{
		skillInstanceMap.get(skill).setCurrentRechargeTime(rechargeTime);
	}
	
	@Override
	public int getSkillRechargeTime(Skills skill)
	{
		return skillInstanceMap.get(skill).getCurrentRechargeTime();
	}
	
	@Override
	public double calculateHpPercent()
	{
		return currentHp / maxHp;
	}
	
	@Override
	public boolean isAwake()
	{
		return currentHp>0;
	}
	
	@Override
	public void reset()
	{
		resetHp();
		resetSkills();
	}

	// TODO remove from interface and make private?
	@Override
	public void resetHp()
	{
		currentHp = maxHp;
	}
	
	@Override
	public void decrementRechargeTimes()
	{
		skillInstanceMap.forEach((key, value) -> value.decrementRecharge());
	}
	
	@Override
	public Skills getSkillPrediction()
	{
		ShootTheMoonInstance stmInstance = (ShootTheMoonInstance)skillInstanceMap.get(Skills.SHOOT_THE_MOON);
		return stmInstance.getPredictedSkillEnum();
	}
	
	
	private void resetSkills()
	{
		skillInstanceMap.forEach((key, value) -> value.reset());
	}
	
	protected Map<Skills, SkillInstance> getSkillInstanceMap()
	{
		return skillInstanceMap;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("PetInstance [pet=");
		builder.append(pet);
		builder.append(", maxHp=");
		builder.append(maxHp);
		builder.append(", currentHp=");
		builder.append(currentHp);
		builder.append(", skillInstanceMap=");
		builder.append(skillInstanceMap);
		builder.append("]");
		
		return builder.toString();
	}
	
}
