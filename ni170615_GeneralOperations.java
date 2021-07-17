/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import rs.etf.sab.operations.GeneralOperations;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_GeneralOperations implements GeneralOperations {

    Connection conn = DB.getInstance().getConnection();
    
    @Override
    public void eraseAll() {
        
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Administrator where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Zahtev where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Ponuda where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
         try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Paket where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Opstina where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Grad where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Kurir where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Korisnik where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try (
            Statement stmt = conn.createStatement();){
            stmt.execute("delete from Vozilo where 1=1");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
