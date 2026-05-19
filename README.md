# KinalApp
Sistema de Gestion de Ventas, usuarios y clientes 
#  KinalApp - Sistema de Gestión de Ventas y Clientes

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green?logo=springboot)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-blue?logo=thymeleaf)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple?logo=bootstrap)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green?logo=springsecurity)
![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

##  Tabla de Contenido

- [Descripción](#-descripción)
- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [Roles y Permisos](#-roles-y-permisos)
- [API REST](#-api-rest)
- [Contribución](#-contribución)
- [Autor](#-autor)
- [Licencia](#-licencia)

---

##  Descripción

**KinalApp** es un sistema completo de gestión de ventas y clientes, desarrollado como proyecto académico para demostrar el dominio de tecnologías empresariales Java. La aplicación permite administrar de manera eficiente el catálogo de productos, la cartera de clientes y el registro de ventas, con control de acceso basado en roles.

###  Objetivo del Proyecto

Brindar una solución de software funcional que simule el entorno de trabajo de un negocio real, implementando buenas prácticas de desarrollo como:

- Arquitectura en capas (MVC)
- Principios SOLID
- Inyección de dependencias
- Seguridad con Spring Security
- API REST documentada
- Interfaz de usuario responsiva

---

##  Características

###  Autenticación y Autorización
- Login seguro con Spring Security
- Control de acceso basado en roles (RBAC)
- Sesiones protegidas
- Página de acceso denegado personalizada

###  Gestión de Clientes
- Registro de clientes con DPI único
- Búsqueda y filtrado de clientes
- Edición y eliminación con confirmación
- Visualización de historial de compras

###  Gestión de Productos
- Catálogo completo con descripciones
- Control de inventario (stock)
- Precios en quetzales (Q)
- Alertas visuales de stock bajo

###  Gestión de Ventas
- Punto de venta intuitivo
- Asociación cliente-producto
- Cálculo automático de totales
- Historial detallado de transacciones
- Vista de detalle imprimible

###  Dashboard
- Estadísticas en tiempo real
- Conteo de clientes, productos y ventas
- Últimas ventas registradas
- Acciones rápidas

###  API REST
- Endpoints CRUD para todas las entidades
- Respuestas en formato JSON
- Códigos HTTP semánticos

---

##  Tecnologías

| Categoría | Tecnología | Versión | Descripción |
|-----------|------------|---------|-------------|
| **Lenguaje** | Java | 17 | LTS, rendimiento y estabilidad |
| **Framework** | Spring Boot | 3.5.10 | Desarrollo rápido de aplicaciones |
| **Seguridad** | Spring Security | 6.x | Autenticación y autorización |
| **Vistas** | Thymeleaf | 3.1 | Motor de plantillas server-side |
| **Frontend** | Bootstrap | 5.3 | Framework CSS responsivo |
| **Iconos** | Bootstrap Icons | 1.11 | Iconografía profesional |
| **Persistencia** | Spring Data JPA | 3.5 | ORM y acceso a datos |
| **Base de Datos** | MySQL | 8.0 | Almacenamiento relacional |
| **Build** | Maven | 3.9 | Gestión de dependencias y build |
| **Servidor** | Apache Tomcat | 10.1 | Embebido en Spring Boot |

