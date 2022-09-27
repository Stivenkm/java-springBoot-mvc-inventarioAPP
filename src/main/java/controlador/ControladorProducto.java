/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import modelo.Producto;
import modelo.RepositorioProducto;
import vista.VistaInventario;

/**
 *
 * @author stive
 */
public class ControladorProducto implements ActionListener {

    protected RepositorioProducto repositorioProducto;
    protected VistaInventario vistaInventario;

    public ControladorProducto(RepositorioProducto repositorio, VistaInventario vista) {
        this.repositorioProducto = repositorio;
        this.vistaInventario = vista;
        this.agregarEventos();
        this.listar();
    }

    private void agregarEventos() {
        this.vistaInventario.getBtnAgregar().addActionListener(this);
        this.vistaInventario.getBtnActualizar().addActionListener(this);
        this.vistaInventario.getBtnBorrar().addActionListener(this);
        this.vistaInventario.getBtnInformes().addActionListener(this);
    }

    private List<Producto> listar() {
        JTable tablaInventario = this.vistaInventario.getTblInventario();
        List<Producto> listProductos = (List<Producto>) this.repositorioProducto.findAll();
        int fila = 0;
        for (Producto producto : listProductos) {
            tablaInventario.getModel().setValueAt(producto.getCodigo(), fila, 0);
            tablaInventario.getModel().setValueAt(producto.getNombre(), fila, 1);
            tablaInventario.getModel().setValueAt(producto.getPrecio(), fila, 2);
            tablaInventario.getModel().setValueAt(producto.getInventario(), fila, 3);
            fila++;
        }
        for(int i=fila;i<tablaInventario.getRowCount();i++){
            tablaInventario.setValueAt("", fila, 0);
            tablaInventario.setValueAt("", fila, 1);
            tablaInventario.setValueAt("", fila, 2);
            tablaInventario.setValueAt("", fila, 3);
        }
        return listProductos;
    }

    public void resetTxt() {
        this.vistaInventario.setTxtNombre(null);
        this.vistaInventario.setTxtPrecio(null);
        this.vistaInventario.setTxtInventario(null);
    }

    public void agregar() {
        String nombre = this.vistaInventario.getTxtNombre().getText();
        String txtPrecio = this.vistaInventario.getTxtPrecio().getText();
        String txtInventario = this.vistaInventario.getTxtInventario().getText();
        if (!nombre.equals("") && !txtPrecio.equals("") && !txtInventario.equals("")) {
            float precio = Float.parseFloat(txtPrecio);
            int inventario = Integer.parseInt(txtInventario);
            Producto producto = Producto.crearProducto(nombre, precio, inventario);
            repositorioProducto.save(producto);
        } else {
            JOptionPane.showMessageDialog(vistaInventario, "Todos los campos son obligatorios", "ADVERTENCIA",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    public void actualizar() {
        JTable tablaInventario = this.vistaInventario.getTblInventario();
        int fila = tablaInventario.getSelectedRow();
        if (fila != -1) {
            JTextField txtNuevoNombre = new JTextField((String) tablaInventario.getModel().getValueAt(fila, 1));
            JTextField txtNuevoPrecio = new JTextField(tablaInventario.getModel().getValueAt(fila, 2).toString());
            JTextField txtNuevoInventario = new JTextField(tablaInventario.getModel().getValueAt(fila, 3).toString());

            int result = JOptionPane.showOptionDialog(vistaInventario, new Object[]{"Actualizar", txtNuevoNombre, txtNuevoPrecio, txtNuevoInventario},
                    "Actualizar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (result == JOptionPane.OK_OPTION) {
                Producto producto = this.repositorioProducto.findById((Long) tablaInventario.getModel().getValueAt(fila, 0)).get();
                producto.setNombre(txtNuevoNombre.getText());
                producto.setPrecio(Float.parseFloat(txtNuevoPrecio.getText()));
                producto.setInventario(Integer.parseInt(txtNuevoInventario.getText()));
                this.repositorioProducto.save(producto);
            }
        } else {
            JOptionPane.showMessageDialog(vistaInventario, "No ha elegido la fila del producto a actualizar", "ADVERTENCIA ACTUALIZAR PRODUCTO",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void eliminar() {
        JTable tablaInventario = this.vistaInventario.getTblInventario();
        int fila = tablaInventario.getSelectedRow();
        if (fila != -1) {
            Long idProducto = (Long) tablaInventario.getModel().getValueAt(fila, 0);
            String nombre = (String) tablaInventario.getModel().getValueAt(fila, 1);
            this.repositorioProducto.deleteById(idProducto);
            JOptionPane.showMessageDialog(vistaInventario, "Se ha eliminado correctamente el producto ( " + nombre + " )", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vistaInventario, "No ha elegido la fila del producto a borrar", "ADVERTENCIA BORRAR PRODUCTO",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void generarInforme() {
        double totalPrecio = 0, precioMayor = 0, precioAnterior = 0;
        double promPrecios = 0, valInventario = 0, cantidad = 0;
        String productoValMayor = "", productoValMenor = "";
        List<Producto> listaProductos = (List<Producto>) this.repositorioProducto.findAll();
        //HashMap<Integer, Producto> listaProductos = new HashMap<>();
        for (int i = 0; i < listaProductos.size(); i++) {
            cantidad += 1;
            totalPrecio += listaProductos.get(i).getPrecio();
            valInventario += listaProductos.get(i).getPrecio() * listaProductos.get(i).getInventario();
            if (listaProductos.get(i).getPrecio() > precioMayor) {
                precioMayor = listaProductos.get(i).getPrecio();
                productoValMayor = listaProductos.get(i).getNombre();
            }
            if (listaProductos.get(i).getPrecio() < precioAnterior) {
                productoValMenor = listaProductos.get(i).getNombre();
                precioAnterior = listaProductos.get(i).getPrecio();
            } else if (precioAnterior > listaProductos.get(i).getPrecio() || precioAnterior == 0) {
                precioAnterior = listaProductos.get(i).getPrecio();
                productoValMenor = listaProductos.get(i).getNombre();
            }
        }
        promPrecios = totalPrecio / cantidad;
        JOptionPane.showMessageDialog(vistaInventario, "Producto precio mayor: " + productoValMayor + "\n"
                + "Producto precio menor: " + productoValMenor + "\n" + "Promedio precios: " + String.format("%.1f", promPrecios) + "\n"
                + "Valor del inventario: " + valInventario, "Informes",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaInventario.getBtnAgregar()) {
            this.agregar();
            this.listar();
            this.resetTxt();
        }
        if (e.getSource() == vistaInventario.getBtnActualizar()) {
            this.actualizar();
            this.listar();
        }
        if (e.getSource() == vistaInventario.getBtnBorrar()) {
            this.eliminar();
            this.listar();
        }
        if (e.getSource() == vistaInventario.getBtnInformes()) {
            generarInforme();
            this.listar();
        }
    }

}
