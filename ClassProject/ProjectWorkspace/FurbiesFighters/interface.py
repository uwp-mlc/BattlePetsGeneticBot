from pexpect import popen_spawn
import sys
from io import BufferedWriter
import os
import json
import random
from gamestate import *

# create a piped thread for java process 
dirname = os.path.dirname(__file__)
child = popen_spawn.PopenSpawn(['java', '-cp', 'working_bp.jar', 'edu.furbiesfighters.gameplay.Main'])


print(child.readline())

child.sendline("1\r\n".encode('UTF-8'))

print("1\r\n".encode('UTF-8').decode('UTF-8'))
json_string = child.readline()
print(str(json_string)[2:-5])

state = json.loads(str(json_string)[2:-5])
print(state["Jarvis 1"]["current_health"])
my_hp = state["P1_NAME"]["current_health"]
opp_hp = state["Jarvis 1"]["current_health"]
next_att = 1
while(my_hp > 0 and opp_hp > 0):
        child.stdin.flush()
        if(next_att == 3):
                next_att = 1
        next_att += 1
        child.sendline("{}\r\n".format(next_att).encode('UTF-8'))
        json_string = child.readline()
        state = json.loads(str(json_string)[2:-5])
        my_hp = state["P1_NAME"]["current_health"]
        opp_hp = state["Jarvis 1"]["current_health"]

print(state)
print("my_hp: {} opp_hp: {}".format(my_hp,opp_hp))


def sendAttack(attackArr):
        attackIndex = attackArr.index(max(attackArr))
        attackIndex += 1

        if(attackIndex > 4):
                child.sendline("{}\r\n".format(5).encode('UTF-8'))
                child.sendline("{}\r\n".format(attackIndex - 4).encode('UTF-8'))
        else:
                child.sendline("{}\r\n".format(attackIndex).encode('UTF-8'))

