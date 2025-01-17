public class Steak extends AbstractAction2{
    /*private int arg1,arg2,arg3;
    public Steak(int arg1, int arg2, int arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }*/
    @Override
    public String hand(String input, String ingredient) {
        return input + ingredient;
    } 
    @Override
    public String getOrder(String client) {
        //RecipeFetcher reci = new RecipeFetcher();
        //int loop = reci.convertAndDisplay().
       String input = hand(client, "st3aK");
       for ( int i = 0; i<14224; i ++) {
        input = md5(input);
       }

       return input;
    }
    /*public static void main(String[] args) {
        Steak steak = new Steak();
        System.out.println(steak.getOrder("C2lZKoJQvKjNrJxY"));
    }*/
}
