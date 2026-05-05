# CineManager - Sistema de Gestión de Cine

Proyecto desarrollado para la materia de **Computación Avanzada en JAVA** en la Universidad Tecmilenio. Este sistema permite la gestión de reservaciones de asientos en tiempo real mediante una interfaz web interactiva.

Enlace del Proyecto en Vivo
**Despliegue en Render:** 

Tecnologías Utilizadas
- **Lenguaje:** Java 21
- **Framework:** Spring Boot 4.0.6
- **Base de Datos:** H2 Database (En memoria)
- **Frontend:** Thymeleaf, HTML5, CSS3 (Dark Mode) y JavaScript nativo.
- **Contenedor:** Docker para el despliegue.

Funcionalidades
- **CRUD Completo:** Registro, visualización y eliminación de boletos.
- **Lógica de Precios:** Diferenciación automática entre boletos General ($80) y VIP ($150).
- **Mapa de Asientos:** Selección visual e interactiva con coordenadas (A1-E5).
- **Validación de Ocupación:** Los asientos ya reservados se bloquean visualmente en rojo.

## 💻 Instalación Local
1. Clonar el repositorio: `git clone https://github.com/David04eng/cine.git`
2. Ejecutar el comando Maven: `./mvnw spring-boot:run`
3. Acceder a: `http://localhost:8080`