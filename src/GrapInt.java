import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GrapInt extends JFrame {
    private JPanel mainPanel;
    private JSlider slider;
    private JButton formatBtn;
    private JTextPane textPane;

    public GrapInt() {
        super("BoberOffice Bydle");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);

        slider.setMajorTickSpacing(20); // Основные отметки через каждые 20
        slider.setMinorTickSpacing(5);  // Дополнительные отметки через каждые 5
        slider.setPaintTicks(true);     // Отображение отметок
        slider.setPaintLabels(true);   // Отображение значений


        // Настраиваем textPane
        textPane.setText("Введите значение в ползунке");
        textPane.setPreferredSize(new Dimension(550, 200)); // Увеличенный TextPane

        // Устанавливаем моноширинный шрифт
        Font monospacedFont = new Font("Courier New", Font.PLAIN, 14);
        textPane.setFont(monospacedFont);

        formatBtn.addActionListener(e -> textPane.setText(Format.format(textPane.getText(), slider.getValue())));

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private JMenu createFileMenu() {
        // Создание выпадающего меню
        JMenu tableMenu = new JMenu("Таблица");

        // Пункт меню "Сохранить"
        JMenuItem saveItem = new JMenuItem("Сохранить в json");
        // Добавление в меню пункта save
        tableMenu.add(saveItem);
        // callback (метод который будет вызван при нажатии кнопки save)
        saveItem.addActionListener(arg0 -> {
            String filePath = chooseFile();
            if (filePath != null) {
                String outData = textPane.getText();
                try {
                    FileIO.writeStringToFile(filePath.replace(".txt", ".json"), outData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Пункт меню "Открыть"
        JMenuItem openItem = new JMenuItem("Открыть из файла");
        tableMenu.add(openItem);
        openItem.addActionListener(arg0 -> {
            String filePath = chooseFile();
            if (filePath != null) {
                try {
                    textPane.setText(FileIO.readStringFromFile(filePath));
                    formatBtn.doClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return tableMenu;
    }

    public String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GrapInt());
    }
}