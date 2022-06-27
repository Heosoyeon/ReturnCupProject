from pickletools import read_decimalnl_long
from threading import Thread
import paho.mqtt.client as mqtt
import paho.mqtt.publish as pub
from threading import Thread
import servo
# from webcam2 import cmrcapture 
#from servo import Servo
import RPi.GPIO as GPIO
from servo import Servo
from hc_test import HC
# from water import WaterPump
import os, sys
import time
import mycamera
# import paho.mqtt.publish as publisher


import time
import traceback

def ErrorLog(error: str):
    current_time = time.strftime("%Y.%m.%d/%H:%M:%S", time.localtime(time.time()))
    with open("Log.txt", "a") as f:
        f.write(f"[{current_time}] - {error}\n")

class Mqttworker:
    def __init__(self):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        # self.mycamera = cmrcapture()
        #self.camera = camera_work()
        self.sort = Servo()  # 마지막에 컵을 분류하는 class
        # self.perception = HC()  # 소비자가 컵을 놓았는지 감지하는 class
        # self.clean = WaterPump()  # 감지 후 컵을 세척하는 class
        # self.clean.start()
        self.distance_value = 0
        self.state = True
        self.camera = mycamera.MyCamera()
        
    def mymqtt_connect(self):
        try:
            print("브로커 연결 시작하기")
            self.client.connect("35.78.109.81", 1883, 60)
            myThread = Thread(target=self.client.loop_forever)
            myThread.start()
        except KeyboardInterrupt:
            pass
        finally:
            print("종료")
    
    def on_connect(self,client, userdata, flags, rc):
        print("connect.."+str(rc))
        if rc == 0:
            client.subscribe("return/#")
            client.subscribe("kiosk/camera")
        else:
            print("연결 실패..")
          
    def on_message(self,client, userdata, message):
        try:
            myvalue = message.payload.decode("utf-8")  # split은 ""안의 : 를 기준으로 나눔 
            print(message.topic+"---"+myvalue)
            if myvalue == "camera_on":  # web에서 start하면
                while True: 
                    frame = self.camera.getStreaming()  # 한 frame씩 읽어서 보냄
                    pub.single("rasp1/camera",frame,hostname="35.78.109.81")
        except:
            pass
        finally:
            pass  

    # def hc_start(self):
    #     while self.state:
    #         self.distance_value = self.perception.getDistance()
    #         print("distance: %.2f cm" % self.distance_value)   # 거리 출력해서 확인