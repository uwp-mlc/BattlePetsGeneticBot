from pexpect import popen_spawn
from pexpect import exceptions

responses = {'Enter a random seed':'1', 
                'Enter a number of players':'2', 
                'Enter a number of fights':'1',
                'Enter the player type for player 1':'2',
                'Enter the pet type for player 1':'1',
                'Enter a name for player 1':'P1',
                'P1: Enter a name for your pet':'PET1',
                'Enter the player type for player 2':'2',
                'Enter the pet type for player 2':'1',
                'Enter a name for player 2':'P2',
                'P2: Enter a name for your pet':'PET2',
                'Enter a starting hp':100,
                'Player 2\r\nPlayer Name: P2\r\nPet Name: PET2\r\nPet Type: Power\r\nStarting HP: 100.0\r\n\n\r\n':1,
                }

expected = list(responses.keys())
    
child = popen_spawn.PopenSpawn(['java', '-cp', 'bp.jar', "battlepets.GameMain"])

#print(child.readline())
while True:
        try:
                expected_index = child.expect(expected,timeout=1)
                print(expected[expected_index])
                print('Entering: {}'.format(responses[expected[expected_index]]))
                child.write("{}\n".format(responses[expected[expected_index]]).encode())
                child.flush()
        except exceptions.TIMEOUT:
                line = child.readline()
                if not line == b'\r\n':
                        print(line)
