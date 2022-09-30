# Create Image

docker build -t fmoz/fmanager:sixth .

<hr />

# Push it to the docker hub

docker push fmoz/fmanager:sixth

<hr />

# Deploy to k8s

kubectl create deployment fm-node --image=fmoz/fmanager:first

kubectl expose deployment fm-node --type=NodePort --port=8080

minikube service fm-node --url

<hr />

