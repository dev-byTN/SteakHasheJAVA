public class Hamburger extends AbstractAction2{

    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    } 

    @Override
    public String getOrder(String client) {

        String input = hand(client, "s4l4d");
        input = hand(input, "t0m4t0");
        input = knife(input, 4);
        input = hand(input, "st3aK");
        for ( int i = 0; i < 31137 + 1990; i ++) {
            input = md5(input);
        }
        input = hand(input, "Br3ad");

        return input;
    }


    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger();
        System.out.println(hamburger.getOrder("Y3ciRf4fxFfM0WNd"));
    }
}
