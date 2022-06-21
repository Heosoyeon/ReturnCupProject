import time
import RPi.GPIO as GPIO

class Servo:
    def __init__(self):
        self.servo_pin = 17
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(self.servo_pin,GPIO.OUT) 
        self.pwm = GPIO.PWM(self.servo_pin,50)
        self.pwm.start(0)
        
    def servo(self):
        self.pwm.ChangeDutyCycle(12.5)
        time.sleep(5)
        self.pwm.ChangeDutyCycle(2.5)
        
    def servo_clean(self):
        # self.pwm.ChangeDutyCycle(0)
        # self.pwm.stop()
        GPIO.cleanup()