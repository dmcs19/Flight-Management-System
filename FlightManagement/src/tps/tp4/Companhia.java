package tps.tp4;

import java.util.*;
import org.w3c.dom.*;

public class Companhia {
    
    private String nome;
    private ArrayList<Voo> voos = new ArrayList<Voo>();
    
    public Companhia(String nome){
        this.nome = nome;
    }

    //adiciona o voo a lista de voos
    public void novo_voo(Voo voo){
        voos.add(voo);
    }

    //mostra na consola a lista dos proximos voos da companhia
    public String getProximosVoos(){
        ordenar_voos();
        String vooString = "";
        boolean tem_voos = false;
        if(voos.size() > 0){
            vooString = "Proximos voos:\n";
            //percorre todos os voos e adiciona a string aqueles que tem o estado "A tempo" ou "Atrasado"
            for(int i = 0; i < voos.size(); i++){
                if(voos.get(i).getEstado().equals("A tempo") || voos.get(i).getEstado().equals("Atrasado")){
                    vooString += voos.get(i).toString() + "\n";
                    tem_voos = true;
                }
            }
        }
        //verifica se houve algum voo adicionado a string
        if(tem_voos == true){
            return vooString;
        }else{
            return "Esta companhia não tem voos marcados.";
        }
    }

    //mostra na consola a lista do historico de voos da companhia
    public String getHistoricoVoos(){
        ordenar_voos();
        reverse_voos();
        String vooString = "";
        boolean tem_voos = false;
        if(voos.size() > 0){
            vooString = "Histórico de voos:\n";
            //percorre todos os voos e adiciona a string aqueles que tem o estado "Completo"
            for(int i = 0; i < voos.size(); i++){
                if(voos.get(i).getEstado().equals("Completo")){
                    vooString += voos.get(i).toString() + "\n";
                    tem_voos = true;
                }
            }
        }
        //verifica se houve algum voo adicionado a string
        if(tem_voos == true){
            return vooString;
        }else{
            return "Esta companhia não realizou nenhum voo.";
        }
    }

    //mostra na consola a lista de voos em atraso da companhia
    public String getVoosAtrasados(){
        ordenar_voos();
        String vooString = "";
        boolean tem_voos = false;
        if(voos.size() > 0){
            vooString = "Proximos voos em atraso:\n";
            //percorre todos os voos e adiciona a string aqueles que tem o estado "Atrasado"
            for(int i = 0; i < voos.size(); i++){
                if(voos.get(i).getEstado().equals("Atrasado")){
                    vooString += voos.get(i).toString() + "\n";
                    tem_voos = true;
                }
            }
        }
        //verifica se houve algum voo adicionado a string
        if(tem_voos == true){
            return vooString;
        }else{
            return "Esta companhia não tem voos em atraso.";
        }
    }

    //ordena a lista dos proximos voos por data e caso sejam no mesmo dia ordena por hora
    private void ordenar_voos(){
        Collections.sort(voos, Comparator.comparing(Voo::getPartida));
        Collections.sort(voos, Comparator.comparing(Voo::getData));
    }

    //troca a ordem dos voos
    private void reverse_voos(){
        Collections.reverse(voos);
    }

    //retorna o nome da companhia
    public String getNome(){
        return this.nome;
    }

    //compara duas companhias e verifica se sao iguais
    public boolean equals(Companhia companhia){
        if(companhia.nome.equals(this.nome)){
            return true;
        }
        return false;
    }

    //retona o nome da companhia
    public String toString(){
        return nome;
    }

    //constroi uma nova companhia a partir do Node passado como parametro
    public static Companhia build(Node nNode){
        Element companhia = (Element) nNode;
        String nome = companhia.getElementsByTagName("Nome").item(0).getTextContent();
        Companhia c = new Companhia(nome);
        return c;
    }

    //cria um elemento a partir da companhia atual
    public Element createElement(Document doc){
        Element eCompanhia = doc.createElement("Companhia");

        Element eNome = doc.createElement("Nome");
        eNome.appendChild(doc.createTextNode(this.getNome()));
        eCompanhia.appendChild(eNome);

        return eCompanhia;
    }
}
