NAME := http-server-header-dump-java

BASE_IMAGE_URL := chaudhryfaisal/$(NAME)
IMAGE_URL := $(BASE_IMAGE_URL):latest

build:
	docker build --pull -t ${IMAGE_URL} .

run: build
	docker run -p 8080:8080 --rm -it ${IMAGE_URL}

push: build
	docker push ${IMAGE_URL}
