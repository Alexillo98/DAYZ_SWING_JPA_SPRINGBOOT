package dayz.ui;

import com.google.gson.Gson;
import dayz.API.Country;
import dayz.API.Country_code;
import dayz.controller.WeaponController;
import dayz.entity.Weapon;
import dayz.entity.WeaponKind;
import dayz.entity.WeaponType;
import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import dayz.service.AppService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("serial")
@Component
public class DayZGUI extends JFrame {

    private final WeaponController weaponController;

    enum GUI_STATES
    {
        NEW, EXISTING, CANCEL
    }
    private int entityState;

    private Weapon weapon;
    private WeaponKindRepositoryEntity weaponKindRepositoryEntity;
    private WeaponRepositoryEntity weaponRepositoryEntity;
    private WeaponRepositoryPaging weaponRepositoryPaging;
    private WeaponTypeRepositoryEntity weaponTypeRepositoryEntity;

    private AppService appService;

    private JTextField idfield;
    private JTextField nameField;
    private JTextField kindField;
    private JTextField typeField;
    private JTextField countryField;
    private JTextField imageLinkField;
    private JTextField countryNameField;
    private JLabel l;
    private JLabel imageLabel;
    private JLabel l_Country;
    private JLabel l_imageLink;
    private JLabel bannerLabel;
    private JLabel bannerLabel2;
    private JLabel flagLabel;
    private JButton newButton;
    private JButton cancelButton;
    private JProgressBar progressBar;

    private JEditorPane altPane;

    private Weapon actualWeapon;

    public DayZGUI(AppService appService, WeaponController weaponController) throws IOException {

        this.weaponRepositoryEntity = appService.getWeaponRepositoryEntity();
        this.weapon = weaponRepositoryEntity.findFirstByOrderByIdAsc();
        this.weaponController = weaponController;
        this.weaponRepositoryPaging = appService.getWeaponRepositoryPaging();
        this.weaponKindRepositoryEntity = appService.getWeaponKindRepositoryEntity();
        this.weaponTypeRepositoryEntity = appService.getWeaponTypeRepositoryEntity();
        this.appService = appService;
        this.weaponController.setWeapon(this.weapon);

        JLabel l;

        // Configuración básica de la ventana
        setTitle("DAYZ COUNTRIES");
        setSize(795, 690);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        layoutComponents();
        updateFields();
    }

    private void initComponents(){
        idfield = new JTextField(10);
        nameField = new JTextField(10);
        kindField = new JTextField(10);
        typeField = new JTextField(10);
        imageLabel = new JLabel();
        bannerLabel = new JLabel();
        bannerLabel2 = new JLabel();
        l_Country = new JLabel();
        countryField = new JTextField(10);
        imageLinkField = new JTextField(10);
        cancelButton = new JButton();
        progressBar = new JProgressBar();
    }

    private void layoutComponents() throws IOException {
        // Crear el componente de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Crear la primera pestaña con sus subpaneles
        JPanel tab1 = new JPanel();
        tab1.setLayout(null);  // Usar un layout nulo para posicionar los paneles manualmente

        //Banner
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBounds(0,0,795,150);
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK,2);
        bannerPanel.setBorder(lineBorder);
        bannerPanel.add(bannerLabel);
        tab1.add(bannerPanel);

        File file = new File("src/main/resources/banner.png");
        Image originalImage = ImageIO.read(file);
        Image scaledImage = originalImage.getScaledInstance(795,150,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        bannerLabel.setIcon(icon);

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBounds(0, 150, 775, 300);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        topPanel.add(imageLabel);
        tab1.add(topPanel,BorderLayout.CENTER);

        // Panel vertical izquierdo

        JButton botonAnterior = new JButton();
        ImageIcon iconAnterior = new ImageIcon("src/main/resources/boton_retroceder.png");
        Image resizeIcon = iconAnterior.getImage().getScaledInstance(45,40,Image.SCALE_SMOOTH);
        ImageIcon resizedIconAnterior = new ImageIcon(resizeIcon);
        botonAnterior.setIcon(resizedIconAnterior);
        botonAnterior.setBounds(2,0,70,87);

        JButton botonFirst = new JButton("<<<");
        botonFirst.setBounds(2,87,70,87);

        JPanel leftVerticalPanel = new JPanel();
        leftVerticalPanel.setLayout(null);
        leftVerticalPanel.setBounds(0, 450, 75, 175);/*
        JPanel topFiller = new JPanel();
        JPanel bottomFiller = new JPanel();
        leftVerticalPanel.add(topFiller,BorderLayout.NORTH);
        topFiller.setLayout(null);*/
        leftVerticalPanel.add(botonAnterior);
        leftVerticalPanel.add(botonFirst);
/*        leftVerticalPanel.add(bottomFiller,BorderLayout.SOUTH);
        bottomFiller.setLayout(null);*/
        botonAnterior.addActionListener(e -> {
            try {
                previous();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        leftVerticalPanel.add(botonAnterior);
        botonFirst.addActionListener(e -> {
            try {
                first();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        leftVerticalPanel.add(botonFirst);
        tab1.add(leftVerticalPanel);

        // Panel horizontal central
        JLabel l_ID = new JLabel("ID: ");
        l_ID.setBounds(5, 5, 100, 30);
/*        idfield.setEnabled(false);*/
        idfield.setBackground(Color.GRAY);
        idfield.setBounds(75,5,200,30);

        JLabel l_Name = new JLabel("NOMBRE: ");
        l_Name.setBounds(5, 45, 100, 30);
        nameField.setBackground(Color.GRAY);
/*        nameField.setEnabled(true);*/
        nameField.setBounds(75,45,200,30);

        JLabel l_Kind = new JLabel("CLASE: ");
        l_Kind.setBounds(5, 85, 100, 30);
        kindField.setBackground(Color.GRAY);
/*        kindField.setEnabled(false);*/
        kindField.setBounds(75,85,200,30);

        JLabel l_Type = new JLabel("TIPO: ");
        l_Type.setBounds(5, 125, 100, 30);
        typeField.setBackground(Color.GRAY);
/*        typeField.setEnabled(false);*/
        typeField.setBounds(75,125,200,30);

        l_Country = new JLabel("PAIS: ");
        l_Country.setBounds(385, 5, 100, 30);
        countryField.setBackground(Color.GRAY);
        countryField.setBounds(420,5,200,30);

        l_imageLink = new JLabel("URL: ");
        l_imageLink.setBounds(385,45,200,30);
        imageLinkField.setBackground(Color.GRAY);
        imageLinkField.setBounds(420,45,200,30);

        JButton newButton= new JButton("AÑADIR");
        newButton.setBounds(280,5,100,30);

        JButton saveButton = new JButton("GUARDA");
        saveButton.setBounds(280,45,100,30);

        JButton deleteButton = new JButton("ELIMINAR");
        deleteButton.setBounds(280,85,100,30);

        cancelButton = new JButton("CANCELA");
        cancelButton.setBounds(280,125,100,30);
        cancelButton.setForeground(Color.RED);

        progressBar = new JProgressBar();
        progressBar.setBounds(240,125,100,30);
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        tab1.add(progressBar);

        JPanel centerHorizontalPanel = new JPanel();
        centerHorizontalPanel.setLayout(null);
        centerHorizontalPanel.setBackground(Color.WHITE);
        centerHorizontalPanel.setBounds(75, 450, 625, 175);
        centerHorizontalPanel.add(l_ID);
        centerHorizontalPanel.add(l_Name);
        centerHorizontalPanel.add(l_Kind);
        centerHorizontalPanel.add(l_Type);
        centerHorizontalPanel.add(l_Country);
        centerHorizontalPanel.add(l_imageLink);
        centerHorizontalPanel.add(idfield);
        centerHorizontalPanel.add(nameField);
        centerHorizontalPanel.add(kindField);
        centerHorizontalPanel.add(typeField);
        centerHorizontalPanel.add(countryField);
        centerHorizontalPanel.add(imageLinkField);
        centerHorizontalPanel.add(newButton);
        centerHorizontalPanel.add(saveButton);
        centerHorizontalPanel.add(deleteButton);
        centerHorizontalPanel.add(cancelButton);
        cancelButton.addActionListener(e -> {
            try {
                cancel();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteButton.addActionListener(e -> {
            try {
                deleteEntity();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        try {
            saveButton.addActionListener(e -> {
                try {
                    saveEntity();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        newButton.addActionListener(e -> {
            try {
                newEntity();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        tab1.add(centerHorizontalPanel);

        // Panel vertical derecho

        JButton botonSiguiente = new JButton();
        ImageIcon iconSiguiente = new ImageIcon("src/main/resources/boton_avanzar.png");
        Image resizeIcon2 = iconSiguiente.getImage().getScaledInstance(45,40,Image.SCALE_SMOOTH);
        ImageIcon resizedIconSiguiente = new ImageIcon(resizeIcon2);
        botonSiguiente.setIcon(resizedIconSiguiente);
        botonSiguiente.setBounds(2,0,70,87);

        JButton botonLast = new JButton(">>>");
        botonLast.setBounds(2,87,70,87);

        JPanel rightVerticalPanel = new JPanel();
        rightVerticalPanel.setLayout(null);
/*        rightVerticalPanel.setBackground(Color.GREEN);*/
        rightVerticalPanel.setBounds(700, 450, 75, 175);

/*        JPanel topFiller2 = new JPanel();
        JPanel bottomFiller2 = new JPanel();*/
/*        rightVerticalPanel.add(topFiller2,BorderLayout.NORTH);
        topFiller2.setLayout(null);
        rightVerticalPanel.add(bottomFiller2,BorderLayout.SOUTH);
        bottomFiller2.setLayout(null);*/
        botonSiguiente.addActionListener(e -> {
            try {
                next();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rightVerticalPanel.add(botonSiguiente);
        botonLast.addActionListener(e -> {
            try {
                last();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rightVerticalPanel.add(botonLast);
        tab1.add(rightVerticalPanel);

        // Añadir la primera pestaña al tabbedPane
        tabbedPane.addTab("DAYZ/ARMA", tab1);

        // Crear una segunda pestaña vacía
        JPanel tab2 = new JPanel();
        tab2.setLayout(null);

        //PESTAÑA2: PANEL SUPERIOR

        JPanel panelN = new JPanel();
        panelN.setLayout(null);
        panelN.setBounds(0,0,795,150);
        tab2.add(panelN);

        File file2 = new File("src/main/resources/banner.png");
        Image originalImage2 = ImageIO.read(file2);
        Image scaledImage2 = originalImage2.getScaledInstance(795,150,Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(scaledImage2);
        bannerLabel2.setIcon(icon2);

        JPanel bannerPanel2 = new JPanel(new BorderLayout());
        bannerPanel2.setBounds(0,0,795,150);
        bannerLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        Border lineBorder2 = BorderFactory.createLineBorder(Color.BLACK,2);
        bannerPanel2.setBorder(lineBorder2);
        bannerPanel2.add(bannerLabel2);

        panelN.add(bannerPanel2);

        tab2.add(panelN);

        //PESTAÑA2: PANEL IZQUIERDO

        flagLabel = new JLabel();
        flagLabel.setBounds(50,150,300,175);
        flagLabel.setBackground(Color.YELLOW);
        flagLabel.setOpaque(true);

        countryNameField = new JTextField();
        countryNameField.setEditable(false);
        countryNameField.setBounds(50,50,300,50);
        countryNameField.setBackground(Color.GRAY);
        countryNameField.setOpaque(true);
        countryNameField.setBorder(lineBorder2);

        JPanel panelI = new JPanel();
        panelI.setLayout(null);
        panelI.setBounds(0,150,397,540);
/*        panelI.setBackground(Color.blue);*/
        panelI.add(flagLabel);
        panelI.add(countryNameField);
        tab2.add(panelI);

        //PESTAÑA2: PANEL DERECHO

/*        Gson gson = new Gson();
        URL countries = new URL("https://restcountries.com/v3.1/name/"+weapon.getCountry().toLowerCase()+"?fields=name,flags");
        BufferedReader in = new BufferedReader(new InputStreamReader(countries.openStream(), StandardCharsets.UTF_8));
        Country[] c = gson.fromJson(in, Country[].class); //ES UN ARRAY DE OBJETOS*/

        altPane = new JEditorPane();
        altPane.setEditable(false);
/*        altPane.setText("");*/
        altPane.setBounds(5,250,300,400);


        JPanel panelD = new JPanel();
        panelD.setBounds(397,150,397,540);
        panelD.setBackground(Color.GREEN);
        panelD.add(altPane);
        tab2.add(panelD);

        tabbedPane.addTab("PAIS/BANDERA", tab2);

        // Añadir el tabbedPane a la ventana
        add(tabbedPane);
    }
    private void updateFields() throws IOException {
        if (this.weapon != null){

            this.entityState = EntityGUI.GUI_STATES.EXISTING.ordinal();

            this.idfield.setText(this.weapon.getId().toString());
            this.idfield.setEnabled(false);
            this.nameField.setText(this.weapon.getName());
            this.nameField.setEnabled(false);
            this.kindField.setText(this.weapon.getWeapon_kinds().getName());
            this.kindField.setEnabled(false);
            this.typeField.setText(this.weapon.getWeapon_types().getName());
            this.typeField.setEnabled(false);
            this.countryField.setVisible(false);
            this.l_Country.setVisible(false);
            this.imageLinkField.setVisible(false);
            this.l_imageLink.setVisible(false);
            this.cancelButton.setVisible(false);
        }else {
            if (this.entityState == EntityGUI.GUI_STATES.NEW.ordinal()){
                this.entityState = EntityGUI.GUI_STATES.CANCEL.ordinal();
            }else {
                this.entityState = EntityGUI.GUI_STATES.NEW.ordinal();
            }
            this.idfield.setText(String.valueOf(weaponRepositoryPaging.biggerID() + 1/*countAllRecords() + 1*/));
            this.idfield.setEnabled(false);
            this.nameField.setText("");
            this.nameField.setEnabled(true);
            this.kindField.setText("");
            this.kindField.setEnabled(true);
            this.typeField.setText("");
            this.typeField.setEnabled(true);
            this.countryField.setVisible(true);
            this.l_Country.setVisible(true);
            this.imageLinkField.setVisible(true);
            this.l_imageLink.setVisible(true);
            this.cancelButton.setVisible(true);
        }
        updateImage();
        updateFlagImageInThread();
        updateAltInThread();
        updateCountryName();
    }

    private void updateImage() throws IOException {
        if (this.weapon != null && this.weapon.getImgLink() != null){
        URL url = new URL(this.weapon.getImgLink());
        Image originalImage = ImageIO.read(url);
        Image scaledImage = originalImage.getScaledInstance(750,300,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);
/*        setTitle("Prueba");*/ //Funciona, por lo que la imagen carga.
        }else {
            imageLabel.setIcon(null);
        }
    }

    private void updateAlt() throws IOException {
         if (this.weapon != null && this.weapon.getCountry() != null){
             altPane.setText("CARGANDO...[DEPENDIENDO DEL TRAFICO DE LA RED DE LA API ESTO PUEDE TARDAR MUCHO RATO O INCLUSO NO CARGAR]");
             Gson gson = new Gson();
             URL countries = new URL("https://restcountries.com/v3.1/name/"+weapon.getCountry().toLowerCase()+"?fields=name,flags");
             BufferedReader in = new BufferedReader(new InputStreamReader(countries.openStream(), StandardCharsets.UTF_8));
             Country[] c = gson.fromJson(in, Country[].class); //ES UN ARRAY DE OBJETOS
             altPane.setText(c[0].getFlags().getAlt());
         }else {
             altPane.setText("FAILED TO LOAD! ");
         }
    }

    private void updateCountryName() throws IOException {
        if (this.weapon != null && this.weapon.getCountry_code() != null){
            Gson gson = new Gson();
            URL codes = new URL ("https://flagcdn.com/es/codes.json");
            BufferedReader in = new BufferedReader(new InputStreamReader(codes.openStream(), StandardCharsets.UTF_8));
            Country_code cn = gson.fromJson(in, Country_code.class);
            countryNameField.setText(cn.getCode()); // <--- ES NULO !!
        }else {
            countryNameField.setText("FALLO AL CARGAR!");
        }
    }



/*    private void updateFlagImage () throws IOException {
        Gson gson = new Gson();
        URL countries = new URL("https://restcountries.com/v3.1/name/"+weapon.getCountry().toLowerCase()+"?fields=name,flags");
        BufferedReader in = new BufferedReader(new InputStreamReader(countries.openStream(),StandardCharsets.UTF_8));
        Country[] c = gson.fromJson(in, Country[].class);
        URL flagURL = new URL(c[0].getFlags().getPng());
        Image originalImage = ImageIO.read(flagURL);
        Image scaledImage = originalImage.getScaledInstance(250,175,Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        flagLabel.setIcon(imageIcon);
    }*/
    private void updateFlagImage () throws IOException {
/*        Gson gson = new Gson();
        URL countries = new URL("https://flagcdn.com/256x192/ua.png");
        BufferedReader in = new BufferedReader(new InputStreamReader(countries.openStream(),StandardCharsets.UTF_8));
        Country[] c = gson.fromJson(in, Country[].class);*/
        URL flagURL = new URL("https://flagcdn.com/256x192/"+this.weapon.getCountry_code()+".png");
        Image originalImage = ImageIO.read(flagURL);
        Image scaledImage = originalImage.getScaledInstance(300,175,Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        flagLabel.setIcon(imageIcon);
    }

    private void updateFlagImageInThread(){
        Thread thread = new Thread(() -> {
            try {
                updateFlagImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private void updateAltInThread(){
        Thread thread = new Thread(() -> {
            try {
                updateAlt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }
    private void next() throws IOException {
            this.weapon = weaponController.next().orElse(null);
            updateFields();
    }

    private void previous() throws IOException {
        this.weapon = weaponController.previous().orElse(null);
        updateFields();
    }

    private void newEntity() throws IOException {
        actualWeapon = this.weapon;
        this.weapon = null;
        updateFields();
    }

    private void saveEntity() throws IOException {
        this.weapon = new Weapon();
        weapon.setId(Long.valueOf(idfield.getText()));
        weapon.setName(nameField.getText());
        weapon.setCountry(countryField.getText());
        weapon.setImgLink(imageLinkField.getText());
        WeaponKind wk = new WeaponKind(Long.valueOf(kindField.getText()));
        weapon.setWeapon_kinds(wk);
        WeaponType wt = new WeaponType(Long.valueOf(typeField.getText()));
        weapon.setWeapon_types(wt);
        this.entityState = GUI_STATES.EXISTING.ordinal();
        this.weaponRepositoryEntity.save(weapon);

        JOptionPane.showMessageDialog(this, "ARMA GUARDADA!");

        countryField.setText("");
        imageLinkField.setText("");

        updateFields();
    }

    private void deleteEntity() throws IOException {
        if (this.weapon != null){
            this.weaponRepositoryEntity.delete(this.weapon);
            this.newEntity();
            JOptionPane.showMessageDialog(this, "ARMA ELIMINADA!");
        }
    }

    private void first() throws IOException {
        this.weapon = weaponController.first().orElse(null);
        updateFields();
    }

    private void last() throws IOException {
        this.weapon = weaponController.last().orElse(null);
        updateFields();
    }

    private void cancel() throws IOException {
        this.weapon = actualWeapon;
        updateFields();
    }
}

/*        // Añadir la imagen del arma
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 775, 300);

        // Cargar la imagen desde una URL
        try {
            URL url = new URL("https://static.wikia.nocookie.net/dayz_gamepedia/images/e/e6/AugShort.png/revision/latest?cb=20211104175243"); // Reemplaza con la URL de tu imagen
            Image originalImage = ImageIO.read(url);
            Image scaledImage = originalImage.getScaledInstance(775, 300, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        topPanel.add(imageLabel);*/

/*        leftVerticalPanel.setBackground(Color.GREEN);*/

/*        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Añadir un relleno para centrar verticalmente el botón
        gbc.insets = new Insets(5, 10, 5, 10); // Ajusta el valor superior e inferior según sea necesario*/

/*        topPanel.setBackground(Color.LIGHT_GRAY);*/

/*        SwingWorker<ImageIcon,Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                progressBar.setVisible(true);

            }
        }*/