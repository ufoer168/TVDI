import time
import json
import pandas as pd
import datetime as dt
import matplotlib.pyplot as plt
import mplfinance as mpf
from fugle_realtime import WebSocketClient
from apscheduler.schedulers.background import BackgroundScheduler

def main():
    scheduler = BackgroundScheduler()
    scheduler.add_job(chart, 'interval', seconds=10)
    scheduler.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        scheduler.shutdown()

def chart():
    ws_client = WebSocketClient(api_token='demo')
    ws = ws_client.intraday.chart(symbolId='2884', on_message=get)
    ws.run_async()
    time.sleep(2)
    ws.close()

def get(msg):
    df = pd.DataFrame(columns=['Date', 'Open', 'High', 'Low', 'Close'])
    records = json.loads(msg)['data']['chart']

    for index, record in enumerate(records['t']):
        date = dt.datetime.fromtimestamp(int(str(record)[:-3]))
        date = dt.datetime(date.year, date.month, date.day, date.hour, (date.minute // 5 * 5))

        if len(df[df['Date'] == date]) == 0:
            open_price = records['o'][index]
            high_price = records['h'][index]
            low_price = records['l'][index]
            close_price = records['c'][index]

            df = pd.concat([df, pd.DataFrame({'Date': [date], 'Open': [open_price], 'High': [high_price], 'Low': [low_price], 'Close': [close_price]})])
        else:
            df.loc[(df['Date'] == date) & (records['h'][index] > df['High']), 'High'] = records['h'][index]
            df.loc[(df['Date'] == date) & (records['l'][index] < df['Low']), 'Low'] = records['l'][index]
            df.loc[df['Date'] == date, 'Close'] = records['c'][index]

    df.set_index('Date', inplace=True)
    print(df)

    line = [
        mpf.make_addplot([26] * len(df), color='b', width=1),
        mpf.make_addplot([27] * len(df), color='b', width=1)
    ]
    
    s = mpf.make_mpf_style(marketcolors=mpf.make_marketcolors(up='g', down='r', inherit=True), gridcolor='gray', gridstyle=':')
    mpf.plot(df, type='candle', title='Stock K-Line Chart', ylabel='', style=s, addplot=line)

if __name__ == '__main__':
    main()