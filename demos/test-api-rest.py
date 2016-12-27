import requests
adress = raw_input('entrez adresse du serveur:')
port = raw_input('entrez port du serveur:')
channel = raw_input('entrez nom du channel:')
count = raw_input('entrez le nombre de message à afficher:')
url = "https://"+adress+":"+port+"/getMessages/"+channel+"/"+count+""

r = requests.get(url)

print(r.headers['content-type'])

print(r.text)