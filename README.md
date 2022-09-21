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