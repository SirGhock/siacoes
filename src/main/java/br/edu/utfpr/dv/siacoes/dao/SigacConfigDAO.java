package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.SigacConfig;

public class SigacConfigDAO extends TemplateDAO<SigacConfig> {
	
    @Override
    protected SigacConfig loadObject(ResultSet rs) throws SQLException{
        SigacConfig config = new SigacConfig();

        config.getDepartment().setIdDepartment(rs.getInt("idDepartment"));
        config.setMinimumScore(rs.getDouble("minimumScore"));
        config.setMaxFileSize(rs.getInt("maxfilesize"));

        return config;
    }

    @Override
    protected String getStringSQLFind() {
        return "SELECT * FROM sigacconfig WHERE idDepartment = ?";
    }

    @Override
    protected String getStringSQLInsert() {
        return "INSERT INTO sigacconfig(minimumScore, maxfilesize, idDepartment) VALUES(?, ?, ?)";
    }

    @Override
    protected void ormSave(PreparedStatement statement, SigacConfig objeto) throws SQLException {
        statement.setDouble(1, objeto.getMinimumScore());
        statement.setInt(2, objeto.getMaxFileSize());
        statement.setInt(3, objeto.getDepartment().getIdDepartment());
    }

    @Override
    protected String getStringSQLUpdate() {
        return "UPDATE sigacconfig SET minimumScore=?, maxfilesize=? WHERE idDepartment=?";
    }

}
