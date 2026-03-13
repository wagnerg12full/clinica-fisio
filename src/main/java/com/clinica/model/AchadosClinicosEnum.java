package com.clinica.model;

public enum AchadosClinicosEnum {
    FRATURA("Fratura"),
    FERIDA_ULCERA("Ferida/úlcera"),
    LESOES_TECIDOS_MOLES("Lesões de tecidos moles"),
    EDEMA("Edema"),
    INIBICAO_MUSCULAR("Inibição muscular"),
    PONTOS_GATILHOS("Pontos gatilhos"),
    ADERENCIA_FIBROSE("Aderencia/Fibrose"),
    DEFICIT_MOBILIDADE("Defict de mobilidade"),
    DEFICIT_FLEXIBILIDADE("Defict de Flexibilidade"),
    FRAQUEZA("Fraqueza"),
    CINESIOFOBIA("Cinesiofobia"),
    DOR("Dor");

    private String descricao;

    AchadosClinicosEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
