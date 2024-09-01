import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbcDemo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("JDBC Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);

        // Create JPanel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Add image
        try {
            // Load image
            BufferedImage img = ImageIO.read(new File("C://Users//lenovo//IdeaProjects//JDBCDemo//src//Karanimage.jpg")); // Update the path to your image

            // Resize image
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Resize to desired dimensions
            ImageIcon imageIcon = new ImageIcon(scaledImg); // Create ImageIcon with resized image
            JLabel imageLabel = new JLabel(imageIcon);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4; // Span across 4 columns
            panel.add(imageLabel, gbc);
        } catch (IOException e) {
            System.out.println("Image not found or cannot be loaded.");
        }

        // Create labels and text fields
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Span across 3 columns
        panel.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(ageLabel, gbc);

        JTextField ageField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(ageField, gbc);

        JLabel dateLabel = new JLabel("Date of Birth:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(dateLabel, gbc);

        JTextField dateField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(dateField, gbc);

        JLabel monthLabel = new JLabel("Month of Birth:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(monthLabel, gbc);

        JTextField monthField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(monthField, gbc);

        JLabel yearLabel = new JLabel("Year of Birth:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(yearLabel, gbc);

        JTextField yearField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(yearField, gbc);

        // Create buttons
        JButton insertButton = new JButton("Insert Data");
        styleButton(insertButton, Color.WHITE, new Color(0, 102, 204), new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(insertButton, gbc);

        JButton fetchButton = new JButton("Fetch Data");
        styleButton(fetchButton, Color.WHITE, new Color(0, 153, 76), new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 2;
        gbc.gridy = 6;
        panel.add(fetchButton, gbc);

        JButton updateData = new JButton("Edit Data");
        styleButton(updateData, Color.WHITE, new Color(255, 153, 51), new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(updateData, gbc);

        // TextArea for displaying fetched data
        JTextArea resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4; // Span across 4 columns
        panel.add(scrollPane, gbc);

        // Add ActionListener to Insert button
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String ageText = ageField.getText();
                String date = dateField.getText();
                String month = monthField.getText();
                String year = yearField.getText();

                if (name.isEmpty() || ageText.isEmpty() || date.isEmpty() || month.isEmpty() || year.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }
                try {
                    int age = Integer.parseInt(ageText);
                    insertData(name, age, date, month, year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.");
                }
            }
        });

        // Add ActionListener to Fetch button
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData(resultArea);
            }
        });

        // Add ActionListener to Update button
        updateData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String ageText = ageField.getText();
                String date = dateField.getText();
                String month = monthField.getText();
                String year = yearField.getText();

                if (name.isEmpty() || ageText.isEmpty() || date.isEmpty() || month.isEmpty() || year.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }
                try {
                    int age = Integer.parseInt(ageText);
                    updatingData(name, age, date, month, year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid age.");
                }
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }

    // Method to style buttons
    private static void styleButton(JButton button, Color textColor, Color bgColor, Font font) {
        button.setForeground(textColor);
        button.setBackground(bgColor);
        button.setFont(font);
        button.setFocusPainted(false); // Remove focus border
    }

    // Method to insert data into the database
    private static void insertData(String name, int age, String date, String month, String year) {
        String sql = "INSERT INTO student (name, age, date, month, year) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, date); // Assumes dob is in 'YYYY-MM-DD' format
            preparedStatement.setString(4, month);
            preparedStatement.setString(5, year);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Data insertion failed.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    // Method to update data in the database
    private static void updatingData(String name, int age, String date, String month, String year) {
        String sql = "UPDATE student SET age=?, date=?, month=?, year=? WHERE name=?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, age);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, year);
            preparedStatement.setString(5, name);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Data update failed.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    // Method to fetch and display data from the database
    private static void fetchData(JTextArea resultArea) {
        String sql = "SELECT * FROM student";
        StringBuilder resultText = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            resultText.append("Here is your list:\n");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int age = resultSet.getInt(3);
                String date = resultSet.getString(4);
                String month = resultSet.getString(5);
                String year = resultSet.getString(6);
                resultText.append("ID: ").append(id).append(", Name: ").append(name)
                        .append(", Age: ").append(age).append(", Date of Birth: ").append(date)
                        .append(", Month: ").append(month).append(", Year: ").append(year).append("\n");
            }

            resultArea.setText(resultText.toString());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }
}
