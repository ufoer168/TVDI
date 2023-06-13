import googlemaps
import requests
from tkinter import *
from PIL import Image, ImageTk, ImageDraw
from io import BytesIO

root = Tk()
root.title("Google Maps")

map_key = 'AIzaSyCJ9qW_RQMwJKabj-8ZNgX_YCrdSeCL1K8'
gmaps = googlemaps.Client(key=map_key)

geocode_result = gmaps.geocode('臺北市職能發展學院')
location = geocode_result[0]['geometry']['location']
lat = location['lat']
lng = location['lng']

url = f"https://maps.googleapis.com/maps/api/staticmap?center={lat},{lng}&zoom=16&size=800x600&key={map_key}"

response = requests.get(url).content
image = Image.open(BytesIO(response)).resize((800, 600), Image.LANCZOS)
draw = ImageDraw.Draw(image)
draw.rectangle([(395, 295), (405, 305)], fill='red')
photo = ImageTk.PhotoImage(image)
label = Label(root, image=photo)
label.pack()

root.mainloop()

""" reverse_geocode_result = gmaps.reverse_geocode((25.1150128, 121.538346), None, None, 'ZH_TW')
address = reverse_geocode_result[0]['formatted_address']
print(address)

distance_matrix = gmaps.distance_matrix('龍山寺', '碧潭', 'bicycling', 'ZH_TW')
distance = distance_matrix['rows'][0]['elements'][0]['distance']['text']
duration = distance_matrix['rows'][0]['elements'][0]['duration']['text']
print(distance, duration) """

""" m = folium.Map(location=[lat, lng], zoom_start=20)
m.save("map.html")
map_view = HTMLLabel(root, html="<iframe src='map.html' width='800' height='600'></iframe>")
map_view.pack() """