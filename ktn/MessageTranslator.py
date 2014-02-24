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
    
    def addSocket(self, socket, username):
        self.loggedInSockets[socket] = username
        
    # Called if client requests to disconnect, or if client disconnects without warning.
    def removeSocket(self, socket):
        if self.DEBUG: print "translator: removing socket and username"
        if self.loggedInSockets.has_key(socket):
            username = self.loggedInSockets[socket]
            self.loggedInSockets.pop(socket)
            
            # If socket is removed because the client abruptly disconnected,
            # the chat server needs to be informed that the username is not longer in use.
            self.server.removeUsername(username)
            if self.DEBUG: print "translator: socket, and username removed"
        else:
            if self.DEBUG: print "translator: socket does not exist."
    
    def parseJSON(self, jsonString, socket):
        if self.DEBUG: print "translator: parsing JSON"
        data = json.loads(jsonString)
        
        
        try:
            if data['request'] == 'login':
                response = self.server.requestLogin(data["username"])
                if not response.has_key("error"):
                    #login was accepted. Add username to dictionary
                    self.addSocket(socket, response["username"])
                jsonResponse = json.dumps(response)
                socket.sendMessage(jsonResponse)
            
            elif data["request"] == "logout":
                response = self.server.requestLogout(data["username"])
                if not response.has_key("error"):
                    #Logout was accepted. Remove socket and username from dictionary.
                    self.removeSocket(socket)
                jsonResponse = json.dumps(response)
                socket.sendMessage(jsonResponse)
            
            elif data["request"] == "message":
                username = ""
                if self.loggedInSockets.has_key(socket):
                    username = self.loggedInSockets[socket]
                response = self.server.broadcastMessage(data["message"], username)
                if response.has_key("error"):
                    #Message was not accepted. Inform client.
                    jsonResponse = json.dumps(response)
                    socket.sendMessage(jsonResponse)
        except Exception as e:
            if self.DEBUG: print "Translator: error: " + e.message

    
    def sendMessageToAll(self, message):
        try:
            jsonMessage = json.dumps(message)
            for socket in self.loggedInSockets.iterkeys():
                socket.sendMessage(jsonMessage)
        except Exception as e:
            if self.DEBUG: print "Translator: Error: " + e.message    