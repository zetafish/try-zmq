# 0MQ (zeromq)

[home page](http://zeromq.org/)

Slides

- http://www.slideshare.net/fcrippa/europycon2011-implementing-distributed-application-using-zeromq
- http://www.slideshare.net/IanBarber/zeromq-is-the-answer

## Zero

- Zero broker
- Zero latency (almost zero)
- Zero administration
- Zero cost
- Zero waste

## Sockets

- Unicast transport (inproc, ipc, tcp)
- Multicast transport (pgm, epgm)
- connect() and bind() are independent
- Async (with queues)
- Express a certain "message pattern"
- Not necessarily one-to-one

## ...And

- Cross platform
- Multiple languages
- Open source

## Basic Message Patterns

- Request/response
- Pub/sub
- Pipeline

## ZeroMQ is the anwser

http://www.slideshare.net/IanBarber/zeromq-is-the-answer

- 0MQ is unbelievebly cool
- many workflows possible, e.g.
  - queue
  - esb
  - pipelin
  - async
  - pub/sub
  - gateway

### Request/response

    ctx
