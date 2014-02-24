# -*- coding: utf-8 -*-
import SocketServer

# Class that handles one client connection.
class ClientHandler(SocketServer.BaseRequestHandler):
    
    # id is used when calling the message translator
    id = -1
    translator = None
    DEBUG = False
    
    #def __init__(self, request, client_address, server):
    #    print "ClientHandler override init"
    #    SocketServer.BaseRequestHandler.__init__(self, request, client_address, server)
    #    return


    def handle(self):
        # Hent IP-adressen til klienten
        self.ip = self.client_address[0]
        

        # Hent portnummeret til klienten
        self.port = self.client_address[1]

        # Notify messageTranslator that the socket exists. 
        #self.id = self.translator.addSocket(self)
        
        # Si ifra at en ny klient har koblet til serveren
        print 'Client connected @' + self.ip + ':' + str(self.port)
        
        
        #print "ClientHandler received id " + str(self.id)
        try:
            while True:
                if self.DEBUG: print "clientHandler: Listening for data"
                data = self.request.recv(1024)
                if self.DEBUG: print "clientHandler: Received data"
                
                # Avslutt hvis serveren ikke mottar data fra klienten
                if not data: 
                    self.translator.removeSocket(self)
                    break
                    
                if self.DEBUG: print "clientHandler: Calling messageTranslator.parseJSON"
                self.translator.parseJSON(data, self)
        except Exception as e:
            # Exception will occur if client disconnects.
            #print "clientHandler: Exception"
            self.translator.removeSocket(self)
           


    def sendMessage(self, data):
        if self.DEBUG: print "clientHandler: sending data back to client"
        self.request.sendall(data)