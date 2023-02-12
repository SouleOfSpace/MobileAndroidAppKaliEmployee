from django.contrib import admin

from .models import Profile, Violate, Contract, Passport


# Register your models here.
@admin.register(Profile)
class ProfileAdmin(admin.ModelAdmin):
    pass

@admin.register(Passport)
class PassportAdmin(admin.ModelAdmin):
    pass

@admin.register(Contract)
class ContractAdmin(admin.ModelAdmin):
    pass

@admin.register(Violate)
class ViolateAdmin(admin.ModelAdmin):
    pass
