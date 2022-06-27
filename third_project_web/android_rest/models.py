from django.db import models

# Create your models here.
class UserInfo(models.Model):
    phoneNum = models.CharField(db_column='phoneNum', max_length=14, primary_key=True)
    userName = models.CharField(db_column='userName', max_length=50)
    userId = models.CharField(db_column='userId', max_length=50)
    userPass = models.CharField(db_column='userPass', max_length=50)

    def insert_UserInfo(self, phoneNum, userName, userId, userPass):
        if not phoneNum:
            raise ValueError('전화번호를 입력하세요')
        if not userName:
            raise ValueError('이름을 입력하세요')
        if not userId:
            raise ValueError('아이디를 입력하세요')
        if not userPass:
            raise ValueError('비밀번호를 입력하세요')

        user = self.model(
            phoneNum=phoneNum,
            userName=userName,
            userId=userId,
            userPass=userPass,
        )
        user.save(using=self._db)
        return user

    class Meta:
        managed = False
        db_table = 'userinfo'

    def __str__(self):
        return "휴대번호 : " + self.phoneNum + ", 이름 : " + str(self.userName) + ", 아이디 : " + self.userId + ", 비밀번호 : " + str(self.userPass)


class PointInfo(models.Model):
    id = models.IntegerField(db_column='id', primary_key=True)
    phoneNum = models.CharField(db_column='phoneNum', max_length=14)
    acDate = models.DateTimeField(db_column='acDate', auto_now=True)
    acBrand = models.CharField(db_column='acBrand', max_length=50)
    acPoint = models.IntegerField(db_column='acPoint', default=300)

    class Meta:
        managed = False
        db_table = 'pointinfo'

    def __str__(self):
        return "휴대번호:" + self.phoneNum + "적립날짜:" + str(self.acDate) + "적립브랜드:" + self.acBrand + "적립포인트:" + str(self.acPoint)