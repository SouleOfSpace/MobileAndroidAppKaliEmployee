from django.db import models
from django.contrib.auth.models import AbstractUser, User


# Create class User
# class User(AbstractUser):
#     name = models.CharField(max_length=255)
#     email = models.CharField(max_length=255, unique=True)
#     password = models.CharField(max_length=255)
#
#     USERNAME_FIELD = 'email'
#     REQUIRED_FIELDS = []


#Create class Profile
class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, primary_key=True, blank=True)
    firstname = models.CharField(max_length=150)
    lastname = models.CharField(max_length=150)
    surname = models.CharField(max_length=150)

    birthday = models.DateField()
    phone = models.CharField(max_length=7)

    tabel = models.IntegerField()
    work_post = models.CharField(max_length=350)
    address = models.CharField(max_length=300, blank=True, null=True)

    family_status = models.CharField(max_length=100)
    kids = models.BooleanField(default=False)

    created = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)



class Passport(models.Model):
    profile = models.OneToOneField(Profile, on_delete=models.CASCADE, primary_key=True)
    number = models.CharField(max_length=9)
    number_id = models.CharField(max_length=14)

    created = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)


class Contract(models.Model):
    profile = models.OneToOneField(Profile, on_delete=models.CASCADE, primary_key=True)
    start_time = models.DateField()
    finish_time = models.DateField()
    work_class = models.CharField(max_length=2)

    created = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)


class Violate(models.Model):
    profile = models.ForeignKey(Profile, on_delete=models.CASCADE, related_name='violates')
    title = models.CharField(max_length=300)

    created = models.DateTimeField(auto_now_add=True)
    updated = models.DateTimeField(auto_now=True)

    class Meta:
        ordering = ['-created']

    def __str__(self):
        return self.title