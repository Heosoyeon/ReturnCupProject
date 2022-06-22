from django.db import models

# Create your models here.
class UserInfo(models.Model):
    phoneNum = models.CharField(db_column='phoneNum', max_length=11, primary_key=True)
    userName = models.CharField(db_column='userName', max_length=50)
    userId = models.CharField(db_column='userId', max_length=50)
    userPass = models.CharField(db_column='userPass', max_length=50)

    class Meta:
        managed = False
        db_table = 'userinfo'

class PointInfo(models.Model):
    id = models.IntegerField(db_column='id', primary_key=True)
    phoneNum = models.CharField(db_column='phoneNum', max_length=11)
    acDate = models.DateTimeField(db_column='acDate', auto_now=True)
    acBrand = models.CharField(db_column='acBrand', max_length=50)
    acPoint = models.IntegerField(db_column='acPoint', default=300)

    class Meta:
        managed = False
        db_table = 'pointinfo'

    def __str__(self):
        return "휴대번호:" + self.phoneNum + "적립날짜:" + str(self.acDate) + "적립브랜드:" + self.acBrand + "적립포인트:" + str(self.acPoint)