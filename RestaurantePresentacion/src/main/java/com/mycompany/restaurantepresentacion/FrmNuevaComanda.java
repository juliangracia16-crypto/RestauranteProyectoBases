/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.EstadoComanda;
import com.mycompany.restaurantedominio.Mesa;
import com.mycompany.restaurantedtos.ComandaDTO;
import com.mycompany.restaurantenegocio.IComandaBO;
import com.mycompany.restaurantenegocio.IMesaBO;
import com.mycompany.restaurantenegocio.NegocioException;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class FrmNuevaComanda extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FrmNuevaComanda.class.getName());
    
    private final IComandaBO comandaBO;
    private final IMesaBO mesaBO;
    private Comanda comandaEditar;
    private List<Mesa> listaMesas = new ArrayList<>();
    private static final String[] COLUMNA_PRODUCTOS = {"Producto", "Precio", "Tipo", "Disponible"};
    private static final String[] COLUMNA_COMANDA   = {"Producto", "Cant.", "P.Unit", "Subtotal", "Comentario"};
    
    public FrmNuevaComanda(IComandaBO comandaBO, IMesaBO mesaBO) {
        this.comandaBO = comandaBO;
        this.mesaBO = mesaBO;
        this.comandaEditar = null;
        initComponents();
        inicializarTablas();
        cargarMesasLibres();
        cargarClientes();
        txtFolio.setEditable(false);
        txtFolio.setText("Se generará automáticamente");
        txtTotal.setEditable(false);
        txtTotal.setText("$0.00");
    }
    
    public FrmNuevaComanda(IComandaBO comandaBO, IMesaBO mesaBO, Comanda comanda) {
        this.comandaBO = comandaBO;
        this.mesaBO = mesaBO;
        this.comandaEditar = comanda;
        initComponents();
        inicializarTablas();
        cargarMesasLibres();
        cargarClientes();
        cargarDatosComanda(comanda);
        txtTotal.setEditable(false);
    }
    
    private void inicializarTablas() {
        jTable1.setModel(new javax.swing.table.DefaultTableModel(COLUMNA_PRODUCTOS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(COLUMNA_COMANDA, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        });
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarMesasLibres() {
        try {
            cmbMesasLibres.removeAllItems();
            listaMesas = mesaBO.obtenerMesasLibres();
            for (Mesa m : listaMesas) {
                cmbMesasLibres.addItem("Mesa " + m.getNumeroMesa() + " (libre)");
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosComanda(Comanda comanda) {
        txtFolio.setText(comanda.getFolio());
        txtFolio.setEditable(false);
        txtTotal.setText("$" + String.format("%.2f", comanda.getTotal()));
        // Seleccionar el cliente en el combo
        if (comanda.getCliente() instanceof ClienteFrecuente) {
            // Buscar el cliente frecuente en el combo
            for (int i = 1; i < cmbBuscarClientes.getItemCount(); i++) {
                if (cmbBuscarClientes.getItemAt(i).toString().contains(comanda.getCliente().getNombre())) {
                    cmbBuscarClientes.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            //Cliente General siempre es el indice 0
            cmbBuscarClientes.setSelectedIndex(0);
        }

        cmbMesasLibres.addItem("Mesa " + comanda.getMesa().getNumeroMesa() + " (actual)");
        cmbMesasLibres.setSelectedItem("Mesa " + comanda.getMesa().getNumeroMesa() + " (actual)");

        if (comanda.getEstado() == EstadoComanda.ENTREGADA ||
            comanda.getEstado() == EstadoComanda.CANCELADA) {
            btnGuardar.setEnabled(false);
            btnEntregar.setEnabled(false);
            cmbMesasLibres.setEnabled(false);
            cmbBuscarClientes.setEnabled(false);
        }
    }
    
    private void cargarClientes() {
        try {
            cmbBuscarClientes.removeAllItems();
            ClienteDAO clienteDAO = new ClienteDAO();

            // Primero agregamos Cliente General
            ClienteGeneral cg = clienteDAO.crear();
            cmbBuscarClientes.addItem("Cliente General");

            // Luego agregamos todos los frecuentes
            List<ClienteFrecuente> frecuentes = clienteDAO.obtenerTodos();
            for (ClienteFrecuente cf : frecuentes) {
                cmbBuscarClientes.addItem(cf.getNombre() + " - " + cf.getTelefono());
            }
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblFolio = new javax.swing.JLabel();
        txtFolio = new javax.swing.JTextField();
        lblMesa = new javax.swing.JLabel();
        cmbMesasLibres = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lblBuscarPorductosParaAgregar = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProductos = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        btnBuscarProductosParaAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblProductosEnEstaComanda = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEntregar = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnVolver = new javax.swing.JButton();
        cmbBuscarClientes = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Nueva Comanda");

        lblFolio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFolio.setText("Folio:");

        txtFolio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lblMesa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMesa.setText("Mesa:");

        cmbMesasLibres.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Cliente:");

        lblBuscarPorductosParaAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBuscarPorductosParaAgregar.setText("BUSCAR PRODUCTOS PARA AGREGAR");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nombre:");

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipo.setText("Tipo:");

        cmbTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnBuscarProductosParaAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarProductosParaAgregar.setText("Buscar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Precio", "Tipo", "Disponible"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lblProductosEnEstaComanda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblProductosEnEstaComanda.setText("PRODUCTOS EN ESTA COMANDA");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Producto", "Cant.", "P.Unit", "Subtotal", "Comentario"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar Comanda");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnEntregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEntregar.setText("Entregar");
        btnEntregar.addActionListener(this::btnEntregarActionPerformed);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotal.setText("Total:");

        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        cmbBuscarClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbBuscarClientes.addActionListener(this::cmbBuscarClientesActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(84, 84, 84))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel1))
                    .addComponent(lblBuscarPorductosParaAgregar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombreProductos))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblFolio)
                                .addGap(18, 18, 18)
                                .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTipo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMesa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbMesasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnBuscarProductosParaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblProductosEnEstaComanda)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTotal)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEntregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFolio)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMesa)
                        .addComponent(cmbMesasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(cmbBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscarPorductosParaAgregar)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNombreProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipo)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarProductosParaAgregar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblProductosEnEstaComanda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar)
                            .addComponent(btnEntregar))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVolver))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnEntregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregarActionPerformed
        if (comandaEditar == null) {
            JOptionPane.showMessageDialog(this, "Primero guarda la comanda antes de entregarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas marcar la comanda como Entregada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                comandaBO.entregar(comandaEditar.getId());
                //Mesa regresa a LIBRE
                mesaBO.actualizarDisponibilidad(comandaEditar.getMesa().getId());
                JOptionPane.showMessageDialog(this, "Comanda entregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEntregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (cmbMesasLibres.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbBuscarClientes.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Mesa mesaSeleccionada = listaMesas.get(cmbMesasLibres.getSelectedIndex());

            // Obtenemos el cliente seleccionado del combo
            Long idCliente;
            if (cmbBuscarClientes.getSelectedIndex() == 0) {
                // Índice 0 = Cliente General
                idCliente = new ClienteDAO().crear().getIdCliente();
            } else {
                // Índice 1 en adelante = Cliente Frecuente
                List<ClienteFrecuente> frecuentes = new ClienteDAO().obtenerTodos();
                idCliente = frecuentes.get(cmbBuscarClientes.getSelectedIndex() - 1).getIdCliente();
            }

            if (comandaEditar == null) {
                ComandaDTO dto = new ComandaDTO(mesaSeleccionada.getId(), idCliente);
                Comanda nueva = comandaBO.crear(dto);
                this.comandaEditar = nueva;
                txtFolio.setText(nueva.getFolio());
                //Mesa pasa a OCUPADA
                mesaBO.actualizarDisponibilidad(mesaSeleccionada.getId());
                JOptionPane.showMessageDialog(this, "Comanda guardada con folio: " + nueva.getFolio(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Actualizar comanda existente
                ComandaDTO dto = new ComandaDTO(comandaEditar.getTotal());
                comandaBO.actualizar(comandaEditar.getId(), dto);
                JOptionPane.showMessageDialog(this, "Comanda actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            cargarDatosComanda(comandaEditar);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (comandaEditar == null) {
            JOptionPane.showMessageDialog(this, "Primero guarda la comanda antes de cancelarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas cancelar la comanda " + comandaEditar.getFolio() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                comandaBO.cancelar(comandaEditar.getId());
                //Mesa regresa a LIBRE
                mesaBO.actualizarDisponibilidad(comandaEditar.getMesa().getId());
                JOptionPane.showMessageDialog(this, "Comanda cancelada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        FrmGestionComandas frame = new FrmGestionComandas(comandaBO, mesaBO);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void cmbBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBuscarClientesActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarProductosParaAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEntregar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbBuscarClientes;
    private javax.swing.JComboBox<String> cmbMesasLibres;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblBuscarPorductosParaAgregar;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblMesa;
    private javax.swing.JLabel lblProductosEnEstaComanda;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
