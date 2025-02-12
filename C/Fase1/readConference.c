#include "readConference.h"

int minpg, entradaTotalConf, entradasTotais;

static int verificaSigla(char *art, int isgl) {
	int i=isgl+1, okSigla=0, encontra=1;
	
	if(art[i]==' ') {
		okSigla=1;
		i=i+1;
	}
	
	if(art[i]==' ')
		okSigla=0;
	
	while(art[i]!='\n' && okSigla && encontra) {
		if(art[i]==' ' && isdigit(art[i+1]))
				encontra=0;
		i++;
	}
	
	if(art[i-1]==' ' && (isdigit(art[i])) && okSigla)
		okSigla=1;
	else
		okSigla=0;
	
	return (okSigla) ? i : okSigla;
}

static int verificaAno(char *art, int iano) {
	int i=iano+1, okAno=1;
	
	if(art[i]==':')
		okAno=0;
	
	while(art[i]!=':' && art[i]!='\n' && okAno) {
		okAno=isdigit(art[i]);
		i++;
	}
	
	if(art[i]==':' && okAno)
		okAno=1;
	else
		okAno=0;
		
	return (okAno) ? i : okAno;
}

static int verificaPaginas(char *art, int ipg) {
	int i=ipg+1, j=0, okPag=1, pagInf=0, pagSup=0, paginas=minpg;
	char num1[10];
	char num2[10];

	/*Limpar BUFFER*/
	memset(&num1,0,10);
	memset(&num2,0,10);
	
	while(art[i]!='-' && art[i]!='\n' && okPag) {
		okPag=isdigit(art[i]);
		num1[j]=art[i];
		i++;
		j++;
	}
	
	if(art[i]=='-' && okPag) {
		okPag=1;
		i++;
		j=0;
	}
	else
		okPag=0;
		
	while(art[i]!='\n' && okPag) {
		okPag=isdigit(art[i]);
		num2[j]=art[i];		
		i++;
		j++;
	} 
		
	if(okPag) {
		pagInf=atoi(num1);
		pagSup=atoi(num2);
	}
	
	if((pagSup-pagInf+1) >= paginas && okPag)
		okPag=1;
	else
		okPag=0;
				
	return okPag;
}


static int treatsConference(char *art) {
	int testConf=0;
	
	testConf=verificaInt(art);
	if(testConf)
		testConf=verificaAuthors(art,testConf);
		if(testConf)
			testConf=verificaTitle(art,testConf);
			if(testConf)
				testConf=verificaSigla(art,testConf);
				if(testConf)
					testConf=verificaAno(art,testConf);
					if(testConf)
						testConf=verificaPaginas(art,testConf);	
						if(testConf) {
							return testConf;
						}
	return 0;
}

void readConference(char *file, FILE*fr) {
	FILE *fc;
	fc = fopen(file,"r");
	char art[MAXART];
	int entradas=0,aceites=0,rejeitados=0;
	
	int okConf=0;
	
	if(!fc)
		printf("Ficheiro %s não existe!\n",file);
	else {
			aceites=0;
			rejeitados=0;
		while(fgets(art,sizeof(art),fc)) {
			okConf = treatsConference(art);
			entradas++;
			entradasTotais++;
			
			if(okConf) {
				aceites++;
				entradaTotalConf++;
			}
			else
				rejeitados++;
		}
		writeRejeitados(fr,file,rejeitados);	
	}
	fclose(fc);
}
