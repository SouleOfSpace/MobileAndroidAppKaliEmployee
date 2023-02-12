from django.contrib import admin
from django.urls import path, include

from django.http import HttpResponse


def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")


urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', include('api.urls')),
    path('', index),
]