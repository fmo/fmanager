# fmanager

kubectl create deployment fm-node --image=fmoz/fmanager:first

kubectl expose deployment fm-node --type=NodePort --port=8080

minikube service fm-node --url


<hr />

## Run everything on Docker

docker network create rabbits

#### To run rabbit container
docker run -d --rm --net rabbits -p 8081:15672 --hostname rabbit-1 --name rabbit-1 rabbitmq:3.8

#### Rabbit management
docker exec -it rabbit-1 sh

<li>rabbitmq-plugins enable rabbitmq_management</li>

<li>rabbitmqctl stop</li>

<li>rabbitmqctl start</li>


#### Enable stats

cd  /etc/rabbitmq/conf.d/

echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf 


#### Run the app

docker build . -t fmanager

docker run -d --rm --net rabbits -p 8090:8080 --name fm fmanager 

# RabbitMQ Cluster

docker run -d --rm --net rabbits --hostname rabbit-1 --name rabbit-1 -p 8081:15672 rabbitmq:3.8-management

#### Get The Erlang Cookie

docker exec -it rabbit-1 cat /var/lib/rabbitmq/.erlang.cookie

docker run -d --rm --net rabbits --hostname rabbit-2 --name rabbit-2 -p 8082:15672 -e RABBITMQ_ERLANG_COOKIE=SFUHKCELXZJMDRMZBOGK rabbitmq:3.8-management

docker run -d --rm --net rabbits --hostname rabbit-3 --name rabbit-3 -p 8083:15672 -e RABBITMQ_ERLANG_COOKIE=SFUHKCELXZJMDRMZBOGK rabbitmq:3.8-management

docker exec -it rabbit-1 rabbitmqctl cluster_status

## Join node 2

docker exec -it rabbit-2 rabbitmqctl stop_app

docker exec -it rabbit-2 rabbitmqctl reset

docker exec -it rabbit-2 rabbitmqctl join_cluster rabbit@rabbit-1

docker exec -it rabbit-2 rabbitmqctl start_app

docker exec -it rabbit-2 rabbitmqctl cluster_status

## Join node 3

docker exec -it rabbit-3 rabbitmqctl stop_app

docker exec -it rabbit-3 rabbitmqctl reset

docker exec -it rabbit-3 rabbitmqctl join_cluster rabbit@rabbit-1

docker exec -it rabbit-3 rabbitmqctl start_app

docker exec -it rabbit-3 rabbitmqctl cluster_status

# Automated Clustering

```
docker run -d --rm --net rabbits \
-v ${PWD}/docker/config/rabbit-1/:/config/ \
-e RABBITMQ_CONFIG_FILE=/config/rabbitmq \
-e RABBITMQ_ERLANG_COOKIE=WIWVHCDTCIUAWANLMQAW \
--hostname rabbit-1 \
--name rabbit-1 \
-p 8081:15672 \
rabbitmq:3.8-management

docker run -d --rm --net rabbits \
-v ${PWD}/docker/config/rabbit-2/:/config/ \
-e RABBITMQ_CONFIG_FILE=/config/rabbitmq \
-e RABBITMQ_ERLANG_COOKIE=WIWVHCDTCIUAWANLMQAW \
--hostname rabbit-2 \
--name rabbit-2 \
-p 8082:15672 \
rabbitmq:3.8-management

docker run -d --rm --net rabbits \
-v ${PWD}/docker/config/rabbit-3/:/config/ \
-e RABBITMQ_CONFIG_FILE=/config/rabbitmq \
-e RABBITMQ_ERLANG_COOKIE=WIWVHCDTCIUAWANLMQAW \
--hostname rabbit-3 \
--name rabbit-3 \
-p 8083:15672 \
rabbitmq:3.8-management
```

# enable federation plugin
docker exec -it rabbit-1 rabbitmq-plugins enable rabbitmq_federation

docker exec -it rabbit-2 rabbitmq-plugins enable rabbitmq_federation

docker exec -it rabbit-3 rabbitmq-plugins enable rabbitmq_federation

# basic queue mirroring

docker exec -it rabbit-1 bash

rabbitmqctl set_policy ha-fed \
".*" '{"federation-upstream-set":"all", "ha-mode":"nodes", "ha-params":["rabbit@rabbit-1","rabbit@rabbit-2","rabbit@rabbit-3"]}' \
--priority 1 \
--apply-to queues

# Automatic Synchronization

rabbitmqctl set_policy ha-fed \
".*" '{"federation-upstream-set":"all", "ha-sync-mode":"automatic", "ha-mode":"nodes", "ha-params":["rabbit@rabbit-1","rabbit@rabbit-2","rabbit@rabbit-3"]}' \
--priority 1 \
--apply-to queues