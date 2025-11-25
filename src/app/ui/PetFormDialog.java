package app.ui;

import javax.swing.*;
import java.awt.*;
import app.models.Pet;
import app.memorydb.MemoryDatabase;

public class PetFormDialog extends JDialog {

    private JTextField nameField, typeField, breedField;
    private JTextArea descArea;

    public PetFormDialog(JFrame parent) {
        super(parent, "Add Pet", true);
        setSize(350, 350);
        setLayout(new GridLayout(5, 1));

        nameField = new JTextField();
        typeField = new JTextField();
        breedField = new JTextField();
        descArea = new JTextArea();

        add(labeledPanel("Name:", nameField));
        add(labeledPanel("Type:", typeField));
        add(labeledPanel("Breed:", breedField));
        add(labeledPanel("Description:", new JScrollPane(descArea)));

        JButton submit = new JButton("Add Pet");
        submit.addActionListener(e -> addPet());
        add(submit);
    }

    private JPanel labeledPanel(String text, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(text), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void addPet() {
        String name = nameField.getText();
        String type = typeField.getText();
        String breed = breedField.getText();
        String desc = descArea.getText();

        Pet pet = new Pet(name, type, breed, desc);

        MemoryDatabase.addPet(pet);

        JOptionPane.showMessageDialog(this, "Pet Added Successfully!");
        dispose();
    }
}
