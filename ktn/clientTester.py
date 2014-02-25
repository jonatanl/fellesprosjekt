'''
KTN-project 2013 / 2014
'''
import socket
import json
from ThreadedClientSocketListener import ThreadedClientSocketListener
import threading

class Client(object):
    DEBUG = False

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def start(self, host, port):
        try:
            # Make a connection
            self.connection.connect((host, port))
            print "Connected to server"

            # Start the listener thread.
            threadedClientSocketListener = ThreadedClientSocketListener(self, self.connection)
            threadedClientSocketListenerThread = threading.Thread(target=threadedClientSocketListener.listenForever)
            threadedClientSocketListenerThread.daemon = True
            threadedClientSocketListenerThread.start()
            if self.DEBUG: print "listener thread running"
            
            # Start chatting.
            self.acceptCommands()
            
            
        except Exception as e:
            if self.DEBUG: print "Client.__init__: Error: " + e.message
        
       
    # Listens for and returns ONE message on the socket.
    def receiveMessage(self):
        return self.connection.recv(1024)
        
    # Called by the socket listener thread when a new message arrives.
    def newMessage(self, message):
        print message        

    def closeConnection(self):
        self.connection.close()

    def send(self, data):
        self.connection.sendall(data)

    def forceDisconnect(self):
        self.connection.shutdown(socket.SHUT_RDWR)

    def acceptCommands(self):
        while True:
            msg = raw_input("").split()
            
            if msg[0] == "login":
                self.send(json.dumps({"request":"login", "username":msg[1]}))
            elif msg[0] == "message":
                self.send(json.dumps({"request":"message", "message":msg[1]}))
            elif msg[0] == "logout":
                self.send(json.dumps({"request":"logout"}))
                
                

if __name__ == "__main__":
    client = Client()
    ip = raw_input('Type IP-adress of chat server (or hit enter if on localhost): ')
    if not ip:
        ip = 'localhost'
    client.start(ip, 9999)
