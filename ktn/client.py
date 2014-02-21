'''
KTN-project 2013 / 2014
'''
import socket
import json


class Client(object):

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def start(self, host, port):
        self.connection.connect((host, port))
        
        

        self.send(nick + ' has logged on')
        received_data = self.connection.recv(1024).strip()
        print 'Received from server: ' + received_data


        while True:
            message = raw_input('Enter a message (type logout to quit)')

            if (message == 'logout'):
                data = ('nick': nick, 'request': 'logout')
            else:
                data = ('request': 'message', 'message': message)

            data = json.dumps(data)
            self.send(data)
            self.message_received('Received from server: ', connection)

        connection_closed(connection)

    def message_received(self, message, connection):
        received_data = self.connection.recv(1024)
        print message + received_data


    def connection_closed(self, connection):
        self.connection.close()

    def send(self, data):
        self.connection.sendall(data)

    def force_disconnect(self):
        self.shutdown()

    def loginCheck(self):
        check = True
        while check:
            nick = raw_input('Enter your username: ')
            loginData = ('request': 'login', 'username': nick)
            loginData = json.dumps(data)
            self.send(loginData)
            message = self.message_received('Received from server: ', connection)
            response = json.loads(message)
            if(response.get('error') == 'Invalid username!'):
                check = True
            elif(response.get('error') == 'Name allready taken!'):
                check = True
            else:
                check = False
        return nick

    def 

if __name__ == "__main__":
    client = Client()
    ip = raw_input('IP-adress: ')
    if not ip:
        ip = 'localhost'
    client.start(ip, 9999)
