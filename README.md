# fmanager

kubectl create deployment fm-node --image=fmoz/fmanager:first

kubectl expose deployment fm-node --type=NodePort --port=8080
