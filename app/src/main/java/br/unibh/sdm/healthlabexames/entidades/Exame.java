package br.unibh.sdm.healthlabexames.entidades;

import java.util.Date;
import java.util.Objects;

public class Exame {

    private String matricula;
    private String tipoExame;
    private Date dataDoExame;

    public Exame() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(matricula, tipoExame, dataDoExame);
    }

    public String getNomeExame() {
        return tipoExame;
    }

    public Date getDataExame() {
        return dataDoExame;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNomeExame(String nomeExame) {
        this.tipoExame = nomeExame;
    }

    public void setDataExame(Date dataExame) {
        this.dataDoExame = dataExame;
    }

    @Override
    public String toString() {
        return " " + matricula + " | " + tipoExame + " | " + dataDoExame ;
    }
}
