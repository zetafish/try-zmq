import zmq


REQUEST_TIMEOUT = 2500
REQUEST_RETRIES = 3
SERVER_ENDPOINT = "tcp://localhost:5555"

context = zmq.Context(1)

print "I: Connecting to server..."
client = context.socket(zmq.REQ)
client.connect(SERVER_ENDPOINT)

poll = zmq.Poller()
poll.register(client, zmq.POLLIN)

sequence = 0
retries = REQUEST_RETRIES
while retries:
    sequence += 1
    request = str(sequence).encode()
    print ("I: Sending (%s)" % request)
    client.send(request)

    expect_reply = True
    while expect_reply:
        socks = dict(poll.poll(REQUEST_TIMEOUT))
        if socks.get(client) == zmq.POLLIN:
            reply = client.recv()
            if not reply:
                break
            if int(reply) == sequence:
                print ("I: Server replied OK (%s)" % reply)
                retries = REQUEST_RETRIES
                expect_reply = False
            else:
                print ("E: Malformed reply: %s" % reply)
        else:
            print("W: No response from server, retrying...")
            client.setsockopt(zmq.LINGER, 0)
            client.close()
            poll.unregister(client)
            retries -= 1
            if retries == 0:
                print ("E: Server seems offline, abandonind")
                break
            print ("I: Reconnecting and resending (%s)" % request)
            client = context.socket(zmq.REQ)
            client.connect(SERVER_ENDPOINT)
            poll.register(client, zmq.POLLIN)
            client.send(request)

context.term()
