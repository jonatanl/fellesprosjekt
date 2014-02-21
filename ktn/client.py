'''
KTN-project 2013 / 2014
'''
import socket

#test
class Client(object):

    def __init__(self):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def start(self, host, port):
        self.connection.connect((host, port))
        self.send('Hello')
        received_data = self.connection.recv(1024).strip()
        print 'Received from server: ' + received_data
        self.connection.close()

    def message_received(self, message, connection):
        print message

    def connection_closed(self, connection):
        self.connection.close()

    def send(self, data):
        self.connection.sendall(data)

    def force_disconnect(self):
        pass


if __name__ == "__main__":
    client = Client()
    ip = raw_input('IP-adress: ')
    if not ip:
        ip = 'localhost'
    client.start(ip, 9999)
