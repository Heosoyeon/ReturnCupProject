          
         #rp4 #1 -> 파이카메라, 서버 통신 <- 
         #rp4 #2 <- arduino 센서연결  
         
         #rp4 #2 -> 거리센싱, 세척과정, 분류모터 제어 -> #2 완료 메시지 전파 
         #rp4 #1 -> 카메라 촬영, 메시지 출력 안내 멘트 
         #          # 2완료 메시지 수신 -> "완료 메시지 표시" 
         
          
          
        # 사용자가 컬렉터에 접근 
        # 사람 인식 -> 안녕하세요, .... ㅇ웰컴 메시지 음성 출력 , 안내.  (컵뚜껑, 빨대, 내용물 처리 부탁 )
        # machine : 컵 인식중 입니다.   -> 이미지 촬영, -> AI모델 이미지 전송 -
        # machine : 컵을 넣어 주세요 -> 사람:투입구에 컵 투입 
        # machine : 컵 투입을 인식 (초음파) -> ok 
        # machine : 세척 과정 수행 (5초) 
        # machine : 분류 actuator 작동 
        # machine : 고맙습니다. end message 출력
        #  
        # 브랜드 네임 결과 수신 -> [on_message]
    def getBrandName(Message) : 
        return "starbucks"     
    
    
    def ReadDistance() : 
        if iDist <=  3 : 
        else :
            print ("컵을 제대로 넣지 않으셨어요. 컵을 제대로 위치시켜 주세요. ")
            return 
        return 3 
    
    def passDistance() : 
        bOK = false
        while (bOK) :  
            iDist = readDistance()
            if iDist <=  3 : 
                bOK = true
            else :
                print ("컵을 제대로 넣지 않으셨어요. 컵을 제대로 위치시켜 주세요. ")
        
    
    def cleanCup() : 
        #서보모터 동작 
        return "oK"
    
    def separation() :
        #분류기 액츄에이터 동작 
        return "ok"
    
    def getPoint() : 
        #표시할 포인트 수신 
        return 300
    
    def pushDoneMessage(Message) : 
        mqttMessage =  "raspberry pi #2 process done"
        publishMqtt(mqttMessage)
        return "ok"
    
    def processCup(Message): 
        sBrandName = getBrandName(Message)
        if len(sBrandName) :
            
        else :
            print "잘못된 컵을 넣으셨어요."                    
            return
        
        print "컵을 넣어 주세요. "
        
        bok = passDistance()
        
            
        if cleanCup() : 
            pushDoneMessage({type:"control", value:"rp2-clean"})
            
           
        if separation() : 
            pushDoneMessage({type:"control", value:"rp2-separation"})
            
        iPoint = getPoint()
        print ("고맙습니다. 안녕히 가세요. 적립금은 iPoint입니다. ") 
    
     
     
        
    def on_message_guide(self,client, userdata, message):
         ReceivedMessage = message.payload.decode("utf-8")
        print(message.topic+"---"+ReceivedMessage)

            #{type:"brand", value:"starbucks"}
            #{type:"control", value:"rp2-start"}
            #{type:"control", value:"rp2-distance"}
            #{type:"control", value:"rp2-clean"}
            #{type:"control", value:"rp2-separation"}
            #{type:"control", value:"rp2-done"}


        if ReceivedMessage.type = "brand" :  
            processCup(ReceivedMessage.value)
        elif ReceivedMessage.type = "control" :
            handleProcess(ReceivedMessage.value)
              
        
        pushDoneMessage
        
        # machine : 컵 인식중 입니다.   -> 이미지 촬영, -> AI모델 이미지 전송 -
        # machine : 컵을 넣어 주세요 -> 사람:투입구에 컵 투입 
        # machine : 컵 투입을 인식 (초음파) -> ok 
        # machine : 세척 과정 수행 (5초) 
        # machine : 분류 actuator 작동 
        # machine : 고맙습니다. end message 출력 
    
    
    #<
    #rp1에서 화면에 표시되는 내용, 메시지 처리. 
    def handleProcess(Message):    
        if Message = "rp2-distance" :
            print("컵 투입을 인식하고 있는 중 입니다. ")
        elif Message = "rp2-clean" :
            print("컵을 세척중입니다.")
            ....__annotations__
        return "ok"
     #> 