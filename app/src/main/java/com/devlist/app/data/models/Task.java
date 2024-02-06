package com.devlist.app.data.models; // Pacote onde está localizada a classe

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import java.util.Date;

public class Task implements Parcelable {
    // Declaração de variáveis de instância para os atributos da tarefa
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

    // Construtor da classe
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

    // Construtor vazio necessário para a desserialização
    public Task() {}

    // Construtor para a desserialização de um objeto Parcelable
    protected Task(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        notas = in.readString();
        horaInicio = new Date(in.readLong());
        horaFim = new Date(in.readLong());
        prioridade = in.readInt();
        auth = in.readString();
        concluido = in.readInt();
    }

    // Método para escrever os valores dos atributos no Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titulo);
        dest.writeString(notas);
        dest.writeLong(horaInicio.getTime());
        dest.writeLong(horaFim.getTime());
        dest.writeInt(prioridade);
        dest.writeString(auth);
        dest.writeInt(concluido);
    }

    // Método necessário para implementar Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    // Criador estático necessário para implementar Parcelable
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    // Métodos de acesso aos atributos com anotações PropertyName para Firebase Firestore
    @PropertyName("concluido")
    public int getConcluido() {
        return concluido;
    }

    public void setConcluido(int concluido) {
        this.concluido = concluido;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("auth")
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @PropertyName("titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @PropertyName("notas")
    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @PropertyName("horaInicio")
    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    @PropertyName("horaFim")
    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    @PropertyName("prioridade")
    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    @PropertyName("resumoDate")
    public String getResumoDate() {
        return resumoDate;
    }

    public void setResumoDate(String resumoDate) {
        this.resumoDate = resumoDate;
    }

    @PropertyName("resumoCount")
    public int getResumoCount() {
        return resumoCount;
    }

    public void setResumoCount(int resumoCount) {
        this.resumoCount = resumoCount;
    }
}
