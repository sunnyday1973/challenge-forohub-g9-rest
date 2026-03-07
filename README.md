# Challenge Back End -  Alura Latam y ONE (Oracle Next Generation)
MIT License © 2026

---

# 🎓 FOROHUB G9 - API REST CHALLENGE 

## DESCRIPCIÓN

---

ForoHub es una API REST desarrollada con Spring Boot para gestionar un foro.
Los usuarios autenticados pueden crear tópicos de discusión, listar y eliminarlos.
Revisar los requisitos del challenge en [Trello](https://trello.com/b/9DeAlIsq/foro-hub-challenge-back-end).

## ✨ CARACTERÍSTICAS PRINCIPALES

---

- 🔐 Autenticación JWT + Spring Security (permitAll en login, authenticated en resto)
- 📝 CRUD Tópicos con con validación y paginación
- 📊 Filtros: ?curso=g9&page=0&size=10
- 🧪 Validaciones @Valid + Custom Exceptions

## 🛠️ TECNOLOGÍAS

---

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)]
[![Spring Boot 4.0.3](https://img.shields.io/badge/Spring%20Boot-4.0.3-green.svg)]
[![Spring Data JPA 4.0.3](https://img.shields.io/badge/Spring%20Data%20JPA-4.0.3-orange.svg)]
[![Spring Security 4.0.3](https://img.shields.io/badge/Spring%20Security-4.0.3-blueviolet.svg)]
[![HATEOAS 4.0.3](https://img.shields.io/badge/HATEOAS-4.0.3-purple.svg)]
[![JWT 4.5.1](https://img.shields.io/badge/JWT-4.5.1-black?style=flat&logo=json-web-tokens)]
[![MySQL 9.5.0](https://img.shields.io/badge/MySQL-9.5.0-yellow.svg)]
[![Flyway 11.14.1](https://img.shields.io/badge/Flyway-11.14.1-blue.svg)]
[![Maven](https://img.shields.io/badge/Maven-orange.svg)]
[![Lombok 1.18.42](https://img.shields.io/badge/Lombok-1.18.42-yellowgreen.svg)]
[![JSpecify 0.3.0](https://img.shields.io/badge/JSpecify-0.3.0-lightblue.svg)]

## 🚀 INSTALACIÓN RÁPIDA

---

1. Clona el repo
   ```
   $ git clone https://github.com/tu-usuario/forohub-g9-rest.git
   $ cd forohub-g9-rest
   ```

2. Configura base de datos en **application.properties**
    ```
   spring.datasource.url=jdbc:mysql://localhost:3306/forohub
   spring.datasource.username=root
   spring.datasource.password=tu_password
   spring.jpa.hibernate.ddl-auto=update
    ```
3. Antes de ejecutar la aplicación, debes crear una base de datos:
```
CREATE DATABASE forohub;
```
4. Ejecuta

    ```
   $ mvn spring-boot:run
   ```
5. Llama la API: http://localhost:8080


## 🗄️ BASE DE DATOS

Después de ejecutar la aplicacion, se crean las siguientes tablas:
- usuarios (id, login, password)
- topicos (id, titulo, mensaje, fecha_creacion, status, autor_id, curso)

Para probar los endpoints protegidos se debe crear un usuaro:
```
USE forohub;
-- donde la clave del usuario 'test' es 'password'
INSERT INTO usuarios (login, password) VALUES
('test', '$2a$12$aq4Jia6Xn1wU3q0jpDvjUeZienS9Og6oYUGutDE6t/tRutkArqBdS');
```
Para generar tu propia contraseña encriptada usa [Online Hash Generator & Checker.](https://bcrypt-generator.com)

## 📋 ENDPOINTS PRINCIPALES

---

Método | Endpoint        | Descripción         | Autenticación
-------|-----------------|---------------------|-------------
POST   | /login          | Autenticación       | ❌ No
GET    | /topicos        | Lista paginada      | ✅ Token
POST   | /topicos        | Crear tópico        | ✅ Token
GET    | /topicos/{id}   | Detalle tópico      | ✅ Token
PUT    | /topicos/{id}   | Actualizar          | ✅ Token
DELETE | /topicos/{id}   | Eliminar            | ✅ Token

## Ejemplos
### Login
```
$ curl -X POST http://localhost:8080/login \
-H "Content-Type: application/json" \
-d '{"login":"test","password":"password"}'
```
Respuesta: {"token":"eyJhbGciOiJIUzI1NiIs...}

### Crear tópico
```
$ curl -X POST http://localhost:8080/topicos \
-H "Authorization: Bearer TU_TOKEN" \
-H "Content-Type: application/json" \
-d '{
"titulo": "Duda Spring Boot",
"mensaje": "Cómo configuro JWT?",
"curso": "G9",
"autor": 1
}'
```

### Pruebas Insomnia/Postman
1. Crea un request POST /login
2. Configura en Scripts → After-response:
    ```
    insomnia.test('Status 200', () => {
      insomnia.expect(insomnia.response.code).to.eql(200);
      const jsonBody = insomnia.response.json();
      insomnia.environment.set('bearer', jsonBody.token);
    });
   ```
3. Ejecuta la llamada para generar token ``bearer``
4. Crea un request GET /topicos
5. En la Authorization Token pega: {{bearer}}
6. Ejecuta la llamada para probar endpoint protegido.
