/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoIngrediente;
import com.mycompany.restaurantedtos.ProductoDTO;
import com.mycompany.restaurantedtos.ProductoIngredienteDTO;
import com.mycompany.restaurantedtos.TipoProducto;
import com.mycompany.restaurantenegocio.NegocioException;
import com.mycompany.restaurantenegocio.ObjetosBoDTO;
import com.mycompany.restaurantenegocio.ProductoBO;
import com.mycompany.restaurantepersistencia.ProductoDAO;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FrmRegistroProducto extends javax.swing.JDialog {

    private final ObjetosBoDTO objetosBO;
    private Producto productoEditar;
    private DefaultTableModel modeloTabla;
    private byte[] imagenCargada;

    public FrmRegistroProducto(java.awt.Frame parent, boolean modal, ObjetosBoDTO objetosBO) {
        super(parent, modal);
        this.objetosBO = objetosBO;
        this.productoEditar = null;
        initComponents();
        configurarCombo();
        configurarTablaIngredientes();
        configurarEventos();
        configurarModo();
    }

    public FrmRegistroProducto(java.awt.Frame parent, boolean modal, ObjetosBoDTO objetosBO, Producto producto) {
        super(parent, modal);
        this.objetosBO = objetosBO;
        this.productoEditar = producto;
        initComponents();
        configurarCombo();
        configurarTablaIngredientes();
        configurarEventos();
        configurarModo();
    }

    private void configurarCombo() {
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{
                    TipoProducto.PLATILLO.toString(),
                    TipoProducto.BEBIDA.toString(),
                    TipoProducto.POSTRE.toString(),
                    TipoProducto.ENTRADA.toString(),
                    TipoProducto.OTRO.toString()
                }
        ));
    }

    private void configurarTablaIngredientes() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Ingrediente", "Unidad", "Cantidad"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblIngredientes.setModel(modeloTabla);
        tblIngredientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblIngredientes.getTableHeader().setReorderingAllowed(false);

        tblIngredientes.getColumnModel().getColumn(0).setMinWidth(0);
        tblIngredientes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblIngredientes.getColumnModel().getColumn(0).setWidth(0);

        tblIngredientes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblIngredientes.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblIngredientes.getColumnModel().getColumn(3).setPreferredWidth(80);
    }

    private void configurarModo() {
        if (productoEditar == null) {
            lblRegistroProducto.setText("Nuevo Producto");
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
        } else {
            lblRegistroProducto.setText("Editar Producto");
            txtNombre.setText(productoEditar.getNombre());
            txtDescripcion.setText(productoEditar.getDescripcion() != null ? productoEditar.getDescripcion() : "");
            txtPrecio.setText(String.valueOf(productoEditar.getPrecio()));
            cmbTipo.setSelectedItem(productoEditar.getTipo().toString());

            if (productoEditar.getImagen() != null) {
                imagenCargada = productoEditar.getImagen();
                mostrarImagen(imagenCargada);
            }

            if (productoEditar.getIngredientes() != null) {
                for (ProductoIngrediente pi : productoEditar.getIngredientes()) {
                    modeloTabla.addRow(new Object[]{
                        pi.getIngrediente().getId(),
                        pi.getIngrediente().getNombre(),
                        pi.getIngrediente().getUnidadMedida().toString(),
                        pi.getCantidad()
                    });
                }
            }
        }
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(e -> accionGuardar());
        btnCancelar.addActionListener(e -> dispose());
        btnCargarImagen.addActionListener(e -> accionCargarImagen());
        btnAgregarIngrediente.addActionListener(e -> accionAgregarIngrediente());
        btnEliminarIngrediente.addActionListener(e -> accionEliminarIngrediente());
    }

    private void accionGuardar() {
        if (!validarCampos()) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        TipoProducto tipo = TipoProducto.valueOf(cmbTipo.getSelectedItem().toString());

        List<ProductoIngredienteDTO> ingredientes = new ArrayList<>();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            Long idIngrediente = (Long) modeloTabla.getValueAt(i, 0);
            Integer cantidad = (Integer) modeloTabla.getValueAt(i, 3);
            ingredientes.add(new ProductoIngredienteDTO(idIngrediente, cantidad));
        }

        try {
            if (productoEditar == null) {
                ProductoDTO dto = new ProductoDTO(nombre, descripcion, precio, tipo, ingredientes);
                dto.setImagen(imagenCargada);
                objetosBO.getProductoBO().registrar(dto);
                JOptionPane.showMessageDialog(this, "Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                ProductoDTO dto = new ProductoDTO(
                        productoEditar.getId(), nombre, descripcion, precio,
                        tipo, productoEditar.getEstado(), ingredientes
                );
                dto.setImagen(imagenCargada);
                objetosBO.getProductoBO().actualizar(dto);
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionCargarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                imagenCargada = Files.readAllBytes(archivo.toPath());
                mostrarImagen(imagenCargada);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarImagen(byte[] bytes) {
        ImageIcon icon = new ImageIcon(bytes);
        Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblImagen.setIcon(new ImageIcon(scaled));
        lblImagen.setText("");
    }

    private void accionAgregarIngrediente() {
        FrmBuscadorIngredientes buscador = new FrmBuscadorIngredientes(
                objetosBO,
                ingrediente -> {
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        if (modeloTabla.getValueAt(i, 0).equals(ingrediente.getId())) {
                            JOptionPane.showMessageDialog(this,
                                    "Este ingrediente ya fue agregado.",
                                    "Duplicado", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    String input = JOptionPane.showInputDialog(this,
                            "¿Cuánto de \"" + ingrediente.getNombre() + "\" (" + ingrediente.getUnidadMedida() + ") necesita?",
                            "Cantidad", JOptionPane.QUESTION_MESSAGE);

                    if (input == null || input.trim().isEmpty()) {
                        return;
                    }

                    try {
                        int cantidad = Integer.parseInt(input.trim());
                        if (cantidad <= 0) {
                            throw new NumberFormatException();
                        }
                        modeloTabla.addRow(new Object[]{
                    ingrediente.getId(),
                    ingrediente.getNombre(),
                    ingrediente.getUnidadMedida().toString(),
                    cantidad
                });
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this,
                                "La cantidad debe ser un número entero mayor a 0.",
                                "Valor inválido", JOptionPane.WARNING_MESSAGE);
                    }
                }
        );
        buscador.setVisible(true);
    }

    private void accionEliminarIngrediente() {
        int fila = tblIngredientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un ingrediente para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modeloTabla.removeRow(fila);
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String precioTx = txtPrecio.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (nombre.length() > 100) {
            JOptionPane.showMessageDialog(this, "El nombre no puede superar 100 caracteres.", "Campo inválido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (precioTx.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio es obligatorio.", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }
        try {
            double precio = Double.parseDouble(precioTx);
            if (precio <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número mayor a 0.", "Formato inválido", JOptionPane.WARNING_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un ingrediente.", "Sin ingredientes", JOptionPane.WARNING_MESSAGE);
            return false;
        }
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

        lblRegistroProducto = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        scpIngredientes = new javax.swing.JScrollPane();
        tblIngredientes = new javax.swing.JTable();
        btnAgregarIngrediente = new javax.swing.JButton();
        btnEliminarIngrediente = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        btnCargarImagen = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblRegistroProducto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRegistroProducto.setText("Registro de Producto");

        txtNombre.setText("Nombre...");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblNombre.setText("Nombre");

        lblDescripcion.setText("Descripcion");

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcion.setText("Descripción...");
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        lblPrecio.setText("Precio");

        txtPrecio.setText("Precio...");

        lblTipo.setText("Tipo");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblIngredientes.setModel(new javax.swing.table.DefaultTableModel(
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
        scpIngredientes.setViewportView(tblIngredientes);

        btnAgregarIngrediente.setText("Agregar Ingrediente");

        btnEliminarIngrediente.setText("Eliminar Ingrediente");

        lblImagen.setText("Imagen");

        btnCargarImagen.setText("Cargar Imagen");

        btnGuardar.setText("Guardar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpIngredientes, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregarIngrediente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarIngrediente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRegistroProducto)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDescripcion)
                                    .addComponent(lblNombre)
                                    .addComponent(lblPrecio)
                                    .addComponent(lblTipo)
                                    .addComponent(lblImagen))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCargarImagen)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegistroProducto)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescripcion)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecio)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCargarImagen)
                    .addComponent(lblImagen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpIngredientes, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarIngrediente)
                    .addComponent(btnEliminarIngrediente)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        txtDescripcion.requestFocus();
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        txtPrecio.requestFocus();
    }//GEN-LAST:event_txtDescripcionActionPerformed

//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrmRegistroProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmRegistroProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmRegistroProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmRegistroProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                FrmRegistroProducto dialog = new FrmRegistroProducto(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
//    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarIngrediente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminarIngrediente;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblRegistroProducto;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JScrollPane scpIngredientes;
    private javax.swing.JTable tblIngredientes;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
