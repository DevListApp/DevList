package com.devlist.app.data.models;

import java.util.Date;

public class Task {
    private String id;
    private int concluido;
    private String titulo;
    private String notas;
    private Date horaInicio;
    private Date horaFim;
    private int prioridade;
    private String auth;
    private int resumoCount;

    private String resumoDate;

    public Task(String id, String titulo, String notas, Date horaInicio, Date horaFim, int prioridade, String authId, int concluido, int resumoCount, String resumoDate) {
        this.id = id;
        this.titulo = titulo;
        this.notas = notas;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.prioridade = prioridade;
        this.auth = authId;
        this.concluido = concluido;
        this.resumoDate = resumoDate;
        this.resumoCount = resumoCount;
    }
    public Task(){
    }

    public int getConcluido() {
        return concluido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}
