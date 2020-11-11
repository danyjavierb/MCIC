# Docker registry
REGISTRY ?= index.docker.io
#Image namespace
NAMESPACE ?= danyjavierb
# image name
NAME ?= todos
#image default tag
IMAGE_TAG ?= latest

IMAGE_NAME = ${REGISTRY}/${NAMESPACE}/${NAME}:${IMAGE_TAG}

build:
	docker build -t ${IMAGE_NAME} .

push:
	docker push ${IMAGE_NAME}

clean_iamge:
	docker rmi ${IMAGE_NAME}

compose_deploy:
	docker-compose -f docker-compose.yaml up -d

compose_clean:
	docker-compose -f docker-compose.yaml down

k8s_deploy:
	kubectl apply -f k8s

k8s_clean:
	kubectl delete -f k8s
