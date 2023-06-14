import tkinter as tk
import mplfinance as mpf
import pandas as pd

# 建立Tkinter視窗
root = tk.Tk()
root.title("Multi-Kline Charts")
root.geometry("800x600")

# 建立Canvas元件作為繪圖區域
canvas = tk.Canvas(root, width=800, height=600)
canvas.pack()

# 定義K線圖的數據
data1 = {'Date': ['2022-01-01', '2022-01-02', '2022-01-03', '2022-01-04'],
        'Open': [100, 105, 102, 98],
        'High': [110, 112, 108, 104],
        'Low': [95, 98, 100, 96],
        'Close': [105, 110, 100, 100]}

# 將數據轉換為DataFrame格式
df1 = pd.DataFrame(data1)
df1['Date'] = pd.to_datetime(df1['Date'])
df1.set_index('Date', inplace=True)

# 繪製第一個K線圖
ax1 = mpf.make_addplot([26] * len(df1), color='b', width=1)
fig1, _ = mpf.plot(df1, type='candle', addplot=ax1, returnfig=True)
fig1.canvas.draw()

# 獲取第一個K線圖的圖像
kline_img1 = tk.PhotoImage(master=root, width=fig1.canvas.get_width_height()[0], height=fig1.canvas.get_width_height()[1])
canvas.create_image(0, 0, image=kline_img1, anchor=tk.NW)

# 繪製第二個K線圖
ax2 = mpf.make_addplot([26.5] * len(df1), color='b', width=1)
fig2, _ = mpf.plot(df1, type='candle', addplot=ax2, returnfig=True)
fig2.canvas.draw()

# 獲取第二個K線圖的圖像
kline_img2 = tk.PhotoImage(master=root, width=fig2.canvas.get_width_height()[0], height=fig2.canvas.get_width_height()[1])
canvas.create_image(0, fig1.canvas.get_width_height()[1], image=kline_img2, anchor=tk.NW)

# 繪製第三個K線圖
ax3 = mpf.make_addplot([27] * len(df1), color='b', width=1)
fig3, _ = mpf.plot(df1, type='candle', addplot=ax3, returnfig=True)
fig3.canvas.draw()

# 獲取第三個K線圖的圖像
kline_img3 = tk.PhotoImage(master=root, width=fig3.canvas.get_width_height()[0], height=fig3.canvas.get_width_height()[1])
canvas.create_image(0, fig1.canvas.get_width_height()[1] + fig2.canvas.get_width_height()[1], image=kline_img3, anchor=tk.NW)

# 啟動Tkinter事件迴圈
root.mainloop()
