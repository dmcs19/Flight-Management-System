package tps.tp4;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.w3c.dom.*;

public class Voo {

    private String codigo;
    private Companhia companhia;
    private Piloto piloto;
    private Aeroporto origem;
    private Aeroporto destino;
    String hora_partida;
    String hora_chegada;
    String date;
    private Date partida;
    private Date chegada;
    private Date data;
    private int duracao;
    private Aviao aviao;
    private String estado;

    public Voo(String codigo, Aviao aviao, Companhia companhia, Piloto piloto, Aeroporto origem, Aeroporto destino, String hora_partida, String hora_chegada, String date) throws ParseException{
        this.codigo = codigo;
        this.aviao = aviao;
        this.companhia = companhia;
        this.piloto = piloto;
        this.origem = origem;
        this.destino = destino;
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dia = new SimpleDateFormat("dd-MM-yyyy");
        this.hora_partida = hora_partida;
        this.hora_chegada = hora_chegada;
        this.date = date;
        this.data = dia.parse(date);
        this.partida = hora.parse(hora_partida);
        this.chegada = hora.parse(hora_chegada);
        this.novo_voo();
        this.estado = "A Tempo";
    }

    //adicionar o voo ao registo: piloto; aviao; companhia; aeroporto
    private void novo_voo(){
        piloto.novo_voo(this);
        aviao.novo_voo(this);
        companhia.novo_voo(this);
        origem.novo_voo(this);
        destino.novo_voo(this);
    }

    //muda o estado do voo para completo
    public void completo(){
        this.estado = "Completo";
        piloto.atualizarHorasVoo();
    }

    //muda o estado do voo para cancelado
    public void cancelado(){
        this.estado = "Cancelado";
    }

    //muda o estado do voo para atrasado
    public void atrasado(){
        this.estado = "Atrasado";
    }

    //retorna a duraçao do voo em horas
    public int getDuracao(){
        long diferenca = chegada.getTime() - partida.getTime();
        duracao = (int)(diferenca / (1000 * 60 * 60)) % 24;
        if(duracao <= 0){
            duracao += 24;
        }
        return duracao;
    }

    //retorna o aeroporto de onde sai o voo
    public Aeroporto getOrigem(){
        return origem;
    }

    //retorna o aeroporto de destino do voo
    public Aeroporto getDestino(){
        return destino;
    }

    //retorna a hora de partida
    public String getPartida(){
        return hora_partida;
    }

    //retorna a hora de chegada
    public String getChegada(){
        return hora_chegada;
    }

    //retorna a data do voo
    public String getDate(){
        return date;
    }

    //retorna a data do voo em formato Date
    public Date getData(){
        return data;
    }

    //retorna o estado do voo
    public String getEstado(){
        return estado;
    }

    //retorna o codigo do voo
    public String getCodigo(){
        return codigo;
    }

    //retorna o aviao do voo
    public Aviao getAviao(){
        return aviao;
    }

    // retorna a companhia que vai realizar o voo
    public Companhia getCompanhia(){
        return companhia;
    }

    // retorna o piloto do voo
    public Piloto getPiloto(){
        return piloto;
    }

    //verifica se dois voos sao iguais
    public boolean equals(Voo voo){
        if(codigo.equals(voo.codigo)){
            return true;
        }
        else{
            return false;
        }
    }

    //da print das informações do voo
    public String toString(){
        return "Código: " + codigo + " | Companhia: " + companhia.toString() + " | Origem: " + origem.toString() + " | Destino: " + destino.toString() + " | Partida: " + hora_partida + " | Chegada: " + hora_chegada + " | Data: " + date + " | Estado: " + estado;
    } 

    //constroi um voo a partir do Node dado como parametro
    public static Voo build(Node nNode, ArrayList<Companhia> companhias, ArrayList<Aviao> avioes, ArrayList<Piloto> pilotos, ArrayList<Aeroporto> aeroportos) throws ParseException{
        Element voo = (Element) nNode;
        //retorna null se nao houver elementos suficientes para a criação de um voo
        if(companhias.size() == 0 || avioes.size() == 0 || pilotos.size() == 0 || aeroportos.size() < 2){
            return null;
        }

        Aviao aviao = avioes.get(0);
        Companhia companhia = companhias.get(0);
        Piloto piloto = pilotos.get(0);
        Aeroporto aeroporto_partida = aeroportos.get(0);
        Aeroporto aeroporto_chegada = aeroportos.get(0);

        //coleta todas as informacoes do voo e cria-o
        String codigo = voo.getElementsByTagName("Código").item(0).getTextContent();
        String aviao_modelo = voo.getElementsByTagName("Aviao_Modelo").item(0).getTextContent();
        for(int i = 0; i < avioes.size(); i++){
            if(avioes.get(i).getModelo().equals(aviao_modelo)){
                aviao = avioes.get(i);
            }
        }
        String companhia_nome = voo.getElementsByTagName("Companhia_Nome").item(0).getTextContent();
        for(int i = 0; i < companhias.size(); i++){
            if(companhias.get(i).getNome().equals(companhia_nome)){
                companhia = companhias.get(i);
            }
        }
        String piloto_nome = voo.getElementsByTagName("Piloto_Nome").item(0).getTextContent();
        for(int i = 0; i < pilotos.size(); i++){
            if(pilotos.get(i).getNome().equals(piloto_nome)){
                piloto = pilotos.get(i);
            }
        }
        String aeroporto_partida_nome = voo.getElementsByTagName("Aeroporto_Partida_Nome").item(0).getTextContent();
        for(int i = 0; i < aeroportos.size(); i++){
            if(aeroportos.get(i).getNome().equals(aeroporto_partida_nome)){
                aeroporto_partida = aeroportos.get(i);
            }
        }
        String aeroporto_chegada_nome = voo.getElementsByTagName("Aeroporto_Chegada_Nome").item(0).getTextContent();
        for(int i = 0; i < aeroportos.size(); i++){
            if(aeroportos.get(i).getNome().equals(aeroporto_chegada_nome)){
                aeroporto_chegada = aeroportos.get(i);
            }
        }
        String hora_partida = voo.getElementsByTagName("Hora_Partida").item(0).getTextContent();
        String hora_chegada = voo.getElementsByTagName("Hora_Chegada").item(0).getTextContent();
        String data = voo.getElementsByTagName("Data").item(0).getTextContent();
        Voo v = new Voo(codigo, aviao, companhia, piloto, aeroporto_partida, aeroporto_chegada, hora_partida, hora_chegada, data);
        
        //verifica e atualiza o estado do voo 
        String estado = voo.getElementsByTagName("Estado").item(0).getTextContent();
        if(estado.equals("Completo")){
            v.completo();
        }else if(estado.equals("Atrasado")){
            v.atrasado();
        }else if(estado.equals("Cancelado")){
            v.cancelado();
        }
        return v;
    }

    //cria um elemento a partir do voo atual
    public Element createElement(Document doc){
        Element eVoo = doc.createElement("Voo");

        Element eCodigo = doc.createElement("Código");
        eCodigo.appendChild(doc.createTextNode(this.codigo));
        eVoo.appendChild(eCodigo);

        Element eAviao = doc.createElement("Aviao_Modelo");
        eAviao.appendChild(doc.createTextNode(this.aviao.getModelo()));
        eVoo.appendChild(eAviao);

        Element eCompanhia = doc.createElement("Companhia_Nome");
        eCompanhia.appendChild(doc.createTextNode(this.companhia.getNome()));
        eVoo.appendChild(eCompanhia);

        Element ePiloto = doc.createElement("Piloto_Nome");
        ePiloto.appendChild(doc.createTextNode(this.piloto.getNome()));
        eVoo.appendChild(ePiloto);

        Element ePartida = doc.createElement("Aeroporto_Partida_Nome");
        ePartida.appendChild(doc.createTextNode(this.origem.getNome()));
        eVoo.appendChild(ePartida);

        Element eChegada = doc.createElement("Aeroporto_Chegada_Nome");
        eChegada.appendChild(doc.createTextNode(this.destino.getNome()));
        eVoo.appendChild(eChegada);

        Element eEstado = doc.createElement("Estado");
        eEstado.appendChild(doc.createTextNode(this.getEstado()));
        eVoo.appendChild(eEstado);

        Element eHora_partida = doc.createElement("Hora_Partida");
        eHora_partida.appendChild(doc.createTextNode(this.hora_partida));
        eVoo.appendChild(eHora_partida);

        Element eHora_chegada = doc.createElement("Hora_Chegada");
        eHora_chegada.appendChild(doc.createTextNode(this.hora_chegada));
        eVoo.appendChild(eHora_chegada);

        Element eData = doc.createElement("Data");
        eData.appendChild(doc.createTextNode(this.date));
        eVoo.appendChild(eData);

        return eVoo;
    }
}
