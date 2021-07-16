package com.main;


// Classe que representa a estrutura da tabela de lexemas
public class LinhaTabela {

    public int id;
    public String lexema;
    public String token;
    public String valorInicial;
    public String escopo;
    public String linhas;
    public String colunas;
    public int linha;

    public LinhaTabela(int id) {
        this.id = id;
    }

    public LinhaTabela(int id, String lexema, String token, String valorInicial, String escopo, String linhas, String colunas, int linha) {
        this.id = id;
        this.lexema = lexema;
        this.token = token;
        this.valorInicial = valorInicial;
        this.escopo = escopo;
        this.linhas = linhas;
        this.colunas = colunas;
        this.linha = linha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public String getLinhas() {
        return linhas;
    }

    public void setLinhas(String linhas) {
        this.linhas = linhas;
    }

    public String getColunas() {
        return colunas;
    }

    public void setColunas(String colunas) {
        this.colunas = colunas;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }


}
