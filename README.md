# CodeHub Backend

Este es el backend de CodeHub, una API REST desarrollada con Java 21 y Spring Boot. Maneja recursos y categorías, además de incluir autenticación básica. Usa una base de datos H2 en memoria para almacenamiento temporal.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database** (en memoria)
- **JUnit 5 y Hamcrest** (para pruebas unitarias)
- **Maven**

## Instalación y Configuración

### Prerrequisitos

- Tener instalado **Java 21**
- Tener **Maven** instalado
- Un IDE como **Visual Studio Code**

### Clonar el repositorio
```bash
git clone https://github.com/tu-usuario/codehub-backend.git
cd codehub-backend
```

### Ejecutar el proyecto

Puedes ejecutar la aplicación con el siguiente comando:
```bash
mvn spring-boot:run
```

### Acceder a la base de datos H2

La base de datos en memoria se puede visualizar accediendo a:
```
http://localhost:8080/h2-console
```
Configurar los siguientes valores:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User**: `sa`
- **Password**: (vacío)

## Endpoints Disponibles

### Autenticación

| Método  | Endpoint  | Descripción |
|---------|----------|-------------|
| POST    | `/login` | Inicia sesión con credenciales | 

### Recursos (Resources)

| Método  | Endpoint             | Descripción |
|---------|----------------------|-------------|
| GET     | `/resources`         | Obtiene todos los recursos |
| GET     | `/resources/{id}`    | Obtiene un recurso por ID |
| POST    | `/resources`         | Crea un nuevo recurso |
| PUT     | `/resources/{id}`    | Actualiza un recurso |
| DELETE  | `/resources/{id}`    | Elimina un recurso |

### Categorías (Categories)

| Método  | Endpoint            | Descripción |
|---------|---------------------|-------------|
| GET     | `/categories`       | Obtiene todas las categorías |
| GET     | `/categories/{id}`  | Obtiene una categoría por ID |
| POST    | `/categories`       | Crea una nueva categoría |
| PUT     | `/categories/{id}`  | Actualiza una categoría |
| DELETE  | `/categories/{id}`  | Elimina una categoría |

## Pruebas Unitarias

Las pruebas unitarias se encuentran en `src/test/java` y se ejecutan con:
```bash
mvn test
```
Se validan:
- Creación y validación de modelos (`Category`, `Resource`)
- Métodos de `ResourceService` y `CategoryService`
- Uso de **Mockito** para pruebas con repositorios

## Mejoras Futuras

- Implementar autenticación JWT
- Migración a una base de datos PostgreSQL
- Agregar DTOs en todas las capas




