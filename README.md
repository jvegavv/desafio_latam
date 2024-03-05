Desafío Técnico DevSecOps/SRE
===============

Instrucciones previas
---------------	
1. Utiliza un repositorio público de git para resolver el desafío
    - https://github.com/jvegavv/desafio_latam
2. Utiliza una rama master y otra develop al resolver el problema
    - Se utiliza gitflow utilizacion rama **master** (principal), **develop** (base para features) y las ramas **feature** para los distintos desarrollos 
3. En el repositorio deben estar todos los archivos utilizados para la resolución del
desafío.
    - **.github/worflow**: Contiene el CI/CD.
    - **Iac**: Contiene los terraform para el despliegue de la infraestructura. 
    - **src**: Contiene el codigo de la aplicacion en **JAVA17**, se utiliza framework **SpringBoot**.
4. Ten en cuenta que nuestro lenguaje preferido es Python.
    - **JAVA17**.


Parte 1:Infraestructura e IaC
---------------	

1. Identificar la infraestructura necesaria para ingestar, almacenar y exponer datos:
    Utilizar el esquema Pub/Sub: Se utiliza esquema pubsub, para esto se exponen 3 enpoints.
    <ul>
        <li> POST : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Crear un tema)</li>
        <li> PUT : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Actualizar un tema)</li>
        <li> GET : https://instant-pivot-410117.uc.r.appspot.com/api/tema/{id} (Obtener los mensajes de un tema)</li>
    </ul>
    </br>
    b. Base de datos para el almacenamiento enfocado en analítica de datos.</br>
    Se utiliza una Base de Datos en GCP bajo el servicio Sql Cloud, nombre **database_latam**, motor Mysql 8.

    c. Endpoint HTTP para servir parte de los datos almacenados.
    <ul>
        <li> POST : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Crear un tema)</li>
        <li> PUT : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Actualizar un tema)</li>
        <li> GET : https://instant-pivot-410117.uc.r.appspot.com/api/tema/{id} (Obtener los mensajes de un tema)</li>
    </ul>

2. Deployar infraestructura mediante Terraform de la manera que más te acomode. Incluir código fuente Terraform.</br>
    Se utiliza terraform para desplegar la base de datos, unico componente que estariamos usando de infraestructura.
     <ul>
        <li>main.tf</li>
        <li>provider.tf</li>
    </ul>
</br>
</br>

Parte 2: Aplicaciones y flujo CI/CD
---------------	
1. API HTTP: Levantar un endpoint HTTP con lógica que lea datos de base de datos y los exponga al recibir una petición GET.<br>

### Invocacion del Metodo
https://instant-pivot-410117.uc.r.appspot.com/api/tema/59

### Estado OK, encontro mensajes del tema.
[
    {
        "id": 407,
        "texto": "prueba1"
    },
    {
        "id": 408,
        "texto": "prueba2"
    }
]

### Estado Error, el tema no existe.
{
    "mensaje": "Tema 59 No existe"
}




2. Deployar API HTTP en la nube mediante CI/CD a tu elección. Flujo CI/CD y
ejecuciones deben estar visibles en el repositorio git.
3. (Opcional) Ingesta: Agregar suscripción al sistema Pub/Sub con lógica para ingresar
los datos recibidos a la base de datos. El objetivo es que los mensajes recibidos en
un tópico se guarden en la base de datos. No requiere CI/CD.
4. Incluye un diagrama de arquitectura con la infraestructura del punto 1.1 y su
interacción con los servicios/aplicaciones que demuestra el proceso end-to-end de
ingesta hasta el consumo por la API HTTP