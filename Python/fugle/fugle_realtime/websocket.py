# 載入 websocket 套件
from fugle_realtime import WebSocketClient
 
# 取得最新報價
def _on_new_price(message):
    json_data = json.loads(message)

    # 查看整股行情
    if json_data['data']['info']['type'] == "EQUITY":
        # 更新目前價格
        now_price = json_data['data']['quote']['trade']['price']
        print(now_price)

def create_ws_quote(symbol):

    ws_client = WebSocketClient(api_token=key) # key 是指您的行情 API Token
    ws = ws_client.intraday.quote(symbolId=str(symbol), on_message=_on_new_price)
    ws.run_async()
#     time.sleep(1)

if __name__ == '__main__':
    create_ws_quote("2330")  