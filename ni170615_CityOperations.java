/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.List;
import rs.etf.sab.operations.CityOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_CityOperations implements CityOperations{
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public int insertCity(String ime, String postanskiBroj) {
        try (
            PreparedStatement ps = conn.prepareStatement("insert into Grad(Naziv, PostanskiBroj) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, ime);
            ps.setString(2, postanskiBroj);
            
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next())
                    return rs.getInt(1);
            }catch(Exception e){
                
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int deleteCity(String... gradovi) {
        int br = 0;
        for(String grad: gradovi){
            try (
                PreparedStatement ps = conn.prepareStatement("delete from grad where Naziv = ?");){
                ps.setString(1, grad);
                br += ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return br;
    }

    @Override
    public boolean deleteCity(int idGrad) {
        try (
            PreparedStatement ps = conn.prepareStatement("delete from grad where IdG = ?");){
            ps.setInt(1, idGrad);
            if(ps.executeUpdate() > 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllCities() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from grad");
            while(rs.next()){
                lista.add(rs.getInt(1));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
