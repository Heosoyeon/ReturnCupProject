from rest_framework import serializers
from .models import PointInfo, UserInfo

# 테이블의 조회한 데이터 (queryset)를 model
# 이 db데이터를 json이나 xml로 쉽게 변환할 수 있도록 지원되는 기능
# Board model을 변환 즉 serialize해주어야 하므로 ModelSerializer를 사용한다.
# => DB에서 조회된 데이터를 models.py에 정의된 Board에 맞게 xml이나 json으로 자동변환해줄 객체
class PointInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = PointInfo
        fields = ['phoneNum', 'acDate', 'acBrand', 'acPoint']

# class UserInfoSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = UserInfo
#         fields = ['phoneNum', 'userName', 'userId', 'userPass']

class UserInfoSerializer(serializers.ModelSerializer):
    def insert(self,vaildated_data):
        user = UserInfo.objects.insert_board(
                phoneNum=vaildated_data["phoneNum"],
                userName = vaildated_data["userName"],
                userId=vaildated_data["userId"],
                userPass=vaildated_data["userPass"]
        )
        return user

    class Meta:
        model = UserInfo
        fields = ['phoneNum', 'userName', 'userId', 'userPass']