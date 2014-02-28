
import SocketServer

# Class that receives incoming TCP connections
# and delegates each connection to a new ClientHandler
class ThreadedTCPServer(SocketServer.ThreadingMixIn, SocketServer.TCPServer):
    pass
    
    
