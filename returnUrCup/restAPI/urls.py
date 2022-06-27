
from django.contrib import admin
from django.urls import path
from restAPI import views

urlpatterns = [
    path('list', views.list, name="list"),
    path('user_list', views.user_list, name="list"),
    path('login', views.login, name="login"),
    path('insert', views.insert, name="login"),

]
