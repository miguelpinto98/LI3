import static java.lang.System.out;
import java.util.ArrayList;

public class TesteLI3 {
	
	static RedeAno ra = new RedeAno();
	
	public static void main(String[] args) {
		ArrayList<String> linhas = new ArrayList<String>();
	      
	    long inicio = System.nanoTime();
	    linhas = Utils.leLinhasScanner("publicx.txt");
	       
	    out.println("Linhas Lidas com Scanner: " + linhas.size());
	    	    
	    Utils.trataLinhas(ra,linhas);
	    System.out.println(ra.toString());
	    
	    long fim = System.nanoTime();
	    out.println("Tempo: " + (fim - inicio)/1.0E09 + " segs.\n");
	   }
	}
