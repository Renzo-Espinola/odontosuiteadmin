# 🦷 OdontoSuite Admin

Microservicio **administrativo y financiero** del sistema **OdontoSuite**, orientado a la gestión económica de clínicas odontológicas.

---

## 🎯 Objetivo

Centralizar y controlar:
- Ingresos y egresos de dinero
- Cierres de caja diarios
- Reportes financieros
- Turnos administrativos
- Seguridad y auditoría

Pensado para crecer hacia:
- Reportes avanzados
- Multi-clínica
- Control por roles
- Exportaciones contables

---

## 🏗️ Stack tecnológico

- Java 25
- Spring Boot 3.5.x
- Spring Web (REST)
- Spring Data JPA
- PostgreSQL
- Spring Security
- OAuth2 Resource Server (JWT)

---

## 🧱 Arquitectura

Arquitectura en capas:

```
web/
 └── controller
application/
 ├── service
 └── dto
domain/
 ├── model
 │   ├── entity
 │   └── enums
 └── repository
infrastructure/
```

---

## 💰 Gestión de Caja

### MovementNature

```java
public enum MovementNature {
    INCOME,
    EXPENSE
}
```

### MovementConcept

Conceptos odontológicos asociados a una naturaleza:

**Ingresos**
- CONSULTATION
- CLEANING
- FILLING
- ROOT_CANAL
- EXTRACTION
- ORTHODONTICS
- PROSTHESIS
- WHITENING
- CONTROL_VISIT
- OTHER_INCOME

**Egresos**
- MATERIALS
- LABORATORY
- SUPPLIERS
- RENT
- SERVICES
- SALARIES
- TAXES
- MAINTENANCE
- OTHER_EXPENSE

Cada concepto define internamente su naturaleza (`INCOME` / `EXPENSE`).

---

## 📄 MoneyMovement

Entidad que representa un movimiento de caja.

Campos principales:
- naturaleza (INCOME / EXPENSE)
- concepto odontológico
- método de pago
- monto y moneda
- paciente (opcional)
- turno (opcional)
- fecha de creación

### Regla de dominio

```java
if (request.concept().nature() != request.nature()) {
    throw new IllegalArgumentException(
        "El concepto no coincide con la naturaleza del movimiento"
    );
}
```

---

## 📦 Endpoints

### Movimientos de caja

- `POST /api/cash/movements`
- `GET  /api/cash/movements`
- `GET  /api/cash/movements/patient/{patientId}`

### Reportes

- `GET /api/reports/cash/summary`

### Cierre de caja

- `POST /api/cash/closures`
- `GET  /api/cash/closures/{date}`
- `GET  /api/cash/closures`

### Turnos

- `POST  /api/appointments`
- `PATCH /api/appointments/{id}/status`
- `GET   /api/appointments`
- `GET   /api/appointments/patient/{patientId}`

---

## 🔐 Seguridad

- OAuth2 Resource Server
- Autenticación JWT
- Roles:
    - ROLE_ADMIN
    - ROLE_STAFF

Los endpoints administrativos requieren autenticación salvo configuración explícita.

---

## 🧪 Datos de prueba

El sistema se probó con:
- múltiples ingresos y egresos
- distintos conceptos odontológicos
- distintos métodos de pago
- rangos de fechas para reportes

---

## 🚀 Próximos pasos

- Multi-clínica
- Reportes por profesional
- Dashboard financiero
- Exportación a Excel / PDF
- Integración con facturación electrónica
- Dockerización

---

## 📌 Proyecto relacionado

- OdontoSuite Patients (gestión clínica)
