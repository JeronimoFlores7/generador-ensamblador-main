import java.io.*;

public class AnaLex{
	private static int a_a = 0;
	private static int a_i = 0;
	private static int filesize = 0;
	private static boolean fin_archivo = false;
	private static char[] linea;
	private static int diag = 0;
	private static int estado = 0;
	private static int c;
	private static String lex = "";
	private static String entrada = "";
	private static String salida = "";
	private static String miToken = "";
	private static int renglon = 1;

	public static boolean creaEscribeArchivo(File xFile, String mensaje){
		PrintWriter fileOut;
		try {
			fileOut = new PrintWriter(new FileWriter(xFile, true));
			if(mensaje=="666"){
				fileOut.print(mensaje);
			}else{
				fileOut.println(mensaje);
			}
			fileOut.close();
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}


	public static boolean es_any(int x){
		return x != 10 && x != 13 && x != 255;
	}

	public static boolean es_delim(int x){
		return x == 9 || x == 10 || x == 13 || x == 32;
	}

	public static String ob_lex(){
		String x = "";
		for(int i= a_i; i<a_a; i++)
			x += linea[i];
		return x;
	}
	
	public static int lee_car(){
		if(a_a <= filesize-1){
			if(c == 10){
				renglon++;
			}
			return linea[a_a++];
		}else{
			fin_archivo = true;
			return 255;
		}
	}
	
	public static char[] abreLeeCierra(String xName){
		File xFile = new File(xName);
		char[] data;
		try{
			FileReader fin = new FileReader(xFile);
			filesize = (int) xFile.length();
			data = new char[filesize+1];
			fin.read(data,0,filesize);
			data[filesize] = ' ';
			filesize++;
			return data;
		}catch(FileNotFoundException exc){
			
		}catch(IOException exc){
			
		}
		return null;
	}
	
	public static File xArchivo(String xName){
		File xFile = new File(xName);
		return xFile;
	}

	public static String pausa(){
		BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
		String nada = null;
		try{
			nada = entrada.readLine();
			return(nada);
		}catch(Exception e){
			System.err.println(e);
		}
		return("");
	}
	
	public static void error(){
		System.out.println("ERROR: En el carácter: " + (char) c + " cerca del renglon " + renglon);
		System.exit(4);
	}
	
	public static int DIAGRAMA(){
		a_a = a_i;
		switch(diag){
			case 0:
				diag = 3;
				break;
			case 3:
				diag = 7;
				break;
			case 7:
				diag = 12;
				break;
			case 12:
				diag = 15;
				break;
			case 15:
				diag = 19;
				break;
			case 19:
				diag = 30;
				break;
			case 30:
				diag = 47;
				break;
			case 47:
				error();				
		}
		return diag;
	}
	
	public static String TOKEN(){
		do{
			switch(estado){
				case 0:
					c=lee_car();
					if(es_delim(c)){
						estado=1;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 1:
					c=lee_car();
					if(es_delim(c)){
						estado=1;
					}else{
						estado=2;
					}
					break;
				case 2:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "nosirve";
				case 3:
					c = lee_car();
					if(Character.isAlphabetic(c)){
						estado = 4;
					}else{
						estado = DIAGRAMA();
					}
					break;
				case 4:
					c = lee_car();
					if(Character.isAlphabetic(c)){
						estado = 4;
						break;
					}
					if(Character.isDigit(c)){
						estado = 4;
						break;
					}
					if(c == '_'){
						estado = 5;
					}else{
						estado = 6;
					}
					break;
				case 5:
					c=lee_car();
					if(Character.isAlphabetic(c)){
						estado=4;
						break;
					}
					if(Character.isDigit(c)){
						estado = 4;
						break;
					}else{
						estado=DIAGRAMA();						
					}
					break;
				case 6:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "id";
				case 7:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=8;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 8:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=8;
						break;
					}
					if(c=='.'){
						estado=9;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 9:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=10;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 10:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=10;
					}else{
						estado=11;
					}
					break;
				case 11:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "dec";
				case 12:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=13;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 13:
					c=lee_car();
					if(Character.isDigit(c)){
						estado=13;
					}else{
						estado=14;
					}
					break;
				case 14:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "ent";
				case 15:
					c=lee_car();
					if (c=='/'){
						estado=16;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 16:
					c=lee_car();
					if (c=='/'){
						estado=17;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 17:
					c=lee_car();
					if(es_any(c)){
						estado=17;
					}else{
						estado=18;
					}
					break;
				case 18:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "nosirve";
				case 19:
					c=lee_car();
					switch (c){
						case '+':
							estado=20;
							break;
						case '-':
							estado=21;
							break;
						case '*':
							estado=22;
							break;
						case '/':
							estado=23;
							break;
						case '(':
							estado=24;
							break;
						case ')':
							estado=25;
							break;
						case '{':
							estado=26;
							break;
						case '}':
							estado=27;
							break;
						case ':':
							estado=28;
							break;
						case ',':
							estado=29;
							break;
						default:
							estado=DIAGRAMA();
					}
					break;
				case 20:
					lex = ob_lex();
					a_i = a_a;
					return "+";
				case 21:
					lex = ob_lex();
					a_i = a_a;
					return "-";
				case 22:
					lex = ob_lex();
					a_i = a_a;
					return "*";
				case 23:
					lex = ob_lex();
					a_i = a_a;
					return "/";
				case 24:
					lex = ob_lex();
					a_i = a_a;
					return "(";
				case 25:
					lex = ob_lex();
					a_i = a_a;
					return ")";
				case 26:
					lex = ob_lex();
					a_i = a_a;
					return "{";
				case 27:
					lex = ob_lex();
					a_i = a_a;
					return "}";
				case 28:
					lex = ob_lex();
					a_i = a_a;
					return ":";
				case 29:
					lex = ob_lex();
					a_i = a_a;
					return ",";
				case 30:
					c=lee_car();
					switch(c){
						case '=':
							estado=31;
							break;
						case '<':
							estado=37;
							break;
						case '>':
							estado=42;
							break;
						case '!':
							estado=45;
							break;
						default:
							estado=DIAGRAMA();
					}
					break;
				case 31:
					c=lee_car();
					if(c=='/'){
						estado=32;
						break;
					}
					if(c=='>'){
						estado=34;
						break;
					}
					if(c=='<'){
						estado=35;
						break;
					}else{
						estado=36;
					}
					break;
				case 32:
					c=lee_car();
					if(c=='='){
						estado=33;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 33:
					lex = ob_lex();
					a_i = a_a;
					return "dif";
				case 34:
					lex = ob_lex();
					a_i = a_a;
					return "mayi";
				case 35:
					lex = ob_lex();
					a_i = a_a;
					return "meni";
				case 36:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "igual";
				case 37:
					c=lee_car();
					if(c=='>'){
						estado=38;
						break;
					}
					if(c=='='){
						estado=39;
						break;
					}
					if(c=='-'){
						estado=40;
						break;
					}else{
						estado=41;
					}
					break;
				case 38:
					lex = ob_lex();
					a_i = a_a;
					return "dif";
				case 39:
					lex = ob_lex();
					a_i = a_a;
					return "meni";
				case 40:
					lex = ob_lex();
					a_i = a_a;
					return "asig";
				case 41:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "men";
				case 42:
					c=lee_car();
					if(c=='='){
						estado=43;
					}else{
						estado=44;
					}
					break;
				case 43:
					lex = ob_lex();
					a_i = a_a;
					return "mayi";
				case 44:
					a_a--;
					lex = ob_lex();
					a_i = a_a;
					return "may";
				case 45:
					c=lee_car();
					if(c=='='){
						estado=46;
					}else{
						estado=DIAGRAMA();
					}
					break;
				case 46:
					lex = ob_lex();
					a_i = a_a;
					return "dif";
				case 47:
					c=lee_car();
					if(c == 255){
						estado = 48;
					}else{
						estado = DIAGRAMA();
					}
					break;
				case 48:
					lex = ob_lex();
					a_i = a_a;
					return "nosirve";
			}
		}while(true);
	}
	
	public static void main(String args[]){
		entrada = args[0] + ".prg";
		salida = args[0] + ".sal";

		if(!xArchivo(entrada).exists()){
			System.out.println("\n\nERROR: El archivo no existe");
			System.exit(4);
		}
		linea = abreLeeCierra(entrada);
		do{
			estado = 0;
			diag = 0;
			miToken = TOKEN();
			switch(lex){
				case "datos":
					miToken = "datos";
					break;
				case "fin_datos":
					miToken = "fin_datos";
					break;
				case "entero":
					miToken = "entero";
					break;
				case "decimal":
					miToken = "decimal";
					break;
				case "cierto":
					miToken = "cierto";
					break;
				case "haz":
					miToken = "haz";
					break;
				case "falso":
					miToken = "falso";
					break;
				case "fin_cond":
					miToken = "fin_cond";
					break;
				case "mientras":
					miToken = "mientras";
					break;
				case "fin_mientras":
					miToken = "fin_mientras";
					
			}
			if(!miToken.equals("nosirve")){
				creaEscribeArchivo(xArchivo(salida), miToken);
				creaEscribeArchivo(xArchivo(salida), lex);
				creaEscribeArchivo(xArchivo(salida), renglon + "");
			}

			a_i = a_a;
		}while(!fin_archivo);
		creaEscribeArchivo(xArchivo(salida), "eof");
		creaEscribeArchivo(xArchivo(salida), "eof");
		creaEscribeArchivo(xArchivo(salida), "666");
		System.out.println("Analisis Lexico Correcto"); 
	}
}