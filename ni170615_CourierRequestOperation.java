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
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_CourierRequestOperation implements CourierRequestOperation {
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertCourierRequest(String korisnickoIme, String registracioniBroj) {
        try (
            PreparedStatement ps = conn.prepareStatement("insert into Zahtev(KorisnickoIme, RegistracioniBroj) values (?, ?)");){
            ps.setString(1, korisnickoIme);
            ps.setString(2, registracioniBroj);
            
            if(ps.executeUpdate() > 0)
                return true;
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteCourierRequest(String korisnickoIme) {
        try (
            PreparedStatement ps = conn.prepareStatement("delete from Zahtev where KorisnickoIme = ?");){
            ps.setString(1, korisnickoIme);
            if(ps.executeUpdate() > 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeVehicleInCourierRequest(String korisnickoIme, String registracioniBroj) {
        try (
            PreparedStatement ps = conn.prepareStatement("update Zahtev set RegistracioniBroj = ? where KorisnickoIme = ?");){
            ps.setString(1, registracioniBroj);
            ps.setString(2, korisnickoIme);
            if(ps.executeUpdate() > 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllCourierRequests() {
        ArrayList<String> lista = new ArrayList<String>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from Zahtev");
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean grantRequest(String korisnickoIme) {
        try (
            CallableStatement cs = conn.prepareCall(" { call spOdobriZahtev (?, ?) }");){
            cs.setString(1, korisnickoIme);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.execute();
            return cs.getInt(2) == 1;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
