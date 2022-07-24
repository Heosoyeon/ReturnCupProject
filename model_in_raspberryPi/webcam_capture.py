import picamera                 #라이브러리를 임포트합니다.
import time
import RPi.GPIO as gpio
import os, sys
import gc
import paho.mqtt.publish as publish


dir_path = './term'

push_btn = 26
gpio.setmode(gpio.BCM)
gpio.setup(push_btn, gpio.IN, pull_up_down=gpio.PUD_DOWN)

try:   
    while True:
        if gpio.input(push_btn) == gpio.LOW:
            break
        else:
            pass
        time.sleep(0.1)
finally:
    gpio.cleanup()

print('3')
# time.sleep(1)
print('2')
# time.sleep(1)
print('1')
time.sleep(1.5)
print('shoot!')
camera = picamera.PiCamera()    #파이카메라 객체 생성
camera.rotation = 180
camera.resolution = (512,512)  #카메라 화질 설정
# camera.start_preview()          #카메라 프리뷰 보여주기 시작
# camera.stop_preview()           #프리뷰 멈추기
# print('shoot done')
folder = './Sample_TFLite_model/photo/testtest2.jpg'

camera.capture(folder)
# print('capture done')
time.sleep(1)

# terminal_command = f"ps"
# os.system(terminal_command)

camera.close()
del camera
gc.collect(generation=0)

f=open(folder, "rb") #3.7kiB in same folder
fileContent = f.read()
byteArr = bytearray(fileContent)

publish.single("result2", byteArr, hostname="35.78.109.81")


exec(open('good_bad_detect.py').read())

# camera.start_preview(fullscreen=False, window=(0, 0, 640, 480))
# time.sleep(5)
# camera.stop_preview()
