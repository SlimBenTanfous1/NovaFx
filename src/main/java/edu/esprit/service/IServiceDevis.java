package edu.esprit.service;

import edu.esprit.entities.AvisRestau;

import java.util.List;


public interface IServiceDevis<T> {
    public  void ajouter(T t) ;
    public void modifier(T t) ;
    public void supprimer(int id) ;
    public List<T> getAll();
    public T getOneById(int id) ;
}
