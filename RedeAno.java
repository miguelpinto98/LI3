import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class RedeAno {
	private TreeMap<Integer, RedeAutor> rano;
	private int nautores;
	private int nartunicoaut;
	
	public RedeAno() {
		this.rano = new TreeMap<>();
		this.nautores = 0;
		this.nartunicoaut = 0;
	}
	
	public RedeAno(TreeMap<Integer, RedeAutor> tra, int naut, int nunico) {
		this.rano = new TreeMap<>();
		for(Integer n : tra.keySet()) {
			RedeAutor aux = tra.get(n);
			this.rano.put(n, aux.clone());
		}
		this.nautores = naut;
		this.nartunicoaut = nunico;
	} 
	
	public RedeAno(RedeAno ra) {
		this.rano = ra.getRedeAno();
		this.nautores = ra.getNumeroAutores();
		this.nartunicoaut = ra.getNumeroArtigosUnicoAutor();
	}
	
	public int getNumeroArtigosUnicoAutor() {
		return this.nartunicoaut;
	}

	public TreeMap<Integer, RedeAutor> getRedeAno() {
		TreeMap<Integer, RedeAutor> aux = new TreeMap<>();
		
		for(Integer n : this.rano.keySet()) {
			RedeAutor ra = this.rano.get(n);
			aux.put(n, ra.clone());
		}
		return aux;
	}
	
	public int getNumeroAutores() {
		return this.nautores;
	}
	
	public void setNumeroAutores(int n) {
		this.nautores = n;
	}
	
	public void setNumeroArtigosUnicoAutor(int nunico) {
		this.nartunicoaut = nunico;
	}

	public RedeAno clone() {
		return new RedeAno(this);
	}
	
	public String toString() {
		StringBuilder s =  new StringBuilder("REDE\n");
		
		for(Integer n : this.rano.keySet())
			s.append("Ano: "+n+"\n"+ this.rano.get(n).toString());
		
		return s.toString();
	}
	
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if((o == null) || (this.getClass() != o.getClass()))
			return false;
		RedeAno ra = (RedeAno) o;
		return this.rano.equals(ra.getRedeAno());
	}
	
	@SuppressWarnings("unchecked")
	public void carregaRM(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream o = new ObjectInputStream(f);
        
        this.rano = (TreeMap<Integer, RedeAutor>) o.readObject();

        o.close();
        f.close();
	}
	
	public void gravaRM(String file) throws FileNotFoundException, IOException {
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream o = new ObjectOutputStream(f);
        
        o.writeObject(this.rano);

        o.close();
        f.close();
	}
	
	public int anoInferior() {
		return this.rano.firstKey();
	}
	
	public int anoSuperior() {
		return this.rano.lastKey();
	}
	
	public void insereRedeAno(int ano, Autor a, ArrayList<Autor> ca) {
		RedeAutor ra = null;
		
		if(this.rano.containsKey(ano)) {
			ra = this.rano.get(ano);
			ra.insereAutores(a,ca);
		} else {
			ra = new RedeAutor();
			ra.insereAutores(a, ca);
			this.rano.put(ano, ra);		
		}
	}

	public void addNumeroAutores(int tam) {
		this.setNumeroAutores(this.getNumeroAutores()+tam);
	}
	
	public void addNumeroArtigosUnicoAutor() {
		this.setNumeroArtigosUnicoAutor(this.getNumeroArtigosUnicoAutor()+1);
	} 
	
	public TreeMap<Integer, HashSet<String>> AutoresEntreAnos(int anoi, int anof) {
		TreeMap<Integer,HashSet<String>> laut = new TreeMap<>();
		
		for(Integer i : this.rano.keySet()) {
			if(i>=anoi && i<=anof) {
				HashSet<String> la = this.rano.get(i).autoresAno();
				laut.put(i, la);
			}
		}
		return laut;
	}
	
	public HashSet<String> AutoresPorTodosAnos(int anoi, int anof) {
		TreeMap<Integer, HashSet<String>> lautores = this.AutoresEntreAnos(anoi, anof);
		int n = lautores.firstKey();
		HashSet<String> hs = lautores.get(n), aux;
		
		for(Integer i : lautores.keySet()) {
			aux = new HashSet<>();
			for(String s : lautores.get(i)) {
				if(hs.contains(s))
					aux.add(s);
				}
			hs = aux;
		}
		return hs;
	}

	public TreeMap<Integer, Integer> listaPublicacoesPorAno() {
		TreeMap<Integer, Integer> lpa = new TreeMap<>();
		
		for(Integer i : this.rano.keySet())
			lpa.put(i, this.rano.get(i).getNumeroPublicacoes());
		
		return lpa;
	}

	public void addNumeroPublicacoesAno(int ano) {
		this.rano.get(ano).addNumeroPublicacoes();
	}
	
	
	public int coisa() {
		HashSet<String> hsa = new HashSet<>();
		HashSet<String> hsb = new HashSet<>();
		
		for(RedeAutor ra : this.rano.values()) {
			ra.verificaAutoresPublicaramSozinhos(hsa, hsb);
		}
		return hsa.size();
	}
	
	
	
	
}
