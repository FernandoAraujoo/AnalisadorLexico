public class Token {
     


    private TipoToken tipo; 


    private String lexema; 






    public Token(TipoToken type, String value) { 


        this.tipo = type; 


        this.lexema = value; 


    } 



    public TipoToken getTipo() { 


        return tipo; 


    } 






    public String getlexema() { 


        return lexema; 


    } 






    @Override 


    public String toString() { 


        return "Token{" + "tipo= " + tipo + ", lexema='" + lexema + '\'' + '}'; 


    } 


} 


