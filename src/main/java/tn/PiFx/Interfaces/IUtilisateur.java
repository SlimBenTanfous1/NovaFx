package tn.PiFx.Interfaces;

import tn.PiFx.entities.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public interface IUtilisateur<T> {
    boolean Add (T t);
    ArrayList<T> getAll();
    List<T> afficher();
    List<T> TriparNom();
    List<T> TriparEmail();
    List<T> Rechreche(String recherche);
    boolean Update(T t);

   // boolean Update(Connection connection, User user);

    void Delete(T t);
    void DeleteByID(int id);
}
