
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorMoneda {

    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/6c334795f10e3527d63d9f09/latest/";

    private static final Map<String, String> monedas = new HashMap<>();
    private static final Map<String, Double> tasasLongitud = new HashMap<>();
    private static final Map<String, Double> tasasVolumen = new HashMap<>();
    private static final Map<String, Double> tasasMasa = new HashMap<>();
    private static final Map<String, Double> tasasArea = new HashMap<>();
    private static final Map<String, Double> tasasVelocidad = new HashMap<>();

    static {
        monedas.put("Dólar", "USD");
        monedas.put("Euro", "EUR");
        monedas.put("Soles", "PEN");
        monedas.put("Libras Esterlinas", "GBP");
        monedas.put("Yen Japonés", "JPY");
        monedas.put("Won sul-coreano", "KRW");
        // Agregar más monedas aquí

        tasasLongitud.put("Kilómetros", 1000.0);
        tasasLongitud.put("Metros", 1.0);
        tasasLongitud.put("Decímetros", 0.1);
        tasasLongitud.put("Centímetros", 0.01);
        // Agregar más tasas de longitud aquí

        tasasVolumen.put("Litros", 1.0);
        tasasVolumen.put("Decilitros", 0.1);
        tasasVolumen.put("Mililitros", 0.001);
        // Agregar más tasas de volumen aquí

        tasasMasa.put("Toneladas", 1000000.0);
        tasasMasa.put("Kilogramos", 1000.0);
        tasasMasa.put("Gramos", 1.0);
        tasasMasa.put("Miligramos", 0.001);
        // Agregar más tasas de masa aquí

        tasasArea.put("Hectáreas", 10000.0);
        tasasArea.put("Metros cuadrados", 1.0);
        tasasArea.put("Centímetros cuadrados", 0.0001);
        // Agregar más tasas de área aquí

        tasasVelocidad.put("Kilómetros por segundo", 1000.0);
        tasasVelocidad.put("Metros por segundo", 1.0);
        tasasVelocidad.put("Kilómetros por hora", 1.0 / 3.6);
        tasasVelocidad.put("Milla por hora", 1609.34 / 3600);
        // Agregar más tasas de velocidad aquí
    }

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            String opcionConversion = mostrarMenuPrincipal();

            if (opcionConversion == null || opcionConversion.equals("Salir")) {
                JOptionPane.showMessageDialog(null, "Programa Finalizado");
                break;
            }

            switch (opcionConversion) {
                case "Conversor de Monedas" ->
                    realizarConversionMoneda();
                case "Conversor de Temperaturas" ->
                    realizarConversionTemperatura();
                case "Conversor de Longitud" ->
                    realizarConversionLongitud();
                case "Conversor de Volumen" ->
                    realizarConversionVolumen();
                case "Conversor de Masa" ->
                    realizarConversionMasa();
                case "Conversor de Área" ->
                    realizarConversionArea();
                case "Conversor de Velocidad" ->
                    realizarConversionVelocidad();
                default -> {
                }
            }
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea continuar?", "Confirmación", JOptionPane.YES_NO_CANCEL_OPTION);

            if (respuesta == JOptionPane.NO_OPTION || respuesta == JOptionPane.CANCEL_OPTION) {
                continuar = false;
            }
        }

        JOptionPane.showMessageDialog(null, "Programa Finalizado");
    }

    public static String mostrarMenuPrincipal() {
        String[] opciones = {
            "Conversor de Monedas",
            "Conversor de Temperaturas",
            "Conversor de Longitud",
            "Conversor de Volumen",
            "Conversor de Masa",
            "Conversor de Área",
            "Conversor de Velocidad"

        };

        return (String) JOptionPane.showInputDialog(null, "Seleccione una opción de conversión", "Menú", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }

    public static void realizarConversionMoneda() {
        String[] opciones = {
            "De Dólar a Soles",
            "De Dólar a Euro",
            "De Dólar a Libras Esterlinas",
            "De Dólar a Yen Japonés",
            "De Dólar a Won sul-coreano",
            "De Soles a Dólar",
            "De Soles a Euro",
            "De Soles a Libras Esterlinas",
            "De Soles a Yen Japonés",
            "De Soles a Won sul-coreano",
            "De Euro a Dólar",
            "De Euro a Soles",
            "De Euro a Libras Esterlinas",
            "De Euro a Yen Japonés",
            "De Euro a Won sul-corean",
            "De Libras Esterlinas a Dólar",
            "De Libras Esterlinas a Soles",
            "De Libras Esterlinas a Euro",
            "De Libras Esterlinas a Yen Japonés",
            "De Libras Esterlinas a Won sul-coreano",
            "De Yen Japonés a Dólar",
            "De Yen Japonés a Soles",
            "De Yen Japonés a Euro",
            "De Yen Japonés a Libras Esterlinas",
            "De Yen Japonés a Won sul-coreano",
            "De Won sul-coreano a Dólar",
            "De Won sul-coreano a Soles",
            "De Won sul-coreano a Euro",
            "De Won sul-coreano a Libras Esterlinas",
            "De Won sul-coreano a Yen Japonés"

        };

        String opcionConversion = (String) JOptionPane.showInputDialog(null, "Elige la moneda a la que desees convertir tu dinero:", "Monedas", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();
            if (valorConversion == -1) {
                JOptionPane.showMessageDialog(null, "Valor no válido. Ingrese un número válido.");
                return;
            }

            double valorConvertido = convertirMoneda(valorConversion, opcionConversion);

            JOptionPane.showMessageDialog(null, "Tienes " + String.format("%.2f", valorConvertido) + " " + obtenerUnidadDestinoMoneda(opcionConversion));

        }
    }

    public static void realizarConversionTemperatura() {
        String[] opciones = {
            "Grados Celsius a Grados Kelvin",
            "Grados Celsius a Grados Fahrenheit",
            "Grados Kelvin a Grados Celsius",
            "Grados Kelvin a Grados Fahrenheit",
            "Grados Fahrenheit a Grados Celsius",
            "Grados Fahrenheit a Grados Kelvin"
        };

        String opcionConversion = (String) JOptionPane.showInputDialog(null, "Elige una opción para convertir", "Temperatura", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        if (opcionConversion != null) {
            double temperatura = obtenerTemperatura();

            if (temperatura != -1) {
                double temperaturaConvertida = convertirTemperatura(temperatura, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0]; // Obtener la unidad de origen directamente
                String unidadDestino = obtenerUnidadDestinoTemperatura(opcionConversion);

                String mensaje = String.format("%.2f grados %s son %.2f grados %s", temperatura, unidadOrigen, temperaturaConvertida, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    public static double obtenerValorConversion() {
        String input = JOptionPane.showInputDialog(null, "Ingresa la cantidad de dinero que deseas convertir", "Entrada de valor", JOptionPane.PLAIN_MESSAGE);

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static double convertirMoneda(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0].substring(3);
        String unidadDestino = partes[1];

        double tasaOrigen = obtenerTasaConversion(monedas.get(unidadOrigen));
        double tasaDestino = obtenerTasaConversion(monedas.get(unidadDestino));

        return valor / tasaOrigen * tasaDestino;
    }

    public static double obtenerTemperatura() {
        String input = JOptionPane.showInputDialog(null, "Ingresa el valor de la temperatura que deseas convertir", "Conversión de Temperatura", JOptionPane.PLAIN_MESSAGE);

        try {
            double temperatura = Double.parseDouble(input);
            return temperatura;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor no válido. Ingrese un número válido.");
            return obtenerTemperatura(); // Volver a solicitar la temperatura válida
        }
    }

    public static double convertirTemperatura(double temperatura, String opcionConversion) {
        return switch (opcionConversion) {
            case "Grados Celsius a Grados Kelvin" ->
                temperatura + 273.15;
            case "Grados Celsius a Grados Fahrenheit" ->
                (temperatura * 9 / 5) + 32;
            case "Grados Kelvin a Grados Celsius" ->
                temperatura - 273.15;
            case "Grados Kelvin a Grados Fahrenheit" ->
                (temperatura - 273.15) * 9 / 5 + 32;
            case "Grados Fahrenheit a Grados Celsius" ->
                (temperatura - 32) * 5 / 9;
            case "Grados Fahrenheit a Grados Kelvin" ->
                (temperatura - 32) * 5 / 9 + 273.15;
            default ->
                temperatura;
        };
    }

    public static String obtenerUnidadDestinoMoneda(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    public static String obtenerUnidadDestinoTemperatura(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    public static double obtenerTasaConversion(String simboloMonedaDestino) {
        try {
            String apiUrl = API_BASE_URL + "USD"; // Obteniendo las tasas en relación al dólar (USD)
            URL url = new URL(apiUrl);
            String response;
            try (Scanner scanner = new Scanner(url.openStream())) {
                response = scanner.useDelimiter("\\Z").next();
            }

            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getJSONObject("conversion_rates").getDouble(simboloMonedaDestino);
        } catch (IOException e) {
            return -1; // Manejo de error
        }
    }

    private static void realizarConversionLongitud() {
        String[] opciones = {
            "Kilómetros a Metros",
            "Kilómetros a Decímetros",
            "Kilómetros a Centímetros",
            "Metros a Kilómetros",
            "Metros a Decímetros",
            "Metros a Centímetros",
            "Decímetros a Kilómetros",
            "Decímetros a Metros",
            "Decímetros a Centímetros",
            "Centímetros a Kilómetros",
            "Centímetros a Metros",
            "Centímetros a Decímetros"
        // Agregar más opciones de longitud aquí
        };

        String opcionConversion = (String) JOptionPane.showInputDialog(
                null, "Elige una opción para convertir", "Longitud", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
        );

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();

            if (valorConversion != -1) {
                double valorConvertido = convertirLongitud(valorConversion, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0];
                String unidadDestino = obtenerUnidadDestinoLongitud(opcionConversion);

                String mensaje = String.format("%.2f %s son %.2f %s", valorConversion, unidadOrigen, valorConvertido, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    private static double convertirLongitud(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0];
        String unidadDestino = partes[1];

        double tasaOrigen = tasasLongitud.get(unidadOrigen);
        double tasaDestino = tasasLongitud.get(unidadDestino);

        return valor * tasaOrigen / tasaDestino;
    }

    private static void realizarConversionVolumen() {
        String[] opciones = {
            "Litros a Decilitros",
            "Litros a Mililitros",
            "Decilitros a Litros",
            "Decilitros a Mililitros",
            "Mililitros a Litros",
            "Mililitros a Decilitros"
        // Agregar más opciones de volumen aquí
        };

        String opcionConversion = (String) JOptionPane.showInputDialog(
                null, "Elige una opción para convertir", "Volumen", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
        );

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();

            if (valorConversion != -1) {
                double valorConvertido = convertirVolumen(valorConversion, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0];
                String unidadDestino = obtenerUnidadDestinoVolumen(opcionConversion);

                String mensaje = String.format("%.2f %s son %.2f %s", valorConversion, unidadOrigen, valorConvertido, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    private static double convertirVolumen(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0];
        String unidadDestino = partes[1];

        double tasaOrigen = tasasVolumen.get(unidadOrigen);
        double tasaDestino = tasasVolumen.get(unidadDestino);

        return valor * tasaOrigen / tasaDestino;
    }

    private static void realizarConversionMasa() {
        String[] opciones = {
            "Toneladas a Kilogramos",
            "Toneladas a Gramos",
            "Toneladas a Miligramos",
            "Kilogramos a Toneladas",
            "Kilogramos a Gramos",
            "Kilogramos a Miligramos",
            "Gramos a Toneladas",
            "Gramos a Kilogramos",
            "Gramos a Miligramos",
            "Miligramos a Toneladas",
            "Miligramos a Kilogramos",
            "Miligramos a Gramos"
        // Agregar más opciones de masa aquí
        };

        String opcionConversion = (String) JOptionPane.showInputDialog(
                null, "Elige una opción para convertir", "Masa", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
        );

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();

            if (valorConversion != -1) {
                double valorConvertido = convertirMasa(valorConversion, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0];
                String unidadDestino = obtenerUnidadDestinoMasa(opcionConversion);

                String mensaje = String.format("%.2f %s son %.2f %s", valorConversion, unidadOrigen, valorConvertido, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    private static double convertirMasa(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0];
        String unidadDestino = partes[1];

        double tasaOrigen = tasasMasa.get(unidadOrigen);
        double tasaDestino = tasasMasa.get(unidadDestino);

        return valor * tasaOrigen / tasaDestino;
    }

    private static void realizarConversionArea() {
        String[] opciones = {
            "Hectáreas a Metros cuadrados",
            "Hectáreas a Centímetros cuadrados",
            "Metros cuadrados a Hectáreas",
            "Metros cuadrados a Centímetros cuadrados",
            "Centímetros cuadrados a Hectáreas",
            "Centímetros cuadrados a Metros cuadrados"
        // Agregar más opciones de área aquí
        };

        String opcionConversion = (String) JOptionPane.showInputDialog(
                null, "Elige una opción para convertir", "Área", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
        );

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();

            if (valorConversion != -1) {
                double valorConvertido = convertirArea(valorConversion, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0];
                String unidadDestino = obtenerUnidadDestinoArea(opcionConversion);

                String mensaje = String.format("%.2f %s son %.2f %s", valorConversion, unidadOrigen, valorConvertido, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    private static double convertirArea(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0];
        String unidadDestino = partes[1];

        double tasaOrigen = tasasArea.get(unidadOrigen);
        double tasaDestino = tasasArea.get(unidadDestino);

        return valor * tasaOrigen / tasaDestino;
    }

    private static void realizarConversionVelocidad() {
        String[] opciones = {
            "Kilómetros por segundo a Metros por segundo",
            "Kilómetros por segundo a Kilómetros por hora",
            "Kilómetros por segundo a Millas por hora",
            "Metros por segundo a Kilómetros por segundo",
            "Metros por segundo a Kilómetros por hora",
            "Metros por segundo a Millas por hora",
            "Kilómetros por hora a Kilómetros por segundo",
            "Kilómetros por hora a Metros por segundo",
            "Kilómetros por hora a Millas por hora",
            "Millas por hora a Kilómetros por segundo",
            "Millas por hora a Kilómetros por hora",
            "Millas por hora a Metros por segundo" 
            // Agregar más opciones de velocidad aquí
};

        String opcionConversion = (String) JOptionPane.showInputDialog(
                null, "Elige una opción para convertir", "Velocidad", JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]
        );

        if (opcionConversion != null) {
            double valorConversion = obtenerValorConversion();

            if (valorConversion != -1) {
                double valorConvertido = convertirVelocidad(valorConversion, opcionConversion);

                String unidadOrigen = opcionConversion.split(" a ")[0];
                String unidadDestino = obtenerUnidadDestinoVelocidad(opcionConversion);

                String mensaje = String.format("%.2f %s son %.2f %s", valorConversion, unidadOrigen, valorConvertido, unidadDestino);

                JOptionPane.showMessageDialog(null, mensaje);
            }
        }
    }

    private static double convertirVelocidad(double valor, String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        String unidadOrigen = partes[0];
        String unidadDestino = partes[1];

        double tasaOrigen = tasasVelocidad.get(unidadOrigen);
        double tasaDestino = tasasVelocidad.get(unidadDestino);

        return valor * tasaOrigen / tasaDestino;
    }

    private static String obtenerUnidadDestinoLongitud(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    private static String obtenerUnidadDestinoVolumen(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    private static String obtenerUnidadDestinoMasa(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    private static String obtenerUnidadDestinoArea(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }

    private static String obtenerUnidadDestinoVelocidad(String opcionConversion) {
        String[] partes = opcionConversion.split(" a ");
        return partes[1];
    }
}
