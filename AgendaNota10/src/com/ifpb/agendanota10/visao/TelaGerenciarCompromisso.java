/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpb.agendanota10.visao;

import com.ifpb.agendanota10.controle.CompromissoDao;
import com.ifpb.agendanota10.controle.CompromissoDaoBanco;
import com.ifpb.agendanota10.controle.CompromissoDaoBinario;
import com.ifpb.agendanota10.entidade.Compromisso;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ricar
 */
public class TelaGerenciarCompromisso extends javax.swing.JFrame {

    private static CompromissoDao dao;

    public TelaGerenciarCompromisso() {
        dao = new CompromissoDaoBinario();
        initComponents();
        ImageIcon imagemTituloJanela = new ImageIcon("C:\\Users\\ricar\\Documents\\NetBeansProjects\\AgendaNota10\\img\\icone.png");
        setIconImage(imagemTituloJanela.getImage());
        atualizarTabela();
    }

    public static void atualizarTabelaIntervalo() throws IOException, SQLException {
        List<Compromisso> compromissos;
        jTable1.removeAll();
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            compromissos = dao.list(jDateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), jDateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), "Todas");
            String[] cabecalho = {"Data", "Hora", "Compromisso", "Agenda"};
            String[][] compromissosMat = new String[compromissos.size()][4];
            for (int i = 0; i < compromissos.size(); i++) {
                Compromisso comp = compromissos.get(i);
                compromissosMat[i][0] = comp.getData().format(df);
                compromissosMat[i][1] = comp.getHora().getHour() + ":" + comp.getHora().getMinute();
                compromissosMat[i][2] = comp.getDescricao();
                compromissosMat[i][3] = comp.getAgenda();

            }
            System.out.println(compromissosMat);
            jTable1.removeAll();
            DefaultTableModel modelo = new DefaultTableModel(compromissosMat, cabecalho);
            jTable1.setModel(modelo);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            JOptionPane.showMessageDialog(jTable1.getRootPane().getContentPane(), "Falha na conexão");
        }
    }

    public static void atualizarTabela() {
        List<Compromisso> compromissos;
        jTable1.removeAll();
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            compromissos = dao.list();
            String[] cabecalho = {"Data", "Hora", "Compromisso", "Agenda"};
            String[][] compromissosMat = new String[compromissos.size()][4];
            for (int i = 0; i < compromissos.size(); i++) {
                Compromisso comp = compromissos.get(i);
                compromissosMat[i][0] = comp.getData().format(df);
                compromissosMat[i][1] = comp.getHora().getHour() + ":" + comp.getHora().getMinute();
                compromissosMat[i][2] = comp.getDescricao();
                compromissosMat[i][3] = comp.getAgenda();

            }
            System.out.println(compromissosMat);
            jTable1.removeAll();
            DefaultTableModel modelo = new DefaultTableModel(compromissosMat, cabecalho);
            jTable1.setModel(modelo);

        } catch (ClassNotFoundException | IOException | SQLException ex) {
            JOptionPane.showMessageDialog(jTable1.getRootPane().getContentPane(), "Falha na conexão");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Gerenciar Compromisso");

        jLabel2.setText("de");

        jLabel3.setText("para");

        jButton1.setText("Atualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(170, 170, 170)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jButton1)
                                .addGap(71, 71, 71)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int k = jTable1.getSelectedRow();
        String data = (String) jTable1.getValueAt(k, 0);
        String hora = (String) jTable1.getValueAt(k, 1);
        String descricao = (String) jTable1.getValueAt(k, 2);
        String agenda = (String) jTable1.getValueAt(k, 3);
        TelaEditarCompromisso gerenciaCompromissos;
        try {
            Compromisso comp;
            DateTimeFormatter dft = DateTimeFormatter.ofPattern("H:m");
            comp = dao.read(LocalDate.parse(data), LocalTime.parse(hora, dft), agenda);
            gerenciaCompromissos = new TelaEditarCompromisso(comp);
            gerenciaCompromissos.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            atualizarTabelaIntervalo();
        } catch (IOException ex) {
            Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaGerenciarCompromisso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaGerenciarCompromisso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private static com.toedter.calendar.JDateChooser jDateChooser1;
    private static com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}