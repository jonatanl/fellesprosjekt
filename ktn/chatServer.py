# -*- coding: utf-8 -*-
"""
Created on Fri Feb 21 13:31:43 2014

@author: Oivind
"""
from ThreadedTCPServer import ThreadedTCPServer
from clientHandler import ClientHandler
from MessageTranslator import MessageTranslator
import threading
import sys
from datetime import datetime

# Main Class that manages requests, usernames and the message log.
class ChatServer():
    usernames = []
    messageLog = []
    translator = None
    
    def __init__(self):
        print "Chat server starting."
        
        self.translator = MessageTranslator(self)        
        ClientHandler.translator = self.translator
        
        HOST = ''
        PORT = 9999
        threadedTCPServer = ThreadedTCPServer((HOST, PORT), ClientHandler)
        
        threadedTCPServerThread = threading.Thread(target=threadedTCPServer.serve_forever)
        threadedTCPServerThread.daemon = True
        threadedTCPServerThread.start()
        print "Chat server running."        
        
    # Removes the specified username from the list of usernames.
    def removeUsername(self, username):
        try:
            self.usernames.remove(username)
            print "Removed user: " + username
        except:
            pass
    
    # Adds the specified username to the list of usernames.
    def addUsername(self, username):
        self.usernames.append(username)
        print "Added user: " + username
        
    # Handles login requests, and sends response back to the message translator.
    def requestLogin(self, username):
        response = {}
        copy = username
        if not copy.replace("_", "X").isalnum():
            response = {"response":"login", "error":"Invalid username!", "username":username}
        elif username in self.usernames:
            response = {"response":"login", "error":"Name already taken!", "username":username}
        else:
            response = {"response":"login", "username":username, "messages":self.messageLog}
            self.addUsername(username)
            
        return response
    
    # Handles logout requests, and sends response back to the message translator.
    def requestLogout(self, username):
        response = {}
        if not username in self.usernames:
            response = {"response":"logout", "error":"Not logged in!", "username":username}
        else:
            response = {"response":"logout", "username":username}
            self.removeUsername(username)
        
        return response
    
    # Handles requests to broadcast message. 
    # Sends response back to message translator if there is an error,
    # otherwise instructs message translator to send the message to all connections.
    def broadcastMessage(self, message, username):
        try:
            response = None
            if not username in self.usernames:
                response = {"response":"message", "error":"You are not logged in!"}
            else:
                loggedMessage = str(datetime.now().hour) + ":" + str(datetime.now().minute) + " " + username + ": " + message
                response = {"response":"message","message":loggedMessage}
                self.messageLog.append(loggedMessage)
                print loggedMessage
                self.translator.sendMessageToAll(response)
                    
            return response
        except Exception as e:
            print "ChatServer: error: " + e.message

if __name__ == "__main__":
    chatServer = ChatServer()
    while True:
        pass

        