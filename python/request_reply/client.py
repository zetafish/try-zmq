import zmq

context = zmq.Context()
socket = context.socket(zmq.REQ)
socket.connect("tcp://localhost:5555")

for i in range(1000):
    message = "This is a hello %r" % i

    print "Sending message:", message
    socket.send(message)

    reply = socket.recv()
    print "Received reply:", reply
