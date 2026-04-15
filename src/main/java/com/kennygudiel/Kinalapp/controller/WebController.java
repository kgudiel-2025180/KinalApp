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
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        model.addAttribute("totalVentas", ventaService.listarVentas().size());
        model.addAttribute("totalClientes", clienteService.listarClientes().size());
        model.addAttribute("totalProductos", productoService.listarProductos().size());
        model.addAttribute("ventas", ventaService.listarVentas().stream().limit(5).toList());
        model.addAttribute("usuario", session.getAttribute("usuario"));

        return "index";
    }

    // ========== VENTAS ==========

    @GetMapping("/ventas")
    public String listarVentas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        List<Venta> ventas = ventaService.listarVentas();
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("titulo", "Gestión de Ventas");

        return "ventas";
    }

    @GetMapping("/ventas/buscar")
    public String buscarVentas(@RequestParam(required = false) String dpi,
                               @RequestParam(required = false) String fechaInicio,
                               @RequestParam(required = false) String fechaFin,
                               @RequestParam(required = false) Integer estado,
                               HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        List<Venta> ventas = ventaService.listarVentas();

        // Filtrar por DPI
        if (dpi != null && !dpi.isEmpty()) {
            ventas = ventas.stream()
                    .filter(v -> v.getCliente() != null &&
                            v.getCliente().getDpiCliente() != null &&
                            v.getCliente().getDpiCliente().contains(dpi))
                    .toList();
        }

        // Filtrar por estado
        if (estado != null) {
            ventas = ventas.stream()
                    .filter(v -> v.getEstado() == estado)
                    .toList();
        }

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventaService.listarVentas().size());
        model.addAttribute("totalResultados", ventas.size());
        model.addAttribute("filtroActivo", "Filtros aplicados");

        return "ventas";
    }

    @GetMapping("/ventas/nueva")
    public String nuevaVenta(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("titulo", "Nueva Venta");
        model.addAttribute("modo", "crear");
        return "formVenta";
    }

    // ========== CLIENTES ==========
    @GetMapping("/clientes")
    public String listarClientes(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("modo", "crear");
        return "formCliente";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes ra) {
        try {
            cliente.setEstado(1);
            clienteService.guardar(cliente);
            ra.addFlashAttribute("success", "✅ Cliente registrado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }

    @GetMapping("/clientes/editar/{dpi}")
    public String editarCliente(@PathVariable String dpi, HttpSession session, Model model, RedirectAttributes ra) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

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
    public String actualizarCliente(@PathVariable String dpi,
                                    @ModelAttribute Cliente cliente,
                                    RedirectAttributes ra) {
        try {
            clienteService.actualizar(dpi, cliente);
            ra.addFlashAttribute("success", "✅ Cliente actualizado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }

    @GetMapping("/clientes/eliminar/{dpi}")
    public String eliminarCliente(@PathVariable String dpi, RedirectAttributes ra) {
        try {
            clienteService.eliminar(dpi);
            ra.addFlashAttribute("success", "✅ Cliente eliminado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }
    // ========== PRODUCTOS ==========
    // ========== PRODUCTOS ==========

    @GetMapping("/productos")
    public String listarProductos(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Producto> productos = productoService.listarProductos();
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("modo", "crear");
        return "formProducto";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes ra) {
        try {
            producto.setEstado(1);
            productoService.guardar(producto);
            ra.addFlashAttribute("success", "✅ Producto registrado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes ra) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        return productoService.buscarPorId(id)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    model.addAttribute("titulo", "Editar Producto");
                    model.addAttribute("modo", "editar");
                    return "formProducto";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Producto no encontrado");
                    return "redirect:/web/productos";
                });
    }

    @PostMapping("/productos/actualizar/{id}")
    public String actualizarProducto(@PathVariable Integer id,
                                     @ModelAttribute Producto producto,
                                     RedirectAttributes ra) {
        try {
            productoService.actualizar(id, producto);
            ra.addFlashAttribute("success", "✅ Producto actualizado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            productoService.eliminar(id);
            ra.addFlashAttribute("success", "✅ Producto eliminado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Error: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }

    @GetMapping("/productos/buscar")
    public String buscarProductos(@RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) Integer estado,
                                  @RequestParam(required = false) String stock,
                                  HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        List<Producto> productos = productoService.listarProductos();

        // Filtrar por nombre
        if (nombre != null && !nombre.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getNombreProducto() != null &&
                            p.getNombreProducto().toLowerCase().contains(nombre.toLowerCase()))
                    .toList();
        }

        // Filtrar por estado
        if (estado != null) {
            productos = productos.stream()
                    .filter(p -> p.getEstado() == estado)
                    .toList();
        }

        // Filtrar por stock
        if (stock != null) {
            switch (stock) {
                case "low":
                    productos = productos.stream()
                            .filter(p -> p.getStock() > 0 && p.getStock() <= 10)
                            .toList();
                    break;
                case "available":
                    productos = productos.stream()
                            .filter(p -> p.getStock() > 0)
                            .toList();
                    break;
                case "out":
                    productos = productos.stream()
                            .filter(p -> p.getStock() == 0)
                            .toList();
                    break;
            }
        }

        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productoService.listarProductos().size());
        model.addAttribute("totalResultados", productos.size());
        model.addAttribute("filtroActivo", "Filtros aplicados");

        return "productos";
    }



    @GetMapping("/ventas/editar/{id}")
    public String editarVenta(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes ra) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

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
    public String actualizarVenta(@PathVariable Integer id,
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
    @PostMapping("/ventas/guardar")
    public String guardarVenta(@ModelAttribute Venta venta,
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

}