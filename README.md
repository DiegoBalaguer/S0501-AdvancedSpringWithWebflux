# S5.01 - Spring Framework Avanzado con WebFlux

---
# JUEGO BLACKJACK

---

## 🎯 Objetivos

* Aprender a programar una **API reactiva** en Java utilizando Spring Boot y WebFlux.
* Conectar a bases de datos relacionales (**MySQL**) y no relacionales (**MongoDB**).
* Implementar pruebas unitarias y documentar la API.
* Adquirir conocimientos sobre la **dockerización** y el **despliegue** de una aplicación Spring Boot en un servidor web.

---

## 🔹 Descripción

En este ejercicio práctico, se creará una API reactiva con **Spring Boot WebFlux** para un juego de **Blackjack**. La aplicación se conectará a dos bases de datos, **MongoDB** y **MySQL**, y gestionará las funcionalidades necesarias del juego, como jugadores, partidas y manos de cartas. La API deberá ser probada, documentada con **Swagger** y preparada para su despliegue.

---

## ☁️ Servicios en la Nube para la Conectividad

Para asegurar la correcta funcionalidad y consistencia de la imagen Docker en cualquier entorno, hemos optado por utilizar servicios de bases de datos en la nube en lugar de contenedores de bases de datos locales.

---

* **Mongo Atlas:** Se utiliza como el servicio principal para la base de datos **MongoDB**. Esta elección garantiza una alta disponibilidad, escalabilidad y un entorno de producción que simula un despliegue real. Al usar Mongo Atlas, la aplicación se conecta a una instancia remota, lo que evita problemas de configuración y persistencia de datos que podrían surgir al usar un contenedor de MongoDB local.
* **Clever Cloud:** Se ha seleccionado para la base de datos **MySQL**. Similar a Mongo Atlas, Clever Cloud proporciona una solución robusta y gestionada para la base de datos relacional. Esto nos permite asegurar que la conexión y las operaciones de MySQL funcionen de manera fiable y consistente, independientemente del entorno en el que se ejecute el contenedor Docker de la aplicación.

El uso de estos servicios externos permite que la imagen Docker de la aplicación sea **independiente** de las bases de datos, lo que simplifica la configuración y el despliegue, y nos asegura que la aplicación se comportará de la misma manera en todos los entornos previstos.

## 🔹 Nivel 1

### 📘 Implementación de una API Reactiva

Este nivel se centra en la implementación de una API reactiva completa para el juego de Blackjack.

### **Requisitos:**

* **Desarrollo con Spring WebFlux:** Crea una aplicación puramente reactiva utilizando Spring WebFlux para el desarrollo de la API.
* **Gestión de Excepciones Global:** Implementa un `GlobalExceptionHandler` para gestionar de manera centralizada las excepciones de la aplicación.
* **Configuración de Bases de Datos:** Configura la aplicación para trabajar con dos esquemas de bases de datos: **MySQL** y **MongoDB**.
* **Pruebas Unitarias:** Implementa pruebas unitarias para al menos un controlador y un servicio utilizando **JUnit** y **Mockito**.
* **Documentación con Swagger:** Utiliza Swagger para generar documentación automática y detallada de la API.

### **Endpoints del Juego:**

La API deberá ser capaz de dar respuesta a las siguientes peticiones para gestionar el juego:

* **Crear partida:**
    * **Método:** `POST`
    * **Endpoint:** `/game/new`
* **Obtener detalles de partida:**
    * **Método:** `GET`
    * **Endpoint:** `/game/{id}`
* **Realizar jugada:**
    * **Método:** `POST`
    * **Endpoint:** `/game/{id}/play`
* **Eliminar partida:**
    * **Método:** `DELETE`
    * **Endpoint:** `/game/{id}/delete`
* **Obtener rànquing de jugadores:**
    * **Método:** `GET`
    * **Endpoint:** `/ranking`
* **Cambiar nombre del jugador:**
    * **Método:** `PUT`
    * **Endpoint:** `/player/{playerId}`

---

## 🔹 Nivel 2

### 📘 Dockerización de la Aplicación

En este nivel, prepararás la aplicación para ser empaquetada en un **contenedor Docker**. Esto permite una ejecución consistente en diferentes entornos.

### **Pasos a seguir:**

1.  Crear el archivo `Dockerfile` en la raíz del proyecto.
2.  Crear el archivo `.dockerignore` para excluir archivos innecesarios de la imagen.
3.  Construir la imagen Docker.
4.  Ejecutar la imagen para verificar su funcionamiento localmente.
5.  Etiquetar la imagen para su posterior subida.
6.  Iniciar sesión en Docker Hub y subir la imagen.
7.  Probar que la imagen funcione correctamente después de ser subida.

---

## 🔹 Nivel 3

### 📘 Despliegue de la Aplicación

Este nivel cubre el despliegue de la aplicación en un entorno real, utilizando la imagen Docker creada en el nivel anterior.

### **Pasos a seguir:**

1.  **Despliegue manual en Render:**
    * Iniciar sesión en Render y crear un nuevo servicio web.
    * Proporcionar la URL de la imagen de Docker subida previamente.
    * Probar que la aplicación se ejecuta correctamente en el servicio web proporcionado por Render.

2.  **Despliegue automatizado con GitHub Actions:**
    * Preparar el repositorio en GitHub con el `Dockerfile` y el código fuente.
    * Subir la imagen Docker a **GitHub Packages**.
    * Configurar **GitHub Actions** creando un archivo `.github/workflows/deploy-to-render.yml`.
    * Configurar los `Secrets` necesarios en GitHub para la autenticación con Render.
    * Verificar que un `commit` en la rama principal active el flujo de trabajo de despliegue automático.

---

## 🛠️ Tecnologías Utilizadas

* **Java**
* **Spring Boot**
* **Spring WebFlux**
* **MySQL**
* **MongoDB**
* **JUnit**
* **Mockito**
* **Swagger**
* **Postman**
* **Docker**
* **GitHub Actions**
* **Render**

---

## ⚙️ Instalación & Ejecución
### 📋 Requisitos

Para ejecutar este proyecto, necesitamos:

    Java Development Kit (JDK) 11 o superior

    Eclipse o IntelliJ IDEA

    Git

    MySQL Server

    MongoDB Server


## 🛠️  Instalación

    Asegurarse de que JDK 11 o superior está instalado.

    Clonar este repositorio:

    git clone https://github.com/DiegoBalaguer/S0501-AdvancedSpringWithWebflux.git

    Importar el proyecto con nuestro IDE preferido (Eclipse o IntelliJ IDEA) como un proyecto Maven.

---

## ▶️ Ejecución nivel 01. Aplicación Blackjack.

### Desde el IDE (IntelliJ IDEA)

* **Configuración con un archivo `.env`:**
    * Copiamos el archivo `config.env.example` y renómbralo a `archivo.env`. Este archivo de ejemplo contiene las variables de entorno necesarias para la configuración de las conexiones a las bases de datos.
    * Una vez que hayamos copiado el archivo, lo abrimos y **modificamos los valores de las variables** para que coincidan con nuestra configuración personal.
    * En IntelliJ IDEA, abrimos la **ventana del menú `Run`** y seleccionamos `Edit Configurations...`.
    * En la configuración de la aplicación (por ejemplo, `Spring Boot`), buscamos la sección **`Environment variables`** y hacemos clic en el botón de la carpeta `(...)`.
    * En la nueva ventana, hacemos clic en el botón **`+`** y seleccionamos **`EnvFile`**.
    * Marcamos la opción **`Enable EnvFile`** y hacemos clic en el botón de la carpeta `(...)` para seleccionar el archivo `archivo.env` que acabamos de crear.
    * Tenemos que asegurarnos de que la opción **`Ignore missing files`** esté seleccionada y guardamos los cambios.

* **Ejecución directa:**
    * Con las variables de entorno ya configuradas, podemos ejecutar la aplicación directamente desde la clase principal (`main()`). IntelliJ IDEA cargará automáticamente los valores desde el archivo `archivo.env`.

---

### Desde la terminal (con Maven)

* **Versión sin archivo `.env`:**
    * Navegamos al directorio raíz del proyecto (donde se encuentra `pom.xml`).
    * Ejecutamos el siguiente comando para iniciar la aplicación:
        ```bash
        mvn spring-boot:run
        ```

* **Versión con archivo `.env`:**
    * Para pasar las variables de entorno desde la terminal, utilizamos el siguiente comando. Se ha de tener en cuenta que este método es menos común para proyectos configurados en el IDE:
        ```bash
        mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.import=file:archivo.env"
        ```

---

## ▶️ Ejecucion nivel 02. Creación y Ejecución de la imagen Docker

Para preparar y ejecutar los contenedores Docker, debemos seguir estos pasos en la terminal, asegurandonos de que los archivos `Dockerfile` y `docker-compose.yml` estén en la carpeta raíz del proyecto.

---

### **1. Configurar las conexiones de la base de datos**

* Copiamos el archivo `config.env.example` y lo renombramos a `archivo.env`.
* Abrimos el archivo `archivo.env` y modificamos los valores de las variables de entorno para que coincidan con nuestras credenciales y configuración personal de las bases de datos.

---

### **2. Ejecutar los comandos de Docker**

Utilizamos los siguientes comandos en la terminal. El archivo `docker-compose.yml` leerá las variables de entorno de tu archivo `archivo.env` para configurar las conexiones a las bases de datos.

1.  **Detener y eliminar contenedores y volúmenes anteriores:**
    Este comando se asegura de que no haya versiones antiguas de los contenedores o volúmenes que puedan causar conflictos.

    ```bash
    docker-compose down -v
    ```

2.  **Construir la imagen de la aplicación:**
    Este comando construye la imagen de la aplicación desde cero, sin utilizar la caché, para asegurar que se incluyan los cambios más recientes en el código.

    ```bash
    docker-compose build --no-cache
    ```

3.  **Iniciar los contenedores:**
    Este comando levanta todos los servicios definidos en el `docker-compose.yml`, incluyendo la aplicación y las bases de datos, si es que se definen aquí.

    ```bash
    docker-compose up
    ```

---

### **Verificación de la ejecución**

* Una vez que los contenedores se estén ejecutando, podremos acceder a la API a través de la URL y el puerto configurados en `docker-compose.yml`.
* Para ver el estado de los contenedores, usamos `docker-compose ps`.
* Para ver los logs de los contenedores, usamos `docker-compose logs`.

---


## ▶️ Ejecucion nivel 03. Despliegue de la Aplicación

Una vez que la aplicación está lista y dockerizada, el siguiente paso es desplegarla en un entorno real. A continuación, se detallan los pasos para un despliegue manual en Render, y el proceso más avanzado y robusto para un despliegue automatizado con GitHub Actions.

---
### 1. Despliegue manual en Render

1.  **Iniciar sesión en Render**

    Inicia sesión en nuestra cuenta de Render. Si no tenemos una, se tiene que crear.


2.  **Crear un nuevo servicio web**

    Desde el panel de control, hacemos clic en New Web Service.

    Seleccionaos la opción para desplegar desde un Docker Image URL y proporcionamos la URL de la imagen de Docker que subimos a GitHub Packages.


3. **Probar que la aplicación funciona**

   Una vez que Render ha desplegado la imagen, nos proporcionará una URL. Abrimos un navegador web con la URL para verificar que la aplicación se ejecuta correctamente.

---
### 2. Despliegue automatizado con GitHub Actions

Este proceso elimina la necesidad de subir la imagen de forma manual y de iniciar el despliegue a mano. Se activará automáticamente cada vez que subas cambios a la rama principal de tu repositorio.

1.  **Prepararmos el repositorio en GitHub**

    Tenemos que asegurarnos de que el proyecto en GitHub contenga un Dockerfile en la raíz.

    El repositorio ya está configurado para usar GitHub Packages y las dependencias de Render.


2.  **Configurar los Secretos de GitHub**

    Para la automatización, necesitamos configurar tres secretos en el repositorio de GitHub para que el flujo de trabajo pueda autenticarse. No se tienen que exponer estos valores en el código.

    Vamos a Settings > Secrets and variables > Actions y creamos los siguientes:

         GH_PAT: Un Personal Access Token de GitHub con permisos de write:packages.
         RENDER_API_KEY: Una clave de API generada en la cuenta de Render (Account Settings > API Keys).
         RENDER_SERVICE_ID: La ID única del servicio en Render, se puede obtener de la URL del servicio.

3.  **Crear el Archivo de Flujo de Trabajo (.github/workflows)**

    Creamos la carpeta .github/workflows en la raíz del repositorio.

    Dentro de esta carpeta, creamos un archivo llamado deploy-to-render.yml como el que tenemos en el repositorio.


4.  **Verificación y Activación**

    Una vez que el archivo .yml esté en el repositorio y los secretos estén configurados, cualquier git push a la rama main activará el flujo de trabajo.

    Podremos ver el estado del despliegue en la pestaña de Actions del repositorio. Una vez que todos los pasos se completen con éxito, la aplicación se habrá actualizado en Render.


## 🌐 Despliegue

Este proyecto es para fines educativos y está destinado para desarrollo local únicamente. No se requiere despliegue ni un entorno externo.

## 📦 Repositorio

Puedes encontrar el código fuente completo en GitHub:
🔗 👉 https://github.com/DiegoBalaguer/S0501-AdvancedSpringWithWebflux.git


## ✅ Notas del Autor

Estos ejercicios están diseñados para proporcionar experiencia práctica en el desarrollo de APIs reactivas con Spring Boot, integración con múltiples bases de datos, implementación de pruebas y automatización del despliegue. ¡Te invito a explorar, modificar y expandir el código base!

¡Feliz codificación! 🚀
