package com.odontosuiteadmin.domain.model.enums;

public enum MovementConcept {
    // Ingresos
    CONSULTATION(MovementNature.INCOME), // Consulta
    CLEANING(MovementNature.INCOME), // Limpieza
    FILLING(MovementNature.INCOME), // Obturación
    ROOT_CANAL(MovementNature.INCOME), // Endodoncia
    EXTRACTION(MovementNature.INCOME), // Extracción
    ORTHODONTICS(MovementNature.INCOME), // Ortodoncia
    PROSTHESIS(MovementNature.INCOME), // Prótesis
    WHITENING(MovementNature.INCOME), // Blanqueamiento
    CONTROL_VISIT(MovementNature.INCOME), // Control
    OTHER_INCOME(MovementNature.INCOME), // Otro ingreso

    // Egresos
    MATERIALS(MovementNature.EXPENSE), // Insumos odontológicos
    LABORATORY(MovementNature.EXPENSE), // Laboratorio dental
    SUPPLIERS(MovementNature.EXPENSE), // Proveedores
    RENT(MovementNature.EXPENSE), // Alquiler
    SERVICES(MovementNature.EXPENSE), // Luz / Agua / Internet
    SALARIES(MovementNature.EXPENSE), // Sueldos
    TAXES(MovementNature.EXPENSE), // Impuestos
    MAINTENANCE(MovementNature.EXPENSE), // Mantenimiento
    OTHER_EXPENSE(MovementNature.EXPENSE); // Otro gasto

    private final MovementNature nature;

    MovementConcept(MovementNature nature) {
        this.nature = nature;
    }

    public MovementNature nature() {
        return nature;
    }
}
