import java.util.ArrayList;
import java.util.Scanner;

class Cliente {
    String nombre;
    String direccion;
    String telefono;
    boolean esFrecuente;
    boolean esTerceraEdad;

    public Cliente(String nombre, String direccion, String telefono, boolean esFrecuente, boolean esTerceraEdad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.esFrecuente = esFrecuente;
        this.esTerceraEdad = esTerceraEdad;
    }
}

class Pedido {
    Cliente cliente;
    String tipoComida;
    double precioComida;
    double precioExtras;
    double total;

    public Pedido(Cliente cliente, String tipoComida, double precioComida, double precioExtras, double total) {
        this.cliente = cliente;
        this.tipoComida = tipoComida;
        this.precioComida = precioComida;
        this.precioExtras = precioExtras;
        this.total = total;
    }
}

public class Prueba_semana_8 {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Pedido> pedidosDelDia = new ArrayList<>();

    public static void main(String[] args) {
        // Configuración inicial: precios del día
        System.out.println("Ingrese los precios del día:");
        System.out.print("Precio de comida económica: ");
        double precioEconomica = scanner.nextDouble();
        System.out.print("Precio de comida regular: ");
        double precioRegular = scanner.nextDouble();
        System.out.print("Precio de comida premium: ");
        double precioPremium = scanner.nextDouble();
        System.out.print("Precio de los extras: ");
        double precioExtras = scanner.nextDouble();

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n--- Nuevo Pedido ---");
            Cliente cliente = registrarCliente();
            double totalPedido = facturarPedido(cliente, precioEconomica, precioRegular, precioPremium, precioExtras);
            System.out.printf("Total del pedido (con descuentos si aplica): %.2f%n", totalPedido);

            System.out.print("¿Desea ingresar otro pedido? (s/n): ");
            continuar = scanner.next().equalsIgnoreCase("s");
        }

        mostrarResumenVentas();
    }

    public static Cliente registrarCliente() {
        scanner.nextLine(); 
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Número de teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("¿Es cliente frecuente? (s/n): ");
        boolean esFrecuente = scanner.next().equalsIgnoreCase("s");
        System.out.print("¿Es de tercera edad? (s/n): ");
        boolean esTerceraEdad = scanner.next().equalsIgnoreCase("s");

        return new Cliente(nombre, direccion, telefono, esFrecuente, esTerceraEdad);
    }

    public static double facturarPedido(Cliente cliente, double precioEconomica, double precioRegular, double precioPremium, double precioExtras) {
        System.out.println("Seleccione el tipo de comida:");
        System.out.println("1. Económica");
        System.out.println("2. Regular");
        System.out.println("3. Premium");
        int opcion = scanner.nextInt();
        double precioComida = 0;
        String tipoComida = "";

        switch (opcion) {
            case 1:
                precioComida = precioEconomica;
                tipoComida = "Económica";
                break;
            case 2:
                precioComida = precioRegular;
                tipoComida = "Regular";
                break;
            case 3:
                precioComida = precioPremium;
                tipoComida = "Premium";
                break;
            default:
                System.out.println("Opción inválida.");
                return 0;
        }

        System.out.print("¿Desea agregar extras? (s/n): ");
        boolean agregarExtras = scanner.next().equalsIgnoreCase("s");
        double precioConExtras = agregarExtras ? precioExtras : 0;
        double subtotal = precioComida + precioConExtras;

        
        double descuento = 0;
        if (cliente.esTerceraEdad) {
            descuento = subtotal * 0.25;
        } else if (cliente.esFrecuente) {
            descuento = subtotal * 0.15;
        }
        double total = subtotal - descuento;

        pedidosDelDia.add(new Pedido(cliente, tipoComida, precioComida, precioConExtras, total));
        return total;
    }

    public static void mostrarResumenVentas() {
        System.out.println("\n--- Resumen de Ventas ---");
        double totalDia = 0;

        for (Pedido pedido : pedidosDelDia) {
            System.out.printf("Cliente: %s, Tipo de comida: %s, Total: %.2f%n",
                    pedido.cliente.nombre, pedido.tipoComida, pedido.total);
            totalDia += pedido.total;
        }

        System.out.printf("Total recaudado en el día: %.2f%n", totalDia);
    }
}

