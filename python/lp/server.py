from random import randint
import time
import zmq

context = zmq.Context(1)
server = context.socket(zmq.REP)
server.bind("tcp://*:5555")

print "I: starting server"
cycles = 0
while True:
    request = server.recv()
    cycles += 1

    # simulate problems
    if cycles > 3 and randint(0, 3) == 0:
        print "I: Simulating a crash"
        break
    elif cycles > 3 and randint(0, 3) == 0:
        print "I: Simulating CPU overload"
        time.sleep(2)

    print ("I: Normal request (%s)" % request)
    time.sleep(1)
    server.send(request)

server.close()
context.term()
