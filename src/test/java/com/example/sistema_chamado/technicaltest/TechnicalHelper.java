package com.example.sistema_chamado.technicaltest;

import com.example.sistema_chamado.enums.Perfil;
import com.example.sistema_chamado.models.Technical;

public class TechnicalHelper {
    public  static Technical buildSaveTechnical () {
        Technical technical = new Technical();
        technical.setId(1);
        technical.setName("Wilson Tech");
        technical.setEmail("Wilson.Tech@gmail.com");
        technical.setPassword("12345");
        technical.addPerfil(Perfil.TECH);

        return technical;
    }
}
