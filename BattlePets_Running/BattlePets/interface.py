import subprocess
import threading, queue
import time

def send_input(proc, inp):
    proc.stdin.flush()
    proc.stdin.write("{}\n".format(inp).encode())

def output_reader(proc):

    for line in iter(proc.stdout.readline, b''):
        print('got line: {0}'.format(line.decode('utf-8')), end='')

proc = subprocess.Popen(['java', '-cp', 'bp.jar', "battlepets.GameMain"], stdout=subprocess.PIPE, stdin=subprocess.PIPE)
t = threading.Thread(target=output_reader, args=(proc,))

t.start()

send_input(proc, "1") # seed
send_input(proc, "2") # num players
send_input(proc, "10") # num fights
send_input(proc, "2") # p1 type
send_input(proc, "1") # p1 power type
send_input(proc, "P1") # p1 name
send_input(proc, "Pet1") # p1 pet name
send_input(proc, "100") # p1 hp
send_input(proc, "2") # p2 type
send_input(proc, "1") # p2 power type
send_input(proc, "P2") # p2 name
send_input(proc, "Pet2") # p2 pet name
send_input(proc, "100") # p2 hp

send_input(proc, "asd") # random garbage to see next line

time.sleep(1)
    

