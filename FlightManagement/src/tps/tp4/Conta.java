package tps.tp4;

import org.w3c.dom.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conta {
    
    private String email;
    private String password;

    public Conta(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //verifica se os detalhes da conta estao corretos
    public boolean validarConta(String email, String password) {
        if(this.email.equals(email) && this.password.equals(password)){
            return true;
        }
        return false;
    }

    //retorna o estatuto da conta
    public String getEstatuto(){
        String[] parts = this.email.split("@");
        String[] parts2 = parts[1].split("\\.");
        String prefix = parts2[0];
        if(prefix.equals("administrador")){
            return "Administrador";
        }else if(prefix.equals("piloto")){
            return "Piloto";
        }else{
            return "Cliente";
        }
    }

    //retorna o email da conta
    public String getEmail(){
        return this.email;
    }

    //retorna a password da conta
    public String getPassword(){
        return this.password;
    }

    //verifica se um mail é valido
    public boolean isValidEmailAddress() {
        boolean isEmailIdValid = false;
        if (this.email != null && this.email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(this.email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    //verifica se duas contas sao iguais
    public boolean equals(Conta conta){
        if(this.email.equals(conta.getEmail())){
            return true;
        }
        return false;
    }

    //constroi uma nova conta a partir do Node passado como parametro
    public static Conta build(Node nNode){
        Element conta = (Element) nNode;
        String email = conta.getElementsByTagName("Email").item(0).getTextContent();
        String password = conta.getElementsByTagName("Password").item(0).getTextContent();
        Conta c = new Conta(email, password);
        return c;
    }

    //cria um elemento a partir da conta atual
    public Element createElement(Document doc){
        Element eConta = doc.createElement("Conta");

        Element eEmail = doc.createElement("Email");
        eEmail.appendChild(doc.createTextNode(this.getEmail()));
        eConta.appendChild(eEmail);

        Element ePassword = doc.createElement("Password");
        ePassword.appendChild(doc.createTextNode(this.getPassword()));
        eConta.appendChild(ePassword);

        return eConta;
    }
}
