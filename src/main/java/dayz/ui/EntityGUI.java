package dayz.ui;

import dayz.entity.Weapon;
import dayz.repository.WeaponKindRepositoryEntity;
import dayz.repository.WeaponRepositoryEntity;
import dayz.repository.WeaponRepositoryPaging;
import dayz.repository.WeaponTypeRepositoryEntity;
import dayz.service.AppService;
import org.springframework.stereotype.Component;

import javax.swing.*;
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
        layoutComponents();
    }

    private void initComponents(){
        panel = new JPanel();
        idfield = new JTextField(10);
        nameField = new JTextField(10);
        kindField = new JTextField(10);
        typeField = new JTextField(10);
    }

    private void layoutComponents() {
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
    }

    private void bindData(){
        Iterable<Weapon> entityIterable = weaponRepositoryEntity.findAll();

        List<Weapon> entityList = new ArrayList<>();
        entityIterable.forEach(entityList::add);

        weaponTypeCombo = new JComboBox(entityList.toArray(new Weapon[0]));
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
