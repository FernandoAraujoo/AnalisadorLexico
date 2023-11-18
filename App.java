import java.util.ArrayList;
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class App { 

    private String codigoFonte; 


    private int PosicaoAtual = 0; 


    private List<Token> tokens = new ArrayList<>(); 
 
    private static final String[] PALAVRA_RESERVADA = {  "int", "float", "char", "boolean", "void", "if", "else", "for", "while", "scanf", "println", "main" ,"return" }; 


    private static final String[] SÍMBOLO_ESPECIAL = { "(", ")", "[", "]", "{", "}", ",", ";" }; 

    public App(String sourceCode) { 


        this.codigoFonte = sourceCode; 


    } 

    public List<Token> analyze() { 


        while (PosicaoAtual < codigoFonte.length()) { 


            char currentChar = codigoFonte.charAt(PosicaoAtual); 


            if (Character.isWhitespace(currentChar)) { 


                PosicaoAtual++; 


            } else if (currentChar == '.') { 


                analyzeDecimal(); 


            }else if (Character.isDigit(currentChar)) { 


                analyzeInteger(); 


            }  else if (Character.isLetter(currentChar) || currentChar == '_') { 


                analyzeIdentifierOrKeyword(); 


            } else if (currentChar == '"') { 


                analyzeText(); 


            } else if (currentChar == '/') { 


                if (PosicaoAtual + 1 < codigoFonte.length() && codigoFonte.charAt(PosicaoAtual + 1) == '/') { 

                    analyzeComment(); 

                } else { 


                    analyzeOperator(); 

                } 

            } else if (isOperadorSimbolo(currentChar)) { 


                analyzeOperator(); 


            } else if (isSimboloEspecial(String.valueOf(currentChar))) { 


                analyzeSpecialSymbol(); 


            } else { 


                System.err.println("Erro: Token inválido na posição " + PosicaoAtual); 


                PosicaoAtual++; 

            } 

        } 

        return tokens; 
    } 

    private void analyzeInteger() { 


        StringBuilder tokenValue = new StringBuilder(); 


        while (PosicaoAtual < codigoFonte.length() && Character.isDigit(codigoFonte.charAt(PosicaoAtual))) { 


            tokenValue.append(codigoFonte.charAt(PosicaoAtual)); 


            PosicaoAtual++; 

        } 

        if (PosicaoAtual < codigoFonte.length() 


                && (Character.isDigit(codigoFonte.charAt(PosicaoAtual)) || codigoFonte.charAt(PosicaoAtual) == '.')) { 


            tokens.add(new Token(TipoToken.NUM_DECIMAL, tokenValue.toString())); 


        } else { 


            tokens.add(new Token(TipoToken.NUM_INTEIRO, tokenValue.toString())); 

        } 


    } 

    private void analyzeDecimal() { 


        StringBuilder tokenValue = new StringBuilder();

        while (PosicaoAtual < codigoFonte.length() &&
                (Character.isDigit(codigoFonte.charAt(PosicaoAtual)) || codigoFonte.charAt(PosicaoAtual) == '.')) {
            tokenValue.append(codigoFonte.charAt(PosicaoAtual));
            PosicaoAtual++;
        }
    
        // Adiciona ".0" se não houver parte decimal
        if (!tokenValue.toString().contains(".")) {
            tokenValue.append(".0");
        }
    
        tokens.add(new Token(TipoToken.NUM_DECIMAL, tokenValue.toString()));
    }

    private void analyzeIdentifierOrKeyword() { 


        StringBuilder tokenValue = new StringBuilder(); 


        while (PosicaoAtual < codigoFonte.length() && (Character.isLetterOrDigit(codigoFonte.charAt(PosicaoAtual)) 


                || codigoFonte.charAt(PosicaoAtual) == '_')) { 


            tokenValue.append(codigoFonte.charAt(PosicaoAtual)); 


            PosicaoAtual++; 


        } 


        String tokenString = tokenValue.toString(); 


        if (isKeyword(tokenString)) { 


            tokens.add(new Token(TipoToken.PALAVRARESERVADA, tokenString)); 


        } else { 


            tokens.add(new Token(TipoToken.IDENTIFICADOR, tokenString)); 


        } 


    } 

    private void analyzeText() { 


        PosicaoAtual++; 


        StringBuilder tokenValue = new StringBuilder(); 


        while (PosicaoAtual < codigoFonte.length() && codigoFonte.charAt(PosicaoAtual) != '"') { 


            tokenValue.append(codigoFonte.charAt(PosicaoAtual)); 


            PosicaoAtual++; 


        } 


        PosicaoAtual++; // Avança para além da última aspa dupla 


        tokens.add(new Token(TipoToken.TEXTO, tokenValue.toString())); 


    } 


    private void analyzeComment() { 


        PosicaoAtual += 2; // Pula os dois caracteres "//" 


        while (PosicaoAtual < codigoFonte.length() && codigoFonte.charAt(PosicaoAtual) != '\n') { 


            PosicaoAtual++; 


        } 


    } 

    private void analyzeOperator() { 


        String operator = String.valueOf(codigoFonte.charAt(PosicaoAtual)); 


        PosicaoAtual++; 


        while (PosicaoAtual < codigoFonte.length() && isOperadorSimbolo(codigoFonte.charAt(PosicaoAtual))) { 


            operator += codigoFonte.charAt(PosicaoAtual); 


            PosicaoAtual++; 


        } 


        tokens.add(new Token(TipoToken.OPERADOR, operator)); 


    } 


    private void analyzeSpecialSymbol() { 


        String symbol = String.valueOf(codigoFonte.charAt(PosicaoAtual)); 


        PosicaoAtual++; 


        tokens.add(new Token(TipoToken.SÍMBOLO, symbol)); 


    } 


    private boolean isKeyword(String tokenString) { 


        for (String keyword : PALAVRA_RESERVADA) { 


            if (keyword.equals(tokenString)) { 


                return true; 


            } 


        } 


        return false; 


    } 

    private boolean isOperadorSimbolo(char c) { 


        return "=+-*/%!<>".indexOf(c) != -1; 


    } 

    private boolean isSimboloEspecial(String symbol) { 


        for (String s : SÍMBOLO_ESPECIAL) { 


            if (s.equals(symbol)) { 


                return true; 

            } 

        } 

        return false; 

    } 
}
