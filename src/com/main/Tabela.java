package com.main;

import java.util.ArrayList;
import java.util.List;

public class Tabela {//Contrução da tabela

    public void exibeTabela(ArrayList<LinhaTabela> tabela){//Função utilizada para exibir tabela pelo terminal
        int tamTabela = tabela.size();
        int i=0;

        System.out.println("-------------TABELA-------------\n");
        for(i=0; i<tamTabela; i++){
            System.out.println("*********************************************************\n");
            System.out.println("\n[ID: "+tabela.get(i).getId()+"]");
            System.out.println("\n[LEXEMA: "+tabela.get(i).getLexema()+"]");
            System.out.println("\n[TOKEN: "+tabela.get(i).getToken()+"]");
            System.out.println("\n[VALOR INICIAL: "+tabela.get(i).getValorInicial()+"]");
            System.out.println("\n[ESCOPO: "+tabela.get(i).getEscopo()+"]");
            System.out.println("\n[LINHAS: "+tabela.get(i).getLinhas()+"]");
            System.out.println("\n[COLUNAS: "+tabela.get(i).getColunas()+"]");
            System.out.println("\n[LINHA: "+tabela.get(i).getLinha()+"]");
            System.out.println("*********************************************************\n");
        }
    }
}
