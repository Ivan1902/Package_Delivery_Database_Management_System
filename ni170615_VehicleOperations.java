/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.VehicleOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_VehicleOperations implements VehicleOperations {
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertVehicle(String registracioniBroj, int tipGoriva, BigDecimal potrosnja) {
        try (
            PreparedStatement ps = conn.prepareStatement("insert into Vozilo(RegistracioniBroj, TipGoriva, Potrosnja) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, registracioniBroj);
            ps.setInt(2, tipGoriva);
            ps.setBigDecimal(3, potrosnja);
            
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next())
                    return true;
            }catch(Exception e){
                
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteVehicles(String... vozila) {
        int br = 0;
        for(String vozilo: vozila){
            try (
                PreparedStatement ps = conn.prepareStatement("delete from Vozilo where RegistracioniBroj = ?");){
                ps.setString(1, vozilo);
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
    public List<String> getAllVehichles() {
        LinkedList<String> lista = new LinkedList<String>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from Vozilo");
            while(rs.next()){
                lista.add(rs.getString("RegistracioniBroj"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeFuelType(String registracioniBroj, int tip) {
        try (
            PreparedStatement ps = conn.prepareStatement("update Vozilo set TipGoriva = ? where RegistracioniBroj = ?");){
            ps.setInt(1, tip);
            ps.setString(2, registracioniBroj);
            if(ps.executeUpdate() > 0){
                return true;
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeConsumption(String registracioniBroj, BigDecimal potrosnja) {
        try (
            PreparedStatement ps = conn.prepareStatement("update Vozilo set Potrosnja = ? where RegistracioniBroj = ?");){
            ps.setBigDecimal(1, potrosnja);
            ps.setString(2, registracioniBroj);
            if(ps.executeUpdate() > 0){
                return true;
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
