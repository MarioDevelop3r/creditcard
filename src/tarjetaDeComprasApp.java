import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class tarjetaDeComprasApp {
    // Mensajes de la aplicación
    private static final String MSG_LIMITE_TARJETA = "Ingrese el límite de la tarjeta: ";
    private static final String MSG_DESCRIPCION_COMPRA = "Ingrese la descripción de la compra: ";
    private static final String MSG_VALOR_COMPRA = "Ingrese el valor de la compra: ";
    private static final String MSG_CONTINUAR_COMPRANDO = "¿Desea realizar otra compra? (1: sí, 0: no): ";
    private static final String MSG_SALDO_INSUFICIENTE = "Saldo insuficiente. No puede realizar esta compra.";
    private static final String MSG_RESUMEN_COMPRAS = "\nResumen de compras:";
    private static final String MSG_SALDO_RESTANTE = "Saldo restante: $";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar el límite de la tarjeta
        double limiteTarjeta = solicitarLimiteTarjeta(scanner);
        double saldoDisponible = limiteTarjeta;

        // Lista de compras realizadas
        ArrayList<Compra> compras = new ArrayList<>();
        boolean continuarComprando = true;

        while (continuarComprando && saldoDisponible > 0) {
            saldoDisponible = realizarCompra(scanner, compras, saldoDisponible);
            continuarComprando = preguntarSiContinuar(scanner);
        }

        mostrarResumenCompras(compras, saldoDisponible);

        scanner.close();
    }

    // Métodos auxiliares de la aplicación
    private static double solicitarLimiteTarjeta(Scanner scanner) {
        System.out.print(MSG_LIMITE_TARJETA);
        return scanner.nextDouble();
    }

    // Realizar una compra y actualizar el saldo disponible

    private static double realizarCompra(Scanner scanner, ArrayList<Compra> compras, double saldoDisponible) {
        System.out.print(MSG_DESCRIPCION_COMPRA);
        scanner.nextLine();  // Consumir la nueva línea después de nextDouble o nextInt
        String descripcion = scanner.nextLine();

        System.out.print(MSG_VALOR_COMPRA);
        double valorCompra = scanner.nextDouble();

        if (valorCompra <= saldoDisponible) {
            compras.add(new Compra(descripcion, valorCompra));
            saldoDisponible -= valorCompra;
        } else {
            System.out.println(MSG_SALDO_INSUFICIENTE);
        }

        return saldoDisponible;
    }

    // Preguntar al usuario si desea continuar comprando
    private static boolean preguntarSiContinuar(Scanner scanner) {
        System.out.print(MSG_CONTINUAR_COMPRANDO);
        int respuesta = scanner.nextInt();
        return respuesta == 1;
    }

    // Mostrar el resumen de las compras realizadas
    private static void mostrarResumenCompras(ArrayList<Compra> compras, double saldoDisponible) {
        System.out.println(MSG_RESUMEN_COMPRAS);

        // Ordenar las compras por valor acendentemente
        compras.sort(Comparator.comparingDouble(Compra::getValor));

        for (Compra compra : compras) {
            System.out.println(compra.getDescripcion() + ": $" + compra.getValor());
        }

        System.out.println(MSG_SALDO_RESTANTE + saldoDisponible);
    }
}

