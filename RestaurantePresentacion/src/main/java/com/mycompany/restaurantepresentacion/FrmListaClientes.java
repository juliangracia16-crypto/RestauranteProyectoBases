package com.mycompany.restaurantepresentacion;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantenegocio.ClienteBO;
import com.mycompany.restaurantenegocio.NegocioException;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmListaClientes extends javax.swing.JDialog {

    // ── Atributos ─────────────────────────────────────────────────────────────
    private DefaultTableModel modeloTabla;
    private final ClienteBO clienteBO;
    private ClienteFrecuente clienteSeleccionado;

    // ── Constructor ───────────────────────────────────────────────────────────
    public FrmListaClientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.clienteBO = new ClienteBO(new ClienteDAO());
        this.clienteSeleccionado = null;
        initComponents();
        configurarCombo();
        configurarTabla();
        configurarBotones();
        configurarEventos();
        cargarClientes();
    }

    public ClienteFrecuente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    private void configurarCombo() {
        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Nombre", "Teléfono", "Correo"}
        ));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Teléfono", "Correo"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1.setModel(modeloTabla);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);

        // Ocultar columna ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);

        jTable1.getColumnModel().getColumn(1).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(180);
    }

    private void configurarBotones() {
        btnClienteGeneral.setEnabled(false); // se habilita al seleccionar fila
    }

    private void configurarEventos() {
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            btnClienteGeneral.setEnabled(jTable1.getSelectedRow() != -1);
        });

        txtBuscadorClientes.addActionListener(e -> accionBuscar());
        btnBuscar.addActionListener(e -> accionBuscar());
        btnLimpiar.addActionListener(e -> accionLimpiar());
        btnNuevo.addActionListener(e -> dispose());          // Cancelar
        btnClienteGeneral.addActionListener(e -> accionSeleccionar()); // Seleccionar
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        try {
            List<ClienteFrecuente> clientes = clienteBO.obtenerTodos();
            for (ClienteFrecuente c : clientes) {
                modeloTabla.addRow(new Object[]{
                    c.getIdCliente(),
                    c.getNombre(),
                    c.getTelefono(),
                    c.getCorreo() != null ? c.getCorreo() : "—"
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar clientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionBuscar() {
        String texto = txtBuscadorClientes.getText().trim().toLowerCase();
        String filtro = cmbFiltro.getSelectedItem().toString();

        if (texto.isEmpty()) {
            cargarClientes();
            return;
        }

        try {
            List<ClienteFrecuente> todos = clienteBO.obtenerTodos();
            modeloTabla.setRowCount(0);

            for (ClienteFrecuente c : todos) {
                boolean coincide = switch (filtro) {
                    case "Nombre" ->
                        c.getNombre().toLowerCase().contains(texto);
                    case "Teléfono" ->
                        c.getTelefono().contains(texto);
                    case "Correo" ->
                        c.getCorreo() != null && c.getCorreo().toLowerCase().contains(texto);
                    default ->
                        false;
                };

                if (coincide) {
                    modeloTabla.addRow(new Object[]{
                        c.getIdCliente(),
                        c.getNombre(),
                        c.getTelefono(),
                        c.getCorreo() != null ? c.getCorreo() : "—"
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
        txtBuscadorClientes.setText("");
        cmbFiltro.setSelectedIndex(0);
        cargarClientes();
    }

    private void accionSeleccionar() {
        int fila = jTable1.getSelectedRow();
        if (fila == -1) {
            return;
        }

        Long id = (Long) modeloTabla.getValueAt(fila, 0);
        try {
            clienteSeleccionado = clienteBO.buscarPorId(id);
            dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // </editor-fold>
@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscadorClientes = new javax.swing.JTextField();
        cmbFiltro = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnDetalles = new javax.swing.JButton();
        btnClienteGeneral = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setPreferredSize(new java.awt.Dimension(640, 360));

        jLabel1.setText("Buscar:");

        txtBuscadorClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorClientesActionPerformed(evt);
            }
        });

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");

        btnLimpiar.setText("Limpiar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscadorClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscadorClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnLimpiar))
                .addGap(35, 35, 35))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Teléfono", "Correo", "Visitas", "Title 5", "Puntos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnEditar.setText("Editar");

        btnNuevo.setText("Nuevo");

        btnDetalles.setText("Detalles");

        btnClienteGeneral.setText("Cliente General");
        btnClienteGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteGeneralActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDetalles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClienteGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnNuevo)
                    .addComponent(btnDetalles)
                    .addComponent(btnClienteGeneral))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

//    private void initComponents() {
//        jButton1 = new javax.swing.JButton();
//        jPanel1 = new javax.swing.JPanel();
//        jLabel1 = new javax.swing.JLabel();
//        txtBuscadorClientes = new javax.swing.JTextField();
//        cmbFiltro = new javax.swing.JComboBox<>();
//        btnBuscar = new javax.swing.JButton();
//        btnLimpiar = new javax.swing.JButton();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTable1 = new javax.swing.JTable();
//        jPanel2 = new javax.swing.JPanel();
//        btnNuevo = new javax.swing.JButton();       // Cancelar
//        btnClienteGeneral = new javax.swing.JButton(); // Seleccionar
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//        setModal(true);
//
//        jLabel1.setText("Buscar:");
//        btnBuscar.setText("Buscar");
//        btnLimpiar.setText("Limpiar");
//        btnNuevo.setText("Cancelar");
//        btnClienteGeneral.setText("Seleccionar");
//
//    }
    private void txtBuscadorClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorClientesActionPerformed
        accionBuscar();
    }//GEN-LAST:event_txtBuscadorClientesActionPerformed

    private void cmbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActionPerformed
        if (!txtBuscadorClientes.getText().trim().isEmpty())
            accionBuscar();
    }//GEN-LAST:event_cmbFiltroActionPerformed

    private void btnClienteGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteGeneralActionPerformed
        accionSeleccionar();
    }//GEN-LAST:event_btnClienteGeneralActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnClienteGeneral;
    private javax.swing.JButton btnDetalles;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscadorClientes;
    // End of variables declaration//GEN-END:variables

}
