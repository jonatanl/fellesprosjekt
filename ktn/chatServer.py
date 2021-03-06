# -*- coding: utf-8 -*-
"""
Created on Fri Feb 21 13:31:43 2014

@author: Oivind
"""
from ThreadedTCPServer import ThreadedTCPServer
from clientHandler import ClientHandler
from MessageTranslator import MessageTranslator
import threading
from datetime import datetime

# Main Class that manages requests, usernames and the message log.
class ChatServer():
    DEBUG = False
    
    # List of logged in usernames
    usernames = []
    # List of all chat messages
    messageLog = []
    # Reference to the message translator
    translator = None
    
    def __init__(self):
        print "Chat server starting."
        
        # Set up object references.
        self.translator = MessageTranslator(self)        
        ClientHandler.translator = self.translator
        
        # Start thread that always listens for incoming TCP connections.
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
    
    # Creates a message in dictionary format. Use 'None' if keys are not to be included in the message.
    def createMsgDic(self, response, error, username, message, messages):
        dic = {}
        if not response == None:
            dic["response"] = response
        if not error == None:
            dic["error"] = error
        if not username == None:
            dic["username"] = username
        if not message == None:
            dic["message"] = message
        if not messages == None:
            dic["messages"] = messages
        return dic
    
    # Handles login requests, and sends response back to the message translator.
    def requestLogin(self, username):
        try:
            response = {}
            copy = username
            if not copy.replace("_", "X").isalnum():
                response = self.createMsgDic("login", "Invalid username!", username, None, None)
            elif username in self.usernames:
                response = self.createMsgDic("login", "Name already taken!", username, None, None)
            else:
                # Login is accepted.
                response = self.createMsgDic("login", None, username, None, self.messageLog)
                self.addUsername(username)
                
            return response
        except Exception as e:
            if self.DEBUG: print "chatServer.requestLogin: Error: " + e.message
                
    # Handles logout requests, and sends response back to the message translator.
    def requestLogout(self, username):
        try:
            response = {}
            if not username in self.usernames:
                response = self.createMsgDic("logout", "Not logged in!", username, None, None)
            else:
                # Logout is accepted.
                response = self.createMsgDic("logout", None, username, None, None)
                self.removeUsername(username)
            
            return response
        except Exception as e:
            if self.DEBUG: print "chatServer.requestLogout: Error: " + e.message
    
    # Handles requests to broadcast message. 
    # Sends response back to message translator if there is an error,
    # otherwise instructs message translator to send the message to all connections.
    def broadcastMessage(self, message, username):
        try:
            response = None
            if not username in self.usernames:
                response = self.createMsgDic("message", "Your are not logged in!", None, None, None)
            else:
                # message is accepted.
                loggedMessage = str(datetime.now().hour) + ":" + str(datetime.now().minute) + " " + username + ": " + message
                response = self.createMsgDic("message", None, None, loggedMessage, None)
                self.messageLog.append(loggedMessage)
                print loggedMessage
                self.translator.sendMessageToAll(response)
                    
            return response
        except Exception as e:
            if self.DEBUG: print "ChatServer.broadcastMessage: error: " + e.message

if __name__ == "__main__":
    chatServer = ChatServer()
    # To keep the main thread alive.
    while True:
        pass

        