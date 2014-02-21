
import SocketServer

class CLientHandler(SocketServer.BaseRequestHandler):
    def handle(self):
        # Add classvariables
        self.connection = self.request
        self.ip = self.client_address[0]
        self.port = self.client_address[1]
        
        print 'Client connected @' + self.ip + ':' + str(self.port)
        data = self.connection.recv(1024).strip()

        if data:
            print data
            self.connection.sendall(data.upper())
        else:
            print 'Client disconnected!'


class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    pass

if __name__ == "__main__":
    HOST = ''
    PORT = 9999

    server = ThreadedTCPServer((HOST, PORT), CLientHandler)
    server.serve_forever()