package tn.esprit.service;



import tn.esprit.entities.Assurance;
        import tn.esprit.entities.Contrat;
import tn.esprit.utils.DataBase;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

public class Assurance_s implements Service<Assurance> {
    private Connection cnx ;
    private static Assurance_s instance;

    // Private constructor to prevent instantiation from outside
    public Assurance_s() {
        // Initialize the connection using the DataBase class
        this.cnx = DataBase.getInstance().getCnx();
    }

    // Method to get the singleton instance
    public static synchronized Assurance_s getInstance() {
        if (instance == null) {
            instance = new Assurance_s();
        }
        return instance;
    }

    @Override
    public void add(Assurance a) throws SQLException {
        if (cnx != null) {
            String qry = "INSERT INTO assurance (`nom`, `type`, `montant`, `date_debut`, `date_fin`,`contrat_id`) VALUES (?,?,?,?,?,?)";

            try {
                PreparedStatement pstm = cnx.prepareStatement(qry);
                pstm.setString(1, a.getNom());
                pstm.setString(2, a.getType());
                pstm.setFloat(3, a.getMontant());
                pstm.setString(4, a.getDate_debut());
                pstm.setString(5, a.getDate_fin());
                pstm.setInt(6, a.getContrat_id());

                pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void add(Contrat c, Connection conn) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        String qry = "DELETE FROM assurance WHERE id = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Assurance> show() throws SQLException {
        List<Assurance> assurances = new ArrayList<>();
        String qry = "SELECT * FROM assurance";

        try (PreparedStatement pstm = cnx.prepareStatement(qry);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Assurance assurance = new Assurance();
                assurance.setId(rs.getInt("id"));
                assurance.setNom(rs.getString("nom"));
                assurance.setType(rs.getString("type"));
                assurance.setMontant(rs.getFloat("montant"));
                assurance.setDate_debut(rs.getString("date_debut"));
                assurance.setDate_fin(rs.getString("date_fin"));
                assurance.setContrat_id(rs.getInt("contrat_id")); // Fetch and set the contrat_id
                assurance.setFavoris(rs.getBoolean("favoris"));
                assurances.add(assurance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return assurances;
    }


    @Override
    public void edit(Assurance assurance) throws SQLException {
        if (assurance != null) {
            String qry = "UPDATE assurance SET nom = ?, type = ?, montant = ?, date_debut = ?, date_fin = ?, contrat_id = ? WHERE id = ?";

            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setString(1, assurance.getNom());
                pstm.setString(2, assurance.getType());
                pstm.setFloat(3, assurance.getMontant());
                pstm.setString(4, assurance.getDate_debut());
                pstm.setString(5, assurance.getDate_fin());
                pstm.setInt(6, assurance.getContrat_id());
                pstm.setInt(7, assurance.getId());
                pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Cannot edit null Assurance object.");
        }
    }



    @Override
    public void update(Assurance assurance) throws SQLException {
        String qry = "UPDATE assurance SET nom = ?, type = ?, montant = ?, date_debut = ?, date_fin = ?, contrat_id = ?, favoris = ? WHERE id = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, assurance.getNom());
            pstm.setString(2, assurance.getType());
            pstm.setFloat(3, assurance.getMontant());
            pstm.setString(4, assurance.getDate_debut());
            pstm.setString(5, assurance.getDate_fin());
            pstm.setInt(6, assurance.getContrat_id());
            pstm.setBoolean(7, assurance.isFavoris());
            pstm.setInt(8, assurance.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

