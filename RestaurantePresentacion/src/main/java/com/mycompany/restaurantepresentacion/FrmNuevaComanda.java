/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.EstadoComanda;
import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.Mesa;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoIngrediente;
import com.mycompany.restaurantedominio.ProductoSeleccionado;
import com.mycompany.restaurantedominio.TipoProducto;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.ComandaDTO;
import com.mycompany.restaurantenegocio.NegocioException;
import com.mycompany.restaurantenegocio.ObjetosBoDTO;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class FrmNuevaComanda extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(FrmNuevaComanda.class.getName());
    
    private final ObjetosBoDTO objetosBO;
    private Comanda comandaEditar;
    private List<Mesa> listaMesas = new ArrayList<>();
    private static final String[] COLUMNA_PRODUCTOS = {"Producto", "Precio", "Tipo", "Disponible", "Ingredientes OK"};
    private static final String[] COLUMNA_COMANDA   = {"Producto", "Cant.", "P.Unit", "Subtotal", "Comentario"};
    
    public FrmNuevaComanda(ObjetosBoDTO objetosBO) {
        this.objetosBO = objetosBO;
        this.comandaEditar = null;
        initComponents();
        inicializarTablas();
        cargarMesasLibres();
        inicializarCmbTipo();
        cargarClientes();
        txtFolio.setEditable(false);
        txtFolio.setText("Se generará automáticamente");
        txtTotal.setEditable(false);
        txtTotal.setText("$0.00");
        cargarTodosLosProductos();
    }
    
    public FrmNuevaComanda(ObjetosBoDTO objetosBO, Comanda comanda) {
        this.objetosBO = objetosBO;
        this.comandaEditar = comanda;
        initComponents();
        inicializarTablas();
        cargarMesasLibres();
        cargarClientes();
        cargarDatosComanda(comanda);
        txtTotal.setEditable(false);
    }
    
    private void guardarProductosSeleccionados(Comanda comanda) {
        try {
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable2.getModel();
            List<Producto> todosProductos = objetosBO.getProductoBO().obtenerTodos();

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String nombreProducto = modeloTabla.getValueAt(i, 0).toString();
                int cantidad = Integer.parseInt(modeloTabla.getValueAt(i, 1).toString());
                double precioUnitario = Double.parseDouble(modeloTabla.getValueAt(i, 2).toString());
                String comentario = modeloTabla.getValueAt(i, 4).toString();

                // Buscamos el producto por nombre
                Producto productoEncontrado = null;
                for (Producto p : todosProductos) {
                    if (p.getNombre().equals(nombreProducto)) {
                        productoEncontrado = p;
                        break;
                    }
                }

                if (productoEncontrado != null) {
                    objetosBO.getProductoSeleccionadoBO().agregar(comanda,productoEncontrado,cantidad,precioUnitario,comentario);
                }
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarTodosLosProductos() {
        try {
            List<Producto> todos = objetosBO.getProductoBO().obtenerTodos();
            llenarTablaProductos(todos);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void inicializarTablas() {
        jTable1.setModel(new DefaultTableModel(COLUMNA_PRODUCTOS, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        });
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTable2.setModel(new DefaultTableModel(COLUMNA_COMANDA, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        });
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void cargarMesasLibres() {
        try {
            cmbMesasLibres.removeAllItems();
            listaMesas = objetosBO.getMesaBO().obtenerMesasLibres();
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

        if (comanda.getEstado() == EstadoComanda.ENTREGADA || comanda.getEstado() == EstadoComanda.CANCELADA) {
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
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void inicializarCmbTipo() {
        cmbTipo.removeAllItems();
        cmbTipo.addItem("Todos");
        for (TipoProducto t : TipoProducto.values()) {
            cmbTipo.addItem(t.toString());
        }
    }
    
    private void actualizarTotal() {
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        double total = 0.0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 3).toString());
        }
        txtTotal.setText("$" + String.format("%.2f", total));
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
        btnAgregarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        pnlNuevaComanda = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(191, 192, 192));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(33, 84, 164));
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
        cmbTipo.addActionListener(this::cmbTipoActionPerformed);

        btnBuscarProductosParaAgregar.setBackground(new java.awt.Color(23, 117, 209));
        btnBuscarProductosParaAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscarProductosParaAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarProductosParaAgregar.setText("Buscar");
        btnBuscarProductosParaAgregar.addActionListener(this::btnBuscarProductosParaAgregarActionPerformed);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Producto", "Precio", "Tipo", "Disponible", "Ingredientes OK"
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

        btnGuardar.setBackground(new java.awt.Color(0, 128, 0));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setBackground(new java.awt.Color(200, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar Comanda");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnEntregar.setBackground(new java.awt.Color(255, 140, 0));
        btnEntregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEntregar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntregar.setText("Entregar");
        btnEntregar.addActionListener(this::btnEntregarActionPerformed);

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotal.setText("Total:");

        btnVolver.setBackground(new java.awt.Color(66, 66, 66));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        cmbBuscarClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cmbBuscarClientes.addActionListener(this::cmbBuscarClientesActionPerformed);

        btnAgregarProducto.setBackground(new java.awt.Color(0, 128, 0));
        btnAgregarProducto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarProducto.setText("Agregar P.");
        btnAgregarProducto.addActionListener(this::btnAgregarProductoActionPerformed);

        btnEliminarProducto.setBackground(new java.awt.Color(200, 0, 0));
        btnEliminarProducto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarProducto.setText("Eliminar P.");
        btnEliminarProducto.addActionListener(this::btnEliminarProductoActionPerformed);

        pnlNuevaComanda.setBackground(new java.awt.Color(67, 82, 90));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Comandas — Nueva Comanda");

        javax.swing.GroupLayout pnlNuevaComandaLayout = new javax.swing.GroupLayout(pnlNuevaComanda);
        pnlNuevaComanda.setLayout(pnlNuevaComandaLayout);
        pnlNuevaComandaLayout.setHorizontalGroup(
            pnlNuevaComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNuevaComandaLayout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNuevaComandaLayout.setVerticalGroup(
            pnlNuevaComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNuevaComandaLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEntregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVolver))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(lblProductosEnEstaComanda)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAgregarProducto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminarProducto))
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMesa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbMesasLibres, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel2)
                                        .addGap(4, 4, 4)
                                        .addComponent(cmbBuscarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(lblTipo)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscarProductosParaAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
            .addComponent(pnlNuevaComanda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlNuevaComanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addGap(34, 34, 34)
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
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarProducto)
                    .addComponent(btnEliminarProducto))
                .addGap(9, 9, 9)
                .addComponent(lblProductosEnEstaComanda)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar)
                            .addComponent(btnEntregar))
                        .addContainerGap())
                    .addComponent(btnVolver, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void btnEntregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregarActionPerformed
        if (comandaEditar == null) {
            JOptionPane.showMessageDialog(this, "Primero guarda la comanda antes de entregarla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Deseas marcar la comanda como Entregada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Obtener los productos seleccionados de la comanda
            List<ProductoSeleccionado> productos = objetosBO.getProductoSeleccionadoBO().obtenerPorComanda(comandaEditar.getId());

            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La comanda no tiene productos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Asegurarnos de tener los ingredientes cargados
            List<Producto> todosProductos = objetosBO.getProductoBO().obtenerTodos();

            for (ProductoSeleccionado ps : productos) {
                if (ps.getProducto().getIngredientes() == null || ps.getProducto().getIngredientes().isEmpty()) {
                    for (Producto pCompleto : todosProductos) {
                        if (pCompleto.getId().equals(ps.getProducto().getId())) {
                            ps.getProducto().setIngredientes(pCompleto.getIngredientes());
                            break;
                        }
                    }
                }
            }

            // Validar stock ANTES de descontar
            StringBuilder erroresStock = new StringBuilder();
            for (ProductoSeleccionado ps : productos) {
                if (ps.getProducto().getIngredientes() == null) continue;
                for (ProductoIngrediente pi : ps.getProducto().getIngredientes()) {
                    int cantidadNecesaria = pi.getCantidad() * ps.getCantidad();
                    // Consultar el stock fresco desde la BD
                    Ingrediente ingredienteFresco = objetosBO.getIngredientesBO().consultarIngredientePorId(pi.getIngrediente().getId());
                    int stockActual = ingredienteFresco.getStock();

                    if (stockActual < cantidadNecesaria) {
                        erroresStock.append("- ").append(ingredienteFresco.getNombre()).append(": necesitas ").append(cantidadNecesaria).append(", hay ").append(stockActual).append("\n");
                    }
                }
            }

            if (erroresStock.length() > 0) {
                JOptionPane.showMessageDialog(this, "No se puede entregar. Stock insuficiente:\n" + erroresStock.toString(), "Error de Stock", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Todo OK = Descontar ingredientes
            for (ProductoSeleccionado ps : productos) {
                if (ps.getProducto().getIngredientes() == null) continue;
                for (ProductoIngrediente pi : ps.getProducto().getIngredientes()) {
                    int cantidadDescontar = pi.getCantidad() * ps.getCantidad();
                    ActualizarIngredienteDTO dto = new ActualizarIngredienteDTO(pi.getIngrediente().getId(), cantidadDescontar);
                    objetosBO.getIngredientesBO().quitarStockIngrediente(dto);
                }
            }

            //  Marcar comanda como entregada
            objetosBO.getComandaBO().entregar(comandaEditar.getId());

            // Mesa regresa a LIBRE
            objetosBO.getMesaBO().actualizarDisponibilidad(comandaEditar.getMesa().getId());

            JOptionPane.showMessageDialog(this, "Comanda entregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

            Long idCliente;
            if (cmbBuscarClientes.getSelectedIndex() == 0) {
                idCliente = new ClienteDAO().crear().getIdCliente();
            } else {
                List<ClienteFrecuente> frecuentes = new ClienteDAO().obtenerTodos();
                idCliente = frecuentes.get(cmbBuscarClientes.getSelectedIndex() - 1).getIdCliente();
            }

            // Calculamos el total de jTable2
            double totalActual = 0.0;
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable2.getModel();
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                totalActual += Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
            }

            if (comandaEditar == null) {
                ComandaDTO dto = new ComandaDTO(mesaSeleccionada.getId(), idCliente);
                Comanda nueva = objetosBO.getComandaBO().crear(dto);
                this.comandaEditar = nueva;
                txtFolio.setText(nueva.getFolio());
                objetosBO.getMesaBO().actualizarDisponibilidad(mesaSeleccionada.getId());
                if (totalActual > 0) {
                    ComandaDTO dtoActualizar = new ComandaDTO(totalActual);
                    objetosBO.getComandaBO().actualizar(nueva.getId(), dtoActualizar);
                    guardarProductosSeleccionados(nueva); 
                }
                JOptionPane.showMessageDialog(this, "Comanda guardada con folio: " + nueva.getFolio(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                ComandaDTO dto = new ComandaDTO(totalActual);
                objetosBO.getComandaBO().actualizar(comandaEditar.getId(), dto);
                guardarProductosSeleccionados(comandaEditar); 
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
                objetosBO.getComandaBO().cancelar(comandaEditar.getId());
                //Mesa regresa a LIBRE
                objetosBO.getMesaBO().actualizarDisponibilidad(comandaEditar.getMesa().getId());
                JOptionPane.showMessageDialog(this, "Comanda cancelada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void cmbBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBuscarClientesActionPerformed

    private void btnBuscarProductosParaAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductosParaAgregarActionPerformed
        try {
            String nombre = txtNombreProductos.getText().trim();
            String tipoSel = (String) cmbTipo.getSelectedItem();
            List<Producto> todos = objetosBO.getProductoBO().obtenerTodos();
            List<Producto> filtrados = new ArrayList<>();
            for (Producto p : todos) {
                boolean coincideNombre = nombre.isEmpty() || p.getNombre().toLowerCase().contains(nombre.toLowerCase());
                boolean coincideTipo = tipoSel == null || "Todos".equals(tipoSel) || p.getTipo().toString().equals(tipoSel);
                if (coincideNombre && coincideTipo && p.isDisponible()) {
                    filtrados.add(p);
                }
            }
            llenarTablaProductos(filtrados);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTablaProductos(List<Producto> lista) {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getNombre(),
                String.format("%.2f", p.getPrecio()),
                p.getTipo(),
                p.isDisponible() ? "Sí" : "No",
                p.isDisponible() ? "Si" : "Falta stock" 
            });
        }
    }//GEN-LAST:event_btnBuscarProductosParaAgregarActionPerformed

    private void cmbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoActionPerformed

    }//GEN-LAST:event_cmbTipoActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtenemos los datos del producto seleccionado
        String nombre = jTable1.getValueAt(fila, 0).toString();
        double precioUnit = Double.parseDouble(jTable1.getValueAt(fila, 1).toString());
        String tipo = jTable1.getValueAt(fila, 2).toString();

        // Pedimos la cantidad
        String cantidadStr = JOptionPane.showInputDialog(this, "¿Cuántas unidades de " + nombre + "?", "1");
        if (cantidadStr == null || cantidadStr.isBlank()){
             return;
        }
           

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero mayor a 0.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Pedimos comentario opcional
        String comentario = JOptionPane.showInputDialog(this, "Comentario (opcional):", "");
        if (comentario == null) comentario = "";

        double subtotal = precioUnit * cantidad;

        //
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.addRow(new Object[]{
            nombre, cantidad, String.format("%.2f", precioUnit), String.format("%.2f", subtotal), comentario
        });

        // Actualizamos el total
        actualizarTotal();
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        int fila = jTable2.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la comanda para eliminarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.removeRow(fila);
        actualizarTotal();
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarProductosParaAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEntregar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cmbBuscarClientes;
    private javax.swing.JComboBox<String> cmbMesasLibres;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel pnlNuevaComanda;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JTextField txtNombreProductos;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
