package tn.PiFx.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.PiFx.Interfaces.IUtilisateur;
import tn.PiFx.entities.User;
import tn.PiFx.utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceUtilisateurs implements IUtilisateur<User> {
    private Connection conx;
    public ServiceUtilisateurs(){conx = DataBase.getInstance().getConx();}


    @Override
    public void Add(User user) {
        String qry = "INSERT INTO `user`(`nom`, `prenom`, `adresse`, `email`, `num_tel`, `password`, `cin`, `role`) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stm = conx.prepareStatement(qry);
            stm.setString(1, user.getNom());
            stm.setString(2, user.getPrenom());
            stm.setString(3, user.getAdresse());
            stm.setString(4, user.getEmail());
            stm.setInt(5, user.getNum_tel());
            stm.setString(6, user.getPassword());
            stm.setInt(7, user.getCin());
            stm.setString(8, user.getRoles());

            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public List<User> afficher() {
        return null;
    }

    @Override
    public List<User> TriparNom() {
        return null;
    }

    @Override
    public List<User> TriparEmail() {
        return null;
    }

    @Override
    public List<User> Rechreche(String recherche) {
        return null;
    }

    @Override
    public void Update(User user) {

    }

    @Override
    public void Delete(User user) {

    }

    @Override
    public void DeleteByID(int id) {

    }
}
