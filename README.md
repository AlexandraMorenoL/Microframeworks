# Microframeworks
# Servidor Web ligero + Servicios REST — TALLER: Arquitectura de Aplicaciones Distribuidas

**Alumno:** Alexandra Moreno Latorre  
**Asignatura:** Arquitectura Empresarial  
**Docente:** Luis Daniel Benavides Navarro  

Este repositorio contiene implementaciones de un **servidor web ligero en Java** desarrollado desde cero con **sockets TCP**, capaz de servir archivos estáticos y exponer servicios REST básicos.  

Incluye:
- Un **servidor HTTP** minimalista que maneja peticiones GET/POST.  
- Un **router dinámico** para definir rutas REST con funciones Lambda.  
- Soporte para **archivos estáticos** (HTML, CSS, JS, imágenes).  
- Un **cliente HTTP básico** para pruebas desde consola.  

---

## 📑 Tabla de contenidos
1. [Descripción general](#-descripción-general)  
2. [Características](#-características)  
3. [Requisitos](#-requisitos)  
4. [Compilar y ejecutar](#compilar-y-ejecutar)  
5. [Estructura del proyecto](#-estructura-del-proyecto)  
6. [Instalación](#-instalación)
7. [Contacto](#-contacto)  

---

## 📌 Descripción general
El proyecto implementa un **servidor HTTP desde cero** sin usar frameworks externos, únicamente con las librerías estándar de Java.  

- **HTTP Server (puerto 35000):**  
  Maneja peticiones **GET/POST**, sirve archivos estáticos desde un directorio (`/webroot`) y permite definir rutas REST dinámicas.  

- **Cliente HTTP (opcional):**  
  Permite enviar peticiones GET desde consola para probar el servidor.  

El objetivo es comprender:
- Cómo funcionan las conexiones cliente-servidor sobre **TCP**.  
- El protocolo HTTP a bajo nivel.  
- El diseño de un router que asocia rutas con funciones de manejo.  

---

## ⚙️ Características
- **Servidor HTTP:**
  - Manejo de rutas dinámicas (`Router.get("/hello", handler)`).
  - Servir archivos estáticos (HTML, JS, imágenes, CSS).
  - Respuestas con cabeceras correctas (`Content-Type`, `Content-Length`).
  - Manejo de errores (`404 Not Found`, `500 Internal Server Error`).
  - Soporta parámetros de consulta (`?name=Alex`).  

- **Cliente HTTP:**
  - Envía solicitudes GET simples.
  - Muestra respuesta en consola.  

- **Código portable:** no requiere librerías externas.  

---

## 🖥️ Requisitos
- **Java JDK 11+** (se recomienda JDK 17).  
- **Maven 3.6+** para compilar y ejecutar.  

---

## 🚀 Compilar y ejecutar
Rutas dinámicas:
```bash
http://localhost:35000/hello?name=Alex → Respuesta: Hello Alex
http://localhost:35000/pi → Respuesta: valor de π.
```

Archivos estáticos:
```bash
Acceder a http://localhost:35000/index.html.
```
## 1. Compilar el proyecto
Corriendo la clase App.java, ubicada en la carpeta example 

--- 
## 📂 Estructura del proyecto
src/main/java/
│── co/edu/escuelaing/httpserver/
│   ├── FunctionalHandler.java
│   ├── HttpRequest.java
│   ├── HttpResponse.java
│   ├── Router.java
│   ├── Server.java
│   ├── HttpServer.java
│   └── Client.java
│
└── co/edu/escuelaing/example/
    └── App.java   # Clase principal de ejemplo

Recursos estáticos (HTML/CSS/JS/img):
webroot/
│── index.html
│── style.css
└── js/

---

## 📥 Instalación

Clona el repositorio y compila con Maven:
```bash
git clone https://github.com/AlexandraMorenoL/Microframeworks.git
mvn clean install
```

---  
## 📧 Contacto

Alexandra Moreno Latorre
Email: alexandra.moreno-l@mail.escuelaing.edu.co
Universidad Escuela Colombiana de Ingeniería Julio Garavito
