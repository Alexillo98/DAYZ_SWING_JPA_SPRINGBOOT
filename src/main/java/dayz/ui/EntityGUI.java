package dayz.ui;

import dayz.entity.Weapon;
import dayz.entity.WeaponType;
import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import dayz.service.AppService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntityGUI extends JFrame {

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

    private JSeparator s;

    private JLabel l;
    private JFrame frame;

    private JComboBox weaponTypeCombo;
    private JComboBox weaponKindCombo;

    private JPanel panel;

    public EntityGUI (AppService appService){

        this.weaponRepositoryEntity = appService.getWeaponRepositoryEntity();
        this.weapon = weaponRepositoryEntity.findFirstByOrderByIdAsc();

        this.weaponRepositoryPaging = appService.getWeaponRepositoryPaging();
        this.weaponKindRepositoryEntity = appService.getWeaponKindRepositoryEntity();
        this.weaponTypeRepositoryEntity = appService.getWeaponTypeRepositoryEntity();
        this.appService = appService;

        setTitle("Dayz Weapons And Country Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        initComponents();
        bindData();
        updateFields();
/*        layoutComponents2();*/
    }

    private void initComponents(){
        panel = new JPanel(new BorderLayout());
        idfield = new JTextField(10);
        nameField = new JTextField(10);
        kindField = new JTextField(10);
        typeField = new JTextField(10);
    }

/*    private void layoutComponents() {
        JLabel l;

        this.setLayout(null);
        panel.setLayout(null);
        panel.setBounds(0,0,800,600);


        l = new JLabel("ID");
        l.setBounds(50,30,100,30);
        panel.add(l);
        idfield.setEnabled(false);
        idfield.setBounds(150,30,100,30);
        panel.add(idfield);

        l = new JLabel("Name");
        l.setBounds(50,60,100,30);
        panel.add(l);
        nameField.setBounds(150,60,100,30);
        panel.add(nameField);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBounds(0,250,800,10);
        panel.add(separator1);

        l = new JLabel("Kind");
        l.setBounds(50,90,100,30);
        panel.add(l);
        kindField.setBounds(150,90,100,30);
        panel.add(kindField);

        l =  new JLabel("Type");
        l.setBounds(50,120,100,30);
        panel.add(l);
        typeField.setBounds(150,120,100,30);
        panel.add(typeField);

        l = new JLabel("Weapon Kind ID");
        l.setBounds(50,150,100,30);
        panel.add(l);
        weaponTypeCombo.setBounds(150,150,100,30);
        panel.add(weaponTypeCombo);

        this.add(panel);

        // Crear una etiqueta para mostrar la imagen
        JLabel label = new JLabel();

        // Cargar la imagen desde la URL
        try {
            URL url = new URL("https://static.wikia.nocookie.net/dayz_gamepedia/images/8/8b/AK74.png/revision/latest?cb=20210505013141");
            Image originalImage = ImageIO.read(url);
            // Escalar la imagen al tamaño deseado (por ejemplo, 200x200 píxeles)
            Image scaledImage = originalImage.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            label.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
/*
    private void layoutComponents2(){
        // Crear un JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        // Crear el panel principal
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 600);

        // Añadir la imagen al centro de la ventana
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 200, 600, 400); // Ajusta las coordenadas y el tamaño según tus necesidades
        // Cargar la imagen desde una URL
        try {
            URL url = new URL("https://static.wikia.nocookie.net/dayz_gamepedia/images/e/e6/AugShort.png/revision/latest?cb=20211104175243"); // Reemplaza con la URL de tu imagen
            Image originalImage = ImageIO.read(url);
            Image scaledImage = originalImage.getScaledInstance(600, 200, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.add(imageLabel);

        // Paneles ID y Name en la parte superior
        l = new JLabel("ID");
        l.setBounds(50, 30, 100, 30);
        panel.add(l);
        idfield.setEnabled(false);
        idfield.setBounds(150, 30, 100, 30);
        panel.add(idfield);

        l = new JLabel("Name");
        l.setBounds(300, 30, 100, 30);
        panel.add(l);
        nameField.setBounds(400, 30, 100, 30);
        panel.add(nameField);

        // Añadir separador entre la parte superior y el centro
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBounds(0, 100, 800, 10); // Ajusta las coordenadas y el tamaño según tus necesidades
        panel.add(separator1);

        // Paneles Kind, Type y Weapon Kind ID en la parte inferior
        l = new JLabel("Kind");
        l.setBounds(50, 670, 100, 30);
        panel.add(l);
        kindField.setBounds(150, 670, 100, 30);
        panel.add(kindField);

        l = new JLabel("Type");
        l.setBounds(300, 670, 100, 30);
        panel.add(l);
        typeField.setBounds(400, 670, 100, 30);
        panel.add(typeField);

        l = new JLabel("Weapon Kind ID");
        l.setBounds(550, 670, 120, 30);
        panel.add(l);
        weaponTypeCombo.setBounds(670, 670, 100, 30);
        panel.add(weaponTypeCombo);

        // Añadir separador entre el centro y la parte inferior
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setBounds(0, 600, 800, 10); // Ajusta las coordenadas y el tamaño según tus necesidades
        panel.add(separator2);

        // Añadir el panel al JFrame
        this.add(panel);
    }*/

    private void bindData(){
        Iterable<WeaponType> entityIterable = weaponTypeRepositoryEntity.findAll();

        List<WeaponType> entityList = new ArrayList<>();
        entityIterable.forEach(entityList::add);

        weaponTypeCombo = new JComboBox(entityList.toArray(new WeaponType[0]));
    }

    private void updateFields(){
        if (this.weapon != null){

            this.entityState = GUI_STATES.EXISTING.ordinal();

            this.idfield.setText(this.weapon.getId().toString());
            this.nameField.setText(this.weapon.getName());
            this.kindField.setText(this.weapon.getWeapon_kinds().getName());
            this.typeField.setText(this.weapon.getWeapon_types().getName());
            this.weaponTypeCombo.setSelectedItem(this.weapon.getWeapon_types());

        }else {
            if (this.entityState == GUI_STATES.NEW.ordinal()){
                this.entityState = GUI_STATES.CANCEL.ordinal();
            }else {
                this.entityState = GUI_STATES.NEW.ordinal();
            }
        }
    }
}
