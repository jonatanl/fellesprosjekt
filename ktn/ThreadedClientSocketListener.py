
from threading import Thread

# Class that always listens for incoming messages on a client's socket. 
class ThreadedClientSocketListener(Thread):

    DEBUG = False
    listener = None
    connection = None
    
    def __init__(self, listener, connection):
        self.daemeon = True
        self.listener = listener
        self.connection = connection

    def run(self):
        pass
    
    def listenForever(self):
        if self.DEBUG: print "thread listening"
        while True:
            try:
                message = self.connection.recv(1024).strip()
                self.listener.newMessage(message)
            except:
                break
        