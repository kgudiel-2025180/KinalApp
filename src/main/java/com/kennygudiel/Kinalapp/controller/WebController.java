package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.Cliente;
import com.kennygudiel.Kinalapp.entity.Producto;
import com.kennygudiel.Kinalapp.entity.Venta;
import com.kennygudiel.Kinalapp.service.IClienteService;
import com.kennygudiel.Kinalapp.service.IProductoService;
import com.kennygudiel.Kinalapp.service.IVentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web")
public class WebController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IProductoService productoService;

    public WebController(IVentaService ventaService,
                         IClienteService clienteService,
                         IProductoService productoService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    // ========== DASHBOARD ==========
    @GetMapping("/dashboard")
    public String paginaDashboard(HttpSession session, Model model) {
        // Spring Security ya maneja la autenticación
        model.addAttribute("totalVentas", ventaService.listarVentas().size());
        model.addAttribute("totalClientes", clienteService.listarClientes().size());
        model.addAttribute("totalProductos", productoService.listarProductos().size());
        model.addAttribute("ventas", ventaService.listarVentas().stream().limit(5).toList());
        return "index";
    }

    // ========== VENTAS ==========
    @GetMapping("/ventas")
    public String paginaVentas(HttpSession session, Model model) {
        List<Venta> ventas = ventaService.listarVentas();
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventas.size());
        return "ventas";
    }

    @GetMapping("/ventas/nueva")
    public String formularioNuevaVenta(HttpSession session, Model model) {

        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("titulo", "Nueva Venta");
        model.addAttribute("modo", "crear");
        return "formVenta";
    }

    @PostMapping("/ventas/guardar")
    public String procesarGuardarVenta(@ModelAttribute Venta venta,
                                       @RequestParam("dpiCliente") String dpiCliente,
                                       RedirectAttributes ra) {
        try {
            clienteService.buscarPorDPI(dpiCliente).ifPresent(venta::setCliente);
            venta.setEstado(1);
            ventaService.guardar(venta);
            ra.addFlashAttribute("success", "✅ Venta registrada exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/ventas";
    }

    @GetMapping("/ventas/eliminar/{id}")
    public String procesarEliminarVenta(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            ventaService.eliminar(id);
            ra.addFlashAttribute("success", "✅ Venta eliminada!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/ventas";
    }

    @GetMapping("/ventas/editar/{id}")
    public String formularioEditarVenta(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes ra) {

        return ventaService.buscarPorId(id)
                .map(venta -> {
                    model.addAttribute("venta", venta);
                    model.addAttribute("clientes", clienteService.listarClientes());
                    model.addAttribute("titulo", "Editar Venta #" + id);
                    model.addAttribute("modo", "editar");
                    return "formVenta";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Venta no encontrada");
                    return "redirect:/web/ventas";
                });
    }

    @PostMapping("/ventas/actualizar/{id}")
    public String procesarActualizarVenta(@PathVariable Integer id,
                                          @ModelAttribute Venta venta,
                                          @RequestParam("dpiCliente") String dpiCliente,
                                          RedirectAttributes ra) {
        try {
            clienteService.buscarPorDPI(dpiCliente).ifPresent(venta::setCliente);
            ventaService.actualizar(id, venta);
            ra.addFlashAttribute("success", "✅ Venta #" + id + " actualizada!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/ventas";
    }

    @GetMapping("/ventas/buscar")
    public String paginaBuscarVentas(@RequestParam(required = false) String dpi,
                                     @RequestParam(required = false) Integer estado,
                                     HttpSession session, Model model) {

        List<Venta> ventas = ventaService.listarVentas();
        if (dpi != null && !dpi.isEmpty()) {
            ventas = ventas.stream()
                    .filter(v -> v.getCliente() != null &&
                            v.getCliente().getDpiCliente() != null &&
                            v.getCliente().getDpiCliente().contains(dpi))
                    .toList();
        }
        if (estado != null) {
            ventas = ventas.stream()
                    .filter(v -> v.getEstado() == estado)
                    .toList();
        }
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventaService.listarVentas().size());
        model.addAttribute("totalResultados", ventas.size());
        return "ventas";
    }

    @GetMapping("/ventas/ver/{id}")
    public String verDetalleVenta(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes ra) {


        return ventaService.buscarPorId(id)
                .map(venta -> {
                    model.addAttribute("venta", venta);
                    model.addAttribute("titulo", "Detalle de Venta #" + id);
                    return "detalleVenta";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Venta no encontrada");
                    return "redirect:/web/ventas";
                });
    }

    // ========== CLIENTES ==========
    @GetMapping("/clientes")
    public String paginaClientes(HttpSession session, Model model) {

        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String formularioNuevoCliente(HttpSession session, Model model) {

        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("modo", "crear");
        return "formCliente";
    }

    @PostMapping("/clientes/guardar")
    public String procesarGuardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        try {
            cliente.setEstado(1);
            clienteService.guardar(cliente);
            ra.addFlashAttribute("success", "✅ Cliente registrado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }

    @GetMapping("/clientes/editar/{dpi}")
    public String formularioEditarCliente(@PathVariable String dpi, HttpSession session, Model model, RedirectAttributes ra) {

        return clienteService.buscarPorDPI(dpi)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    model.addAttribute("titulo", "Editar Cliente");
                    model.addAttribute("modo", "editar");
                    return "formCliente";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Cliente no encontrado");
                    return "redirect:/web/clientes";
                });
    }

    @PostMapping("/clientes/actualizar/{dpi}")
    public String procesarActualizarCliente(@PathVariable String dpi,
                                            @ModelAttribute Cliente cliente,
                                            RedirectAttributes ra) {
        try {
            clienteService.actualizar(dpi, cliente);
            ra.addFlashAttribute("success", "✅ Cliente actualizado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }

    @GetMapping("/clientes/eliminar/{dpi}")
    public String procesarEliminarCliente(@PathVariable String dpi, RedirectAttributes ra) {
        try {
            clienteService.eliminar(dpi);
            ra.addFlashAttribute("success", "✅ Cliente eliminado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ No se puede eliminar: el cliente tiene ventas registradas. Elimine primero las ventas.");
        }
        return "redirect:/web/clientes";
    }

    // ========== PRODUCTOS ==========
    @GetMapping("/productos")
    public String paginaProductos(HttpSession session, Model model) {

        model.addAttribute("productos", productoService.listarProductos());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String formularioNuevoProducto(HttpSession session, Model model) {

        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("modo", "crear");
        return "formProducto";
    }

    @PostMapping("/productos/guardar")
    public String procesarGuardarProducto(@ModelAttribute Producto producto, RedirectAttributes ra) {
        try {
            producto.setEstado(1);
            productoService.guardar(producto);
            ra.addFlashAttribute("success", "✅ Producto registrado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }
    // ========== PRODUCTOS (COMPLETAR) ==========

    @GetMapping("/productos/editar/{id}")
    public String formularioEditarProducto(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        return productoService.buscarPorId(id)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    model.addAttribute("titulo", "Editar Producto #" + id);
                    model.addAttribute("modo", "editar");
                    return "formProducto";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Producto no encontrado");
                    return "redirect:/web/productos";
                });
    }

    @PostMapping("/productos/actualizar/{id}")
    public String procesarActualizarProducto(@PathVariable Integer id,
                                             @ModelAttribute Producto producto,
                                             RedirectAttributes ra) {
        try {
            productoService.actualizar(id, producto);
            ra.addFlashAttribute("success", "✅ Producto #" + id + " actualizado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String procesarEliminarProducto(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            productoService.eliminar(id);
            ra.addFlashAttribute("success", "✅ Producto eliminado!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }

}