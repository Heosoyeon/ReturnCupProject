from django.shortcuts import render

# Create your views here.
def mqttvideo(request):
    return render(request, 'mqttpic.html')