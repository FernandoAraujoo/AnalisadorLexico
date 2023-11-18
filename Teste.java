import java.io.File; 


import java.io.FileNotFoundException; 


import java.util.List; 


import java.util.Scanner; 


 
 


public class Teste { 


    public static void main(String[] args) throws FileNotFoundException { 


        File arquivo = new File("/workspaces/AnalisadorLexico/teste1.txt"); 


        StringBuilder stringBuilder = new StringBuilder(); 
        
        Scanner scanner = new Scanner(arquivo); 
 
 


        while (scanner.hasNextLine()) { 


            String line = scanner.nextLine(); 


            stringBuilder.append(line).append("\n"); 


        } 


 
 


        String codFonte = stringBuilder.toString(); 


        App lexema = new App(codFonte); 


        List<Token> tokens = lexema.analyze(); 


 
 


        System.out.println("RESULTADOS/TOKENS: \n"); 


        for (Token token : tokens) { 


            System.out.println(" (  "+token.getlexema() +"  ) " + ", tipo do token = " + token.getTipo()); 


        } 


        scanner.close(); 


    } 


} 

