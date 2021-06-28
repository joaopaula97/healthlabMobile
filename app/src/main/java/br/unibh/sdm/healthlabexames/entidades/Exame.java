package br.unibh.sdm.healthlabexames.entidades;

import java.util.Date;
import java.util.Objects;

public class Exame {

    private String matricula;
    private String nomeExame;
    private Date dataExame;

    public Exame() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(matricula, nomeExame, dataExame);
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public Date getDataExame() {
        return dataExame;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataExame = dataExame;
    }


}
