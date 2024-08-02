import json

import zmq

context = zmq.Context()
socket = context.socket(zmq.REQ)
socket.connect("tcp://localhost:46758")

for request in range(10):
    print(f"Sending request {request}...")
    send_message = {
        "type": "query",
        "text": "今天的天气真好"
    }
    json_str = json.dumps(send_message)
    socket.send(json_str.encode('utf-8'))
    # 接收返回值
    message = socket.recv()
    message_str = message.decode('utf-8')
    result = json.loads(message_str)

    print(result)
