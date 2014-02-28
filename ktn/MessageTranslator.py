# -*- coding: utf-8 -*-
"""
Created on Fri Feb 21 14:35:17 2014

@author: Oivind
"""
import json

class MessageTranslator:
    server = None
    DEBUG = False
    
    # Dictionary containing username (value) of all logged in sockets (key)
    loggedInSockets = {}
    
    def  __init__(self, chatServer):
        self.server = chatServer
    
    def getSocketUsername(self, socket):
        if self.loggedInSockets.has_key(socket):
            return self.loggedInSockets[socket]
        else:
            return ""
    
    # Adds a socket to the list of logged in sockets, along with its username.
    def addSocket(self, socket, username):
        self.loggedInSockets[socket] = username
        
    # Called if client requests to disconnect, or if client disconnects without warning.
    def removeSocket(self, socket):
        if self.DEBUG: print "MessageTranslator.removeSocket"
        if self.loggedInSockets.has_key(socket):
            username = self.loggedInSockets[socket]
            self.loggedInSockets.pop(socket)
            
            # If socket is removed because the client abruptly disconnected,
            # the chat server needs to be informed that the username is not longer in use.
            self.server.removeUsername(username)
            if self.DEBUG: print "MessageTranslator.removeSocket: Socket and username removed"
        else:
            if self.DEBUG: print "MessageTranslator.removeSocket: Socket does not exist."
    
    # Called by a clientHandler. Interprets the json string and calls the appropriate chatServer method.
    def parseJSON(self, jsonString, socket):
        if self.DEBUG: print "MessageTranslator.parseJSON"
        
        data = json.loads(jsonString)
        
        try:
            if data['request'] == 'login':
                response = self.server.requestLogin(data["username"])
                if not response.has_key("error"):
                    #login was accepted. 
                    # Remove socket if already loged in as someone else.
                    if self.loggedInSockets.has_key(socket):
                        self.removeSocket(socket)
                    #Add socket and username to dictionary
                    self.addSocket(socket, response["username"])
                socket.sendMessage(json.dumps(response))
            
            elif data["request"] == "logout":
                username = self.getSocketUsername(socket)
                response = self.server.requestLogout(username)
                if not response.has_key("error"):
                    #Logout was accepted. Remove socket and username from dictionary.
                    self.removeSocket(socket)
                socket.sendMessage(json.dumps(response))
            
            elif data["request"] == "message":
                username = self.getSocketUsername(socket)
                response = self.server.broadcastMessage(data["message"], username)
                if response.has_key("error"):
                    #Message was not accepted. Inform client.
                    socket.sendMessage(json.dumps(response))
        except Exception as e:
            if self.DEBUG: print "Translator.ParseJson: error: " + e.message

    # Called by the chatServer to send a message to all logged in sockets. 
    def sendMessageToAll(self, message):
        try:
            jsonMessage = json.dumps(message)
            for socket in self.loggedInSockets.iterkeys():
                socket.sendMessage(jsonMessage)
        except Exception as e:
            if self.DEBUG: print "MessageTranslator.sendMessageToAll: Error: " + e.message    