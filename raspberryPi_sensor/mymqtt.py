from pickletools import read_decimalnl_long
from threading import Thread
import paho.mqtt.client as mqtt
import paho.mqtt.publish as pub
from threading import Thread
from servo import Servo
import RPi.GPIO as GPIO
from servo import Servo
from hc_test import HC
from water import WaterPump
import time

class Mqttworker:
    def __init__(self):
        self.client = mqtt.Client()
        self.client.on_connect = self.on_connect
        self.client.on_message = self.on_message
        self.sort = Servo()  # 마지막에 컵을 분류하는 class
        self.perception = HC()  # 소비자가 컵을 놓았는지 감지하는 class
        self.clean = WaterPump()  # 감지 후 컵을 세척하는 class
        self.clean.start()
        self.distance_value = 0
        self.state = True
        
    def mymqtt_connect(self):
        try:
            print("브로커 연결 시작하기")
            self.client.connect("192.168.219.105",1883,60)
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
        else:
            print("연결 실패..")
          
    def on_message(self,client, userdata, message):
        try:
            myvalue = message.payload.decode("utf-8")
            print(message.topic+"---"+myvalue)
        
            if myvalue == "brand name":  # 브랜드 이름이 넘어오고,
                
                myThread2 = Thread(target=self.hc_start)
                myThread2.start()
    
                if self.distance_value > 5:  # 초음파센서로 컵이 놓여 있는지 확인 (거리가 5cm 이하면, 컵이 놓여있다고 인식)
                    print("컵이 감지되지 않습니다.")
                    pub.single("return/kiosk", "fail", hostname="192.168.219.105")
                else:
                    print("동작확인")
                    self.clean.manaulwater()  # 워터펌프로 세척
                    print("세척중입니다.")
                    time.sleep(7)
                    print("분류를 시작합니다.")
                    self.sort.servo()  # 서보모터로 분류
                    print("분류되었습니다.")
                    pub.single("return/kiosk", "ok", hostname="192.168.219.105")  # 태블릿으로 "ok" message를 publish    
        except:
            pass
        finally:
            pass
            # self.state = False   # 초음파센서 한 번 동작되고 나서 다시 false로 바꾸면, 바로 연속 동작이 안된다.
                                   # 아예, run을 다시 시켜야만 동작하게 된다.

    def hc_start(self):
        while self.state:
            self.distance_value = self.perception.getDistance()
            print("distance: %.2f cm" % self.distance_value)   # 거리 출력해서 확인