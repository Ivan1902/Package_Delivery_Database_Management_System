/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.DistrictOperations;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_DistrictOperations implements DistrictOperations {
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public int insertDistrict(String ime, int idGrad, int xCord, int yCord) {
        try (
            PreparedStatement ps = conn.prepareStatement("insert into Opstina(Naziv, IdG, Xkoord, Ykoord) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, ime);
            ps.setInt(2, idGrad);
            ps.setInt(3, xCord);
            ps.setInt(4, yCord);
            
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next())
                    return rs.getInt(1);
            }catch(Exception e){
                
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteDistricts(String... opstine) {
        int br = 0;
        for(String opstina: opstine){
            try (
                PreparedStatement ps = conn.prepareStatement("delete from Opstina where Naziv = ?");){
                ps.setString(1, opstina);
                br += ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return br;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteDistrict(int idOpstina) {
        try (
            PreparedStatement ps = conn.prepareStatement("delete from opstina where IdO = ?");){
            ps.setInt(1, idOpstina);
            if(ps.executeUpdate() != 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteAllDistrictsFromCity(String grad) {
        int br = 0;
        try (
            PreparedStatement ps = conn.prepareStatement("delete from opstina where IdG IN (select IdG from grad where Naziv = ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, grad);
            br += ps.executeUpdate();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return br;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getAllDistrictsFromCity(int idGrad) {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from opstina where IdG = ?");){
            ps.setInt(1, idGrad);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getInt(1));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista.size() == 0 ? null : lista ;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getAllDistricts() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from opstina");
            while(rs.next()){
                lista.add(rs.getInt(1));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
