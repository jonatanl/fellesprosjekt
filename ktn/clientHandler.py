# -*- coding: utf-8 -*-
import SocketServer

# Class that handles one client connection.
class ClientHandler(SocketServer.BaseRequestHandler):
    
    translator = None
    DEBUG = False

    def handle(self):
        # Hent IP-adressen til klienten
        self.ip = self.client_address[0]
        
        # Hent portnummeret til klienten
        self.port = self.client_address[1]

        # Si ifra at en ny klient har koblet til serveren
        print 'Client connected @' + self.ip + ':' + str(self.port)
        
        
        try:
            while True:
                if self.DEBUG: print "clientHandler: Listening for data"
                
                # Listen for incoming message.
                data = self.request.recv(1024)

                if self.DEBUG: print "clientHandler: Received data"
                
                # Avslutt hvis serveren ikke mottar data fra klienten
                if not data: 
                    self.translator.removeSocket(self)
                    break
                    
                if self.DEBUG: print "clientHandler: Calling messageTranslator.parseJSON"
                
                # Sending data to translator for parsing.
                self.translator.parseJSON(data, self)
        except Exception as e:
            # Exception will occur if client disconnects.
            self.translator.removeSocket(self)
           

    # Called by translator to send data to a client. 
    def sendMessage(self, data):
        if self.DEBUG: print "clientHandler: sending data back to client: " + str(data)
        self.request.sendall(data)