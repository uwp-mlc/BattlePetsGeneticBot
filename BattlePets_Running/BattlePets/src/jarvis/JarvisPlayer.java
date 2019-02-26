package jarvis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import event.AttackEvent;
import event.FightStartEvent;
import event.PlayerEventInfo;
import event.RoundStartEvent;
import player.PetInstance;
import player.PetTypes;
import settings.PlayerInfo;
import skill.Skills;

/**
 * @authors Furbies Fighters
 *
 */
public class JarvisPlayer extends PetInstance {
	protected Map<Skills, Integer> rechargingOpponentSkills, jarvisRechargingSkills;
	private int roundNumber, fightNumber, opponentIndex, jarvisIndex;
	private String opponentName;
	private PetTypes opponentType;
	private Map<Integer, Double> randomDifference;
	private double opponentHealth;
	private Random random;
	private int roundsSinceUse;
	private double maxROFDiff;
	private Skills predictedSkill;//Override from the Player... We want to use this one. 
	
	
	public JarvisPlayer(double initialHP, String name, String petName, PetTypes petType, PlayerInfo p) {
		super(p);
		
		this.randomDifference = new HashMap<Integer, Double>();
		this.rechargingOpponentSkills = new HashMap<Skills, Integer>();
		this.rechargingOpponentSkills.put(Skills.ROCK_THROW, 0);
		this.rechargingOpponentSkills.put(Skills.SCISSORS_POKE, 0);
		this.rechargingOpponentSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingOpponentSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingOpponentSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		this.random  = new Random(10320l);
		roundsSinceUse = 0;
		maxROFDiff = (-1) * Double.MAX_VALUE;
	}
	
	/**
	 * Choose a skill 
	 */
	@Override
	public Skills chooseSkill()
	{
		return learnSkill();
	}

	/**
	 * Return the best skill after trying them all. 
	 * @return
	 */
	private Skills learnSkill()
	{
		Map<Skills, Double> outputDamageMap;
		Map<Skills, Double> inputDamageMap;
		Map<Skills, Double> differenceMap;
		Skills skill;
		
		outputDamageMap = populateOutputMap();
		skill = getBestSkillGivenDifferences(outputDamageMap);

		return skill;
	}
	
	/**
	 * Optimizes for the best skill preferences
	 * @return
	 */
	private Map<Skills, Double> populateOutputMap()
	{
		double rofDiff = 0.0;
		Map<Skills, Double> outputDamageMap = new HashMap<Skills, Double>();
		if(super.getSkillRechargeTime(Skills.ROCK_THROW) == 0)
		{			
			if(this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.PAPER_CUT) != 0))
			{
				outputDamageMap.put(Skills.ROCK_THROW, (5.0 * 3.0) - this.rockConsequence());
			}
			else
				outputDamageMap.put(Skills.ROCK_THROW, -1 * this.rockConsequence());
		}
		
		if(super.getSkillRechargeTime(Skills.SCISSORS_POKE) == 0)
		{
			if(this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.ROCK_THROW) != 0))
			{
				outputDamageMap.put(Skills.SCISSORS_POKE, (5.0 * 3.0) - this.scissorConsequence());
			}
			else
				outputDamageMap.put(Skills.SCISSORS_POKE, -1 * this.scissorConsequence());
		}
		
		if(super.getSkillRechargeTime(Skills.PAPER_CUT) == 0)
		{
			if(this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0 
					&& (this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) != 0
					|| this.rechargingOpponentSkills.get(Skills.PAPER_CUT) != 0))
			{
				outputDamageMap.put(Skills.PAPER_CUT, (5.0 * 3.0) - this.paperConsequence());
			}
			else
				outputDamageMap.put(Skills.PAPER_CUT, -1 * this.paperConsequence());
		}
		
		if (this.getSkillRechargeTime(Skills.SHOOT_THE_MOON) == 0 &&
				this.getOpponentSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) > 0 && 
				this.getOpponentSkillRechargeTime(Skills.SHOOT_THE_MOON) > 0 &&
				   (this.getOpponentSkillRechargeTime(Skills.ROCK_THROW) > 0 ||
					this.getOpponentSkillRechargeTime(Skills.SCISSORS_POKE) > 0 ||
					this.getOpponentSkillRechargeTime(Skills.PAPER_CUT) > 0)  &&
				   this.opponentType != PetTypes.INTELLIGENCE)
		{
			outputDamageMap.put(Skills.SHOOT_THE_MOON, Double.MAX_VALUE);
			
			ArrayList<Skills> skills;
			Skills skill;
			
			skills = new ArrayList<Skills>();
			skill = null;
			
			if(this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			{
				skills.add(Skills.ROCK_THROW);
			}
			if (this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) == 0) 
			{
				skills.add(Skills.SCISSORS_POKE);
			}
			if (this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0) 
			{
				skills.add(Skills.PAPER_CUT);
			}
			
			int index = random.nextInt(skills.size());
			
			skill = skills.get(index);
			
			this.predictedSkill = skill;	
		}
		
		rofDiff = randomDifference.get(this.jarvisIndex);
		
		if (this.maxROFDiff < rofDiff)
		{
			maxROFDiff = rofDiff;
		}
		
		if (this.getSkillRechargeTime(Skills.REVERSAL_OF_FORTUNE) == 0 &&
				   this.opponentType != PetTypes.INTELLIGENCE)
		{
			if (this.roundsSinceUse > 10 && rofDiff > 0.0 && rofDiff > (maxROFDiff * .5))
				outputDamageMap.put(Skills.REVERSAL_OF_FORTUNE, Double.MAX_VALUE);
		}
		
		return outputDamageMap;
	}
	
	/**
	 * We want to return the current predicted skill located
	 * in Jarvis, so we override this method to return our
	 * correct predicted skill. 
	 */
	@Override
	public Skills getSkillPrediction()
	{
		return this.predictedSkill;
	}
	
	/**
	 * Find possible damage when using rock
	 * @return
	 */
	private double rockConsequence()
	{
		double healthPercent = super.getCurrentHp();
		if(this.opponentType == PetTypes.SPEED && healthPercent < .75 && healthPercent >= .25)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent < .25)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 4.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 6.0;
		else 
			return 0.0;
	}
	
	/**
	 * Calculate possible consequences of using the paper skill.
	 * @return
	 */
	private double paperConsequence()
	{
		double healthPercent = this.calculateHpPercent();
		if (this.opponentType == PetTypes.SPEED && healthPercent >= .75)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent >= .25 && healthPercent < .75)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) == 0)
			return 6.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.PAPER_CUT) == 0)
			return 4.0;
		else
			return 0.0;
	}
	
	/**
	 * Calculate possible consequences of using the scissor skill.
	 * @return
	 */
	private double scissorConsequence()
	{
		double healthPercent = super.calculateHpPercent();
		if (this.opponentType == PetTypes.SPEED && healthPercent >= .75)
			return 10.0;
		else if (this.opponentType == PetTypes.SPEED && healthPercent < .25)
			return 10.0;
		else if (this.opponentType == PetTypes.POWER && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 20.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.ROCK_THROW) == 0)
			return 6.0;
		else if(this.opponentType == PetTypes.INTELLIGENCE && this.rechargingOpponentSkills.get(Skills.SCISSORS_POKE) == 0)
			return 4.0;
		else
			return 0.0;
	}
	
	/**
	 * Returns the best skill in the map.
	 * @param differenceMap
	 * @return
	 */
	private Skills getBestSkillGivenDifferences(Map<Skills, Double> differenceMap)
	{
		Skills skill = null;
		double bestDifference = (-1) * Double.MAX_VALUE;
		
		for (Map.Entry<Skills, Double> pair : differenceMap.entrySet())
		{
			if (pair.getValue() > bestDifference && this.getSkillRechargeTime(pair.getKey()) == 0)
			{
				bestDifference = pair.getValue();
				skill = pair.getKey();
			}
		}
		
		return skill;
	}
	
	/**
	 * return the random integer generated
	 */
	private int randomSkillRNG()
	{
		return (int)Math.ceil(random.nextFloat() * 3);
	}
	
	/**
	 * Gets teh recharge time of a skill for the opponent. 
	 * @param skill
	 * @return
	 */
	public int getOpponentSkillRechargeTime(Skills skill)
	{
		return this.rechargingOpponentSkills.get(skill);
	}
	
	/**
	 * The method for updating the observable.
	 */
	@Override
	public void update(Observable arg0, Object event) 
	{
		if(event instanceof FightStartEvent)
		{
			FightStartEvent fse = (FightStartEvent) event;
			rememberFightStartEvent(fse);
		}
		if(event instanceof RoundStartEvent)
		{
			RoundStartEvent rse = (RoundStartEvent) event;
			rememberRoundStartEvent(rse);
		}         
		if(event instanceof AttackEvent)
		{
			AttackEvent ae = (AttackEvent) event;
			this.rememberAttackEvent(ae);
		}
	}
	
	/**
	 * Remember the fight start information 
	 * @param event
	 */
	private void rememberFightStartEvent(FightStartEvent event) {
		this.fightNumber = event.getFightNumber();
		List<PlayerEventInfo> playerEventInfo = event.getPlayerEventInfo();
		this.rechargingOpponentSkills.forEach((key,value) -> value = 0);
		
		//Get Opponent information (index and name)
		for(int i = 0; i < playerEventInfo.size(); i++)
		{
			PlayerEventInfo playerInfo = playerEventInfo.get(i);
			this.randomDifference.put(i, 0.0);
			if(playerInfo.getPetName().equals(super.getPetName()))
			{
				this.jarvisIndex = i;
				this.opponentIndex = (i + 1) % playerEventInfo.size();
				this.opponentHealth = playerEventInfo.get((i+1) % playerEventInfo.size()).getStartingHp();
				this.opponentName = playerEventInfo.get(this.opponentIndex).getPetName();
				this.opponentType = playerEventInfo.get(this.opponentIndex).getPetType();
				this.randomDifference.put(i, 0.0);
			}
		}
	}

	/**
	 * Remember all information for a round start event. 
	 * @param event
	 */
	private void rememberRoundStartEvent(RoundStartEvent event){
		this.roundNumber = event.getRoundNumber();
	}
	
	/**
	 * Remember an attack event information. 
	 * @param event
	 */
	private void rememberAttackEvent(AttackEvent event)
	{
		if(event.getAttackingPlayableIndex() == opponentIndex)
		{
			int rechargeTime = (event.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE || event.getAttackingSkillChoice() == Skills.SHOOT_THE_MOON) ? 6 : 1;
			this.decrementOpponentRechargeTimes();
			this.rechargingOpponentSkills.put(event.getAttackingSkillChoice(), rechargeTime);
			
			this.randomDifference.put(event.getAttackingPlayableIndex(), this.randomDifference.get(event.getAttackingPlayableIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableIndex(), this.randomDifference.get(event.getVictimPlayableIndex()) + event.getRandomDamage());
			this.roundsSinceUse++;
		}
		else if(event.getAttackingPlayableIndex() == jarvisIndex)
		{
			this.opponentHealth -= (event.getConditionalDamage() + event.getRandomDamage());
			
			this.randomDifference.put(event.getAttackingPlayableIndex(), this.randomDifference.get(event.getAttackingPlayableIndex()) - event.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableIndex(), this.randomDifference.get(event.getVictimPlayableIndex()) + event.getRandomDamage());
		
			Skills s = event.getAttackingSkillChoice();
		}
	}
	
	/**
	 * Decrement the recharging times for all opponent skills. 
	 */
	private void decrementOpponentRechargeTimes()
	{
		for (Map.Entry<Skills, Integer> pair : rechargingOpponentSkills.entrySet())
		{
			int val = pair.getValue();
			Skills s = pair.getKey();
			
			if (val > 0)
			{
				rechargingOpponentSkills.put(s, --val);
			}
		}
	}
}


