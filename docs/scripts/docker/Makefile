IMAGE_NAME=fire-ultimate

build:
	docker build -t $(IMAGE_NAME) .

run:
	docker run -e FIRE_JWT_SECRET='$(FIRE_JWT_SECRET)' -e MONGO_URI='$(MONGO_URI)' -p 8080:8080 $(IMAGE_NAME)
