import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.io.File;

public class GrapInt extends JFrame {
    private JPanel mainPanel;
    private JSlider widthSlider;
    private JButton formatBtn;
    private JTextPane textPane;

    public GrapInt() {
        super("BoberOffice Bydle");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);

        // Создаем слайдер
        JSlider slider = new JSlider(30, 150, 90);
        slider.setPreferredSize(new Dimension(300, 40)); // Уменьшенный слайдер
        // Настраиваем ползунок
        slider.setMajorTickSpacing(20); // Основные отметки через каждые 20
        slider.setMinorTickSpacing(5);  // Дополнительные отметки через каждые 5
        slider.setPaintTicks(true);     // Отображение отметок
        slider.setPaintLabels(true);   // Отображение значений

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);

        // Создаем метку для отображения значения
        JLabel label = new JLabel("Значение: 90");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Настраиваем textPane
        textPane.setText("Введите значение в ползунке");
        textPane.setPreferredSize(new Dimension(700, 200)); // Увеличенный TextPane

        // Обработчик изменения значения слайдера
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                label.setText("Значение: " + value);
            }
        });
        formatBtn.addActionListener(e -> textPane.setText(Format.format(textPane.getText(), slider.getValue())));

        // Настройка компоновки
        setLayout(new BorderLayout());

        add(slider, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        add(new JScrollPane(textPane), BorderLayout.NORTH);
        add(formatBtn, BorderLayout.EAST);

        // Отображаем окно
        setVisible(true);


    }

    private JMenu createFileMenu() {
        // Создание выпадающего меню
        JMenu tableMenu = new JMenu("Таблица");

        // Пункт меню "Сохранить"
        JMenuItem saveItem = new JMenuItem("Сохранить в txt");
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
        new GrapInt();
    }
}