package tps.tp4;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Main {

    private static ArrayList<Conta> contas = new ArrayList<Conta>();
    private static ArrayList<Companhia> companhias = new ArrayList<Companhia>();
    private static ArrayList<Aviao> avioes = new ArrayList<Aviao>();
    private static ArrayList<Piloto> pilotos = new ArrayList<Piloto>();
    private static ArrayList<Aeroporto> aeroportos = new ArrayList<Aeroporto>();
    private static ArrayList<Voo> voos = new ArrayList<Voo>();

    private static String data_regresso = "";
    private static String email_piloto = "";
    private static int index;
    private static int index_account;
    private static boolean comp;
    private static boolean avi;
    private static boolean pil;
    private static boolean aero;

    public static void main(String[] args) throws ParseException, TransformerException, ParserConfigurationException, IOException {
        
        //abre e le o fichero BaseDados.xml e cria os objetos lá presentes
        try {

            File inputFile = new File("XML/BaseDados.xml");
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            String expression = "/BaseDados/*";
            NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
           
            //percorre todo o xml e cria os objetos
            for(int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if(nNode.getNodeName().equals("Conta")){
                    //cria e adiciona uma conta ao arraylist de contas se o arraylist estiver vazio ou se nou houver outra conta igual
                    Conta c = Conta.build(nNode);
                    boolean add = true;
                    if(contas.size() > 0){
                        for(int j = 0; j < contas.size(); j++){
                            if(c.equals(contas.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            contas.add(c);
                        }
                    }else{
                        contas.add(c);
                    }
                }else if(nNode.getNodeName().equals("Companhia")){
                    //cria e adiciona uma companhia ao arraylist de companhias se o arraylist estiver vazio ou se nou houver outra companhia igual
                    Companhia C = Companhia.build(nNode);
                    boolean add = true;
                    if(companhias.size() > 0){
                        for(int j = 0; j < companhias.size(); j++){
                            if(C.equals(companhias.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            companhias.add(C);
                        }
                    }else{
                        companhias.add(C);
                    }
                }else if(nNode.getNodeName().equals("Aviao")){
                    //cria e adiciona um aviao ao arraylist de avioes se o arraylist estiver vazio ou se nao houver outro aviao igual
                    Aviao a = Aviao.build(nNode);
                    boolean add = true;
                    if(avioes.size() > 0){
                        for(int j = 0; j < avioes.size(); j++){
                            if(a.equals(avioes.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            avioes.add(a);
                        }
                    }else{
                        avioes.add(a);
                    }
                }else if(nNode.getNodeName().equals("Piloto")){
                    //cria e adiciona um piloto ao arraylist de pilotos se o arraylist estiver vazio ou se nao houver outro piloto igual
                    Piloto P = Piloto.build(nNode);
                    boolean add = true;
                    if(pilotos.size() > 0){
                        for(int j = 0; j < pilotos.size(); j++){
                            if(P.equals(pilotos.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            pilotos.add(P);
                        }
                    }else{
                        pilotos.add(P);
                    }
                }else if(nNode.getNodeName().equals("Aeroporto")){
                    //cria e adiciona um aeroporto ao arraylist de aeroportos se o arraylist estiver vazio ou se nao houver outro aeroporto igual
                    Aeroporto A = Aeroporto.build(nNode);
                    boolean add = true;
                    if(aeroportos.size() > 0){
                        for(int j = 0; j < aeroportos.size(); j++){
                            if(A.equals(aeroportos.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            aeroportos.add(A);
                        }
                    }else{
                        aeroportos.add(A);
                    }
                }else if(nNode.getNodeName().equals("Voo")){
                    //cria e adiciona um voo ao arraylist de voos se o arraylist estiver vazio ou se nao houver outro voo igual
                    Voo V = Voo.build(nNode, companhias, avioes, pilotos, aeroportos);
                    boolean add = true;
                    if(voos.size() > 0){
                        for(int j = 0; j < voos.size(); j++){
                            if(V.equals(voos.get(j))){ 
                                add = false;
                            }
                        }
                        if(add){
                            voos.add(V);
                        }
                    }else{
                        voos.add(V);
                    }
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Frame
        JFrame frame = new JFrame("Agência de viagens - Diogo Saraiva");
        frame.setSize(1000, 800);
        frame.setResizable(false);
        // adicionar uma imagem como icone do frame
        BufferedImage frameIcon = ImageIO.read(new File("img/logo.png"));
        frame.setIconImage(frameIcon);

        //Panels
        // Menu panel
        JPanel p_menu = new JPanel();
        p_menu.setLayout(null);
        frame.setContentPane(p_menu);
        // Login panel
        JPanel p_login = new JPanel();
        p_login.setLayout(null);
        // SignUp panel
        JPanel p_signup = new JPanel();
        p_signup.setLayout(null);
        // BuyFligths panel
        JPanel p_buyFligths = new JPanel();
        p_buyFligths.setLayout(null);
        // ShowFligths panel
        JPanel p_showFligths = new JPanel();
        p_showFligths.setLayout(null);
        // Piloto panel
        JPanel p_piloto = new JPanel();
        p_piloto.setLayout(null);
        // Admin panel
        JPanel p_admingeneral = new JPanel();
        p_admingeneral.setLayout(null);
        // Admin panel with tables
        JPanel p_admintables = new JPanel();
        p_admintables.setLayout(null);
        // Admin criar voo panel
        JPanel p_criar_voo = new JPanel();
        p_criar_voo.setLayout(null);
        // Admin remover objeto panel
        JPanel p_remover = new JPanel();
        p_remover.setLayout(null);
        // Admin criar objeto panel
        JPanel p_criar = new JPanel();
        p_criar.setLayout(null);



        //Menu items
        // LogIn button
        JButton b_login = new JButton("LogIn");
        b_login.addActionListener(e -> {
            // trocar para o panel de login
            frame.setContentPane(p_login);
            frame.revalidate();
        });
        b_login.setBounds(400, 250, 200, 100);
        p_menu.add(b_login);
        // signUp button
        JButton b_signup = new JButton("SignUp");
        b_signup.setBounds(400, 400, 200, 100);
        b_signup.addActionListener(e -> {
            // trocar para o panel de signup
            frame.setContentPane(p_signup);
            frame.revalidate();
        });
        p_menu.add(b_signup);



        //Login Items
        // Login label
        JLabel l_login = new JLabel("Login");
        l_login.setBounds(375, 100, 200, 100);
        l_login.setFont(new Font("Arial", Font.BOLD, 35));
        p_login.add(l_login);
        // UsernameEmail Label
        JLabel l_email = new JLabel("Email");
        l_email.setBounds(375, 200, 250, 30);
        p_login.add(l_email);
        // Username Field
        JTextField tf_usernameField = new JTextField();
        tf_usernameField.setBounds(375, 230, 250, 30);
        p_login.add(tf_usernameField);
        // Password Label
        JLabel l_password = new JLabel("Password");
        l_password.setBounds(375, 275, 250, 30);
        p_login.add(l_password);
        // Password Field
        JPasswordField tf_passwordField = new JPasswordField();
        tf_passwordField.setBounds(375,305, 250, 30);
        p_login.add(tf_passwordField);
        //credentials error
        JLabel l_credentials = new JLabel("O teu email ou password estão erradas.");
        l_credentials.setBounds(375,340, 300, 30);
        l_credentials.setForeground(Color.RED);
        l_credentials.setVisible(false);
        p_login.add(l_credentials);
        // Login button
        JButton b1_login = new JButton("Login");
        b1_login.setBounds(375, 380, 250, 30);
        b1_login.addActionListener(e ->{
            String email = tf_usernameField.getText().toString();
            char[] password_array = tf_passwordField.getPassword();
            String password = "";
            for(int i = 0; i < password_array.length; i++){
                password += password_array[i];
            }
            tf_passwordField.setText("");
            boolean exists = false;
            index_account = 0;
            for(int i = 0; i < contas.size(); i++){
                if(contas.get(i).validarConta(email, password)){
                    exists = true;
                    index_account = i;
                }
            }
            if(exists && contas.get(index_account).getEstatuto().equals("Administrador")){
                //passar para o painel de administrador
                l_credentials.setVisible(false);
                tf_usernameField.setText("");
                frame.setContentPane(p_admingeneral);frame.revalidate();
            }else if(exists && contas.get(index_account).getEstatuto().equals("Piloto")){
                //passar para o painel do piloto
                l_credentials.setVisible(false);
                tf_usernameField.setText("");
                email_piloto = email;
                //obter o nome do piloto e o indice no array
                String[]parts = email_piloto.split("@");
                String[]nomes = parts[0].split("_");
                String nome_proprio = nomes[0].substring(0, 1).toUpperCase() + nomes[0].substring(1);
                String apelido = nomes[1].substring(0, 1).toUpperCase() + nomes[1].substring(1);
                String nome_completo = nome_proprio + " " + apelido;
                for(int i = 0; i < pilotos.size(); i++){
                    if(nome_completo.equals(pilotos.get(i).getNome())){
                        index = i;
                    }
                }
                // criar um label com o nome do piloto
                //nome piloto label
                JLabel l_piloto = new JLabel("Piloto: " + pilotos.get(index).getNome());
                l_piloto.setBounds(25, 20, 500, 100);
                l_piloto.setFont(new Font("Arial", Font.BOLD, 35));
                p_piloto.add(l_piloto);
                //horas de voo label
                JLabel l_horas = new JLabel("Horas de voo: " + pilotos.get(index).getTempoVoo());
                l_horas.setBounds(25, 90, 150, 30);
                p_piloto.add(l_horas);
                // criar as tabelas com os voos do piloto
                //tabela de todos os voos do piloto
                int voos_total = pilotos.get(index).getNumVoos();
                int num_proximos_voos = pilotos.get(index).getNumProximosVoos();
                int num_historico_voos = pilotos.get(index).getNumHistoricoVoos();
                String[] columnsName = {"Código", "Companhia", "Origem", "Destino", "Partida", "Chegada", "Data", "Estado"};
                Object[][] data_total = new Object[voos_total + 1][8];
                Object[][] data_proximos = new Object[num_proximos_voos + 1][8];
                Object[][] data_historico = new Object[num_historico_voos + 1][8];
                //adicionar os detalhes dos voos a tabela
                data_total[0][0] = "Código";
                data_total[0][1] = "Companhia";
                data_total[0][2] = "Origem";
                data_total[0][3] = "Destino";
                data_total[0][4] = "Partida";
                data_total[0][5] = "Chegada";
                data_total[0][6] = "Data";
                data_total[0][7] = "Estado";
                data_proximos[0][0] = "Código";
                data_proximos[0][1] = "Companhia";
                data_proximos[0][2] = "Origem";
                data_proximos[0][3] = "Destino";
                data_proximos[0][4] = "Partida";
                data_proximos[0][5] = "Chegada";
                data_proximos[0][6] = "Data";
                data_proximos[0][7] = "Estado";
                data_historico[0][0] = "Código";
                data_historico[0][1] = "Companhia";
                data_historico[0][2] = "Origem";
                data_historico[0][3] = "Destino";
                data_historico[0][4] = "Partida";
                data_historico[0][5] = "Chegada";
                data_historico[0][6] = "Data";
                data_historico[0][7] = "Estado";
                ArrayList<Voo> voos_piloto = pilotos.get(index).getVoos();
                ArrayList<Voo> proximos_voos_piloto = pilotos.get(index).getProximosVoos();
                ArrayList<Voo> historico_voos_piloto = pilotos.get(index).getHistoricoVoos();
                if(voos_total > 0){
                    for(int i = 1; i < voos_total + 1; i++){
                        for(int j = 0; j < 8; j++){
                            if(j == 0){
                                data_total[i][j] = voos_piloto.get(i - 1).getCodigo();
                            }else if(j == 1){
                                data_total[i][j] = voos_piloto.get(i - 1).getCompanhia().getNome();
                            }else if(j == 2){
                                data_total[i][j] = "(" + voos_piloto.get(i - 1).getOrigem().getAbreviatura() + ")" + voos_piloto.get(i - 1).getOrigem().getCidade();
                            }else if(j == 3){
                                data_total[i][j] = "(" + voos_piloto.get(i - 1).getDestino().getAbreviatura() + ")" + voos_piloto.get(i - 1).getDestino().getCidade();
                            }else if(j == 4){
                                data_total[i][j] = voos_piloto.get(i - 1).getPartida();
                            }else if(j == 5){
                                data_total[i][j] = voos_piloto.get(i - 1).getChegada();
                            }else if(j == 6){
                                data_total[i][j] = voos_piloto.get(i - 1).getDate();
                            }else{
                                data_total[i][j] = voos_piloto.get(i - 1).getEstado();
                            }
                        }
                    }
                }
                if(num_proximos_voos > 0){
                    for(int i = 1; i < num_proximos_voos + 1; i++){
                        for(int j = 0; j < 8; j++){
                            if(j == 0){
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getCodigo();
                            }else if(j == 1){
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getCompanhia().getNome();
                            }else if(j == 2){
                                data_proximos[i][j] = "(" + proximos_voos_piloto.get(i - 1).getOrigem().getAbreviatura() + ")" + proximos_voos_piloto.get(i - 1).getOrigem().getCidade();
                            }else if(j == 3){
                                data_proximos[i][j] = "(" + proximos_voos_piloto.get(i - 1).getDestino().getAbreviatura() + ")" + proximos_voos_piloto.get(i - 1).getDestino().getCidade();
                            }else if(j == 4){
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getPartida();
                            }else if(j == 5){
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getChegada();
                            }else if(j == 6){
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getDate();
                            }else{
                                data_proximos[i][j] = proximos_voos_piloto.get(i - 1).getEstado();
                            }
                        }
                    }
                }
                if(num_historico_voos > 0){
                    for(int i = 1; i < num_historico_voos + 1; i++){
                        for(int j = 0; j < 8; j++){
                            if(j == 0){
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getCodigo();
                            }else if(j == 1){
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getCompanhia().getNome();
                            }else if(j == 2){
                                data_historico[i][j] = "(" + historico_voos_piloto.get(i - 1).getOrigem().getAbreviatura() + ")" + historico_voos_piloto.get(i - 1).getOrigem().getCidade();
                            }else if(j == 3){
                                data_historico[i][j] = "(" + historico_voos_piloto.get(i - 1).getDestino().getAbreviatura() + ")" + historico_voos_piloto.get(i - 1).getDestino().getCidade();
                            }else if(j == 4){
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getPartida();
                            }else if(j == 5){
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getChegada();
                            }else if(j == 6){
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getDate();
                            }else{
                                data_historico[i][j] = historico_voos_piloto.get(i - 1).getEstado();
                            }
                        }
                    }
                }
                TableModel model_total = new DefaultTableModel(data_total, columnsName){
                    public boolean isCellEditable(int row, int column){
                        return false;//This causes all cells to be not editable
                    }
                };
                JTable table_total = new JTable(model_total){
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                        Component c = super.prepareRenderer(renderer, row, column);
                        if(!c.getBackground().equals(getSelectionBackground())) {
                            Color coleur = (row == 0 ? Color.CYAN : Color.WHITE);
                            c.setBackground(coleur);
                            coleur = null;
                        }
                        return c;
                    }
                };
                TableModel model_proximos = new DefaultTableModel(data_proximos, columnsName){
                    public boolean isCellEditable(int row, int column){
                        return false;//This causes all cells to be not editable
                    }
                };
                JTable table_proximos = new JTable(model_proximos){
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                        Component c = super.prepareRenderer(renderer, row, column);
                        if(!c.getBackground().equals(getSelectionBackground())) {
                            Color cor = (row == 0 ? Color.CYAN : Color.WHITE);
                            c.setBackground(cor);
                            cor = null;
                        }
                        return c;
                    }
                };
                TableModel model_historico = new DefaultTableModel(data_historico, columnsName){
                    public boolean isCellEditable(int row, int column){
                        return false;//This causes all cells to be not editable
                    }
                };
                JTable table_historico = new JTable(model_historico){
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                        Component c = super.prepareRenderer(renderer, row, column);
                        if(!c.getBackground().equals(getSelectionBackground())) {
                            Color cor = (row == 0 ? Color.CYAN : Color.WHITE);
                            c.setBackground(cor);
                            cor = null;
                        }
                        return c;
                    }
                };
                table_total.setBounds(25, 150, 725, 585);
                table_proximos.setBounds(25, 150, 725, 585);
                table_historico.setBounds(25, 150, 725, 585);
                p_piloto.add(table_proximos);
                p_piloto.add(table_historico);
                p_piloto.add(table_total);
                table_proximos.setVisible(true);
                table_historico.setVisible(false);
                table_total.setVisible(false);
                // botoes para alterar as tabelas
                // button proximos voos
                JButton b_proximos_voos = new JButton("Próximos Voos");
                b_proximos_voos.setBounds(795, 250, 150, 75);
                b_proximos_voos.addActionListener(l ->{
                    table_proximos.setVisible(true);
                    table_historico.setVisible(false);
                    table_total.setVisible(false);
                });
                p_piloto.add(b_proximos_voos);
                // button historico voos
                JButton b_historico_voos = new JButton("Histórico Voos");
                b_historico_voos.setBounds(795, 350, 150, 75);
                b_historico_voos.addActionListener(l ->{
                    table_historico.setVisible(true);
                    table_proximos.setVisible(false);
                    table_total.setVisible(false);
                });
                p_piloto.add(b_historico_voos);
                // button total voos
                JButton b_total_voos = new JButton("Todos os Voos");
                b_total_voos.setBounds(795, 450, 150, 75);
                b_total_voos.addActionListener(l ->{
                    table_total.setVisible(true);
                    table_proximos.setVisible(false);
                    table_historico.setVisible(false);
                });
                p_piloto.add(b_total_voos);
                //terminar sessao button
                JButton b1_terminar = new JButton("Terminar Sessão");
                b1_terminar.setBounds(800, 25, 150, 75);
                b1_terminar.addActionListener(l ->{
                    p_piloto.removeAll();
                    frame.setContentPane(p_menu);
                    frame.revalidate();
                });
                p_piloto.add(b1_terminar);
                frame.setContentPane(p_piloto);
                frame.revalidate();
            }else if(exists && contas.get(index_account).getEstatuto().equals("Cliente")){
                //passar para o painel de Cliente
                l_credentials.setVisible(false);
                tf_usernameField.setText("");
                frame.setContentPane(p_buyFligths);
                frame.revalidate();
            }else{
                l_credentials.setVisible(true);
            }
        });
        p_login.add(b1_login);
        // NewUser
        JLabel l_newUser = new JLabel("New User?");
        l_newUser.setBounds(375, 415, 75, 30);
        l_newUser.setForeground(Color.gray);
        p_login.add(l_newUser);
        // signUp redirect button
        JButton l_signUp = new JButton("SignUp");
        l_signUp.setBounds(425, 415, 80, 30);
        l_signUp.addActionListener(e ->{
            // trocar para o panel de signUp
            l_credentials.setVisible(false);
            tf_usernameField.setText("");
            tf_passwordField.setText("");
            frame.setContentPane(p_signup);
            frame.revalidate();
        });
        // apagar o background do botao para deixar apenas o texto
        l_signUp.setBorderPainted(false); 
        l_signUp.setContentAreaFilled(false); 
        l_signUp.setFocusPainted(false); 
        l_signUp.setOpaque(false);
        p_login.add(l_signUp);
        JButton b_voltar = new JButton("Voltar");
        b_voltar.setBounds(20, 20, 100, 50);
        b_voltar.addActionListener(e -> {
            // trocar para o panel Menu
            l_credentials.setVisible(false);
            tf_usernameField.setText("");
            tf_passwordField.setText("");
            frame.setContentPane(p_menu);
            frame.revalidate();
        });
        p_login.add(b_voltar);



        // admin item
        // pilotos combobox
        JComboBox <String> cb_pilotos = new JComboBox<>();
        cb_pilotos.setBounds(400, 425, 200, 30);
        cb_pilotos.addItem("");
        for(int i = 0; i < pilotos.size(); i++){
            cb_pilotos.addItem(pilotos.get(i).getNome());
        }
        cb_pilotos.setVisible(false);
        p_admingeneral.add(cb_pilotos);




        //SignUo Items
        // signUp label
        JLabel l1_signUp = new JLabel("SignUp");
        l1_signUp.setBounds(375, 100, 200, 100);
        l1_signUp.setFont(new Font("Arial", Font.BOLD, 35));
        p_signup.add(l1_signUp);
        // UsernameEmail Label
        JLabel l1_email = new JLabel("Email");
        l1_email.setBounds(375, 200, 250, 30);
        p_signup.add(l1_email);
        // Username Field
        JTextField tf1_usernameField = new JTextField();
        tf1_usernameField.setBounds(375, 230, 250, 30);
        p_signup.add(tf1_usernameField);
        // Password Label
        JLabel l1_password = new JLabel("Password");
        l1_password.setBounds(375, 275, 250, 30);
        p_signup.add(l1_password);
        // Password Field
        JPasswordField tf1_passwordField = new JPasswordField();
        tf1_passwordField.setBounds(375,305, 250, 30);
        p_signup.add(tf1_passwordField);
        //credentials error
        JLabel l1_credentials = new JLabel("O email que inseriste já está a ser utilizado.");
        l1_credentials.setBounds(375,340, 400, 30);
        l1_credentials.setForeground(Color.RED);
        l1_credentials.setVisible(false);
        p_signup.add(l1_credentials);
        JLabel l2_credentials = new JLabel("O email que inseriste não é válido.");
        l2_credentials.setBounds(375,340, 400, 30);
        l2_credentials.setForeground(Color.RED);
        l2_credentials.setVisible(false);
        p_signup.add(l2_credentials);
        JLabel l3_credentials = new JLabel("A tua password tem de ter pelo menos um caracter.");
        l3_credentials.setBounds(375,340, 400, 30);
        l3_credentials.setForeground(Color.RED);
        l3_credentials.setVisible(false);
        p_signup.add(l3_credentials);
        // SignUp button
        JButton b1_signup = new JButton("SignUp");
        b1_signup.setBounds(375, 380, 250, 30);
        b1_signup.addActionListener(e ->{
            String email = tf1_usernameField.getText().toString();
            char[] password_array = tf1_passwordField.getPassword();
            String password = "";
            for(int i = 0; i < password_array.length; i++){
                password += password_array[i];
            }
            if(password.length() == 0){
                l1_credentials.setVisible(false);
                l2_credentials.setVisible(false);
                l3_credentials.setVisible(true);
            }else{
                tf1_passwordField.setText("");
                boolean exists = false;
                for(int i = 0; i < contas.size(); i++){
                    if(email.equals(contas.get(i).getEmail())){
                        exists = true;
                    }
                }
                if(exists){
                    l1_credentials.setVisible(true);
                    l2_credentials.setVisible(false);
                    l3_credentials.setVisible(false);
                }else{
                    Conta c = new Conta(email, password);
                    if(c.isValidEmailAddress()){
                        l1_credentials.setVisible(false);
                        l2_credentials.setVisible(false);
                        l3_credentials.setVisible(false);
                        contas.add(c);
                        //escrever xml
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        //mudar para o painel de login
                        l1_credentials.setVisible(false);
                        l2_credentials.setVisible(false);
                        l3_credentials.setVisible(false);
                        tf1_usernameField.setText("");
                        tf1_passwordField.setText("");
                        //criar um piloto no xml se a conta for para um piloto
                        if(c.getEstatuto().equals("Piloto")){
                            String[]parts = email.split("@");
                            String[]nomes = parts[0].split("_");
                            String nome_proprio = nomes[0].substring(0, 1).toUpperCase() + nomes[0].substring(1);
                            String apelido = nomes[1].substring(0, 1).toUpperCase() + nomes[1].substring(1);
                            String nome_completo = nome_proprio + " " + apelido;
                            Piloto p = new Piloto(nome_completo, 0);
                            pilotos.add(p);
                            cb_pilotos.addItem(nome_completo);
                            try {
                                writeXml();
                            } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                                e1.printStackTrace();
                            }
                        }
                        frame.setContentPane(p_login);
                        frame.revalidate();
                    }else{
                        l1_credentials.setVisible(false);
                        l2_credentials.setVisible(true);
                        l3_credentials.setVisible(false);
                    }
                }
            }
        });
        p_signup.add(b1_signup);
        //Already user
        JLabel l_alreadyUser = new JLabel("Already User?");
        l_alreadyUser.setBounds(375, 415, 100, 30);
        l_alreadyUser.setForeground(Color.gray);
        p_signup.add(l_alreadyUser);
        // Login redirect button
        JButton l1_login = new JButton("LogIn");
        l1_login.setBounds(440, 415, 80, 30);
        l1_login.addActionListener(e ->{
            // trocar para o panel de LogIn
            l1_credentials.setVisible(false);
            l2_credentials.setVisible(false);
            l3_credentials.setVisible(false);
            tf1_usernameField.setText("");
            tf1_passwordField.setText("");
            frame.setContentPane(p_login);
            frame.revalidate();
        });
        // apagar o background do botao para deixar apenas o texto
        l1_login.setBorderPainted(false); 
        l1_login.setContentAreaFilled(false); 
        l1_login.setFocusPainted(false); 
        l1_login.setOpaque(false);
        p_signup.add(l1_login);
        //voltar button
        JButton b1_voltar = new JButton("Voltar");
        b1_voltar.setBounds(20, 20, 100, 50);
        b1_voltar.addActionListener(e ->{
            // trocar para o panel Menu
            l1_credentials.setVisible(false);
            l2_credentials.setVisible(false);
            l3_credentials.setVisible(false);
            tf1_usernameField.setText("");
            tf1_passwordField.setText("");
            frame.setContentPane(p_menu);
            frame.revalidate();
        });
        p_signup.add(b1_voltar);



        //buy_fligths Items
        //de e para labels
        JLabel l_de = new JLabel("De");
        l_de.setBounds(110, 255, 200, 20);
        JLabel l_para = new JLabel("Para");
        l_para.setBounds(335, 255, 200, 20);
        p_buyFligths.add(l_de);
        p_buyFligths.add(l_para);
        //De e para combobox
        JComboBox <String> aeroportos_partida = new JComboBox<>();
        aeroportos_partida.setBounds(85, 275, 200, 40);
        JComboBox <String> aeroportos_chegada = new JComboBox<>();
        aeroportos_chegada.setBounds(325, 275, 200, 40);
        aeroportos_partida.addItem("");
        aeroportos_chegada.addItem("");
        for(int i = 0; i < aeroportos.size(); i++){
            aeroportos_partida.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
            aeroportos_chegada.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
        }
        p_buyFligths.add(aeroportos_partida);
        p_buyFligths.add(aeroportos_chegada);
        //troca buttons
        //imagem do botao
        BufferedImage buttonIcon = ImageIO.read(new File("img/trocabotao.png"));
        JButton b_troca = new JButton(new ImageIcon(buttonIcon));
        b_troca.setBorder(BorderFactory.createEmptyBorder());
        b_troca.setContentAreaFilled(false);
        b_troca.setBounds(285, 275, 40, 40);
        b_troca.addActionListener(e ->{
            //trocar a partida com o destino
            int origem = aeroportos_partida.getSelectedIndex();
            int destino = aeroportos_chegada.getSelectedIndex();
            aeroportos_partida.setSelectedIndex(destino);
            aeroportos_chegada.setSelectedIndex(origem);
        });
        p_buyFligths.add(b_troca);
        // partida textfield
        JTextField tf_partida = new JTextField();
        tf_partida.setBounds(550, 275, 150, 40);
        p_buyFligths.add(tf_partida);
        //regresso textfield
        JTextField tf_regresso = new JTextField();
        tf_regresso.setBounds(725, 275, 150, 40);
        tf_regresso.setText("dd-MM-yyyy");
        p_buyFligths.add(tf_regresso);
        //partida label
        JLabel l_partida = new JLabel("Partida");
        l_partida.setBounds(565, 255, 200, 20);
        p_buyFligths.add(l_partida);
        //regresso label
        JLabel l_regresso = new JLabel("Regresso");
        l_regresso.setBounds(740, 255, 200, 20);
        p_buyFligths.add(l_regresso);
        //Ida e Ida e volta checkbox
        JCheckBox cb_ida = new JCheckBox("Ida");
        cb_ida.setBounds(300, 200, 50, 20);
        cb_ida.setSelected(true);
        tf_regresso.setEnabled(false);
        JCheckBox cb_ida_volta = new JCheckBox("Ida e Volta");
        cb_ida_volta.setBounds(400, 200, 100, 20);
        cb_ida.addActionListener(e ->{
            if(cb_ida.isSelected()){
                cb_ida_volta.setSelected(false);
                data_regresso = tf_regresso.getText();
                tf_regresso.setText("dd-MM-yyyy");
                tf_regresso.setEnabled(false);
            }
            if(!cb_ida.isSelected() && !cb_ida_volta.isSelected()){
                cb_ida.setSelected(true);
            }
        });
        cb_ida_volta.addActionListener(e ->{
            if(cb_ida_volta.isSelected()){
                cb_ida.setSelected(false);
                tf_regresso.setText(data_regresso);
                tf_regresso.setEnabled(true);
            }
            if(!cb_ida.isSelected() && !cb_ida_volta.isSelected()){
                cb_ida_volta.setSelected(true);
            }
        });
        p_buyFligths.add(cb_ida);
        p_buyFligths.add(cb_ida_volta);
        // label falta aeroporto
        JLabel l_falta_aeroporto = new JLabel("Selecione um aeroporto de origem e um de destino");
        l_falta_aeroporto.setBounds(100, 285, 500, 100);
        l_falta_aeroporto.setForeground(Color.RED);
        l_falta_aeroporto.setVisible(false);
        p_buyFligths.add(l_falta_aeroporto);
        // label aeroportos diferentes
        JLabel l_aeroportos_diferentes = new JLabel("Selecione dois aeroportos diferentes");
        l_aeroportos_diferentes.setBounds(100, 285, 500, 100);
        l_aeroportos_diferentes.setForeground(Color.RED);
        l_aeroportos_diferentes.setVisible(false);
        p_buyFligths.add(l_aeroportos_diferentes);
        //procurar voo button
        JButton b_procurar = new JButton("Procurar Voos ->");
        b_procurar.setBounds(600, 375, 200, 50);
        b_procurar.addActionListener(e ->{
            //procurar os voos pedidos
            if(cb_ida.isSelected()){
                // cria uma tabela com os voos so de ida
                if(aeroportos_partida.getSelectedItem().equals("") || aeroportos_chegada.getSelectedItem().equals("")){
                    l_falta_aeroporto.setVisible(true);
                    l_aeroportos_diferentes.setVisible(false);
                }else if(aeroportos_partida.getSelectedIndex() == aeroportos_chegada.getSelectedIndex()){
                    l_aeroportos_diferentes.setVisible(true);
                    l_falta_aeroporto.setVisible(false);
                }else{
                    l_falta_aeroporto.setVisible(false);
                    l_aeroportos_diferentes.setVisible(false);
                    String data = tf_partida.getText();
                    int origem = aeroportos_partida.getSelectedIndex();
                    int destino = aeroportos_chegada.getSelectedIndex();
                    Aeroporto a_origem = aeroportos.get(origem - 1);
                    Aeroporto a_destino = aeroportos.get(destino - 1);
                    ArrayList<Voo> voos_pedidos = new ArrayList<Voo>();
                    for(int i = 0; i < voos.size(); i++){
                        if(a_origem.equals(voos.get(i).getOrigem()) && a_destino.equals(voos.get(i).getDestino()) && data.equals(voos.get(i).getDate())){
                            voos_pedidos.add(voos.get(i));
                        }
                    }
                    String[] columnsName = {"Código", "Companhia", "Origem", "Destino", "Partida", "Chegada", "Data", "Estado"};
                    Object[][] data_voos = new Object[voos_pedidos.size() + 1][8];
                    //adicionar os detalhes dos voos a tabela
                    data_voos[0][0] = "Código";
                    data_voos[0][1] = "Companhia";
                    data_voos[0][2] = "Origem";
                    data_voos[0][3] = "Destino";
                    data_voos[0][4] = "Partida";
                    data_voos[0][5] = "Chegada";
                    data_voos[0][6] = "Data";
                    data_voos[0][7] = "Estado";
                    ordenar_voos();
                    if(voos_pedidos.size() > 0){
                        for(int i = 1; i < voos_pedidos.size() + 1; i++){
                            for(int j = 0; j < 8; j++){
                                if(j == 0){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getCodigo();
                                }else if(j == 1){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getCompanhia().getNome();
                                }else if(j == 2){
                                    data_voos[i][j] = "(" + voos_pedidos.get(i - 1).getOrigem().getAbreviatura() + ")" + voos_pedidos.get(i - 1).getOrigem().getCidade();
                                }else if(j == 3){
                                    data_voos[i][j] = "(" + voos_pedidos.get(i - 1).getDestino().getAbreviatura() + ")" + voos_pedidos.get(i - 1).getDestino().getCidade();
                                }else if(j == 4){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getPartida();
                                }else if(j == 5){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getChegada();
                                }else if(j == 6){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getDate();
                                }else{
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getEstado();
                                }
                            }
                        }
                    }
                    TableModel model = new DefaultTableModel(data_voos, columnsName){
                        public boolean isCellEditable(int row, int column){
                            return false;//This causes all cells to be not editable
                        }
                    };
                    JTable table = new JTable(model){
                        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                            Component c = super.prepareRenderer(renderer, row, column);
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur = (row == 0 ? Color.CYAN : Color.WHITE);
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    table.setBounds(25, 150, 850, 585);
                    p_showFligths.add(table);
                    // button voltar p_showFligths
                    JButton b2_voltar = new JButton("Voltar");
                    b2_voltar.setBounds(20, 20, 100, 50);
                    b2_voltar.addActionListener(l ->{
                        p_showFligths.removeAll();
                        frame.setContentPane(p_buyFligths);
                        frame.revalidate();
                    });
                    p_showFligths.add(b2_voltar);
                    frame.setContentPane(p_showFligths);
                    frame.revalidate();
                }
            }else{
                //cria uma tabela com os voos de ida e volta
                if(aeroportos_partida.getSelectedItem().equals("") || aeroportos_chegada.getSelectedItem().equals("")){
                    l_falta_aeroporto.setVisible(true);
                    l_aeroportos_diferentes.setVisible(false);
                }else if(aeroportos_partida.getSelectedIndex() == aeroportos_chegada.getSelectedIndex()){
                    l_aeroportos_diferentes.setVisible(true);
                    l_falta_aeroporto.setVisible(false);
                }else{
                    l_falta_aeroporto.setVisible(false);
                    l_aeroportos_diferentes.setVisible(false);
                    String data = tf_partida.getText();
                    String data_volta = tf_regresso.getText();
                    int origem = aeroportos_partida.getSelectedIndex();
                    int destino = aeroportos_chegada.getSelectedIndex();
                    Aeroporto a_origem = aeroportos.get(origem - 1);
                    Aeroporto a_destino = aeroportos.get(destino - 1);
                    ArrayList<Voo> voos_pedidos = new ArrayList<Voo>();
                    for(int i = 0; i < voos.size(); i++){
                        if((a_origem.equals(voos.get(i).getOrigem()) && a_destino.equals(voos.get(i).getDestino()) && data.equals(voos.get(i).getDate())) || a_destino.equals(voos.get(i).getOrigem()) && a_origem.equals(voos.get(i).getDestino()) && data_volta.equals(voos.get(i).getDate())){
                            voos_pedidos.add(voos.get(i));
                        }
                    }
                    String[] columnsName = {"Código", "Companhia", "Origem", "Destino", "Partida", "Chegada", "Data", "Estado"};
                    Object[][] data_voos = new Object[voos_pedidos.size() + 1][8];
                    //adicionar os detalhes dos voos a tabela
                    data_voos[0][0] = "Código";
                    data_voos[0][1] = "Companhia";
                    data_voos[0][2] = "Origem";
                    data_voos[0][3] = "Destino";
                    data_voos[0][4] = "Partida";
                    data_voos[0][5] = "Chegada";
                    data_voos[0][6] = "Data";
                    data_voos[0][7] = "Estado";
                    ordenar_voos();
                    if(voos_pedidos.size() > 0){
                        for(int i = 1; i < voos_pedidos.size() + 1; i++){
                            for(int j = 0; j < 8; j++){
                                if(j == 0){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getCodigo();
                                }else if(j == 1){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getCompanhia().getNome();
                                }else if(j == 2){
                                    data_voos[i][j] = "(" + voos_pedidos.get(i - 1).getOrigem().getAbreviatura() + ")" + voos_pedidos.get(i - 1).getOrigem().getCidade();
                                }else if(j == 3){
                                    data_voos[i][j] = "(" + voos_pedidos.get(i - 1).getDestino().getAbreviatura() + ")" + voos_pedidos.get(i - 1).getDestino().getCidade();
                                }else if(j == 4){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getPartida();
                                }else if(j == 5){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getChegada();
                                }else if(j == 6){
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getDate();
                                }else{
                                    data_voos[i][j] = voos_pedidos.get(i - 1).getEstado();
                                }
                            }
                        }
                    }
                    TableModel model = new DefaultTableModel(data_voos, columnsName){
                        public boolean isCellEditable(int row, int column){
                            return false;//This causes all cells to be not editable
                        }
                    };
                    JTable table = new JTable(model){
                        public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                            Component c = super.prepareRenderer(renderer, row, column);
                            if(!c.getBackground().equals(getSelectionBackground())) {
                                Color coleur = (row == 0 ? Color.CYAN : Color.WHITE);
                                c.setBackground(coleur);
                                coleur = null;
                            }
                            return c;
                        }
                    };
                    table.setBounds(25, 150, 850, 585);
                    p_showFligths.add(table);
                    // button voltar p_showFligths
                    JButton b2_voltar = new JButton("Voltar");
                    b2_voltar.setBounds(20, 20, 100, 50);
                    b2_voltar.addActionListener(l ->{
                        p_showFligths.removeAll();
                        frame.setContentPane(p_buyFligths);
                        frame.revalidate();
                    });
                    p_showFligths.add(b2_voltar);
                    frame.setContentPane(p_showFligths);
                    frame.revalidate();
                }
            }
        });
        p_buyFligths.add(b_procurar);
        //terminar sessao button
        JButton b_terminar = new JButton("Terminar Sessão");
        b_terminar.setBounds(800, 25, 150, 75);
        b_terminar.addActionListener(e ->{
            aeroportos_partida.setSelectedIndex(0);
            aeroportos_chegada.setSelectedIndex(0);
            l_falta_aeroporto.setVisible(false);
            l_aeroportos_diferentes.setVisible(false);
            cb_ida.setSelected(true);
            cb_ida_volta.setSelected(false);
            tf_partida.setText("");
            data_regresso = "";
            tf_regresso.setText("dd-MM-yyyy");
            tf_regresso.setEnabled(false);
            frame.setContentPane(p_menu);
            frame.revalidate();
        });
        p_buyFligths.add(b_terminar);

        

        //Admin items
        // Companhias
        // companhias label
        JLabel l_companhias = new JLabel("Companhias");
        l_companhias.setBounds(425, 405, 200, 20);
        comp = true;
        p_admingeneral.add(l_companhias);
        // companhias combobox
        JComboBox <String> cb_companhias = new JComboBox<>();
        cb_companhias.setBounds(400, 425, 200, 30);
        cb_companhias.addItem("");
        for(int i = 0; i < companhias.size(); i++){
            cb_companhias.addItem(companhias.get(i).getNome());
        }
        p_admingeneral.add(cb_companhias);
        // Avioes
        // avioes label
        JLabel l_avioes = new JLabel("Aviões");
        l_avioes.setBounds(425, 405, 200, 20);
        l_avioes.setVisible(false);
        avi = false;
        p_admingeneral.add(l_avioes);
        // avioes combobox
        JComboBox <String> cb_avioes = new JComboBox<>();
        cb_avioes.setBounds(400, 425, 200, 30);
        cb_avioes.addItem("");
        for(int i = 0; i < avioes.size(); i++){
            cb_avioes.addItem(avioes.get(i).getModelo());
        }
        cb_avioes.setVisible(false);
        p_admingeneral.add(cb_avioes);
        // Pilotos
        // pilotos label
        JLabel l_pilotos = new JLabel("Pilotos");
        l_pilotos.setBounds(425, 405, 200, 20);
        l_pilotos.setVisible(false);
        pil = false;
        p_admingeneral.add(l_pilotos);
        // Aeroportos
        // aeroportos label
        JLabel l_aeroportos = new JLabel("Aeroportos");
        l_aeroportos.setBounds(425, 405, 200, 20);
        l_aeroportos.setVisible(false);
        aero = false;
        p_admingeneral.add(l_aeroportos);
        // aeroportos combobox
        JComboBox <String> cb_aeroportos = new JComboBox<>();
        cb_aeroportos.setBounds(400, 425, 200, 30);
        cb_aeroportos.addItem("");
        for(int i = 0; i < aeroportos.size(); i++){
            cb_aeroportos.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
        }
        cb_aeroportos.setVisible(false);
        p_admingeneral.add(cb_aeroportos);
        // botoes de troca de classe
        // companhias button
        JButton b_companhias = new JButton("Companhias");
        b_companhias.setBounds(165, 250, 150, 75);
        b_companhias.addActionListener(e ->{
            comp = true;
            avi = false;
            pil = false;
            aero = false;
            l_companhias.setVisible(true);
            cb_companhias.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
        });
        p_admingeneral.add(b_companhias);
        // avioes button
        JButton b_avioes = new JButton("Aviões");
        b_avioes.setBounds(340, 250, 150, 75);
        b_avioes.addActionListener(e ->{
            comp = false;
            avi = true;
            pil = false;
            aero = false;
            l_avioes.setVisible(true);
            cb_avioes.setVisible(true);
            l_companhias.setVisible(false);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
        });
        p_admingeneral.add(b_avioes);
        // pilotos button
        JButton b_pilotos = new JButton("Pilotos");
        b_pilotos.setBounds(515, 250, 150, 75);
        b_pilotos.addActionListener(e ->{
            comp = false;
            avi = false;
            pil = true;
            aero = false;
            l_pilotos.setVisible(true);
            cb_pilotos.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_companhias.setVisible(false);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
        });
        p_admingeneral.add(b_pilotos);
        // aeroportos button
        JButton b_aeroportos = new JButton("Aeroportos");
        b_aeroportos.setBounds(690, 250, 150, 75);
        b_aeroportos.addActionListener(e ->{
            comp = false;
            avi = false;
            pil = false;
            aero = true;
            l_aeroportos.setVisible(true);
            cb_aeroportos.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_companhias.setVisible(false);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(false);
        });
        p_admingeneral.add(b_aeroportos);
        // perquisar voos button
        JButton b1_procurar = new JButton("Procurar Voos ->");
        b1_procurar.setBounds(600, 550, 200, 50);
        b1_procurar.addActionListener(e ->{
            ArrayList<Voo> voos_a_adicionar = new ArrayList<Voo>();
            int indice = 0;
            if(comp == true && cb_companhias.getSelectedIndex() > 0){
                indice = cb_companhias.getSelectedIndex();
                for(int i = 0; i < voos.size(); i++){
                    if(voos.get(i).getCompanhia().equals(companhias.get(indice - 1))){ 
                        voos_a_adicionar.add(voos.get(i));
                    }
                }
            }else if(avi == true && cb_avioes.getSelectedIndex() > 0){
                indice = cb_avioes.getSelectedIndex();
                for(int i = 0; i < voos.size(); i++){
                    if(voos.get(i).getAviao().equals(avioes.get(indice - 1))){ 
                        voos_a_adicionar.add(voos.get(i));
                    }
                }
            }else if(pil == true && cb_pilotos.getSelectedIndex() > 0){
                indice = cb_pilotos.getSelectedIndex();
                for(int i = 0; i < voos.size(); i++){
                    if(voos.get(i).getPiloto().equals(pilotos.get(indice - 1))){ 
                        voos_a_adicionar.add(voos.get(i));
                    }
                }
            }else if(aero == true && cb_aeroportos.getSelectedIndex() > 0){
                indice = cb_aeroportos.getSelectedIndex();
                for(int i = 0; i < voos.size(); i++){
                    if(voos.get(i).getOrigem().equals(aeroportos.get(indice - 1)) || voos.get(i).getDestino().equals(aeroportos.get(indice - 1))){ 
                        voos_a_adicionar.add(voos.get(i));
                    }
                }
            }
            // mostrar mensagem caso o item escolhido tenha o indice 0
            // caso contrario constroi a tabela pedida 
            if(indice == 0){
                JLabel l_indice = new JLabel("Escolha um objeto válido.");
                l_indice.setBounds(175, 125, 1000, 500);
                l_indice.setForeground(Color.RED);
                l_indice.setFont(new Font("Arial", Font.BOLD, 50));
                p_admintables.add(l_indice);
            }else{
                String[] columnsName = {"Código", "Companhia", "Origem", "Destino", "Partida", "Chegada", "Data", "Estado"};
                Object[][] data_voos = new Object[voos_a_adicionar.size() + 1][8];
                //adicionar os detalhes dos voos a tabela
                data_voos[0][0] = "Código";
                data_voos[0][1] = "Companhia";
                data_voos[0][2] = "Origem";
                data_voos[0][3] = "Destino";
                data_voos[0][4] = "Partida";
                data_voos[0][5] = "Chegada";
                data_voos[0][6] = "Data";
                data_voos[0][7] = "Estado";
                ordenar_voos();
                if(voos_a_adicionar.size() > 0){
                    for(int i = 1; i < voos_a_adicionar.size() + 1; i++){
                        for(int j = 0; j < 8; j++){
                            if(j == 0){
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getCodigo();
                            }else if(j == 1){
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getCompanhia().getNome();
                            }else if(j == 2){
                                data_voos[i][j] = "(" + voos_a_adicionar.get(i - 1).getOrigem().getAbreviatura() + ")" + voos_a_adicionar.get(i - 1).getOrigem().getCidade();
                            }else if(j == 3){
                                data_voos[i][j] = "(" + voos_a_adicionar.get(i - 1).getDestino().getAbreviatura() + ")" + voos_a_adicionar.get(i - 1).getDestino().getCidade();
                            }else if(j == 4){
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getPartida();
                            }else if(j == 5){
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getChegada();
                            }else if(j == 6){
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getDate();
                            }else{
                                data_voos[i][j] = voos_a_adicionar.get(i - 1).getEstado();
                            }
                        }
                    }
                }
                TableModel model = new DefaultTableModel(data_voos, columnsName){
                    public boolean isCellEditable(int row, int column){
                        return false;//This causes all cells to be not editable
                    }
                };
                JTable table = new JTable(model){
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                        Component c = super.prepareRenderer(renderer, row, column);
                        if(!c.getBackground().equals(getSelectionBackground())) {
                            Color coleur = (row == 0 ? Color.CYAN : Color.WHITE);
                            c.setBackground(coleur);
                            coleur = null;
                        }
                        return c;
                    }
                };
                table.setBounds(25, 150, 700, 585);
                p_admintables.add(table);
                // remover voo button
                JButton b_remover = new JButton("-   Remover Voo");
                b_remover.setBounds(775, 650, 150, 75);
                b_remover.addActionListener(l ->{
                    int row = table.getSelectedRow();
                    if(row > 0){
                        // remove o voo da tabela e do xml
                        for(int i = 0; i < voos.size(); i++){
                            if(voos_a_adicionar.get(row - 1).equals(voos.get(i))){
                                voos.remove(i);
                            }
                        }
                        ((DefaultTableModel) model).removeRow(row);
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "O voo foi removido com sucesso!");
                    }
                });
                p_admintables.add(b_remover);
                // criar objetos button
                if(comp == true){
                    // nome label
                    JLabel l_nome = new JLabel("Nome");
                    l_nome.setBounds(420, 200, 200, 30);
                    // nome textfield
                    JTextField tf_nome = new JTextField();
                    tf_nome.setBounds(400, 225, 200, 30);
                    // companhias label
                    JLabel l1_companhias = new JLabel("Companhias");
                    l1_companhias.setBounds(420, 200, 150, 30);
                    p_remover.add(l1_companhias);
                    // companhias combobox
                    JComboBox <String> cb1_companhias = new JComboBox<String>();
                    cb1_companhias.setBounds(400, 225, 200, 30);
                    cb1_companhias.addItem("");
                    if(companhias.size() > 0){
                        for(int i = 0; i < companhias.size(); i++){
                            cb1_companhias.addItem(companhias.get(i).getNome());
                        }   
                    }
                    p_remover.add(cb1_companhias);
                    //remover button
                    JButton b1_remover = new JButton("-   Remover");
                    b1_remover.setBounds(525, 500, 150, 75);
                    b1_remover.addActionListener(l ->{
                        int i = cb1_companhias.getSelectedIndex() - 1;
                        for(int j = 0; j < voos.size(); j++){
                            if(voos.get(j).getCompanhia().equals(companhias.get(i))){
                                voos.remove(j);
                            }
                        }
                        try {
                            companhias.remove(i);
                            writeXml();
                            JOptionPane.showMessageDialog(null, "A companhia foi removida com sucesso!");
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        p_remover.removeAll();
                        cb_companhias.removeItemAt(i + 1);
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b1_remover);
                    // criar button
                    JButton b1_criar = new JButton("+   Criar");
                    b1_criar.setBounds(525, 500, 150, 75);
                    b1_criar.addActionListener(l ->{
                        String nome = tf_nome.getText();
                        if(nome.length() > 0){
                            boolean add = true;
                            Companhia c = new Companhia(nome);
                            for(int i = 0; i < companhias.size(); i++){
                                if(c.equals(companhias.get(i))){
                                    add = false;
                                }
                            }
                            if(add){
                                try {
                                    companhias.add(c);
                                    writeXml();
                                    JOptionPane.showMessageDialog(null, "A companhia foi criada com sucesso!");
                                } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                                    e1.printStackTrace();
                                }
                                cb_companhias.addItem(nome);
                                p_criar.removeAll();
                                frame.setContentPane(p_admingeneral);
                                frame.revalidate();
                            }
                        }
                    });
                    p_criar.add(b1_criar);
                    //cancelar button
                    JButton b_cancelar = new JButton("x   Cancelar");
                    b_cancelar.setBounds(325, 500, 150, 75);
                    b_cancelar.addActionListener(l ->{
                        p_remover.removeAll();
                        p_criar.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b_cancelar);
                    JButton b2_cancelar = new JButton("x   Cancelar");
                    b2_cancelar.setBounds(325, 500, 150, 75);
                    b2_cancelar.addActionListener(l ->{
                        p_criar.removeAll();
                        p_remover.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_criar.add(b2_cancelar);

                    //criar companhia button
                    JButton b_criar_companhia = new JButton("+   Criar Companhia");
                    b_criar_companhia.setBounds(775, 150, 150, 75);
                    b_criar_companhia.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_criar.add(l_nome);
                        p_criar.add(tf_nome);
                        p_admintables.removeAll();
                        frame.setContentPane(p_criar);
                        frame.revalidate();
                    });
                    p_admintables.add(b_criar_companhia);
                    //remover companhia button
                    JButton b_remover_companhia = new JButton("-   Remover Companhia");
                    b_remover_companhia.setBounds(775, 250, 150, 75);
                    b_remover_companhia.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_admintables.removeAll();
                        frame.setContentPane(p_remover);
                        frame.revalidate();
                    });
                    p_admintables.add(b_remover_companhia);
                }else if(avi == true){
                    // modelo label
                    JLabel l_modelo = new JLabel("Modelo");
                    l_modelo.setBounds(310, 200, 200, 30);
                    // modelo textfield
                    JTextField tf_modelo = new JTextField();
                    tf_modelo.setBounds(290, 225, 200, 30);
                    // capacidade label
                    JLabel l_capacidade = new JLabel("Capacidade");
                    l_capacidade.setBounds(530, 200, 200, 30);
                    // capacidade textfield
                    JTextField tf_capacidade = new JTextField();
                    tf_capacidade.setBounds(510, 225, 200, 30);
                    // Avioes label
                    JLabel l1_avioes = new JLabel("Aviões");
                    l1_avioes.setBounds(420, 200, 150, 30);
                    p_remover.add(l1_avioes);
                    // avioes combobox
                    JComboBox <String> cb1_avioes = new JComboBox<String>();
                    cb1_avioes.setBounds(400, 225, 200, 30);
                    cb1_avioes.addItem("");
                    if(avioes.size() > 0){
                        for(int i = 0; i < avioes.size(); i++){
                            cb1_avioes.addItem(avioes.get(i).getModelo());
                        }   
                    }
                    p_remover.add(cb1_avioes);
                    //remover button
                    JButton b1_remover = new JButton("-   Remover");
                    b1_remover.setBounds(525, 500, 150, 75);
                    b1_remover.addActionListener(l ->{
                        int i = cb1_avioes.getSelectedIndex() - 1;
                        for(int j = 0; j < voos.size(); j++){
                            if(voos.get(j).getAviao().equals(avioes.get(i))){
                                voos.remove(j);
                            }
                        }
                        try {
                            avioes.remove(i);
                            writeXml();
                            JOptionPane.showMessageDialog(null, "O Avião foi removido com sucesso!");
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        p_remover.removeAll();
                        cb_avioes.removeItemAt(i + 1);
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b1_remover);
                    // criar button
                    JButton b1_criar = new JButton("+   Criar");
                    b1_criar.setBounds(525, 500, 150, 75);
                    b1_criar.addActionListener(l ->{
                        String modelo = tf_modelo.getText();
                        int capacidade = Integer.parseInt(tf_capacidade.getText());
                        if(modelo.length() > 0 && tf_capacidade.getText().length() > 0){
                            boolean add = true;
                            Aviao a = new Aviao(modelo, capacidade);
                            for(int i = 0; i < avioes.size(); i++){
                                if(a.equals(avioes.get(i))){
                                    add = false;
                                }
                            }
                            if(add){
                                try {
                                    avioes.add(a);
                                    writeXml();
                                    JOptionPane.showMessageDialog(null, "O Avião foi criado com sucesso!");
                                } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                                    e1.printStackTrace();
                                }
                                cb_avioes.addItem(modelo);
                                p_criar.removeAll();
                                frame.setContentPane(p_admingeneral);
                                frame.revalidate();
                            }
                        }
                    });
                    p_criar.add(b1_criar);
                    //cancelar button
                    JButton b_cancelar = new JButton("x   Cancelar");
                    b_cancelar.setBounds(325, 500, 150, 75);
                    b_cancelar.addActionListener(l ->{
                        p_remover.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b_cancelar);
                    JButton b2_cancelar = new JButton("x   Cancelar");
                    b2_cancelar.setBounds(325, 500, 150, 75);
                    b2_cancelar.addActionListener(l ->{
                        p_criar.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_criar.add(b2_cancelar);

                    //criar aviao button
                    JButton b_criar_aviao = new JButton("+   Criar Avião");
                    b_criar_aviao.setBounds(775, 150, 150, 75);
                    b_criar_aviao.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_criar.add(l_modelo);
                        p_criar.add(tf_modelo);
                        p_criar.add(l_capacidade);
                        p_criar.add(tf_capacidade);
                        p_admintables.removeAll();
                        frame.setContentPane(p_criar);
                        frame.revalidate();
                    });
                    p_admintables.add(b_criar_aviao);
                    //remover aviao button
                    JButton b_remover_aviao = new JButton("-   Remover Avião");
                    b_remover_aviao.setBounds(775, 250, 150, 75);
                    b_remover_aviao.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_admintables.removeAll();
                        frame.setContentPane(p_remover);
                        frame.revalidate();
                    });
                    p_admintables.add(b_remover_aviao);
                }else if(pil == true){
                    // nome label
                    JLabel l_nome = new JLabel("Nome");
                    l_nome.setBounds(310, 200, 200, 30);
                    // nome textfield
                    JTextField tf_nome = new JTextField();
                    tf_nome.setBounds(290, 225, 200, 30);
                    // horas de voo label
                    JLabel l_horas = new JLabel("Horas de Voo");
                    l_horas.setBounds(530, 200, 200, 30);
                    // horas de voo textfield
                    JTextField tf_horas = new JTextField();
                    tf_horas.setBounds(510, 225, 200, 30);
                    // pilotos label
                    JLabel l1_pilotos = new JLabel("Pilotos");
                    l1_pilotos.setBounds(420, 200, 150, 30);
                    p_remover.add(l1_pilotos);
                    // pilotos combobox
                    JComboBox <String> cb1_pilotos = new JComboBox<String>();
                    cb1_pilotos.setBounds(400, 225, 200, 30);
                    cb1_pilotos.addItem("");
                    if(pilotos.size() > 0){
                        for(int i = 0; i < pilotos.size(); i++){
                            cb1_pilotos.addItem(pilotos.get(i).getNome());
                        }   
                    }
                    p_remover.add(cb1_pilotos);
                    //remover button
                    JButton b1_remover = new JButton("-   Remover");
                    b1_remover.setBounds(525, 500, 150, 75);
                    b1_remover.addActionListener(l ->{
                        int i = cb1_pilotos.getSelectedIndex() - 1;
                        for(int j = 0; j < voos.size(); j++){
                            if(voos.get(j).getPiloto().equals(pilotos.get(i))){
                                voos.remove(j);
                            }
                        }
                        pilotos.remove(i);
                        // remover a conta do piloto
                        int num = 0;
                        for(int j = 0; j < contas.size(); j++){
                            if(contas.get(j).getEstatuto().equals("Piloto")){
                                if(num == i){
                                    contas.remove(j);
                                }
                                num += 1;
                            }
                        }
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        p_remover.removeAll();
                        cb_pilotos.removeItemAt(i + 1);
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                        JOptionPane.showMessageDialog(null, "O Piloto foi removido com sucesso!");
                    });
                    p_remover.add(b1_remover);
                    // criar button
                    JButton b1_criar = new JButton("+   Criar");
                    b1_criar.setBounds(525, 500, 150, 75);
                    b1_criar.addActionListener(l ->{
                        if(tf_nome.getText().length() > 0 && tf_horas.getText().length() > 0){
                            int horas = Integer.parseInt(tf_horas.getText());
                            String nome = tf_nome.getText();
                            boolean add = true;
                            Piloto p = new Piloto(nome, horas);
                            for(int i = 0; i < pilotos.size(); i++){
                                if(p.equals(pilotos.get(i))){
                                    add = false;
                                }
                            }
                            if(add){                                
                                try {
                                    pilotos.add(p);
                                    writeXml();
                                    JOptionPane.showMessageDialog(null, "O Piloto foi criado com sucesso!");
                                } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                                    e1.printStackTrace();
                                }
                                cb_pilotos.addItem(nome);
                                p_criar.removeAll();
                                frame.setContentPane(p_admingeneral);
                                frame.revalidate();
                            }
                        }
                    });
                    p_criar.add(b1_criar);
                    //cancelar button
                    JButton b_cancelar = new JButton("x   Cancelar");
                    b_cancelar.setBounds(325, 500, 150, 75);
                    b_cancelar.addActionListener(l ->{
                        p_remover.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b_cancelar);
                    JButton b2_cancelar = new JButton("x   Cancelar");
                    b2_cancelar.setBounds(325, 500, 150, 75);
                    b2_cancelar.addActionListener(l ->{
                        p_criar.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_criar.add(b2_cancelar);

                    //criar piloto button
                    JButton b_criar_piloto = new JButton("+   Criar Piloto");
                    b_criar_piloto.setBounds(775, 150, 150, 75);
                    b_criar_piloto.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_criar.add(l_nome);
                        p_criar.add(tf_nome);
                        p_criar.add(l_horas);
                        p_criar.add(tf_horas);
                        p_admintables.removeAll();
                        frame.setContentPane(p_criar);
                        frame.revalidate();
                    });
                    p_admintables.add(b_criar_piloto);
                    //remover piloto button
                    JButton b_remover_piloto = new JButton("-   Remover Piloto");
                    b_remover_piloto.setBounds(775, 250, 150, 75);
                    b_remover_piloto.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_admintables.removeAll();
                        frame.setContentPane(p_remover);
                        frame.revalidate();
                    });
                    p_admintables.add(b_remover_piloto);
                }else{
                    //  nome label
                    JLabel l_nome = new JLabel("Nome");
                    l_nome.setBounds(200, 200, 200, 30);
                    // nome textfield
                    JTextField tf_nome = new JTextField();
                    tf_nome.setBounds(180, 225, 200, 30);
                    // cidade label
                    JLabel l_cidade = new JLabel("Cidade");
                    l_cidade.setBounds(420, 200, 200, 30);
                    // cidade textfield
                    JTextField tf_cidade = new JTextField();
                    tf_cidade.setBounds(400, 225, 200, 30);
                    // abreviatura label
                    JLabel l_abreviatura = new JLabel("Abreviatura");
                    l_abreviatura.setBounds(640, 200, 200, 30);
                    // abreviatura textfield
                    JTextField tf_abreviatura = new JTextField();
                    tf_abreviatura.setBounds(620, 225, 200, 30);
                    // aeroportos label
                    JLabel l1_aeroportos = new JLabel("Aeroportos");
                    l1_aeroportos.setBounds(420, 200, 150, 30);
                    p_remover.add(l1_aeroportos);
                    // aeroportos combobox
                    JComboBox <String> cb1_aeroportos = new JComboBox<String>();
                    cb1_aeroportos.setBounds(400, 225, 200, 30);
                    cb1_aeroportos.addItem("");
                    if(aeroportos.size() > 0){
                        for(int i = 0; i < aeroportos.size(); i++){
                            cb1_aeroportos.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
                        }   
                    }
                    p_remover.add(cb1_aeroportos);
                    //remover button
                    JButton b1_remover = new JButton("-   Remover");
                    b1_remover.setBounds(525, 500, 150, 75);
                    b1_remover.addActionListener(l ->{
                        int i = cb1_aeroportos.getSelectedIndex() - 1;
                        for(int j = 0; j < voos.size(); j++){
                            if(voos.get(j).getOrigem().equals(aeroportos.get(i)) || voos.get(j).getDestino().equals(aeroportos.get(i))){
                                voos.remove(j);
                            }
                        }
                        try {
                            aeroportos.remove(i);
                            writeXml();
                            JOptionPane.showMessageDialog(null, "O Aeroporto foi removido com sucesso!");
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                        p_remover.removeAll();
                        cb_aeroportos.removeItemAt(i + 1);
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b1_remover);
                    // criar button
                    JButton b1_criar = new JButton("+   Criar");
                    b1_criar.setBounds(525, 500, 150, 75);
                    b1_criar.addActionListener(l ->{
                        String nome = tf_nome.getText();
                        String cidade = tf_cidade.getText();
                        String abreviatura = tf_abreviatura.getText();
                        if(nome.length() > 0 && cidade.length() > 0 && abreviatura.length() > 0){
                            boolean add = true;
                            Aeroporto a = new Aeroporto(nome, cidade, abreviatura);
                            for(int i = 0; i < aeroportos.size(); i++){
                                if(a.equals(aeroportos.get(i))){
                                    add = false;
                                }
                            }
                            if(add){
                                try {
                                    aeroportos.add(a);
                                    writeXml();
                                    JOptionPane.showMessageDialog(null, "O Aeroporto foi criado com sucesso!");
                                } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                                    e1.printStackTrace();
                                }
                                p_criar.removeAll();
                                frame.setContentPane(p_admingeneral);
                                frame.revalidate();
                            }
                        }
                    });
                    p_criar.add(b1_criar);
                    //cancelar button
                    JButton b_cancelar = new JButton("x   Cancelar");
                    b_cancelar.setBounds(325, 500, 150, 75);
                    b_cancelar.addActionListener(l ->{
                        p_remover.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_remover.add(b_cancelar);
                    JButton b2_cancelar = new JButton("x   Cancelar");
                    b2_cancelar.setBounds(325, 500, 150, 75);
                    b2_cancelar.addActionListener(l ->{
                        p_criar.removeAll();
                        frame.setContentPane(p_admingeneral);
                        frame.revalidate();
                    });
                    p_criar.add(b2_cancelar);

                    //criar aeroporto button
                    JButton b_criar_aeroporto = new JButton("+   Criar Aeroporto");
                    b_criar_aeroporto.setBounds(775, 150, 150, 75);
                    b_criar_aeroporto.addActionListener(l ->{
                        p_criar.add(l_nome);
                        p_criar.add(tf_nome);
                        p_criar.add(l_cidade);
                        p_criar.add(tf_cidade);
                        p_criar.add(l_abreviatura);
                        p_criar.add(tf_abreviatura);
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_admintables.removeAll();
                        frame.setContentPane(p_criar);
                        frame.revalidate();
                    });
                    p_admintables.add(b_criar_aeroporto);
                    //remover aeroporto button
                    JButton b_remover_aeroporto = new JButton("-   Remover Aeroporto");
                    b_remover_aeroporto.setBounds(775, 250, 150, 75);
                    b_remover_aeroporto.addActionListener(l ->{
                        comp = true;
                        avi = false;
                        pil = false;
                        aero = false;
                        p_admintables.removeAll();
                        frame.setContentPane(p_remover);
                        frame.revalidate();
                    });
                    p_admintables.add(b_remover_aeroporto);
                }
            }
            // voltar button
            JButton b2_voltar = new JButton("Voltar");
            b2_voltar.setBounds(20, 20, 100, 50);
            b2_voltar.addActionListener(l ->{
                p_admintables.removeAll();
                p_criar.removeAll();
                p_remover.removeAll();
                comp = true;
                avi = false;
                pil = false;
                aero = false;
                frame.setContentPane(p_admingeneral);
                frame.revalidate();
            });
            p_admintables.add(b2_voltar);
            // resetar as combobox
            l_companhias.setVisible(true);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
            frame.setContentPane(p_admintables);
            frame.revalidate();
        });
        p_admingeneral.add(b1_procurar);
        // ver todos os voos button
        JButton b_vertodososvoos = new JButton("Ver todos os voos");
        b_vertodososvoos.setBounds(25, 25, 150, 75);
        b_vertodososvoos.addActionListener(e ->{
            comp = true;
            avi = false;
            pil = false;
            aero = false;
            l_companhias.setVisible(true);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
            // voltar button
            JButton b2_voltar = new JButton("Voltar");
            b2_voltar.setBounds(20, 20, 100, 50);
            b2_voltar.addActionListener(l ->{
                p_admintables.removeAll();
                frame.setContentPane(p_admingeneral);
                frame.revalidate();
            });
            p_admintables.add(b2_voltar);
            // criar e mostra a tabela com todos voos
            String[] columnsName = {"Código", "Companhia", "Origem", "Destino", "Partida", "Chegada", "Data", "Estado"};
            Object[][] data_voos = new Object[voos.size() + 1][8];
            //adicionar os detalhes dos voos a tabela
            data_voos[0][0] = "Código";
            data_voos[0][1] = "Companhia";
            data_voos[0][2] = "Origem";
            data_voos[0][3] = "Destino";
            data_voos[0][4] = "Partida";
            data_voos[0][5] = "Chegada";
            data_voos[0][6] = "Data";
            data_voos[0][7] = "Estado";
            ordenar_voos();
            reverse_voos();
            if(voos.size() > 0){
                for(int i = 1; i < voos.size() + 1; i++){
                    for(int j = 0; j < 8; j++){
                        if(j == 0){
                            data_voos[i][j] = voos.get(i - 1).getCodigo();
                        }else if(j == 1){
                            data_voos[i][j] = voos.get(i - 1).getCompanhia().getNome();
                        }else if(j == 2){
                            data_voos[i][j] = "(" + voos.get(i - 1).getOrigem().getAbreviatura() + ")" + voos.get(i - 1).getOrigem().getCidade();
                        }else if(j == 3){
                            data_voos[i][j] = "(" + voos.get(i - 1).getDestino().getAbreviatura() + ")" + voos.get(i - 1).getDestino().getCidade();
                        }else if(j == 4){
                            data_voos[i][j] = voos.get(i - 1).getPartida();
                        }else if(j == 5){
                            data_voos[i][j] = voos.get(i - 1).getChegada();
                        }else if(j == 6){
                            data_voos[i][j] = voos.get(i - 1).getDate();
                        }else{
                            data_voos[i][j] = voos.get(i - 1).getEstado();
                        }
                    }
                }
            }
            TableModel model = new DefaultTableModel(data_voos, columnsName){
                public boolean isCellEditable(int row, int column){
                    return false;//This causes all cells to be not editable
                }
            };
            JTable table = new JTable(model){
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                    Component c = super.prepareRenderer(renderer, row, column);
                    if(!c.getBackground().equals(getSelectionBackground())) {
                        Color coleur = (row == 0 ? Color.CYAN : Color.WHITE);
                        c.setBackground(coleur);
                        coleur = null;
                    }
                    return c;
                }
            };
            table.setBounds(25, 150, 700, 585);
            // desabilitar a escolha de multiplas linhas na tabela
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            p_admintables.add(table);
            // voo completo button
            JButton b_completo = new JButton("Voo Completo");
            b_completo.setBounds(775, 150, 150, 75);
            b_completo.addActionListener(l ->{
                int row = table.getSelectedRow();
                if(row > 0){
                    // atualiza o estado do voo para completo
                    if(voos.get(row - 1).getEstado().equals("A Tempo") || voos.get(row - 1).getEstado().equals("Atrasado")){
                        voos.get(row - 1).completo();
                        table.setValueAt("Completo", row, 7);
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            p_admintables.add(b_completo);
            // voo atrasado button
            JButton b_atrasado = new JButton("Voo Atrasado");
            b_atrasado.setBounds(775, 250, 150, 75);
            b_atrasado.addActionListener(l ->{
                int row = table.getSelectedRow();
                if(row > 0){
                    // atualiza o estado do voo para atrasado
                    if(voos.get(row - 1).getEstado().equals("A Tempo")){
                        voos.get(row - 1).atrasado();
                        table.setValueAt("Atrasado", row, 7);
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            p_admintables.add(b_atrasado);
            // voo cancelado button
            JButton b_cancelado = new JButton("Voo Cancelado");
            b_cancelado.setBounds(775, 350, 150, 75);
            b_cancelado.addActionListener(l ->{
                int row = table.getSelectedRow();
                if(row > 0){
                    // atualiza o estado do voo para cancelado
                    if(voos.get(row - 1).getEstado().equals("A Tempo") || voos.get(row - 1).getEstado().equals("Atrasado")){
                        voos.get(row - 1).cancelado();
                        table.setValueAt("Cancelado", row, 7);
                        try {
                            writeXml();
                        } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
            p_admintables.add(b_cancelado);
            // remover voo button
            JButton b_remover = new JButton("-   Remover Voo");
            b_remover.setBounds(775, 550, 150, 75);
            b_remover.addActionListener(l ->{
                int row = table.getSelectedRow();
                if(row > 0){
                    // remove o voo da tabela e do xml
                    voos.remove(row - 1);
                    ((DefaultTableModel) model).removeRow(row);
                    try {
                        writeXml();
                    } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "O voo foi removido com sucesso!");
                }
            });
            p_admintables.add(b_remover);
            // criar voo button
            JButton b_criar_voo = new JButton("+   Criar Voo");
            b_criar_voo.setBounds(775, 650, 150, 75);
            b_criar_voo.addActionListener(l ->{
                // codigo
                // codigo label
                JLabel l_codigo = new JLabel("Código");
                l_codigo.setBounds(165, 125, 150, 30);
                p_criar_voo.add(l_codigo);
                // codigo textfield
                JTextField tf_codigo = new JTextField();
                tf_codigo.setBounds(155, 150, 150, 30);
                p_criar_voo.add(tf_codigo);
                // Partida
                // partida label
                JLabel l1_partida = new JLabel("Partida");
                l1_partida.setBounds(345, 125, 150, 30);
                p_criar_voo.add(l1_partida);
                // partida textfield
                JTextField tf1_partida = new JTextField();
                tf1_partida.setBounds(335, 150, 150, 30);
                p_criar_voo.add(tf1_partida);
                // Chegada
                // chegada label
                JLabel l1_chegada = new JLabel("Chegada");
                l1_chegada.setBounds(525, 125, 150, 30);
                p_criar_voo.add(l1_chegada);
                // chegada textfield
                JTextField tf1_chegada = new JTextField();
                tf1_chegada.setBounds(515, 150, 150, 30);
                p_criar_voo.add(tf1_chegada);
                // Data
                // data label
                JLabel l_data = new JLabel("Data");
                l_data.setBounds(705, 125, 150, 30);
                p_criar_voo.add(l_data);
                // data textfield
                JTextField tf_data = new JTextField();
                tf_data.setBounds(695, 150, 150, 30);
                p_criar_voo.add(tf_data);
                // companhia
                // companhia label
                JLabel l_companhia = new JLabel("Companhia");
                l_companhia.setBounds(75, 225, 150, 20);
                p_criar_voo.add(l_companhia);
                // companhia combobox
                JComboBox <String> cb_companhia = new JComboBox<String>();
                cb_companhia.setBounds(65, 245, 150, 30);
                cb_companhia.addItem("");
                for(int i = 0; i < companhias.size(); i++){
                    cb_companhia.addItem(companhias.get(i).getNome());
                }
                p_criar_voo.add(cb_companhia);
                // Aviao
                // aviao label
                JLabel l_aviao = new JLabel("Avião");
                l_aviao.setBounds(255, 225, 150, 20);
                p_criar_voo.add(l_aviao);
                // aviao combobox
                JComboBox <String> cb_aviao = new JComboBox<String>();
                cb_aviao.setBounds(245, 245, 150, 30);
                cb_aviao.addItem("");
                for(int i = 0; i < avioes.size(); i++){
                    cb_aviao.addItem(avioes.get(i).getModelo());
                }
                p_criar_voo.add(cb_aviao);
                // Piloto
                // piloto label
                JLabel l_piloto = new JLabel("Piloto");
                l_piloto.setBounds(435, 225, 150, 20);
                p_criar_voo.add(l_piloto);
                // piloto combobox
                JComboBox <String> cb_piloto = new JComboBox<String>();
                cb_piloto.setBounds(425, 245, 150, 30);
                cb_piloto.addItem("");
                for(int i = 0; i < pilotos.size(); i++){
                    cb_piloto.addItem(pilotos.get(i).getNome());
                }
                p_criar_voo.add(cb_piloto);
                // Origem
                // origem label
                JLabel l_origem = new JLabel("Origem");
                l_origem.setBounds(615, 225, 150, 20);
                p_criar_voo.add(l_origem);
                // origem combobox
                JComboBox <String> cb_origem = new JComboBox<String>();
                cb_origem.setBounds(605, 245, 150, 30);
                cb_origem.addItem("");
                for(int i = 0; i < aeroportos.size(); i++){
                    cb_origem.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
                }
                p_criar_voo.add(cb_origem);
                // Destino
                // destino label
                JLabel l_destino = new JLabel("Destino");
                l_destino.setBounds(795, 225, 150, 20);
                p_criar_voo.add(l_destino);
                // destino combobox
                JComboBox <String> cb_destino = new JComboBox<String>();
                cb_destino.setBounds(785, 245, 150, 30);
                cb_destino.addItem("");
                for(int i = 0; i < aeroportos.size(); i++){
                    cb_destino.addItem(aeroportos.get(i).getCidade() + "(" + aeroportos.get(i).getAbreviatura() + ")");
                }
                p_criar_voo.add(cb_destino);
                // criar button
                JButton b_criar = new JButton("+   Criar");
                b_criar.setBounds(525, 500, 150, 75);
                b_criar.addActionListener(o ->{
                    String codigo = tf_codigo.getText();
                    String hora_partida = tf1_partida.getText();
                    String hora_chegada = tf1_chegada.getText();
                    String data = tf_data.getText();
                    int index_companhia = cb_companhia.getSelectedIndex();
                    Companhia comp = companhias.get(index_companhia - 1);
                    int index_aviao = cb_aviao.getSelectedIndex();
                    Aviao av = avioes.get(index_aviao - 1);
                    int index_piloto = cb_piloto.getSelectedIndex();
                    Piloto pil = pilotos.get(index_piloto - 1);
                    int index_origem = cb_origem.getSelectedIndex();
                    Aeroporto aer_origem = aeroportos.get(index_origem - 1);
                    int index_destino = cb_destino.getSelectedIndex();
                    Aeroporto aer_destino = aeroportos.get(index_destino - 1);
                    try {
                        Voo voo = new Voo(codigo, av, comp, pil, aer_origem, aer_destino, hora_partida, hora_chegada, data);
                        voos.add(voo);
                        writeXml();
                        JOptionPane.showMessageDialog(null, "O voo foi criado com sucesso!");
                    } catch (FileNotFoundException | TransformerException | ParserConfigurationException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    p_criar_voo.removeAll();
                    frame.setContentPane(p_admingeneral);
                    frame.revalidate();
                });
                p_criar_voo.add(b_criar);
                // cancelar button
                JButton b_cancelar = new JButton("x   Cancelar");
                b_cancelar.setBounds(325, 500, 150, 75);
                b_cancelar.addActionListener(o ->{
                    p_criar_voo.removeAll();
                    frame.setContentPane(p_admingeneral);
                    frame.revalidate();
                });
                p_criar_voo.add(b_cancelar);
                p_admintables.removeAll();
                frame.setContentPane(p_criar_voo);
                frame.revalidate();
            });
            p_admintables.add(b_criar_voo);
            frame.setContentPane(p_admintables);
            frame.revalidate();
        });
        p_admingeneral.add(b_vertodososvoos);
        // terminar sessao button
        JButton b1_terminar = new JButton("Terminar Sessão");
        b1_terminar.setBounds(800, 25, 150, 75);
        b1_terminar.addActionListener(e -> {
            comp = true;
            avi = false;
            pil = false;
            aero = false;
            l_companhias.setVisible(true);
            cb_companhias.setSelectedIndex(0);
            cb_companhias.setVisible(true);
            l_avioes.setVisible(false);
            cb_avioes.setSelectedIndex(0);
            cb_avioes.setVisible(false);
            l_pilotos.setVisible(false);
            cb_pilotos.setSelectedIndex(0);
            cb_pilotos.setVisible(false);
            l_aeroportos.setVisible(false);
            cb_aeroportos.setSelectedIndex(0);
            cb_aeroportos.setVisible(false);
            frame.setContentPane(p_menu);
            frame.revalidate();
        });
        p_admingeneral.add(b1_terminar);



        // Inicializacao dos frames
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //ordena a lista dos proximos voos por data e caso sejam no mesmo dia ordena por hora
    private static void ordenar_voos(){
        Collections.sort(voos, Comparator.comparing(Voo::getPartida));
        Collections.sort(voos, Comparator.comparing(Voo::getData));
    }

    //troca a ordem dos voos
    private static void reverse_voos(){
        Collections.reverse(voos);
    }

    //atualiza o documento xml da base de dados
    private static void writeXml() throws TransformerException, FileNotFoundException, ParserConfigurationException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        FileOutputStream output = new FileOutputStream("XML/BaseDados.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document newDoc = dBuilder.newDocument();
        Element BaseDados = newDoc.createElement("BaseDados");

        //adiciona ao documento todos os objetos de todas as classes
        if(contas.size() > 0){
            for(int i = 0; i < contas.size(); i++){
                BaseDados.appendChild(contas.get(i).createElement(newDoc));
            }
        }
        if(companhias.size() > 0){
            for(int i = 0; i < companhias.size(); i++){
                BaseDados.appendChild(companhias.get(i).createElement(newDoc));
            }
        }
        if(avioes.size() > 0){
            for(int i = 0; i < avioes.size(); i++){
                BaseDados.appendChild(avioes.get(i).createElement(newDoc));
            }
        }
        if(pilotos.size() > 0){
            for(int i = 0; i < pilotos.size(); i++){
                BaseDados.appendChild(pilotos.get(i).createElement(newDoc));
            }
        }
        if(aeroportos.size() > 0){
            for(int i = 0; i < aeroportos.size(); i++){
                BaseDados.appendChild(aeroportos.get(i).createElement(newDoc));
            }
        }
        if(voos.size() > 0){
            for(int i = 0; i < voos.size(); i++){
                BaseDados.appendChild(voos.get(i).createElement(newDoc));
            }
        }

        newDoc.appendChild(BaseDados);

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}
