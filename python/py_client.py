#-*- coding: utf-8 -*-

import socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(("localhost", 32769))
while 1:
    data = client_socket.recv(512)
    print ("RECEIVED:" , data)

print ("socket colsed... END.")