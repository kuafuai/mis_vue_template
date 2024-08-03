# -*- coding: utf-8 -*-
import json
import uuid

import warnings

from urllib3.exceptions import NotOpenSSLWarning

api_key = "xxxxxxxxxx"  # 需要的apikey
mq_port = 46758  # 与Java线程通信的端口

warnings.filterwarnings("ignore", category=NotOpenSSLWarning)  # 忽略所有的警告

from http import HTTPStatus

import chromadb
import dashscope
from chromadb import EmbeddingFunction
from chromadb.api import Embeddable
from chromadb.api.types import D, Embeddings


class MyCustomEmbeddingFunction(EmbeddingFunction[Embeddable]):
    def __call__(self, input: D) -> Embeddings:
        list_text = []
        for item in input:
            # print(item)
            list_text.append(self.embed_with_str(item))

        return list_text

    # 对文本进行embedding
    def embed_with_str(self, text):
        # print("12121212", text)
        resp = dashscope.TextEmbedding.call(
            model=dashscope.TextEmbedding.Models.text_embedding_v1,
            api_key=api_key,  # 如果您没有配置环境变量，请将您的APIKEY填写在这里
            input=text)
        if resp.status_code == HTTPStatus.OK:
            return resp['output']['embeddings'][0]['embedding']
        else:
            print(resp)
            raise Exception("embedding is error")


# 集合的名称
# collection_name = "chatbot"

embedding_model = "text-embedding-v1"
chroma_client = chromadb.PersistentClient(path="./chroma_db")

embedding_function = MyCustomEmbeddingFunction()  # embedding函数


# 添加文本到集合汇总
def add_text(collection_name, embedding_text):
    collection = chroma_client.get_or_create_collection(name=collection_name,
                                                        embedding_function=embedding_function)
    embedding_ = embedding_function(embedding_text)
    ids = []
    for item in embedding_text:
        doc_id = str(uuid.uuid4())
        ids.append(doc_id)
    collection.add(embeddings=embedding_, documents=embedding_text, ids=ids)


# 根据文本搜索结果
def search(collection_name, text):
    collection = chroma_client.get_or_create_collection(name=collection_name,
                                                        embedding_function=embedding_function)
    # query_text = embed_with_str("衣服质量")
    results = collection.query(
        # query_embeddings=[query_text],
        query_texts=[text]
    )
    # print(results["documents"])
    return results["documents"]


import zmq

context = zmq.Context()
socket = context.socket(zmq.REP)
socket.bind(f"tcp://*:{mq_port}")
print(1, flush=True)
while True:
    message = socket.recv()
    print(message.decode('utf-8'))
    message_str = message.decode('utf-8')
    # print(message_str)
    message_dict = json.loads(message_str)
    result_dict = {
        "code": 0,
        "result": None,
        "msg": None
    }
    try:
        if message_dict["type"] == 'add':
            add_text(message_dict["collectionName"], message_dict['text'])
            # result_dict['result'] =
        elif message_dict['type'] == 'query':
            s_list = search(message_dict["collectionName"], message_dict['text'])
            result_dict['result'] = s_list
        elif message_dict['type'] == 'heartbeat':
            result_dict['msg'] = "success"
        socket.send(json.dumps(result_dict).encode("utf8"))
    except   Exception as e:
        print(e)
        result_dict["code"] = 5000
        result_dict["msg"] = str(e)
        socket.send(json.dumps(result_dict).encode("utf8"))
