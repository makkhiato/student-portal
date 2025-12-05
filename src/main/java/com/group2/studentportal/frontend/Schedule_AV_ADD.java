/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.group2.studentportal.frontend;

import com.group2.studentportal.backend.dao.ScheduleDAO;
import com.group2.studentportal.backend.models.Schedule;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author mcsti
 */
public class Schedule_AV_ADD extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Schedule_AV_ADD.class.getName());

    /**
     * Creates new form Schedule_AV_ADD
     */
    public Schedule_AV_ADD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupForm();
    }

    // --- CUSTOM LOGIC START ---

    private void setupForm() {
        // 1. Dropdowns
        // Map "Date" to Day of Week as per requirement
        jComboBoxDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" }));
        jComboBoxSem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Semester", "1", "2", "3" }));

        // 2. Placeholders
        addPlaceholder(jTextScheduleID, "SID001");
        addPlaceholder(jTextSchoolYear, "2024 - 2025");
        addPlaceholder(jTextSubjectCode, "CSC 0212");
        addPlaceholder(jTextSection, "CSC 0212 - 1");
        addPlaceholder(jTextRoom, "GV 301");
        addPlaceholder(jTextTime, "12:00 PM - 2:00 PM");
        addPlaceholder(jTextFaculty, "Dela Cruz, Juan");
    }

    // Helper: Adds visual placeholder behavior (FIXED: Checks Color)
    private void addPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Only clear if it is currently a placeholder (Gray text)
                if (field.getForeground() == Color.GRAY) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // If empty, restore placeholder
                if (field.getText().trim().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    // Helper: Checks if field is valid (FIXED: Checks Color)
    private boolean isPlaceholder(JTextField field) {
        return field.getForeground() == Color.GRAY || field.getText().trim().isEmpty();
    }

    // --- BUTTON ACTIONS ---

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { // Submit
        // 1. Validation
        if (isPlaceholder(jTextScheduleID) ||
                isPlaceholder(jTextSchoolYear) ||
                isPlaceholder(jTextSubjectCode) ||
                isPlaceholder(jTextSection) ||
                isPlaceholder(jTextRoom) ||
                isPlaceholder(jTextTime) ||
                isPlaceholder(jTextFaculty)) {

            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (jComboBoxDate.getSelectedIndex() == 0 || jComboBoxSem.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a Day and Semester.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Create Object
        Schedule s = new Schedule();
        s.setScheduleId(jTextScheduleID.getText());
        s.setSchoolYear(jTextSchoolYear.getText());
        s.setSemester(jComboBoxSem.getSelectedItem().toString());
        s.setSubjectCode(jTextSubjectCode.getText());
        s.setSection(jTextSection.getText());
        s.setDay(jComboBoxDate.getSelectedItem().toString());
        s.setRoom(jTextRoom.getText());
        s.setTimeSlot(jTextTime.getText());
        s.setEmployeeNo(jTextFaculty.getText()); // Storing Name as ID for now based on requirement
        s.setStatus("Active");

        // 3. Database Logic
        ScheduleDAO dao = new ScheduleDAO();

        // Validate Subject Exists
        if (!dao.subjectExists(s.getSubjectCode())) {
            JOptionPane.showMessageDialog(this, "Error: Subject Code '" + s.getSubjectCode() + "' does not exist.", "Invalid Subject", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dao.addSchedule(s)) {
            JOptionPane.showMessageDialog(this, "Schedule Added Successfully!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add schedule. ID might already exist.");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { // Cancel
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jTextScheduleID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextSchoolYear = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jComboBoxSem = new javax.swing.JComboBox<>();
        jComboBoxDate = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jTextSubjectCode = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextSection = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextRoom = new javax.swing.JTextField();
        jTextTime = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextFaculty = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Inter", 1, 36)); // NOI18N
        jLabel1.setText("Add Schedule");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jTextScheduleID.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(53, 103, 38));
        jButton1.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Submit");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(141, 0, 0));
        jButton2.setFont(new java.awt.Font("Inter", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cancel");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel5.setText("Enter Schedule ID:");

        jLabel15.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel15.setText("Enter School Year:");

        jTextSchoolYear.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel18.setText("Select Semester:");

        jComboBoxSem.setBackground(new java.awt.Color(255, 255, 254));
        jComboBoxSem.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jComboBoxSem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3" }));

        jComboBoxDate.setBackground(new java.awt.Color(255, 255, 254));
        jComboBoxDate.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jComboBoxDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Placeholder1", "Placeholder2" }));

        jLabel19.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel19.setText("Select Date:");

        jTextSubjectCode.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel20.setText("Enter Subject Code:");

        jTextSection.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel21.setText("Enter Section:");

        jLabel22.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel22.setText("Enter Room:");

        jTextRoom.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jTextTime.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel23.setText("Enter Time:");

        jLabel24.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel24.setText("Enter Faculty:");

        jTextFaculty.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(171, 171, 171))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jComboBoxDate, javax.swing.GroupLayout.Alignment.LEADING, 0, 220, Short.MAX_VALUE)
                                                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jComboBoxSem, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextTime, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel23)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel15)
                                                                                .addComponent(jTextSchoolYear, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGap(50, 50, 50)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jTextSubjectCode, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel20)
                                                                                .addComponent(jTextSection, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel21)
                                                                                .addComponent(jLabel22)
                                                                                .addComponent(jTextRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel24)
                                                                                .addComponent(jTextFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jTextScheduleID, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(0, 54, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextScheduleID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextSchoolYear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBoxSem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBoxDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(34, 34, 34))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextSubjectCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextSection, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel24)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextFaculty, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Schedule_AV_ADD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Schedule_AV_ADD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Schedule_AV_ADD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Schedule_AV_ADD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Schedule_AV_ADD dialog = new Schedule_AV_ADD(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxDate;
    private javax.swing.JComboBox<String> jComboBoxSem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFaculty;
    private javax.swing.JTextField jTextRoom;
    private javax.swing.JTextField jTextScheduleID;
    private javax.swing.JTextField jTextSchoolYear;
    private javax.swing.JTextField jTextSection;
    private javax.swing.JTextField jTextSubjectCode;
    private javax.swing.JTextField jTextTime;
    // End of variables declaration
}