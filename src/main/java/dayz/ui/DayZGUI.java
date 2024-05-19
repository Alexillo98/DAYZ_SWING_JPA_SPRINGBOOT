package dayz.ui;

import dayz.controller.WeaponController;
import dayz.entity.Weapon;
import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import dayz.service.AppService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.ImageGraphicAttribute;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

    private JLabel l;
    private JLabel imageLabel;
    private JButton newButton;

    public DayZGUI(AppService appService, WeaponController weaponController) throws IOException {

        this.weaponRepositoryEntity = appService.getWeaponRepositoryEntity();
        this.weapon = weaponRepositoryEntity.findFirstByOrderByIdAsc();
        this.weaponController = weaponController;
        this.weaponRepositoryPaging = appService.getWeaponRepositoryPaging();
        this.weaponKindRepositoryEntity = appService.getWeaponKindRepositoryEntity();
        this.weaponTypeRepositoryEntity = appService.getWeaponTypeRepositoryEntity();
        this.appService = appService;

        JLabel l;

        // Configuración básica de la ventana
        setTitle("DAYZ COUNTRIES");
        setSize(795, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        updateFields();
        layoutComponents();

    }

    private void initComponents(){
        idfield = new JTextField(10);
        nameField = new JTextField(10);
        kindField = new JTextField(10);
        typeField = new JTextField(10);
        imageLabel = new JLabel();
    }

    private void layoutComponents() {
        // Crear el componente de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Crear la primera pestaña con sus subpaneles
        JPanel tab1 = new JPanel();
        tab1.setLayout(null);  // Usar un layout nulo para posicionar los paneles manualmente

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBounds(0, 0, 775, 300);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(imageLabel);
        tab1.add(topPanel,BorderLayout.CENTER);

        // Panel vertical izquierdo

        JButton botonAnterior = new JButton();
        ImageIcon iconAnterior = new ImageIcon("src/main/resources/boton_retroceder.png");
        Image resizeIcon = iconAnterior.getImage().getScaledInstance(45,40,Image.SCALE_SMOOTH);
        ImageIcon resizedIconAnterior = new ImageIcon(resizeIcon);
        botonAnterior.setIcon(resizedIconAnterior);

        JPanel leftVerticalPanel = new JPanel();
        leftVerticalPanel.setLayout(new BorderLayout());
        leftVerticalPanel.setBounds(0, 300, 75, 175);
        JPanel topFiller = new JPanel();
        JPanel bottomFiller = new JPanel();
        leftVerticalPanel.add(topFiller,BorderLayout.NORTH);
        topFiller.setLayout(null);
        leftVerticalPanel.add(bottomFiller,BorderLayout.SOUTH);
        bottomFiller.setLayout(null);
        botonAnterior.addActionListener(e -> {
            try {
                previous();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        leftVerticalPanel.add(botonAnterior);
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

        JButton newButton= new JButton("AÑADIR");
        newButton.setBounds(280,5,100,30);

        JPanel centerHorizontalPanel = new JPanel();
        centerHorizontalPanel.setLayout(null);
        centerHorizontalPanel.setBackground(Color.WHITE);
        centerHorizontalPanel.setBounds(75, 300, 625, 175);
        centerHorizontalPanel.add(l_ID);
        centerHorizontalPanel.add(l_Name);
        centerHorizontalPanel.add(l_Kind);
        centerHorizontalPanel.add(l_Type);
        centerHorizontalPanel.add(idfield);
        centerHorizontalPanel.add(nameField);
        centerHorizontalPanel.add(kindField);
        centerHorizontalPanel.add(typeField);
        centerHorizontalPanel.add(newButton);
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

        JPanel rightVerticalPanel = new JPanel();
        rightVerticalPanel.setLayout(new BorderLayout());
/*        rightVerticalPanel.setBackground(Color.GREEN);*/
        rightVerticalPanel.setBounds(700, 300, 75, 175);
        JPanel topFiller2 = new JPanel();
        JPanel bottomFiller2 = new JPanel();
        rightVerticalPanel.add(topFiller2,BorderLayout.NORTH);
        topFiller2.setLayout(null);
        rightVerticalPanel.add(bottomFiller2,BorderLayout.SOUTH);
        bottomFiller2.setLayout(null);
        botonSiguiente.addActionListener(e -> {
            try {
                next();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rightVerticalPanel.add(botonSiguiente);
        tab1.add(rightVerticalPanel);

        // Añadir la primera pestaña al tabbedPane
        tabbedPane.addTab("DAYZ/ARMA", tab1);

        // Crear una segunda pestaña vacía
        JPanel tab2 = new JPanel();

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

        }else {
            if (this.entityState == EntityGUI.GUI_STATES.NEW.ordinal()){
                this.entityState = EntityGUI.GUI_STATES.CANCEL.ordinal();
            }else {
                this.entityState = EntityGUI.GUI_STATES.NEW.ordinal();
            }
            this.idfield.setText(String.valueOf(weaponRepositoryPaging.countAllRecords() + 1));
            this.idfield.setEnabled(false);
            this.nameField.setText("");
            this.nameField.setEnabled(true);
            this.kindField.setText("");
            this.kindField.setEnabled(true);
            this.typeField.setText("");
            this.typeField.setEnabled(true);
        }
        updateImage();
    }

    private void updateImage() throws IOException {
        if (this.weapon != null && this.weapon.getImgLink() != null){
        URL url = new URL(this.weapon.getImgLink());
        Image originalImage =ImageIO.read(url);
        Image scaledImage = originalImage.getScaledInstance(750,300,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);
/*        setTitle("Prueba");*/ //Funciona, por lo que la imagen carga.
        }else {
            imageLabel.setIcon(null);
        }
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
        this.weapon = null;
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