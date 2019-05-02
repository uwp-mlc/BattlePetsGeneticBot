from pexpect import popen_spawn
import os
import json
from gamestate import GameState

class Game():
    round_info = None
    ATTACKS = ["Rock", "Paper","Scissors","Shoot the moom","ROF Rock","ROF Paper","ROF Scissors","ROF Shoot the moon", "ROF ROF"]
    def __init__(self,child):
        self.child = child
        self.gamestate = GameState(1,1)
        self.game_finished = False
        
    def __del__(self):
        self.child.proc.terminate()
        
    def sendAttack(self, attackArr):
        cooldowns = list(self.gamestate.me_cooldowns.values())
        for i, cooldown in enumerate(cooldowns):
            if cooldown != 0:
                if i == 4:
                    for j in range(len(attackArr)):
                        if j >= 4: attackArr[j] = 0
                else:
                    attackArr[i] = 0
        attackIndex = attackArr.index(max(attackArr)) + 1
        

        if(attackIndex > 4):
                self.child.sendline("{}\r\n".format(5).encode('UTF-8'))
                self.child.sendline("{}\r\n".format(attackIndex - 4).encode('UTF-8'))
        else:
                self.child.sendline("{}\r\n".format(attackIndex).encode('UTF-8'))

        if self.gamestate.me_health <= 0 or self.gamestate.op_health <= 0:
            self.game_finished = True

    def get_fitness(self):
        if self.gamestate.op_health < 0:
            self.gamestate.op_health = 0
        return self.gamestate.me_health - self.gamestate.op_health

    def get_net_data(self):
        return self.gamestate.get_net_data()

    @classmethod
    def remember(child,gamestate):
        json_string = child.readline()
        Game.round_info = json.loads(str(json_string)[2:-5])
        gamestate.remember_turn(Game.round_info)
        print(json_string)