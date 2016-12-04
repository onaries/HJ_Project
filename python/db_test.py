#!/usr/bin/python3
#-*- coding: utf-8 -*-

import MySQLdb
import sys
from time import ctime
from datetime import datetime, time, date

# 데이터베이스 변수
db_host = "localhost"
db_user = "root"
db_pwd = "275image"
db_name = "smartpowerman"


# 데이터베이스 설정
db = MySQLdb.connect(db_host, db_user, db_pwd, db_name);

# SELECT 문 사용
cursor = db.cursor()
cursor.execute("SELECT * FROM user")
rows = cursor.fetchall()
print(rows)

# INSERT, UPDATE 문 사용
# cursor.execute("INSERT INTO 테이블명 (칼럼 이름) VALUES (값)
# cursor.execute("UPDATE 테이블명 SET 칼럼 이름 = 값 WHERE 조건

db.close()