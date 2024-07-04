package tps.tp4;

import java.util.*;
import org.w3c.dom.*;

public class Piloto {
    
    private String nome;
    private int tempo_voo;
    private ArrayList<Voo> voos = new ArrayList<Voo>();

    public Piloto(String nome, int horas_voo){
        this.nome = nome;
        this.tempo_voo = horas_voo;
    }

    //adiciona o voo a lista de proximos voos
    public void novo_voo(Voo voo){
        voos.add(voo);
    }

    //mostra na consola a lista dos proximos voos do piloto
    public String getProximosVoosConsola(){
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
            return "Este piloto não tem voos marcados.";
        }
    }

    //mostra na consola a lista do historico de voos do piloto
    public String getHistoricoVoosConsola(){
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
            return "Este piloto não realizou nenhum voo.";
        }
    }

    //atualiza as horas de voo do piloto
    public void atualizarHorasVoo(){
        tempo_voo = 0;
        if(voos.size() > 0){
            for(int i = 0; i < voos.size(); i++){
                if(voos.get(i).getEstado().equals("Completo")){
                    tempo_voo += voos.get(i).getDuracao();
                }
            }
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

    //retorna o tempo de voo do piloto
    public int getTempoVoo(){
        return tempo_voo;
    }

    //retorna o nome do piloto
    public String getNome(){
        return this.nome;
    }

    //retorna o numero total de voos do piloto 
    public int getNumVoos(){
        return voos.size();
    }

    //retorna o numero de proximos voos do piloto 
    public int getNumProximosVoos(){
        int num = 0;
        for(int i = 0; i < voos.size(); i++){
            if(voos.get(i).getEstado().equals("A Tempo") || voos.get(i).getEstado().equals("Atrasado")){
                num++;
            }
        }
        return num;
    }

    //retorna o numero de voos feitos pelo piloto 
    public int getNumHistoricoVoos(){
        int num = 0;
        for(int i = 0; i < voos.size(); i++){
            if(voos.get(i).getEstado().equals("Completo")){
                num++;
            }
        }
        return num;
    }

    // retorna os proximos voos do piloto
    public ArrayList<Voo> getProximosVoos(){
        ordenar_voos();
        ArrayList<Voo> proximos_voos = new ArrayList<Voo>();
        for(int i = 0; i < voos.size(); i++){
            if(voos.get(i).getEstado().equals("A Tempo") || voos.get(i).getEstado().equals("Atrasado")){
                proximos_voos.add(voos.get(i));
            }
        }
        return proximos_voos;
    }

    // retorna os voos completos do piloto
    public ArrayList<Voo> getHistoricoVoos(){
        ordenar_voos();
        reverse_voos();
        ArrayList<Voo> historico_voos = new ArrayList<Voo>();
        for(int i = 0; i < voos.size(); i++){
            if(voos.get(i).getEstado().equals("Completo")){
                historico_voos.add(voos.get(i));
            }
        }
        return historico_voos;
    }

    //retorna todos os voos do piloto
    public ArrayList<Voo> getVoos(){
        ordenar_voos();
        return voos;
    }

    //verifica se dois pilotos sao iguais
    public boolean equals(Piloto piloto){
        if(this.nome.equals(piloto.nome)){
            return true;
        }
        return false;
    }

    //retorna as informacoes do piloto
    public String toString(){
        return "Piloto:\nNome: " + nome + " | Tempo_de_voo: " + tempo_voo + "h\n";
    }

    //constroi um piloto a partir do Node dado como parametro
    public static Piloto build(Node nNode){
        Element piloto = (Element) nNode;
        String nome = piloto.getElementsByTagName("Nome").item(0).getTextContent();
        int horas_voo = Integer.parseInt(piloto.getElementsByTagName("Horas_de_voo").item(0).getTextContent());
        Piloto p = new Piloto(nome, horas_voo);
        return p;
    }

    //cria um elemento a partir do piloto atual
    public Element createElement(Document doc){
        Element ePiloto = doc.createElement("Piloto");

        Element eNome = doc.createElement("Nome");
        eNome.appendChild(doc.createTextNode(this.getNome()));
        ePiloto.appendChild(eNome);

        Element eHoras = doc.createElement("Horas_de_voo");
        eHoras.appendChild(doc.createTextNode(Integer.toString(this.getTempoVoo())));
        ePiloto.appendChild(eHoras);

        return ePiloto;
    }
}
