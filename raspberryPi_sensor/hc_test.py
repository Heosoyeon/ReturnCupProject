from turtle import distance
import RPi.GPIO as GPIO
import time


class HC:
    def __init__(self):
        self.TRIGER = 21
        self.ECHO = 20

        GPIO.setmode(GPIO.BCM)
        GPIO.setup(self.TRIGER,GPIO.OUT)
        GPIO.setup(self.ECHO,GPIO.IN)

    def getDistance(self):
        GPIO.output(self.TRIGER,False)
        time.sleep(1)
        GPIO.output(self.TRIGER,True)
        time.sleep(0.00001) 
        GPIO.output(self.TRIGER,False)
        
        while GPIO.input(self.ECHO) == 0 : 
            pulse_start = time.time() # 현재 시간을 측정 - HIGH신호가 발생되는 시간을 측정
            
        while GPIO.input(self.ECHO)==1:
            pulse_end = time.time() # ECHO핀이 LOW신호가 발생되는 시간을 측정
            
        pulse_duration = pulse_end - pulse_start
        
        distance = pulse_duration * 340 * 100 /2
        distance = round(distance,2)
        return distance