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

    #### Invocacion del Metodo
        https://instant-pivot-410117.uc.r.appspot.com/api/tema/59

    #### Estado OK, encontro mensajes del tema.
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

    #### Estado Error, el tema no existe.
        {
            "mensaje": "Tema 59 No existe"
        }




2. Deployar API HTTP en la nube mediante CI/CD a tu elección. Flujo CI/CD y ejecuciones deben estar visibles en el repositorio git.
    Se utiliza Github actions

        .github/workflows/deploy-app-engine-develop.yml - Deploy de los cambios en la rama develop, con push y pull request.
        .github/workflows/deploy-app-engine-master.yml - Deploy de los cambios en la rama master, solo con pull request.

3. Agregar suscripción al sistema Pub/Sub con lógica para ingresar los datos recibidos a la base de datos. El objetivo es que los mensajes recibidos en un tópico se guarden en la base de datos. No requiere CI/CD.

     #### Invocacion del Metodo POST (request)
     Con este metodo puedes crear un tema y sus mensajes

        https://instant-pivot-410117.uc.r.appspot.com/api/tema/

        BODY

        {
        "nombre":"tema_nuevo",
        "mensajes":[
            {
                "texto":"mensaje1"

            },
             {
                "texto":"mensaje2"
                
            }

        ]
        }

    #### Estado OK, (response) 
    {
        "Id del tema creado": 302
    }
    _________________

    #### Invocacion del Metodo GET (request)
     Con este metodo puedes recibir los mensajes asociados a un topico o tema.

        https://instant-pivot-410117.uc.r.appspot.com/api/tema/59

    #### Estado OK, (response).
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
     _________________
   
    #### Invocacion del Metodo PUT (request)
     Con este metodo puedes actualizar el nombre del tema y los mensajes, debes incluir el id de los mensajes para que se actualicen, sino se insertaran como nuevos.

        https://instant-pivot-410117.uc.r.appspot.com/api/tema/
    
        BODY

        {
            "nombre":"tema actualizado3",
            "id": 206,
            "mensajes":[
                {
                    "id": 352,
                    "texto":"mensaje actualizado 1"

                },
                {
                    "id": 351, 
                    "texto":"mensaje actualizado 2"
                    
                }

            ]
        }

    #### Estado OK, (response) 

        {
        "Tema actualizado": {
            "id": 206,
            "nombre": "tema actualizado3",
            "mensajes": [
                {
                    "id": 352,
                    "texto": "mensaje actualizado 1"
                },
                {
                    "id": 406,
                    "texto": "mensaje actualizado 2"
                }
            ]
        }
        }


4. Incluye un diagrama de arquitectura con la infraestructura del punto 1.1 y su interacción con los servicios/aplicaciones que demuestra el proceso end-to-end de ingesta hasta el consumo por la API HTTP.

    Tecnologia Serverless utilizada: **App Engine GCP**

![Arquitectura PubSub Desafio Latam!](/images/Architecture_PubSub_Desafio%20Latam%20GCP.jpg "Arquitectura PubSub Desafio Latam")


Parte 3: Pruebas de Integración y Puntos Críticos de Calidad
---------------	

1. Implementa en el flujo CI/CD en test de integración que verifique que la API efectivamente está exponiendo los datos de la base de datos. Argumenta.

    EL CI/CD esta realizado en Github Actions, el primer job que se ejecuta en el CI es **Test APP**.

    ![Prueba Integracion!](/images/prueba_integracion_git.png "Prueba Integracion")

    El job ejecuta los test de la aplicacion en los cuales encontramos **function_revisa_base_de_datos** este realiza una consulta a la base de datos y si esta no se encuentra arriba o existen problemas de comunicacion, el job arroja error y no permite continuar con el job siguiente el cual realiza el deploy.

2. Proponer otras pruebas de integración que validen que el sistema está funcionando correctamente y cómo se implementarían.

    **Prueba de integracion 1**: permite verificar si se pueden realizar insert o update en la base de datos con el usuario actual (descartar que es readonly).
    **Implementacion 1**: Crear una prueba que intente hacer un insert o un update en una tabla x de la base de datos.

    **Prueba de integracion 2**: permite verificar si se crearon todas las tablas necesarias de la base de datos
    **Implemetacion 2**: Por cada tabla crear una prueba que realice una consulta y que se realice correctamente aunque no traiga datos.

3. Identificar posibles puntos críticos del sistema (a nivel de fallo o performance) diferentes al punto anterior y proponer formas de testearlos o medirlos (no implementar)

    No se creo la base de datos antes de deployar la aplicacion, lo cual provocara que el sistema no se despliegue y menos levante.

4. Proponer cómo robustecer técnicamente el sistema para compensar o solucionar dichos puntos críticos

    En caso de detectarse que la base de datos no ha sido creada, en github actions deberian ejecutarse los terraforms para crearla y continuar con el deploy.


Parte 4: Métricas y Monitoreo
---------------	

1. Proponer 3 métricas (además de las básicas CPU/RAM/DISK USAGE) críticas para entender la salud y rendimiento del sistema end-to-end

    <ul>
        <li>network</li>
        <li>sys load</li>
        <li>filesystem</li>
    </ul>

2. Proponer una herramienta de visualización y describe textualmente qué métricas mostraría, y cómo esta información nos permitiría entender la salud del sistema para tomar decisiones estratégicas

    **Grafana** destaca como una plataforma open source o de código abierto utilizada en las labores de análisis de datos y extracción de información y métricas.
    Grafana al igual que otras plataformas nos muestra varias metricas, pero nos concetraremos en las 3 basicas:
    
    <ul>
        <li>cpu</li>
        <li>ram</li>
        <li>disk usage</li>
    </ul>

    Estas metricas nos permite identificar la cantidad de uso que tiene nuestro sistema en la maquina que se esta ejecutando, al sobrepasar los limites establecidos y ser informados por alertas, podemos realizar escalamientos o autoescalamientos verticales (añadir mas recursos) o horizontales (añadir mas maquinas), todo esto para complementar el original y asi no permitir que nuestra aplicacion se caiga por sobrecarga.


3. Describe a grandes rasgos cómo sería la implementación de esta herramienta en la nube y cómo esta recolectaría las métricas del sistema.

    Se puede instalar sencillamente en un cluster de Kubernetes por intermedio de sus yaml.

    El operador Grafana importa los recursos de Grafana que se crean durante la instalación de la aplicación. El GrafanaDashboard explora los recursos en todos los espacios de nombres y ahora los recursos están visibles. Los paneles de instrumentos se organizan en carpetas que corresponden a espacios de nombres. Expanda una carpeta para ver los paneles de control.


4. Describe cómo cambiará la visualización si escalamos la solución a 50 sistemas similares y qué otras métricas o formas de visualización nos permite desbloquear este escalamiento.

    La visualizacion sera mas complicada por lo cual sera esencial el recurso técnico destinada a monitorearla.
    Metricas:

    **GroupMinSize**: El tamaño mínimo del grupo de Auto Scaling.
    **GroupMaxSize**: El tamaño máximo del grupo de Auto Scaling.
    **GroupStandbyInstances**: El número de instancias que tienen el estado Standby. Las instancias con este estado se siguen ejecutando pero no están en servicio.
    **GroupTerminatingInstances:** El número de instancias que se están terminando. Esta métrica no incluye las instancias que están en servicio o pendientes.
	

5. Comenta qué dificultades o limitaciones podrían surgir a nivel de observabilidad de los sistemas de no abordarse correctamente el problema de escalabilidad.

    Podriamos tener dificultades o limitaciones para establecer la cantidad de recursos que se estan creando para el uso de nuestras aplicaciones.

Parte 5: Alertas y SRE (Opcional)
---------------	
**1. Define específicamente qué reglas o umbrales utilizarías para las métricas propuestas, de manera que se disparan alertas al equipo al decaer la performance del sistema. Argumenta.**

    **GroupMinSize**: En caso de que el numero de maquinas sea menor al GroupMinSize, disparar una alerta.<br>
    **GroupMaxSize**: En caso de que el numero de maquinas sea mayor al GroupMaxSize, disparar una alerta.<br>
    **GroupStandbyInstances**: Si el numero supera el 10%, disparar una alerta.<br>
    **GroupStandbyInstances:** Apenas se termine una, disparar una alerta.<br>

    GroupMinSize, por un tema de tiempo de respuesta de la aplicacion este numero no puede ser menor.<br><br>
    GroupMaxSize, por un tema de costos de la nube este numero no puede ser mayor.<br><br>
    GroupStandbyInstances, si tenemos mas del 10% que se ejecuten y no esten en servicio puede ser empeorar los tiempos de respuestas del sistema.<br><br>
    GroupStandbyInstances, apenas se termine una maquina debemos saber cual fue y por que razon esta en este estado.<br><br>

**2. Define métricas SLIs para los servicios del sistema y un SLO para cada uno de los SLIs. Argumenta por qué escogiste esos SLIs/SLOs y por qué desechaste otras métricas para utilizarlas dentro de la definición de SLIs.**

    #### Métricas SLIs
    **totalServiceFilter** : Una métrica que contabiliza el total de eventos. SLI:  99,95 %.<br>
    **badServiceFilter** : En una métrica que cuenta los eventos “incorrectos”.  SLI:  99,95 %.

    Estas metricas son importantes para el negc¡ocio, para tener claridad la cantidad de eventos que sucenden en nuesto sistema y la cantidad de eventos incorrectos esto permite realizar mejoras tanto en el software como en el equipo.


