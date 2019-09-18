package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class TemplateDAO <T> {
    
    protected abstract String getStringSQLFind();
    protected abstract String getStringSQLInsert();
    protected abstract String getStringSQLUpdate();
    protected abstract T loadObject (ResultSet rs) throws SQLException;
    
    public final Object findByDepartment(int idDepartment) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql = getStringSQLFind();
        
        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idDepartment);

            rs = stmt.executeQuery();

            if(rs.next()){
                return this.loadObject(rs);
            }else{
                return null;
            }
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    
    }
    
    
    protected abstract void ormSave(PreparedStatement statement, T objeto) throws SQLException;
    
    public final int save (int idUser, T config) throws SQLException{
        boolean insert = (this.findByDepartment(config.getDepartment().getIdDepartment()) == null);
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String sqlInsert = getStringSQLInsert();
        String sqlUpdate = getStringSQLUpdate();
        
        try{
            conn = ConnectionDAO.getInstance().getConnection();

            if(insert){
                    stmt = conn.prepareStatement(sqlInsert);
            }else{
                    stmt = conn.prepareStatement(sqlUpdate);
            }
        
            ormSave(stmt, config);
            stmt.execute();
			
            new UpdateEvent(conn).registerUpdate(idUser, config);

            return config.getDepartment().getIdDepartment();
        }finally{
                if((stmt != null) && !stmt.isClosed())
                        stmt.close();
                if((conn != null) && !conn.isClosed())
                        conn.close();
        }
    }
    
}
