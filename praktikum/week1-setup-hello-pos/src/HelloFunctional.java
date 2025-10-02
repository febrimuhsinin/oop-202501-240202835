import java.util.function.BiConsumer;

public class HelloFunctional {
    public static void main(String[] args) {
        BiConsumer<String, Integer> sapa = (nama, nim) -> System.out.println("Hello word, i am " + nama + "-" + nim);

        sapa.accept("Febri Muhsinin", 240202835);
    }
}
