import googlemaps
import requests
import tkinter as tk
from PIL import Image, ImageTk, ImageDraw
from io import BytesIO

root = tk.Tk()
root.title("Google Maps")

map_key = ''
gmaps = googlemaps.Client(key=map_key)

map_label = None

def showmap(keyword):
    global map_label

    geocode_result = gmaps.geocode(keyword)
    location = geocode_result[0]['geometry']['location']
    lat = location['lat']
    lng = location['lng']

    url = f"https://maps.googleapis.com/maps/api/staticmap?center={lat},{lng}&zoom=16&size=800x600&key={map_key}"

    response = requests.get(url).content
    image = Image.open(BytesIO(response)).resize((800, 600), Image.LANCZOS)
    draw = ImageDraw.Draw(image)
    draw.rectangle([(395, 295), (405, 305)], fill='red')
    photo = ImageTk.PhotoImage(image)

    if map_label is None:
        map_label = tk.Label(root)
        map_label.pack()

    map_label.configure(image=photo)
    map_label.image = photo

    entry.lift()

def on_enter(event):
    showmap(entry.get())

entry = tk.Entry(root, font=('', 16))
entry.place(relx=0.5, rely=0.05, anchor='center')
entry.bind("<Return>", on_enter)

showmap('台北職能發展學院')
root.geometry("800x600")
root.mainloop()