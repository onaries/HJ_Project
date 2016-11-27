#!/usr/bin/python3
#-*- coding: utf-8 -*-

# Server 프로그램 Python 3.5 사용
# 아두이노와의 라즈베리파이 간의 통신 프로그램

from socket import *
from select import select
import MySQLdb
import sys
from time import ctime
from datetime import datetime, time, date
import time as tm

# 초기 변수 지정
HOST = ''
PORT = 32769
PAYLOAD = 512
ADDR = (HOST, PORT)

# 데이터베이스 변수

db_host = "localhost"
db_user = "root"
db_pwd = "275image"
db_name = "smartpowerman"

# 데이터베이스 설정

db = MySQLdb.connect(db_host, db_user, db_pwd, db_name);

# 시간 설정

# 소켓 객체 생성
try:
    serverSocket = socket(AF_INET, SOCK_STREAM)
    serverSocket.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
except socket.error as e:
    print("Error creating socket: %s" % e)
    sys.exit(1)

# 서버 정보 바인딩
serverSocket.bind(ADDR)

# LISTEN
serverSocket.listen(10)
connection_list = [serverSocket]
print("서버를 시작합니다. %s 포트" % str(PORT))

while True:

    try:

        read_socket, write_socket, error_socket = select(connection_list, [], [], 10)

        # 시간 확인
        d = datetime.now()
        day = d.day
        h = d.hour
        m = d.minute
        s = d.second

        # 1초 딜레이
        tm.sleep(1)

        for sock in read_socket:
            if sock == serverSocket:
                clientSocket, addr = serverSocket.accept()
                connection_list.append(clientSocket)
                print("[INFO] [%s] %s 클라이언트가 연결되었습니다." % (ctime(), addr))

            else:
                data = sock.recv(PAYLOAD)
                if data:    # 데이터를 받으면
                    print("[INFO] [%s] RECEIVED : %s" % (ctime(), data))

                    # 1시간에 1개의 튜플을 생성
                    if datetime.now().minute == m+1:      # 1시간이 지났을 경우
                        # cursor = db.cursor();
                        try:
                            # cursor.execute("SQL 문")     # INSER INTO 문
                            print("INSERT INTO 문 실행")
                            print(datetime.now().second)
                            # db.commit()
                        except:
                            # db.rollback()
                            print("에러")
                    else:                               # 그 외의 경우
                        # cursor = db.cursor();
                        try:
                            # cursor.execute("SQL 문")     # UPDATE SET 문
                            print("UPDATE SET 문 실행")
                            print(datetime.now().second)
                            # db.commit()
                        except:
                            # db.rollback()
                            print("에러")

    except KeyboardInterrupt:
        serverSocket.close()
        print("서버를 종료합니다.")
        sys.exit(0)


