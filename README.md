# S5.01 - Spring Framework Avanzado con WebFlux

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

    Importar el proyecto con tu IDE preferido (Eclipse o IntelliJ IDEA) como un proyecto Maven.

## ▶️ Ejecución nivel 01. Aplicación Blackjack.

### Desde el IDE (IntelliJ IDEA)

* **Configuración con un archivo `.env`:**
    * Copia el archivo `config.env.example` y renómbralo a `.env`. Este archivo de ejemplo contiene las variables de entorno necesarias para la configuración de la aplicación.
    * Una vez que hayas copiado el archivo, ábrelo y **modifica los valores de las variables** (por ejemplo, las credenciales de la base de datos) para que coincidan con tu configuración personal.
    * En IntelliJ IDEA, abre la **ventana del menú `Run`** y selecciona `Edit Configurations...`.
    * En la configuración de tu aplicación (por ejemplo, `Spring Boot`), busca la sección **`Environment variables`** y haz clic en el botón de la carpeta `(...)`.
    * En la nueva ventana, haz clic en el botón **`+`** y selecciona **`EnvFile`**.
    * Marca la opción **`Enable EnvFile`** y haz clic en el botón de la carpeta `(...)` para seleccionar el archivo `.env` que acabas de crear.
    * Asegúrate de que la opción **`Ignore missing files`** esté seleccionada y guarda los cambios.

* **Ejecución directa:**
    * Con las variables de entorno ya configuradas, puedes ejecutar la aplicación directamente desde la clase principal (`main()`). IntelliJ IDEA cargará automáticamente los valores desde el archivo `.env`.

---

### Desde la terminal (con Maven)

* **Versión sin archivo `.env`:**
    * Navega al directorio raíz de tu proyecto (donde se encuentra `pom.xml`).
    * Ejecuta el siguiente comando para iniciar la aplicación:
        ```bash
        mvn spring-boot:run
        ```

* **Versión con archivo `.env`:**
    * Para pasar las variables de entorno desde la terminal, utiliza el siguiente comando. Ten en cuenta que este método es menos común para proyectos configurados en el IDE:
        ```bash
        mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.import=file:.env"
        ```


## ▶️ Ejecucion nivel 02. Creación y Ejecución de la imagen Docker

Para preparar y ejecutar los contenedores Docker, debes seguir estos pasos en la terminal, asegurándote de que los archivos `Dockerfile` y `docker-compose.yml` estén en la carpeta raíz de tu proyecto.

---

### **1. Configurar las conexiones de la base de datos**

* Copia el archivo `config.env.example` y renómbralo a `.env`.
* Abre el archivo `.env` y modifica los valores de las variables de entorno para que coincidan con tus credenciales y configuración personal de las bases de datos.

---

### **2. Ejecutar los comandos de Docker**

Utiliza los siguientes comandos en tu terminal. El archivo `docker-compose.yml` leerá las variables de entorno de tu archivo `.env` para configurar las conexiones a las bases de datos.

1.  **Detener y eliminar contenedores y volúmenes anteriores:**
    Este comando se asegura de que no haya versiones antiguas de los contenedores o volúmenes que puedan causar conflictos.

    ```bash
    docker-compose down -v
    ```

2.  **Construir la imagen de la aplicación:**
    Este comando construye la imagen de tu aplicación desde cero, sin utilizar la caché, para asegurar que se incluyan los cambios más recientes en el código.

    ```bash
    docker-compose build --no-cache
    ```

3.  **Iniciar los contenedores:**
    Este comando levanta todos los servicios definidos en tu `docker-compose.yml`, incluyendo tu aplicación y las bases de datos.

    ```bash
    docker-compose up
    ```

---

### **Verificación de la ejecución**

* Una vez que los contenedores se estén ejecutando, puedes acceder a la API a través de la URL y el puerto configurados en tu `docker-compose.yml`.
* Para ver el estado de los contenedores, usa `docker-compose ps`.
* Para ver los logs de los contenedores, usa `docker-compose logs`.


## 🌐 Despliegue

Este proyecto es para fines educativos y está destinado para desarrollo local únicamente. No se requiere despliegue ni un entorno externo.

## 📦 Repositorio

Puedes encontrar el código fuente completo en GitHub:
🔗 👉 https://github.com/DiegoBalaguer/S0501-AdvancedSpringWithWebflux.git


## ✅ Notas del Autor

Estos ejercicios están diseñados para proporcionar experiencia práctica en el desarrollo de APIs reactivas con Spring Boot, integración con múltiples bases de datos, implementación de pruebas y automatización del despliegue. ¡Te invito a explorar, modificar y expandir el código base!

¡Feliz codificación! 🚀
