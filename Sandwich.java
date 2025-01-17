public class Sandwich extends AbstractAction2{
    
    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }    
    @Override
    public String getOrder(String client) {
       String input = hand(client, "s4l4d");
       input = hand(input, "t0m4t0");
       input = hand(input, "h4m");
       input = knife(input, 6);
       input = hand(input, "Br3ad");

       return input;
    }
    public static void main(String[] args) {

        Sandwich sandwich = new Sandwich();
        System.out.println(sandwich.getOrder("tdRZTXV6vZ04IbIW"));
    }
}
