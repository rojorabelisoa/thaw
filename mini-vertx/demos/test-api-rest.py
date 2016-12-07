import requests
r = requests.get('http://localhost:8080/getMessage/10')

print(r.headers['content-type'])

print(r.text)