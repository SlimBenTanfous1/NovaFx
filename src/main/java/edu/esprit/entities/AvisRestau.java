package edu.esprit.entities;

import java.util.Objects;

public class AvisRestau {
    private int idR;
    private int id;
    private int idUser; // Nouvelle colonne ajoutée

    private String response;
    private String commentaire;
    private String status;

  /*  public AvisRestau(int idA, int idUser, String nomR, String commentaire) {
        this.idA =idA;
        this.idUser = idUser;
        this.nomR = nomR;
        this.commentaire = commentaire;
    }*/
    public AvisRestau(int id, String response, String status) {
        this.id = id;
        this.response = response;
        this.status = status;
    }



    public AvisRestau() {
    }



    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public  void setResponse(String Response) {
        this.response = Response;
    }

    @Override
    public String toString() {
        return "AvisRestau{" +
                "id=" + id +
                ",response='" + response + '\'' +
                ",status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisRestau that = (AvisRestau) o;
        return id == that.id &&
                Objects.equals(response, that.response) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,response,status);
    }
}
