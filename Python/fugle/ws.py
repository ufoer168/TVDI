import time
from fugle_realtime import WebSocketClient

def handle_message(message):
    print(message)

def main():
    ws = WebSocketClient(api_token='643fe792355b97129747f60c4a0d5b37').intraday.chart(symbolId='IX0001', on_message=handle_message)
    ws.run_async()
    time.sleep(1)
    ws.close()

if __name__ == '__main__':
    main()