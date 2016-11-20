#-*- coding: utf-8 -*-

# Server 프로그램 Python 3.5 사용
# 아두이노와의 라즈베리파이 간의 통신 프로그램

from socket import *
from select import select
import sys
from time import ctime

# 초기 변수 지정
HOST = ''
PORT = 12121
BUFSIZE = 128
ADDR = (HOST, PORT)

# 소켓 객체 생성
serverSocket = socket(AF_INET, SOCK_STREAM)

# 서버 정보 바인딩
serverSocket.bind(ADDR)

# LISTEN
serverSocket.listen(10)
connection_list = [serverSocket]
print("서버를 시작합니다. %s 포트" % str(PORT))

while 1:
    clientSocket, addr = serverSocket.accept()
    print("[INFO] [%s] %s 클라이언트가 연결되었습니다." % ctime(), addr)
    while 1:
        data = input("Q입력시 종료 :")