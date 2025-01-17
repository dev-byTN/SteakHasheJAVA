public class TikkaMasala extends AbstractAction2{
    
    @Override 
    public String hand(String input, String ingredient) {
        return input + ingredient;
    }

    @Override
    public String getOrder( String client) {

        String input = hand(client, "Ch1cK3n");

        /////////////////////////////////////      STEP 2      /////////////////////////////////////
        input = knife(input, 3);

        /////////////////////////////////////      STEP 3      /////////////////////////////////////
        
        input = hand(input, "t0m4t0");

        /////////////////////////////////////      STEP 4      /////////////////////////////////////
        
        for (int i = 0; i < 1337; i++) {
            input = hashSHA1(input);
        }

        /////////////////////////////////////      STEP 5      /////////////////////////////////////
        
        input = hand(input, "sp!!!!!!c1es");

        return input;
    }

    public static void main(String[] args) {
        
        TikkaMasala tikkaMasala = new TikkaMasala();
        System.out.println(tikkaMasala.getOrder("UMPCWy1rJj9ehPDq"));
    }
}
