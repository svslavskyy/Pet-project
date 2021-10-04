## Installation requirments
1) ```git clone git@github.com:svslavskyy/Pet-project.git```
2) ```cd Pet-project```
3) ```docker-compose up -d```

## Testing 
1) Player service
```curlhttp://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/``` 
2) Lobby
```http://localhost:8081/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/```
3) Game repository
```http://localhost:8082/swagger-ui/index.html?configUrl=/api-docs/swagger-config#/```