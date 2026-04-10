/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantenegocio.NegocioException;
import com.mycompany.restaurantenegocio.ObjetosBoDTO;
import java.awt.Frame;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daren
 */
public class FrmListaProductos extends javax.swing.JPanel {

    private DefaultTableModel modeloTabla;
    private final ObjetosBoDTO objetosBO;

    public FrmListaProductos() {
        initComponents();
        configurarCombo();
        configurarTabla();
        configurarBotones();
        configurarEventos();
        cargarProductos();
    }

    private void configurarCombo() {
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Nombre", "Tipo"}
        ));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Tipo", "Precio", "Estado", "Disponible"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProductos.setModel(modeloTabla);
        //tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductos.getTableHeader().setReorderingAllowed(false); // BRO

        tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblProductos.getColumnModel().getColumn(0).setWidth(0);

        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblProductos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblProductos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(80);
    }

    private void configurarBotones() {
        btnEditar.setEnabled(false);
        btnCambiarEstado.setEnabled(false);
    }

    private void configurarEventos() {
        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            boolean haySeleccion = tblProductos.getSelectedRow() != -1;
            btnEditar.setEnabled(haySeleccion);
            btnCambiarEstado.setEnabled(haySeleccion);
        });

        btnNuevo.addActionListener(e -> accionNuevo());
        btnLimpiar.addActionListener(e -> accionLimpiar());
    }

    private void cargarProductos() {
        modeloTabla.setRowCount(0);
        try {
            List<Producto> productos = objetosBO.getProductoBO().obtenerTodos();
            for (Producto p : productos) {
                modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTipo().toString(),
                    String.format("$%.2f", p.getPrecio()),
                    p.getEstado().toString(),
                    p.isDisponible() ? "Sí" : "No" // ternario
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Producto obtenerProductoSeleccionado() {
        int fila = tblProductos.getSelectedRow();
        if (fila == -1) {
            return null;
        }
        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        try {
            return objetosBO.getProductoBO().buscarPorId(id);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener producto: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void accionNuevo() {
        Frame padre = (Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FrmRegistroProducto dialogo = new FrmRegistroProducto(padre, true, objetosBO);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
        cargarProductos();
    }

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        String filtro = cmbFiltro.getSelectedItem().toString();

        if (texto.isEmpty()) {
            cargarProductos();
            return;
        }

        try {
            List<Producto> todos = objetosBO.getProductoBO().obtenerTodos();
            modeloTabla.setRowCount(0);
            for (Producto p : todos) {
                boolean coincide = switch (filtro) {
                    case "Nombre" ->
                        p.getNombre().toLowerCase().contains(texto);
                    case "Tipo" ->
                        p.getTipo().toString().toLowerCase().contains(texto);
                    default ->
                        false;
                };
                if (coincide) {
                    modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getTipo().toString(),
                        String.format("$%.2f", p.getPrecio()),
                        p.getEstado().toString(),
                        p.isDisponible() ? "Sí" : "No" //ternario
                    });
                }
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionLimpiar() {
        txtBuscar.setText("");
        cmbFiltro.setSelectedIndex(0);
        cargarProductos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscar = new javax.swing.JTextField();
        cmbFiltro = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrpProductos = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCambiarEstado = new javax.swing.JButton();

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtBuscar.setText("Buscar...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        cmbFiltro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnBuscar.setBackground(new java.awt.Color(12, 70, 160));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar");

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        scrpProductos.setViewportView(tblProductos);

        btnNuevo.setBackground(new java.awt.Color(0, 128, 0));
        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevo.setText("Nuevo");

        btnEditar.setBackground(new java.awt.Color(16, 109, 88));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnCambiarEstado.setBackground(new java.awt.Color(23, 117, 209));
        btnCambiarEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCambiarEstado.setText("Cambiar Estado");
        btnCambiarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrpProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimpiar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar)
                                .addGap(52, 52, 52)
                                .addComponent(btnCambiarEstado)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrpProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnCambiarEstado))
                .addContainerGap(95, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        accionBuscar();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        accionBuscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCambiarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarEstadoActionPerformed
        Producto producto = obtenerProductoSeleccionado();
        if (producto == null) {
            return;
        }

        EstadoProducto nuevoEstado = producto.getEstado() == EstadoProducto.ACTIVO
                ? EstadoProducto.INACTIVO
                : EstadoProducto.ACTIVO;

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Deseas cambiar el estado a " + nuevoEstado + "?",
                "Cambiar Estado", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                objetosBO.getProductoBO().cambiarEstado(producto.getId(), nuevoEstado);
                JOptionPane.showMessageDialog(this,
                        "Estado actualizado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarProductos();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCambiarEstadoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        Producto producto = obtenerProductoSeleccionado();
        if (producto == null) {
            return;
        }
        Frame padre = (Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        FrmRegistroProducto dialogo = new FrmRegistroProducto(padre, true, objetosBO, producto);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
        cargarProductos();
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JScrollPane scrpProductos;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
