/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import rs.etf.sab.operations.PackageOperations.Pair;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_PackageOperationsPair<A,B> implements Pair {
    
    private A prviParam;
    private B drugiParam;
    
    ni170615_PackageOperationsPair(A prvi, B drugi){
        this.prviParam = prvi;
        this.drugiParam = drugi;
    }

    @Override
    public Object getFirstParam() {
        return prviParam;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getSecondParam() {
        return drugiParam;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
