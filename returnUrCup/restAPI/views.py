import json

from django.http import JsonResponse
from django.shortcuts import render

# Create your views here.
from rest_framework.parsers import JSONParser

from restAPI.models import PointInfo, UserInfo

# 요청이 들어오면 point 내역 조회 가능
from restAPI.serializers import PointInfoSerializer, UserInfoSerializer


def list(request):
    ptlist = PointInfo.objects.all()
    print(ptlist)
    if request.method == 'GET':
        datalist = PointInfo.objects.all()
        # db에서 조회한 데이터를 BoardSerializer객체로 변환
        serializer = PointInfoSerializer(datalist, many=True)
        print(json.dumps(serializer.data, ensure_ascii=False, default=str))
        # BoardSerializer데이터를 json으로 변환해서 리턴
        return JsonResponse(serializer.data, safe=False, json_dumps_params={'ensure_ascii': False})

def user_list(request):
    ptlist = UserInfo.objects.all()
    print(ptlist)
    if request.method == 'GET':
        datalist = UserInfo.objects.all()
        # db에서 조회한 데이터를 BoardSerializer객체로 변환
        serializer = UserInfoSerializer(datalist, many=True)
        print(json.dumps(serializer.data, ensure_ascii=False, default=str))
        # BoardSerializer데이터를 json으로 변환해서 리턴
        return JsonResponse(serializer.data, safe=False, json_dumps_params={'ensure_ascii': False})

#새로운 포인트 적립 내역 추가
def insert(request):
    if request.method == "POST":
        data = JSONParser().parse(request)
        phoneNum = data['phoneNum']
        acBrand = data['acBrand']
        obj = PointInfo(phoneNum=phoneNum, acBrand=acBrand)
        obj.save()

        return JsonResponse("ok", safe=False, json_dumps_params={'ensure_ascii': False})
        print("완료*************************************************************")

# 안드로이드에서 로그인 요청시 처리할 rest api(메소드)
def login(request):
    if request.method == "POST":
        print("request ok")

        # 클라이언트가 전달해주는 json data 파싱
        data = JSONParser().parse(request)
        print(data)

        # 파싱한 json데이터에서 phoneNum으로 정의된 값을 추출
        phoneNum = data["phoneNum"]

        # 추출한 전화번호를 이용해서 DB에서 데이터 조회하기
        obj = UserInfo.objects.get(phoneNum=str(phoneNum))
        print(obj)
        print(obj.userName)
        name = obj.userName

        if data['phoneNum'] == obj.phoneNum:
            return JsonResponse(name, safe=False, json_dumps_params={'ensure_ascii': False})
        else:
            return JsonResponse("fail", safe=False, json_dumps_params={'ensure_ascii': False})
