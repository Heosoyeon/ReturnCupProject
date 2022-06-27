import mymqtt
import time
import RPi.GPIO as GPIO
# import hc_test
        
if __name__=="__main__":
    try:
        mqtt = mymqtt.Mqttworker()
        mqtt.mymqtt_connect()
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        pass
    finally:
        GPIO.cleanup()