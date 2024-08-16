<div align="center">
<p>
  <h1>Reto Técnico de Ingreso Laboral - Parqueadero</h1>
</p>
</div>
<div align="center">
  
![nelumbo](https://github.com/user-attachments/assets/1affcd6f-a2b9-4d8e-af0b-13b5f55fd05e)

</div>

## Índice
1. 📋[Requisitos](#requisitos)
2. 💻[Descripción](#descripcion)
3. 🧩[Tecnologías](#tecnologías)
4. 🌐[Modelo Entidad Relación](#modelo)
5. 🖥[Herramientas utilizadas](#herramientas)
6. 📋[Instalación y Uso](#instalaciom)
7. 👫[Autor](#autor)

### Requisitos
___

- Usar PostgreSQL.
- Usar Java SpringBoot.
- Entregar colección de postman, documentando cada request.
- Usar Excepciones cuando se necesiten regresar las respuestas con error 400 detectadas dentro de las clases servicio 
- Implementar patrón de diseño de servicios|repositorios para la lógica manejar los package (controllers, services, entities, repositories) donde cada capa realice su función
- Manejar la protección de los endpoints por rol y token válidos.

### Descripción
___

El ejercicio consiste en desarrollar un sistema de gestión de parqueaderos. El objetivo principal es permitir el registro y seguimiento de la entrada y salida de vehículos en los parqueaderos. Además, el sistema debe enviar notificaciones por correo electrónico cuando un vehículo ingrese al parqueadero, además de consumir un microservicio que simule el envío de correos. Adicionalmente, se debe implementar autenticación JWT para asegurar las solicitudes al sistema y validar y manejar tokens JWT para la autenticación de usuarios.

### Tecnologías
___

  1. [![Java](https://img.shields.io/badge/Java-red?logo=data:image/svg%2bxml;base64,PHN2ZyB2aWV3Qm94PSIwIDAgMzg0IDUxMiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgb3ZlcmZsb3c9ImhpZGRlbiI+PHBhdGggZD0iTTI3Ny43NCAzMTIuOUMyODcuNTQgMzA2LjIgMzAxLjE0IDMwMC40IDMwMS4xNCAzMDAuNCAzMDEuMTQgMzAwLjQgMjYyLjQ0IDMwNy40IDIyMy45NCAzMTAuNiAxNzYuODQgMzE0LjUgMTI2LjI0IDMxNS4zIDEwMC44NCAzMTEuOSA0MC43NCAzMDMuOSAxMzMuODQgMjgxLjggMTMzLjg0IDI4MS44IDEzMy44NCAyODEuOCA5Ny43NCAyNzkuNCA1My4yNCAzMDAuOCAwLjc0IDMyNi4yIDE4My4yNCAzMzcuOCAyNzcuNzQgMzEyLjlaTTE5Mi4zNCAyODAuOEMxNzMuMzQgMjM4LjEgMTA5LjI0IDIwMC42IDE5Mi4zNCAxMzUgMjk2IDUzLjIgMjQyLjg0IDAgMjQyLjg0IDAgMjY0LjM0IDg0LjUgMTY3LjI0IDExMC4xIDEzMi4xNCAxNjIuNiAxMDguMjQgMTk4LjUgMTQzLjg0IDIzNyAxOTIuMzQgMjgwLjhaTTMwNi45NCAxMDQuNkMzMDcuMDQgMTA0LjYgMTMxLjc0IDE0OC40IDIxNS40NCAyNDQuOCAyNDAuMTQgMjczLjIgMjA4Ljk0IDI5OC44IDIwOC45NCAyOTguOCAyMDguOTQgMjk4LjggMjcxLjY0IDI2Ni40IDI0Mi44NCAyMjUuOSAyMTUuOTQgMTg4LjEgMTk1LjM0IDE2OS4zIDMwNi45NCAxMDQuNlpNMzAwLjg0IDM3NS4xQzMwMC4yOTQgMzc2LjA1MyAyOTkuNjIxIDM3Ni45MjcgMjk4Ljg0IDM3Ny43IDQyNy4xNCAzNDQgMzc5Ljk0IDI1OC44IDMxOC42NCAyODAuNCAzMTUuMzI5IDI4MS42MTcgMzEyLjQ2OSAyODMuODE0IDMxMC40NCAyODYuNyAzMTQuMDIgMjg1LjQwNCAzMTcuNjk4IDI4NC40MDEgMzIxLjQ0IDI4My43IDM1Mi40NCAyNzcuMiAzOTYuOTQgMzI1LjIgMzAwLjg0IDM3NS4xWk0zNDggNDM3LjRDMzQ4IDQzNy40IDM2Mi41IDQ0OS4zIDMzMi4xIDQ1OC42IDI3NC4yIDQ3Ni4xIDkxLjMgNDgxLjQgNDAuNSA0NTkuMyAyMi4yIDQ1MS40IDU2LjUgNDQwLjMgNjcuMyA0MzggNzguNSA0MzUuNiA4NSA0MzYgODUgNDM2IDY0LjcgNDIxLjctNDYuMyA0NjQuMSAyOC42IDQ3Ni4yIDIzMi44NCA1MDkuNCA0MDEgNDYxLjMgMzQ4IDQzNy40Wk0xMjQuNDQgMzk2QzQ1Ljc0IDQxOCAxNzIuMzQgNDYzLjQgMjcyLjU0IDQyMC41IDI2Mi43NjggNDE2LjcwMyAyNTMuMzM0IDQxMi4wODcgMjQ0LjM0IDQwNi43IDE5OS42NCA0MTUuMiAxNzguOTQgNDE1LjggMTM4LjM0IDQxMS4yIDEwNC44NCA0MDcuNCAxMjQuNDQgMzk2IDEyNC40NCAzOTZaTTMwNC4yNCA0OTMuMkMyMjUuNTQgNTA4IDEyOC40NCA1MDYuMyA3MC45NCA0OTYuOCA3MC45NCA0OTYuNyA4Mi43NCA1MDYuNSAxNDMuMzQgNTEwLjQgMjM1LjU0IDUxNi4zIDM3Ny4xNCA1MDcuMSAzODAuNDQgNDYzLjUgMzgwLjQ0IDQ2My41IDM3NC4wNCA0ODAgMzA0LjI0IDQ5My4yWk0yNjAuNjQgMzUzQzIwMS40NCAzNjQuNCAxNjcuMTQgMzY0LjEgMTIzLjg0IDM1OS42IDkwLjM0IDM1Ni4xIDExMi4yNCAzMzkuOSAxMTIuMjQgMzM5LjkgMjUuNDQgMzY4LjcgMTYwLjQ0IDQwMS4zIDI4MS43NCAzNjUuOCAyNzMuOSAzNjMuMDMyIDI2Ni43MTcgMzU4LjY3NCAyNjAuNjQgMzUzWiIgZmlsbD0iI0ZGRkZGRiIvPjwvc3ZnPg==)](https://docs.oracle.com/en/java/ )
     - Usted puede encontrar documentación en: [Documentación](https://docs.oracle.com/en/java/ )  
     - Usted puede ver el siguiente marco conceptual y aprender sobre Java: [Guia completa de Java](https://www.w3schools.com/java/default.asp)

  2. [![SpringBoot](https://img.shields.io/badge/SpringBoot-green?logo=springboot&logoColor=f5f5f5)](https://spring.io/projects/spring-boot)
     - Usted puede encontrar documentación en: [Documentación](https://spring.io/projects/spring-boot)
    
  3. [![Maven](https://img.shields.io/badge/Maven-purple)](https://maven.apache.org/)
     - Usted puede encontrar documentación en: [Documentación](https://maven.apache.org/)

  4. [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%230064a5)](https://www.postgresql.org/)
     - Usted puede encontrar documentación en: [Documentación](https://www.postgresql.org/docs/)
     - Usted puede instalar PostgreSQL: [Download](https://www.postgresql.org/download/)

  5. [![Postman](https://img.shields.io/badge/Postman-orange)](https://www.postman.com/)
     - Usted puede encontrar documentación en: [Documentación](https://www.postman.com/api-platform/)
     - Usted puede instalar Postman: [Download](https://www.postman.com/downloads/)

### Modelo Entidad Relación
___
![MER-Parqueadero](https://github.com/user-attachments/assets/7e0c11f6-439b-42dd-b123-e9db0b33ab81)


#### Herramientas utilizadas
___

  1. [![Intellij IDEA](https://img.shields.io/badge/Intellij%20IDEA-%23DE1F6A)](https://www.jetbrains.com/idea/)
  2. [![pgAdmin4](https://img.shields.io/badge/pgAdmin4-%230064a5)](https://www.postgresql.org/)
  3. [![Postman](https://img.shields.io/badge/Postman-orange)](https://www.postman.com/)



#### Instalación y uso

- Clonar el repositorio del proyecto
Abre una terminal o consola de comandos.
Clona el repositorio del proyecto utilizando el siguiente comando "git clone" seguido de la url del repositorio, tal como se muestra a continuación: 
```sh
git clone https://github.com/matildearevalo11/Reto-tecnico-ingreso-laboral-Parqueadero.git
```
- Crear bases de datos para ambos microservicios (API parqueadero - API Email):  
Se abre el gestor de BD pgAdmin4 y se crean dos bases de datos
"correos": Esta base de datos será consumida por la API parqueadero. Debe ser en PostgreSQL.
"parqueadero": Esta base de datos será utilizada para la gestión del parqueadero y consumirá la API correos. Debe ser en PostgreSQL.
- Abrir proyectos: 
Después de haber creado las bases de datos localmente, abre las carpetas correspondientes a cada microservicio de manera independiente. Utiliza un IDE como IntelliJ IDEA Community Edition para esta tarea.
Abre cada carpeta de microservicio en una instancia separada del IDE. Cuando se solicite, selecciona la opción "Trust Project" para ambos proyectos.
Esto permitirá que los proyectos se carguen correctamente y comiencen a descargar las dependencias necesarias automáticamente.
- Ejecutar ambos microservicios: 
Ambos proyectos comenzarán a instalar las múltiples dependencias que hay en ellos. Una vez terminado, en la parte derecha del IDE encontrarás un botón "Maven". Haz clic en él, selecciona "Plugins", luego "spring-boot" y por último "spring-boot:run".
- Probar las funcionalidades con Postman: 
Una vez que ambos microservicios estén en funcionamiento, importa la colección de Postman disponible en el repositorio para probar las diferentes funcionalidades de ambos microservicios.
Es crucial que ambos microservicios estén corriendo simultáneamente para evitar errores, ya que el microservicio de parqueadero depende de una funcionalidad del microservicio de correo.

Importante: Se debe tener en cuenta que el microservicio tiene un INSERT para la precarga del usuario ADMIN. Se debe logear con el usuario ADMIN con las siguientes credenciales:
email: admin@mail.com
contrasenia: admin
Una vez logeado generará un token, damos clic en él para copiarlo, nos dirigimos hacía la pestaña "Authorization" y seleccionamos "Bearer Token", Allí, habrá un campo donde se debe pegar el token previamente copiado.

### Autor
___

#### Proyecto desarrollado por:

- [Matilde Alexandra Arévalo - Ingeniera de Sistemas] (<matildearevalo11@gmail.com>).
