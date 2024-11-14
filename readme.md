# API de Creación de Usuarios

Esta es una API RESTful para la creación y gestión de usuarios. Está construida con Spring Boot, usando JPA para la persistencia en base de datos, JWT para autenticación basada en tokens e incluye Swagger para la documentación de la API.

## Requisitos Previos

- Java 17 o superior
- Maven o Gradle (para compilar el proyecto)
- Base de datos en memoria H2 (no requiere configuración adicional)

## Estructura del Proyecto

- **`com.jcarmona.config`**: Clases de configuración, incluyendo configuración de Swagger y JWT.
- **`com.jcarmona.config.exception`**: Clases de excepción personalizadas y un controlador para manejar excepciones.
- **`com.jcarmona.controller`**: Controladores REST para manejar las solicitudes HTTP.
- **`com.jcarmona.dto`**: Objetos de transferencia de datos (DTOs).
- **`com.jcarmona.model`**: Clases de entidad para JPA.
- **`com.jcarmona.repository`**: Interfaces de repositorio JPA para operaciones en la base de datos.
- **`com.jcarmona.service`**: Capa de servicio para la lógica de negocio.
- **`com.jcarmona.utils`**: Clases utilitarias.

## Dependencias

- **Spring Boot** - Framework de la aplicación
- **Spring Data JPA** - Para la interacción con la base de datos
- **Spring Security** - Para la encriptación de contraseñas
- **JWT (io.jsonwebtoken)** - Para generación y validación de tokens JWT
- **H2 Database** - Base de datos en memoria para pruebas
- **Swagger** - Para la documentación de la API
- **ModelMapper** - Para mapear DTOs a entidades



## Configuración

La aplicación utiliza las siguientes propiedades, que pueden modificarse en `application.properties`:

```properties
spring.application.name=UserCreator
server.port=9011
spring.datasource.url=jdbc:h2:mem:usuarios_gl
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create

jwt.secretKey=YKy4UG/hCXi4/82cMGSuJoiqQ4pMc7++0bgcewfnSmY=
jwt.expirationHours=2
```

jwt.secretKey: Clave secreta para generar JWTs.
jwt.expirationHours: Tiempo de expiración de los tokens en horas.
spring.datasource: Configuración de la base de datos H2 en memoria.

## Ejecución de la aplicación

### Clonar el repositorio
```bash
git clone https://github.com/your-repo/user-creator.git
```
## Compilar y ejecutar

```bash
./mvnw spring-boot:run
```

La aplicación se iniciará en http://localhost:9011

# Probar la API

## Swagger UI
Accede a la documentación y prueba de la API en Swagger UI:

http://localhost:9011/swagger-ui/index.html


## Consola de H2
La consola de la base de datos H2 está disponible en:

http://localhost:9011/h2-console


Utiliza las credenciales predeterminadas en `application.properties` (`username=sa`, `password=`).

## Endpoints

### 1. Registro de Usuario
- **Endpoint**: `/sign-up`
- **Método**: `POST`
- **Descripción**: Registra un nuevo usuario y retorna la información del usuario junto con un token JWT.

#### Ejemplo de Cuerpo de Solicitud:
```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "cityCode": "1",
      "countryCode": "57"
    }
  ]
}
```

# Documentación de Manejo de Errores y Estructura del Proyecto

## 2. Manejo de Errores

Los errores se devuelven como JSON, siguiendo este formato:

```json
{
  "timestamp": "2024-11-14T01:33:00.579-03:00",
  "codigo": 500,
  "detail": "Mensaje de error",
  "path": "ruta de la solicitud"
}
```

## Ejecución de Pruebas

Para ejecutar las pruebas unitarias, usa el siguiente comando:

```bash
./mvnw test
```

### Pruebas Unitarias

Las pruebas están ubicadas en `src/test/java/com/jcarmona/service/impl/UsuarioServiceImplTest.java` e incluyen:

- **Creación de usuario** (`saveUser_ShouldCreateNewUser`)
- **Verificación de existencia de correo electrónico** (`saveUser_ShouldThrowUserEmailAlreadyExistsException_WhenEmailExists`)
- **Validación de encriptación de contraseña** (`saveUser_ShouldEncryptPassword`)

