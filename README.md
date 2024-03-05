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
    - Utilizar el esquema Pub/Sub: Se utiliza esquema pubsub, para esto se exponen 3 enpoints.
    <ul>
        <li> POST : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Crear un tema)</li>
        <li> PUT : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Actualizar un tema)</li>
        <li> GET : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Obtener los mensajes de un tema)</li>
    </ul>
    b. Base de datos para el almacenamiento enfocado en analítica de datos.
    Se utiliza una Base de Datos en GCP bajo el servicio Sql Cloud, nombre **database_latam**, motor Mysql 8.

    c. Endpoint HTTP para servir parte de los datos almacenados
    <ul>
        <li> POST : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Crear un tema)</li>
        <li> PUT : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Actualizar un tema)</li>
        <li> GET : https://instant-pivot-410117.uc.r.appspot.com/api/tema/ (Obtener los mensajes de un tema)</li>
    </ul>

2. Deployar infraestructura mediante Terraform de la manera que más te acomode. Incluir código fuente Terraform.
    - Se utiliza codigo terraform para crear la base de datos, unico componente que estariamos usando de infraestructura.
     <ul>
        <li>main.tf</li>
        <li>provider.tf</li>
    </ul>