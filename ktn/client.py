'''
KTN-project 2013 / 2014
'''
import socket
import json
from ThreadedClientSocketListener import ThreadedClientSocketListener
import threading
import sys

class Client(object):
    DEBUG = False
    listenerThread = None

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def start(self, host, port):
        try:
            # Make a connection
            self.connection.connect((host, port))
            print "Connected to server"

            self.login()
            
            if self.DEBUG: print "login completed"
            
            # Start the listener thread.
            threadedClientSocketListener = ThreadedClientSocketListener(self, self.connection)
            threadedClientSocketListenerThread = threading.Thread(target=threadedClientSocketListener.listenForever)
            threadedClientSocketListenerThread.daemon = True
            self.listenerThread = threadedClientSocketListenerThread
            threadedClientSocketListenerThread.start()
            if self.DEBUG: print "listener thread running"
            
            self.chatAway()
            
            
        except Exception as e:
            if self.DEBUG: print "Client.__init__: Error: " + e.message
        
       
    # Listens for and returns one message on the socket.
    def receiveMessage(self):
        return self.connection.recv(1024)
        
    # Called by the socket listener thread when a new message arrives.
    def newMessage(self, message):
        if self.DEBUG: print "Client.newMessage"
        dic = json.loads(message)

        # Parse the message        
        if dic["response"] == "message":
            if dic.has_key("error"):
                # The message was not accepted by the server.
                print dic["error"]
            else:
                # Received a chat message from the server.
                print dic["message"]
        elif dic["response"] == "logout":
            if dic.has_key("error"):
                # Log out was not accepted by server
                print dic["error"]
            else:
                print "Logged out. Goodbye!"
                self.forceDisconnect()
                self.closeConnection()                
                sys.exit()
                

    def closeConnection(self):
        self.connection.close()

    def send(self, data):
        self.connection.sendall(data)

    def forceDisconnect(self):
        self.connection.shutdown(socket.SHUT_RDWR)

    def login(self):
        if self.DEBUG: print "Client.login"
        
        while True:
            nick = raw_input('Enter your username: ')
            loginData = {'request': 'login', 'username': nick}
            self.send(json.dumps(loginData))
            response = json.loads(self.receiveMessage())
            if response["response"] == "login":
                if response.has_key("error"):
                    print response["error"]
                else:
                    # Login OK
                    print "Logged in as " + response["username"]
                    print ""
                    print "-------Message log--------"
                    for message in response["messages"]:
                        print message
                    print "--------------------------"
                    print ""
                    break;
    
    def chatAway(self):
        print "Enter your chat messages or enter 'logout' to log out."
        while True:
            msg = raw_input("")
            if msg == "logout":
                data = {"request":"logout"}
                self.send(json.dumps(data))
            else:
                data = {"request":"message", "message":msg}
                self.send(json.dumps(data))
    

if __name__ == "__main__":
    client = Client()
    ip = raw_input('Type IP-adress of chat server (or hit enter if on localhost): ')
    if not ip:
        ip = 'localhost'
    client.start(ip, 9999)
