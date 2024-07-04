package tps.tp4;

import java.util.*;
import org.w3c.dom.*;

public class Aviao {

    private String modelo;
    private int capacidade;
    private ArrayList<Voo> voos = new ArrayList<Voo>();

    public Aviao(String modelo, int capacidade){
        this.modelo = modelo;
        this.capacidade = capacidade;
    }

    //adiciona o voo a lista de proximos voos
    public void novo_voo(Voo voo){
        voos.add(voo);
    }

    //mostra na consola a lista dos proximos voos do aviao
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
            return "Este avião não tem voos marcados.";
        }
    }

    //mostra na consola a lista do historico de voos do aviao
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
            return "Este avião não realizou nenhum voo.";
        }
    }

    //ordena a lista dos proximos voos por data e caso sejam no mesmo dia ordena por hora
    private void ordenar_voos(){
        Collections.sort(voos, Comparator.comparing(Voo::getPartida));
        Collections.sort(voos, Comparator.comparing(Voo::getDate));
    }

    //troca a ordem dos voos
    private void reverse_voos(){
        Collections.reverse(voos);
    }

    //retorna o modelo do Aviao
    public String getModelo(){
        return this.modelo;
    }

    //retorna a capacidade do aviao
    public int getCapacidade(){
        return this.capacidade;
    }

    //verifica se dois avioes são iguais
    public boolean equals(Aviao aviao){
        if(this.modelo.equals(aviao.modelo)){
            return true;
        }
        return false;
    }

    //retorna as informacoes do aviao
    public String toString(){
        return "Modelo: " + modelo + " | Capacidade: " + capacidade + "\n";
    }

    //constroi um novo aviao a partir do objeto Node passado como parametro
    public static Aviao build(Node nNode){
        Element aviao = (Element) nNode;
        String modelo = aviao.getElementsByTagName("Modelo").item(0).getTextContent();
        int capacidade = Integer.parseInt(aviao.getElementsByTagName("Capacidade").item(0).getTextContent());
        Aviao a = new Aviao(modelo, capacidade);
        return a;
    }

    //cria um elemento a partir do aviao atual
    public Element createElement(Document doc){
        Element eAviao = doc.createElement("Aviao");

        Element eModelo = doc.createElement("Modelo");
        eModelo.appendChild(doc.createTextNode(this.getModelo()));
        eAviao.appendChild(eModelo);

        Element eCapacidade = doc.createElement("Capacidade");
        eCapacidade.appendChild(doc.createTextNode(Integer.toString(this.getCapacidade())));
        eAviao.appendChild(eCapacidade);

        return eAviao;
    }
}
