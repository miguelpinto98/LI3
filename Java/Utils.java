import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.out;

@SuppressWarnings("serial")
public class Utils implements Serializable{
	public static HashSet<Autor> nome = new HashSet<>();
	public static HashSet<String> autPubOne = new HashSet<>();
	
	public static ArrayList<String> leLinhasScanner(String fichName) throws FileNotFoundException{
		ArrayList<String> linhas = new ArrayList<String>();
		Scanner fichScan = null; 
		try {
			fichScan = new Scanner(new FileReader(fichName));
            fichScan.useDelimiter(System.getProperty("line.separator"));
            while (fichScan.hasNext()) linhas.add(fichScan.next());
            fichScan.close();
		} 
		catch(IOException e) {out.println(e.getMessage()); return null; }
		catch(Exception e) {out.println(e.getMessage()); return null; }
       
		return linhas;
	}
	
	public static void trataLinhas(RedeAno ra ,ArrayList<String> linhas) {
		for(String s : linhas) {
			trataLinha(ra,s);
		}
	}

	public static void trataLinha(RedeAno ra, String linha) {
		String[] str = linha.split(", ");
		ArrayList<Autor> ca = new ArrayList<>();
		int tam = str.length-1, ano = Integer.parseInt(str[tam]);		
		
		for(int i=0; i<tam; i++) {
			Autor a = new Autor(str[i]);
			
			if(tam==1) {
				ra.addNumeroArtigosUnicoAutor();
				autPubOne.add(a.getNomeAutor());
			}
			
			if(!(nome.contains(a)))
				nome.add(a);
			ca = new ArrayList<>();			
			for(int j=0; j<tam; j++) {
				if(i!=j)
					ca.add(new Autor(str[j]));
			}
			ra.insereRedeAno(ano, a, ca);
		}
		ra.addNumeroAutores(tam);
		ra.addNumeroPublicacoesAno(ano);
		ra.addAutoresDistintos(nome.size());
		ra.addAutoresQueEscreveramUmaVezSolo(autPubOne.size());
	}
	
	public static int verificaDuplicados(String fichName) {
		HashSet<String> linhas = new HashSet<String>();
		String s = null;
		int res = 0;
		
		Scanner fichScan = null; 
		try {
			fichScan = new Scanner(new FileReader(fichName));
            fichScan.useDelimiter(System.getProperty("line.separator"));
            while (fichScan.hasNext()) {
            	s=fichScan.next();
            	if(linhas.contains(s))
            		res++;
            	else
            		linhas.add(s);
            }
            fichScan.close();
		} 
		catch(IOException e) {out.println(e.getMessage()); return 0; }
		catch(Exception e) {out.println(e.getMessage()); return 0; }
				
		return res;
	}
}