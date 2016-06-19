import zmq
import time

context = zmq.Context()
socket = context.socket(zmq.REP)
socket.bind("tcp://*:5555")

print "Server started."
while True:
    message = socket.recv()
    print "Received message:", message

    # Do some work.
    time.sleep(0.1)

    socket.send("Yo! (" + message + ")")
