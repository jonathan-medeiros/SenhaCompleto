/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.AtendimentoModel;
import util.ConnectionFactory;

/**
 *
 * @author jonathandamasiomedeiros
 */
public class AtendimentoController {

    private EntityManager entityManager = null;

    public int save(AtendimentoModel atendimentoModel) throws SQLException {

        System.out.println("Estamos inserindo o atendimento no banco de dados utilizando JPA :) ");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(atendimentoModel);

        entityManager.getTransaction().commit();

        entityManager.close();

        return atendimentoModel.getId();

    }

    public List<AtendimentoModel> getAll() throws SQLException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        try {
            Query query = entityManager.createQuery("from AtendimentoModel");
            return query.getResultList();
        } finally {
            entityManager.close();
        }

    }

    public AtendimentoModel getFirst() throws SQLException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        try {
            Query query = entityManager.createQuery("from AtendimentoModel order by id desc");
            query.setMaxResults(1);
            return (AtendimentoModel) query.getSingleResult();
        } finally {
            entityManager.close();
        }
        
    }

    public List<AtendimentoModel> getNextList() throws SQLException {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        try {
            Query query = entityManager.createQuery("from AtendimentoModel where status = :status order by id asc");
            query.setParameter("status", 0);
            query.setMaxResults(3);
            return query.getResultList();
        } finally {
            entityManager.close();
        }

    }

    public List<AtendimentoModel> getChamadosList() throws SQLException {

       EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        try {
            Query query = entityManager.createQuery("from AtendimentoModel where status = :status order by id asc");
            query.setParameter("status", 2);
            query.setMaxResults(3);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
        
    }

    public AtendimentoModel getChamado() throws SQLException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        try {
            Query query = entityManager.createQuery("from AtendimentoModel where status = :status order by id asc");
            query.setParameter("status", 0);
            query.setMaxResults(1);
            return (AtendimentoModel) query.getSingleResult();
        } finally {
            entityManager.close();
        }
        
    }

    public void updateJaAtendido() throws SQLException {
        String sql = "UPDATE ATENDIMENTO SET STATUS = 2 "
                + "WHERE STATUS = 1";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao tentar atualizar para clientes já atendidos" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(conn, statement);
        }
    }

    public void update(AtendimentoModel atendimentoModel) throws SQLException {
         System.out.println("Estamos inserindo o atendimento no banco de dados utilizando JPA :) ");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.merge(atendimentoModel);

        entityManager.getTransaction().commit();

        entityManager.close();

    }

    public static void main(String[] args) {

        List<AtendimentoModel> a;
        try {
            a = new AtendimentoController().getAll();
            for (AtendimentoModel atendimentoModel : a) {
                System.out.println(atendimentoModel.toString());
            }
            
            System.out.println(new AtendimentoController().getFirst());
            
            a = new AtendimentoController().getNextList();
            for (AtendimentoModel atendimentoModel : a) {
                System.out.println("NEXTLIST " + atendimentoModel.toString());
            }
            
            System.out.println("CHAMADO " + new AtendimentoController().getChamado());
            
        } catch (SQLException ex) {
            Logger.getLogger(AtendimentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
                

    }
}
