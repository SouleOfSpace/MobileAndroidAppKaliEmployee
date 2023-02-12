import os

from django.conf import settings
from django.contrib.auth.models import User
from rest_framework.decorators import api_view, APIView, permission_classes
from rest_framework.exceptions import AuthenticationFailed
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated

from .models import Profile, Passport, Contract, Violate
from .serializers import UserSerializer, ProfileSerializer, ContractSerializer, ViolateSerializer, PassportSerializer
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from rest_framework_simplejwt.views import TokenObtainPairView
import jwt, datetime

# @api_view(['POST'])
# def getRouts(request):
#     routes = [
#         '/users/',
#         '/profiles',
#         '/passports',
#         '/contracts',
#         '/violates',
#
#         'token/',
#         'token/refresh/'
#     ]
#
#     return Response(routes)


class MyTokenObtainPairSerializer(TokenObtainPairSerializer):
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)

        # Add custom claims
        token['username'] = user.username
        token['password'] = user.email

        return token

class MyTokenObtainPairView(TokenObtainPairView):
    serializer_class = MyTokenObtainPairSerializer


# Create your views here.
class RegisterView(APIView):
    def post(self, request):
        serializer = UserSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)


class LoginView(APIView):
    def post(self, request):
        email = request.data['email']
        password = request.data['password']

        user = User.objects.filter(email=email).first()

        if user is None:
            raise AuthenticationFailed('User not found!')

        if not user.check_password(password):
            raise AuthenticationFailed('Incorrect password!')

        payload = {
            'id': user.id,
            'exp': datetime.datetime.utcnow() + datetime.timedelta(minutes=60),
            'iat': datetime.datetime.utcnow()
        }

        token = jwt.encode(payload, 'secret', algorithm='HS256').decode('utf-8')

        response = Response()

        response.set_cookie(key='jwt', value=token, httponly=True)
        response.data = {
            'jwt': token
        }
        return response


class UserView(APIView):

    def get(self, request):
        token = request.COOKIES.get('jwt')

        if not token:
            raise AuthenticationFailed('Unauthenticated!')

        try:
            payload = jwt.decode(token, 'secret', algorithm=['HS256'])
        except jwt.ExpiredSignatureError:
            raise AuthenticationFailed('Unauthenticated!')

        user = User.objects.filter(id=payload['id']).first()
        serializer = UserSerializer(user)
        return Response(serializer.data)


class LogoutView(APIView):
    def post(self, request):
        response = Response()
        response.delete_cookie('jwt')
        response.data = {
            'message': 'success'
        }
        return response


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def users_list(request):
    token = bytes(request.headers['Authorization'][7:], 'UTF-8')
    decoded = jwt.decode(token, settings.SECRET_KEY, algorithms=["HS256"])

    queryset = User.objects.get(pk=decoded['user_id'])
    serializer = UserSerializer(queryset)

    return Response(serializer.data)


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def profile_list(request):
    token = bytes(request.headers['Authorization'][7:], 'UTF-8')
    decoded = jwt.decode(token, settings.SECRET_KEY, algorithms=["HS256"])
    user = User.objects.get(pk=decoded['user_id'])

    queryset = Profile.objects.get(user__id=user.id)
    serializer = ProfileSerializer(queryset)

    return Response(serializer.data)


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def passport_list(request):
    token = bytes(request.headers['Authorization'][7:], 'UTF-8')
    decoded = jwt.decode(token, settings.SECRET_KEY, algorithms=["HS256"])
    user = User.objects.get(pk=decoded['user_id'])

    queryset = Passport.objects.get(profile_id=user.id)
    serializer = PassportSerializer(queryset)

    return Response(serializer.data)


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def contract_list(request):
    token = bytes(request.headers['Authorization'][7:], 'UTF-8')
    decoded = jwt.decode(token, settings.SECRET_KEY, algorithms=["HS256"])
    user = User.objects.get(pk=decoded['user_id'])

    queryset = Contract.objects.get(profile_id=user.id)
    serializer = ContractSerializer(queryset)

    return Response(serializer.data)


@api_view(['GET'])
@permission_classes([IsAuthenticated])
def violate_list(request):
    token = bytes(request.headers['Authorization'][7:], 'UTF-8')
    decoded = jwt.decode(token, settings.SECRET_KEY, algorithms=["HS256"])
    user = User.objects.get(pk=decoded['user_id'])

    queryset = Violate.objects.filter(profile_id=user.id)
    serializer = ViolateSerializer(queryset, many=True)

    return Response(serializer.data)