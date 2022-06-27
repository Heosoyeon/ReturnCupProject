from django.contrib import admin
from django.urls import path
from android_rest import views

urlpatterns = [
    path('list', views.list, name="list"),
    path('user_list', views.user_list, name="list"),
    path('login', views.login, name="login"),
    path('register', views.register, name="register"),
    path('user_login', views.user_login, name="user_login"),
]