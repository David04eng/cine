package com.cine.sistema_cine.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cine.sistema_cine.model.Asistente;
import com.cine.sistema_cine.repository.AsistenteRepository;

@Controller
public class CineController {

    @Autowired
    private AsistenteRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("asistentes", repository.findAll());
        model.addAttribute("asistente", new Asistente());
        return "index";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // Paso 1: Procesar selección y mostrar resumen con los nuevos precios
    @PostMapping("/resumen")
    public String mostrarResumen(@ModelAttribute Asistente asistente, 
                                 @RequestParam(value = "asientosSeleccionados", required = false) List<String> asientos,
                                 @RequestParam(value = "dulcesSeleccionados", required = false) List<String> dulces, 
                                 Model model) {
        
        // 1. Procesar asientos
        int cantidadAsientos = (asientos != null) ? asientos.size() : 0;
        if (asientos != null && !asientos.isEmpty()) {
            asistente.setAsiento(String.join(", ", asientos));
        }
        
        // 2. Lógica de precios Base (Boletos)
        double precioBoletos = (asistente.getTipo() != null && asistente.getTipo().equalsIgnoreCase("VIP")) ? 150.0 : 80.0;
        double subtotalBoletos = precioBoletos * cantidadAsientos;

        // 3. Lógica de precios de Dulcería[cite: 1]
        double precioDulces = 0;
        if (dulces != null && !dulces.isEmpty()) {
            asistente.setDulces(String.join(", ", dulces));
            for (String dulce : dulces) {
                if (dulce.equalsIgnoreCase("Palomitas")) precioDulces += 60.0;
                if (dulce.equalsIgnoreCase("Nachos")) precioDulces += 50.0;
                if (dulce.equalsIgnoreCase("Refresco")) precioDulces += 30.0;
            }
        } else {
            asistente.setDulces("Ninguno");
        }

        // 4. Calcular Total Final[cite: 1]
        asistente.setPrecio(subtotalBoletos + precioDulces);

        model.addAttribute("asistente", asistente);
        return "confirmacion"; 
    }

    // Paso 2: Guardar definitivamente y generar el QR sin errores[cite: 1]
    @PostMapping("/comprar")
    public String comprar(@ModelAttribute Asistente asistente) {
        
        // Asignar fecha actual[cite: 1]
        asistente.setFecha(LocalDate.now());
        
        // Generar QR usando QuickChart (Evita el error 404 de Google)[cite: 1]
        String infoParaQR = "CineManager - Cliente: " + asistente.getNombre() + 
                            " | Pelicula: " + asistente.getPelicula() + 
                            " | Asientos: " + asistente.getAsiento() +
                            " | Total: $" + asistente.getPrecio();
        
        // Limpiamos espacios para la URL[cite: 1]
        String qrUrl = "https://quickchart.io/qr?text=" + infoParaQR.replace(" ", "%20") + "&size=250";
        
        asistente.setQrUrl(qrUrl);
        
        // Guardar en la base de datos[cite: 1]
        repository.save(asistente);
        
        return "redirect:/";
    }
}