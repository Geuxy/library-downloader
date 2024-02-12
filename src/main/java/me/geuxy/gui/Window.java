package me.geuxy.gui;

import me.geuxy.Main;
import me.geuxy.util.OSUtil;
import me.geuxy.util.ProjectInfo;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Window extends JFrame implements ProjectInfo {

    private final Button installButton;
    private final Button repairButton;
    private final Button uninstallButton;

    private final JPanel homePanel;

    public Window() {
        this.setTitle(name);
        this.setSize(650, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Image image = new ImageIcon(ClassLoader.getSystemResource("assets/banner.jpg")).getImage().getScaledInstance((int) (getHeight() * 0.6), getHeight(), Image.SCALE_SMOOTH);
        JLabel banner = new JLabel(new ImageIcon(image));
        this.add(banner, BorderLayout.WEST);

        JLabel info = new JLabel(
            "<html>" +
            "<h1>" + name + "</h1>" +
            "<br/>" +
            "Install - Downloads libraries" +
            "<br/>" +
            "Update - Updates libraries" +
            "<br/>" +
            "Uninstall - Deletes libraries" +
            "</html>", JLabel.CENTER
        );
        info.setFont(info.getFont().deriveFont(15F));
        this.add(info);

        this.homePanel = new JPanel();
        this.homePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.homePanel.setBackground(homePanel.getBackground().darker());
        Dimension buttonSize = new Dimension(100, 26);

        this.installButton = new Button("Install");
        this.installButton.setPreferredSize(buttonSize);
        this.installButton.addActionListener(e -> {
            if(new File(OSUtil.getOS().getDirectory()).exists()) {
                JOptionPane.showMessageDialog(null, "Directory already exists");
            } else {
                if(!Main.INSTANCE.getLibraryManager().getDirectory().exists()) {
                    Main.INSTANCE.getLibraryManager().getDirectory().mkdir();
                }

                Main.INSTANCE.getLibraryManager().updateLibraries();
                JOptionPane.showMessageDialog(null, "Installed files");
            }
        });

        this.repairButton = new Button("Update");
        this.repairButton.setPreferredSize(buttonSize);
        this.repairButton.addActionListener(e -> {
            if(new File(OSUtil.getOS().getDirectory()).exists()) {
                Main.INSTANCE.getLibraryManager().updateLibraries();
                JOptionPane.showMessageDialog(null, "Finished updating");

            } else {
                JOptionPane.showMessageDialog(null, "Not installed");
            }
        });

        this.uninstallButton = new Button("Uninstall");
        this.uninstallButton.setPreferredSize(buttonSize);
        this.uninstallButton.addActionListener(e -> {
            File directory = Main.INSTANCE.getLibraryManager().getDirectory();

            if(directory.exists()) {
                for(File library : directory.listFiles()) {
                    library.delete();
                }
                directory.delete();
                JOptionPane.showMessageDialog(null, "Removed libraries");

            } else {
                JOptionPane.showMessageDialog(null, "Not installed");
            }
        });

        this.homePanel.add(installButton);
        this.homePanel.add(repairButton);
        this.homePanel.add(uninstallButton);

        this.add(homePanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
