Swagger <br/>
/swagger-ui/index.html

docker-compose up 

docker build -t movieapp .

docker run --network=movieapp_default -e POSTGRES_HOST=postgresDB -p 8080:8080 movieapp:latest
