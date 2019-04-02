from pexpect import popen_spawn
from pexpect import exceptions
import os
from gamestate import *


# responses based on stdout value
responses = {#'Type "0" for command line or "1" for GUI gameplay:':0, 
                '------------------------------------------------\r\n':None,
                '------------ New Battle Beginning ------------\r\n':None,
                'Enter the amount of human players for this battle: ':1,
                'Enter the amount of AI players for this battle: ':0,
                'Enter the amount of smart AI players for this battle: ':1,
                'Enter the amount of fights in this battle: ':gamestate.num_fights,
                # 'Player 1, enter your name:':gamestate.players[0].name,
                # 'Enter 1 for "INTELLIGENCE", 2 for "SPEED", or 3 for "POWER"':None,
                # 'Player ' + gamestate.players[0].name +', enter your type:':gamestate.players[0].player_type,
                # 'Enter the player\'s health':gamestate.players[0].starting_hp,
                # 'Enter your pets name:':'Default pet name',
                # 'Player 2, enter your name:':gamestate.players[1].name,
                # 'Player ' + gamestate.players[1].name + ', enter your type:':gamestate.players[1].player_type,
                # '|-------------- Battle Information --------------|':None,
                # 'Jarvis 1:':None,
                # 'Player Jarvis 1, enter your type:':gamestate.players[1].player_type,
                # 'Amount of fights:  ' + str(gamestate.num_fights):None,
                # 'Amount of players: ': None,
                # '|-------------- Fight 1. Beginning --------------|':None,
                # '|-------------------- Round ':None,
                r'^(\r\n)':None,
                }

# pexpect list, this is a list of keys from the reponses dictionary
expected = list(responses.keys())

# create a piped thread for java process 
dirname = os.path.dirname(__file__)
child = popen_spawn.PopenSpawn(['java', '-cp', dirname + '\\working_bp.jar',"edu.furbiesfighters.gameplay.Main"])
def game_init():
        init_not_done = True
        while init_not_done:
                try:
                        
                        # index of expected string
                        expected_index = child.expect(expected,timeout=1)
                        output = expected[expected_index]
                        #if not output == r'^(\n\r\n)':
                        print(output)
                                
                        # get response from dictionary through list index
                        response = responses[expected[expected_index]]
                        
                        # write response
                        if response != None:
                                print('Entering: {}'.format(response))
                                child.send("{}\n".format(response).encode())
                        # clear stdout line
                # if expect cannot find match
                # print line
                except exceptions.TIMEOUT:
                        line = child.readlines()
                        if not line == b'\r\n' and not line ==  b'':
                                print(line)

game_init()

def send_att():
        child.write("{}\n".format('1').encode())
        print(child.readline())

send_att()
