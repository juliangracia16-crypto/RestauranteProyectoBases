/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantenegocio.ClienteBO;
import com.mycompany.restaurantenegocio.IComandaBO;
import com.mycompany.restaurantenegocio.IMesaBO;
import com.mycompany.restaurantenegocio.IProductoBO;
import com.mycompany.restaurantenegocio.IReporteClientesFrecuentesBO;
import com.mycompany.restaurantenegocio.NegocioException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class FrmGestionClientes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FrmGestionClientes.class.getName());
    
    private final ClienteFrecuente cliente;
    private final ClienteBO clienteBO;
    private final IComandaBO comandaBO;
    private final IMesaBO mesaBO;
    private final IProductoBO productoBO;
    private final IReporteClientesFrecuentesBO reporteBO;
    private ClienteFrecuente clienteSeleccionado = null;
    private List<ClienteFrecuente> listaActual = new ArrayList<>();

    public FrmGestionClientes(ClienteFrecuente cliente, ClienteBO clienteBO, IComandaBO comandaBO, IMesaBO mesaBO, IProductoBO productoBO, IReporteClientesFrecuentesBO reporteBO) {
        this.cliente    = cliente;
        this.clienteBO  = clienteBO;
        this.comandaBO  = comandaBO;
        this.mesaBO     = mesaBO;
        this.productoBO = productoBO;
        this.reporteBO = reporteBO;
        initComponents();
        inicializarTabla();
        cargarClientes();
    }
    
    private void inicializarTabla() {
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new String[]{"Nombre", "Teléfono", "Correo", "Visitas", "Total ($)", "Puntos"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        });
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
    }

    private void cargarClientes() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            this.listaActual = new ArrayList<>();

            // Primero agregamos Cliente General
            try {
                clienteBO.crear(); // crea o retorna el existente
            } catch (NegocioException ex) {
                LOGGER.severe(ex.getMessage());
            }

            // Llenamos la tabla con frecuentes
            List<ClienteFrecuente> frecuentes = clienteBO.obtenerTodos();
            llenarTabla(frecuentes);

            // Agregamos fila de Cliente General al final
            modelo.addRow(new Object[]{
                "Cliente General", "—", "—", "—", "—", "—"
            });
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTabla(List<ClienteFrecuente> lista) {
        this.listaActual = lista;
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (ClienteFrecuente cf : lista) {
            modelo.addRow(new Object[]{
                cf.getNombre(), cf.getTelefono(), cf.getCorreo() != null ? cf.getCorreo() : "—", cf.getNumVisitas(), String.format("%.2f", cf.getTotalGastado()), cf.getPuntosAcumulados()
            });
        }
    }

    private ClienteFrecuente getClienteSeleccionado() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return listaActual.get(fila);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGestionClientes = new javax.swing.JPanel();
        lblClientesFrecuentes = new javax.swing.JLabel();
        lblClienteFrecuentes = new javax.swing.JLabel();
        btnNuevoCliente = new javax.swing.JButton();
        btnRegistrarClienteGeneral = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarPor = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnVerDetalles = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(191, 192, 192));

        pnlGestionClientes.setBackground(new java.awt.Color(67, 82, 90));

        lblClientesFrecuentes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClientesFrecuentes.setForeground(new java.awt.Color(255, 255, 255));
        lblClientesFrecuentes.setText("Clientes Frecuentes");

        javax.swing.GroupLayout pnlGestionClientesLayout = new javax.swing.GroupLayout(pnlGestionClientes);
        pnlGestionClientes.setLayout(pnlGestionClientesLayout);
        pnlGestionClientesLayout.setHorizontalGroup(
            pnlGestionClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGestionClientesLayout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(lblClientesFrecuentes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGestionClientesLayout.setVerticalGroup(
            pnlGestionClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGestionClientesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblClientesFrecuentes)
                .addContainerGap())
        );

        lblClienteFrecuentes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClienteFrecuentes.setForeground(new java.awt.Color(33, 84, 164));
        lblClienteFrecuentes.setText("Clientes Frecuentes");

        btnNuevoCliente.setBackground(new java.awt.Color(0, 128, 0));
        btnNuevoCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoCliente.setText("+ Nuevo Cliente");
        btnNuevoCliente.addActionListener(this::btnNuevoClienteActionPerformed);

        btnRegistrarClienteGeneral.setBackground(new java.awt.Color(255, 140, 0));
        btnRegistrarClienteGeneral.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrarClienteGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarClienteGeneral.setText("+ Registrar Cliente General");
        btnRegistrarClienteGeneral.addActionListener(this::btnRegistrarClienteGeneralActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Buscar por nombre / teléfono / correo:");

        txtBuscarPor.setToolTipText("");

        btnBuscar.setBackground(new java.awt.Color(23, 117, 209));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Télefono", "Correo", "Visitas", "Total ($)", "Puntos"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnVerDetalles.setBackground(new java.awt.Color(23, 117, 209));
        btnVerDetalles.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVerDetalles.setForeground(new java.awt.Color(255, 255, 255));
        btnVerDetalles.setText("Ver Detalles");
        btnVerDetalles.setToolTipText("");
        btnVerDetalles.addActionListener(this::btnVerDetallesActionPerformed);

        btnEditar.setBackground(new java.awt.Color(23, 117, 209));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnVolver.setBackground(new java.awt.Color(66, 66, 66));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGestionClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(267, 267, 267)
                                .addComponent(lblClienteFrecuentes))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnNuevoCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRegistrarClienteGeneral))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscarPor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addGap(0, 192, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnVerDetalles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVolver)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlGestionClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblClienteFrecuentes)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoCliente)
                    .addComponent(btnRegistrarClienteGeneral))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerDetalles)
                    .addComponent(btnEditar)
                    .addComponent(btnVolver))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,"Selecciona un cliente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si es Cliente General (última fila)
        if (fila >= listaActual.size()) {
            JOptionPane.showMessageDialog(this,
                "Nombre: Cliente General\n" +
                "Teléfono: —\n" +
                "Correo: —\n" +
                "Fecha Registro: —\n" +
                "Visitas: —\n" +
                "Total Gastado: —\n" +
                "Puntos: —\n" +
                "Última Visita: —",
                "Detalles del Cliente", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Si es Cliente Frecuente
        ClienteFrecuente sel = listaActual.get(fila);
        JOptionPane.showMessageDialog(this,
            "Nombre: " + sel.getNombre() + "\n" +
            "Teléfono: " + sel.getTelefono() + "\n" +
            "Correo: " + (sel.getCorreo() != null ? sel.getCorreo() : "—") + "\n" +
            "Fecha Registro: " + sel.getFechaRegistro() + "\n" +
            "Visitas: " + sel.getNumVisitas() + "\n" +
            "Total Gastado: $" + String.format("%.2f", sel.getTotalGastado()) + "\n" +
            "Puntos: " + sel.getPuntosAcumulados() + "\n" +
            "Última Visita: " + (sel.getFechaUltimaVisita() != null ? sel.getFechaUltimaVisita() : "Sin visitas"),
            "Detalles del Cliente", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si es Cliente General no se puede editar
        if (fila >= listaActual.size()) {
            JOptionPane.showMessageDialog(this, "No se puede editar el Cliente General.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si es Cliente Frecuente
        ClienteFrecuente sel = listaActual.get(fila);
        FrmRegistroCliente frame = new FrmRegistroCliente(this, true, clienteBO, sel);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarClientes();
            }
        });
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        FrmGestionDeComandas frame = new FrmGestionDeComandas(comandaBO, mesaBO, productoBO, clienteBO, cliente, reporteBO);
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            String termino = txtBuscarPor.getText().trim().toLowerCase();
            if (termino.isEmpty()) {
                cargarClientes();
                return;
            }
            List<ClienteFrecuente> todos = clienteBO.obtenerTodos();
            List<ClienteFrecuente> filtrados = new ArrayList<>();
            for (ClienteFrecuente cf : todos) {
                boolean coincide = cf.getNombre().toLowerCase().contains(termino) || (cf.getTelefono() != null && cf.getTelefono().contains(termino)) || (cf.getCorreo() != null && cf.getCorreo().toLowerCase().contains(termino));
                if (coincide){
                    filtrados.add(cf);
                }
            }
            llenarTabla(filtrados);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRegistrarClienteGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarClienteGeneralActionPerformed
        try {
            clienteBO.crear();
            JOptionPane.showMessageDialog(this, "Cliente General registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarClienteGeneralActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        FrmRegistroCliente frame = new FrmRegistroCliente(this, true, clienteBO, null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarClientes();
            }
        });
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnRegistrarClienteGeneral;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblClienteFrecuentes;
    private javax.swing.JLabel lblClientesFrecuentes;
    private javax.swing.JPanel pnlGestionClientes;
    private javax.swing.JTextField txtBuscarPor;
    // End of variables declaration//GEN-END:variables
}
