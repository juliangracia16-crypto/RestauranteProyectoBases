/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author daren
 */
public class FrmRegistroCliente extends javax.swing.JDialog {

    private Object clienteEditar; // cambiamos object por el cliente de verdad ya despues

    /**
     * Creates new form FrmRegistroCliente
     */
    
    // CONSTRUCTOR DEL MODO NUEVO
    public FrmRegistroCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.clienteEditar = null;
        configurarModo();
        configurarEventos();
    }

    
    // CONSTRUCTOR DE EL MODO EDITAR
    public FrmRegistroCliente(java.awt.Frame parent, boolean modal, Object cliente) {
        super(parent, modal);
        initComponents();
        this.clienteEditar = cliente;
        configurarModo();
        configurarEventos();
    }
    
    // ESTO CONFIGURA LOS ATRIBUTOS DE EL FORM SEGUN EL MODO
    private void configurarModo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (clienteEditar == null) {
            // MODO NUEVO
            lblTituloDinamico.setText("Nuevo Cliente");
            txtFechaRegistro.setText(LocalDate.now().format(formatter));
        } else {
            // MODO EDITAR 
            lblTituloDinamico.setText("Editar Cliente");
            

            // Cliente c = (Cliente) clienteEditar;
            // txtNombre.setText(c.getNombre());
            // txtTelefono.setText(c.getTelefono());
            // txtCorreo.setText(c.getCorreo());
            // txtFechaRegistro.setText(c.getFechaRegistro().format(formatter));
        }
    }
    
    
    private void configurarEventos() {
        lblGuardar.addActionListener(e -> accionGuardar());
        lblCancelar.addActionListener(e -> dispose());
    }
    
    private void accionGuardar() {
        if (!validarCampos()) return;

        String nombre   = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo   = txtCorreo.getText().trim();

        try {
            if (clienteEditar == null) {
                // MODO NUEVO
                // Cliente nuevo = new Cliente(nombre, telefono, correo, LocalDate.now());
                // ClienteBO.guardar(nuevo);
                JOptionPane.showMessageDialog(this,
                    "Cliente registrado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // MODO EDITAR
                // Cliente c = (Cliente) clienteEditar;
                // c.setNombre(nombre);
                // c.setTelefono(telefono);
                // c.setCorreo(correo);
                // ClienteBO.actualizar(c);
                JOptionPane.showMessageDialog(this,
                    "Cliente actualizado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        String nombre   = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo   = txtCorreo.getText().trim();

        // NOMBRE (OBLIGATORIO)
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre completo es obligatorio.",
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }

        // TELEFONO (OBLIGATORIO)
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El teléfono es obligatorio.",
                "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }

        // TELEFONO SOLO NUMEROS Y LONGITUD 10.
        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this,
                "El teléfono debe contener exactamente 10 dígitos numéricos.",
                "Formato inválido", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }

        // CORREO OPCIONAL, PERO SI SE LLENÓ DEBE TENER FORMATO VÁLIDO
        if (!correo.isEmpty() && !correo.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this,
                "El correo electrónico no tiene un formato válido.",
                "Formato inválido", JOptionPane.WARNING_MESSAGE);
            txtCorreo.requestFocus();
            return false;
        }

        // boolean existe = ClienteBO.existeTelefono(telefono, clienteEditar);
        // if (existe) {
        //     JOptionPane.showMessageDialog(this,
        //         "Ya existe un cliente registrado con ese número de teléfono.",
        //         "Duplicado", JOptionPane.WARNING_MESSAGE);
        //     return false;
        // }

        return true;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTituloDinamico = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblNombreCompleto = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblTelefono1 = new javax.swing.JLabel();
        lblTelefono2 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        txtFechaRegistro = new javax.swing.JTextField();
        lblTelefono3 = new javax.swing.JLabel();
        lblObligatorio2 = new javax.swing.JLabel();
        lblObligatorio3 = new javax.swing.JLabel();
        lblSoloLectura = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblGuardar = new javax.swing.JButton();
        lblCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        lblTituloDinamico.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloDinamico.setText("TITULO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(lblTituloDinamico)
                .addContainerGap(277, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblTituloDinamico)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        lblNombreCompleto.setText("Nombre Completo:");

        lblTelefono.setText("Telefono:");

        lblTelefono1.setText("Correo Electrónico:");

        lblTelefono2.setText("Fecha de Registro:");

        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        txtFechaRegistro.setEnabled(false);
        txtFechaRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaRegistroActionPerformed(evt);
            }
        });

        lblTelefono3.setFont(new java.awt.Font("Segoe UI", 2, 8)); // NOI18N
        lblTelefono3.setText("Opcional");

        lblObligatorio2.setFont(new java.awt.Font("Segoe UI", 2, 8)); // NOI18N
        lblObligatorio2.setText("Obligatorio");

        lblObligatorio3.setFont(new java.awt.Font("Segoe UI", 2, 8)); // NOI18N
        lblObligatorio3.setText("Obligatorio");

        lblSoloLectura.setFont(new java.awt.Font("Segoe UI", 2, 8)); // NOI18N
        lblSoloLectura.setText("Solo Lectura");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono3)
                    .addComponent(lblObligatorio2)
                    .addComponent(lblObligatorio3)
                    .addComponent(lblSoloLectura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefono2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblNombreCompleto)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefono)
                            .addComponent(lblTelefono1))))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCorreo)
                    .addComponent(txtFechaRegistro, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNombre)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreCompleto)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblObligatorio3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblObligatorio2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono1)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono3))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono2)
                    .addComponent(txtFechaRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSoloLectura))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblGuardar.setText("Guardar");

        lblCancelar.setText("Cancelar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGuardar))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGuardar)
                    .addComponent(lblCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtFechaRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaRegistroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaRegistroActionPerformed

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
            java.util.logging.Logger.getLogger(FrmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmRegistroCliente dialog = new FrmRegistroCliente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton lblCancelar;
    private javax.swing.JButton lblGuardar;
    private javax.swing.JLabel lblNombreCompleto;
    private javax.swing.JLabel lblObligatorio2;
    private javax.swing.JLabel lblObligatorio3;
    private javax.swing.JLabel lblSoloLectura;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTelefono2;
    private javax.swing.JLabel lblTelefono3;
    private javax.swing.JLabel lblTituloDinamico;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtFechaRegistro;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
