public class Fries extends AbstractAction2 {
    
    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }

    @Override
    public String getOrder(String client) {

        /////////////////////////////////////      STEP 1      /////////////////////////////////////
        String input = hand(client, "MyL1f3iSP0T4T0");
        input = knife(input, 4);

        /////////////////////////////////////      STEP 3      /////////////////////////////////////
        /// Extraire le pourcecentage de "broken" final dans la classe Mcflurry
        Mcflurry extractor = new Mcflurry();
        String jsonData = extractor.fetchJsonData("https://mcbroken.com/stats.json");
        int loop = extractor.extractFinalBroken(jsonData) * 98;

        for ( int i = 0; i < loop; i++) {
            input = hashSHA512(input);
        }

        return input;
    }

    public static void main(String[] args) {
        Fries fries = new Fries();
        System.out.println(fries.getOrder("uewApGWPXVgWlJVY"));
    }
}
