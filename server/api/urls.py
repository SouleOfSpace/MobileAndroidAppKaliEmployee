from django.urls import path
from rest_framework_simplejwt.views import (
    TokenRefreshView,
)
from . import views
from .views import MyTokenObtainPairView, MyTokenObtainPairSerializer ,\
    RegisterView, LoginView, UserView, LogoutView


urlpatterns = [
    # path('', views.getRouts),

    path('users/', views.users_list),
    path('profiles/', views.profile_list),
    path('contracts/', views.contract_list),
    path('passports/', views.passport_list),
    path('violates/', views.violate_list),
    path('token/', MyTokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),

    path('register', RegisterView.as_view()),
    path('login', LoginView.as_view()),
    path('user', UserView.as_view()),
    path('logout', LogoutView.as_view()),
]