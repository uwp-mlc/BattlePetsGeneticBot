package team_mlc;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import event.AttackEvent;
import event.FightStartEvent;
import event.RoundStartEvent;
import player.PetInstance;
import player.PetTypes;
import python.PythonCommunicator;
import settings.PlayerInfo;
import skill.Skills;

public class DavePetInstance extends PetInstance {
	protected Map<Skills, Integer> rechargingOpponentSkills, jarvisRechargingSkills;
	private int opponentIndex, jarvisIndex;
	private PetTypes opponentType;
	private Map<Integer, Double> randomDifference;
	private double opponentHealth;
	private Random random;
	private int roundsSinceUse;
	private double maxROFDiff;
	private Skills predictedSkill;//Override from the Player... We want to use this one. 
	
	
	private PythonCommunicator pyCom;
	
	public DavePetInstance(PlayerInfo playerInfo)
	{
		super(playerInfo);
		
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

	@Override
	public Skills chooseSkill() {
		// TODO Auto-generated method stub
		return null;
	}

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
}
