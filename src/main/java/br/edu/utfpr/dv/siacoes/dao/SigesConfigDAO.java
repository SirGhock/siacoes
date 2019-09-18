package br.edu.utfpr.dv.siacoes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.SigesConfig;
import br.edu.utfpr.dv.siacoes.model.SigetConfig.SupervisorFilter;

public class SigesConfigDAO extends TemplateDAO<SigesConfig>{

    @Override
    protected SigesConfig loadObject(ResultSet rs) throws SQLException {
        SigesConfig config = new SigesConfig();

        config.getDepartment().setIdDepartment(rs.getInt("idDepartment"));
        config.setMinimumScore(rs.getDouble("minimumScore"));
        config.setSupervisorPonderosity(rs.getDouble("supervisorPonderosity"));
        config.setCompanySupervisorPonderosity(rs.getDouble("companySupervisorPonderosity"));
        config.setShowGradesToStudent(rs.getInt("showgradestostudent") == 1);
        config.setSupervisorFilter(SupervisorFilter.valueOf(rs.getInt("supervisorfilter")));
        config.setSupervisorFillJuryForm(rs.getInt("supervisorFillJuryForm") == 1);
        config.setMaxFileSize(rs.getInt("maxfilesize"));
        config.setJuryTime(rs.getInt("jurytime"));

        return config;
    }

    @Override
    protected String getStringSQLFind() {
        return "SELECT * FROM sigesconfig WHERE idDepartment = ?";
    }

    @Override
    protected String getStringSQLInsert() {
       return "INSERT INTO sigesconfig(minimumScore, supervisorPonderosity, companySupervisorPonderosity, showgradestostudent, supervisorfilter, supervisorFillJuryForm, maxfilesize, jurytime, idDepartment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void ormSave(PreparedStatement statement, SigesConfig objeto) throws SQLException {
        statement.setDouble(1, objeto.getMinimumScore());
        statement.setDouble(2, objeto.getSupervisorPonderosity());
        statement.setDouble(3, objeto.getCompanySupervisorPonderosity());
        statement.setInt(4, objeto.isShowGradesToStudent() ? 1 : 0);
        statement.setInt(5, objeto.getSupervisorFilter().getValue());
        statement.setInt(6, objeto.isSupervisorFillJuryForm() ? 1 : 0);
        statement.setInt(7, objeto.getMaxFileSize());
        statement.setInt(8, objeto.getJuryTime());
        statement.setInt(9, objeto.getDepartment().getIdDepartment()); statement.setDouble(1, objeto.getMinimumScore());
        statement.setDouble(2, objeto.getSupervisorPonderosity());
        statement.setDouble(3, objeto.getCompanySupervisorPonderosity());
        statement.setInt(4, objeto.isShowGradesToStudent() ? 1 : 0);
        statement.setInt(5, objeto.getSupervisorFilter().getValue());
        statement.setInt(6, objeto.isSupervisorFillJuryForm() ? 1 : 0);
        statement.setInt(7, objeto.getMaxFileSize());
        statement.setInt(8, objeto.getJuryTime());
        statement.setInt(9, objeto.getDepartment().getIdDepartment());
    }

    @Override
    protected String getStringSQLUpdate() {
        return "UPDATE sigesconfig SET minimumScore=?, supervisorPonderosity=?, companySupervisorPonderosity=?, showgradestostudent=?, supervisorfilter=?, supervisorFillJuryForm=?, maxfilesize=?, jurytime=? WHERE idDepartment=?";
    }

}
