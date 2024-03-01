**Levantar el proyecto y dirigirse a la siguiente dirección de Swagger

http://localhost:8080/swagger-ui/index.html#/user-controller/createUser

**Una vez ahi ingresar el siguiente JSON

{
    
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "telefonos": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
]
}

**Para ver la BBDD pueden dirigirse a la dirección 

http://localhost:8080/h2-console
user:sa
pass:password


**En caso de querer usar POSTMAN pueden usar el siguiente CURL

curl --location 'http://localhost:8080/api/users/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "telefonos": [
        {
            "number": "1234567",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}'
