/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.group2.studentportal.frontend;

import com.group2.studentportal.backend.dao.StudentDAO;
import com.group2.studentportal.backend.models.Student;
import java.awt.Color;
import java.sql.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author mcsti
 */
public class Student_AV_EDIT extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Student_AV_EDIT.class.getName());
    private String currentStudentId; // To track which student we are editing

    /**
     * Creates new form Student_AV_Edit
     */
    public Student_AV_EDIT(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupDropdowns();
    }

    // LOGIC

    private void setupDropdowns() {
        jComboBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other" }));
        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        jComboBoxCY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
    }

    public void populateData(Student s) {
        this.currentStudentId = s.getStudentNo();

        // INHERIT DATA
        jTextFieldStudentNumber.setText(s.getStudentNo());
        jTextFieldStudentNumber.setEditable(false); // ID cannot be changed
        jTextFieldStudentNumber.setForeground(Color.GRAY); // Visual indication

        jTextFieldCourseCode.setText(s.getCourseCode());
        jTextFieldLastName.setText(s.getLastName());
        jTextFieldFirstName.setText(s.getFirstName());
        jTextFieldEmail.setText(s.getEmail());
        jTextFieldCP.setText(s.getContactNo());
        jTextFieldAddress.setText(s.getAddress());

        // Password handling (Pre-fill or leave blank logic can go here)
        jTextFieldPassword.setText(s.getPassword());
        jTextFieldConfirmPassword.setText(s.getPassword());

        if (s.getBirthday() != null) {
            jTextFieldBirthday.setText(s.getBirthday().toString());
        }

        // Set Dropdowns
        if(s.getGender() != null) jComboBoxGender.setSelectedItem(s.getGender());
        if(s.getStatus() != null) jComboBoxStatus.setSelectedItem(s.getStatus());
        if(s.getYearLevel() != null) jComboBoxCY.setSelectedItem(s.getYearLevel());
    }

    private boolean validateInput() {
        if (jTextFieldLastName.getText().trim().isEmpty() || jTextFieldFirstName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name fields are required.");
            return false;
        }
        if (!jTextFieldPassword.getText().equals(jTextFieldConfirmPassword.getText())) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return false;
        }
        return true;
    }

    // BUTTON ACTIONS

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // SUBMIT / UPDATE
        if (!validateInput()) return;

        Student s = new Student();
        s.setStudentNo(currentStudentId);
        s.setLastName(jTextFieldLastName.getText());
        s.setFirstName(jTextFieldFirstName.getText());
        s.setEmail(jTextFieldEmail.getText());
        s.setPassword(jTextFieldPassword.getText());
        s.setContactNo(jTextFieldCP.getText());
        s.setAddress(jTextFieldAddress.getText());
        s.setCourseCode(jTextFieldCourseCode.getText());

        // Safe Dropdown Retrieval
        if (jComboBoxGender.getSelectedItem() != null) s.setGender(jComboBoxGender.getSelectedItem().toString());
        if (jComboBoxStatus.getSelectedItem() != null) s.setStatus(jComboBoxStatus.getSelectedItem().toString());
        if (jComboBoxCY.getSelectedItem() != null) s.setYearLevel(jComboBoxCY.getSelectedItem().toString());

        try {
            s.setBirthday(Date.valueOf(jTextFieldBirthday.getText()));
        } catch (Exception e) {
            s.setBirthday(new Date(System.currentTimeMillis()));
        }

        StudentDAO dao = new StudentDAO();
        if (dao.updateStudent(s)) {
            JOptionPane.showMessageDialog(this, "Student Updated Successfully!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Update Failed.");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // CANCEL
        this.dispose();
    }

    // MOUSE EVENTS

    private void jTextFieldStudentNumberMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldCourseCodeMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldLastNameMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldFirstNameMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldPasswordMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldConfirmPasswordMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldEmailMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldCPMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldAddressMouseClicked(java.awt.event.MouseEvent evt) {}
    private void jTextFieldBirthdayMouseClicked(java.awt.event.MouseEvent evt) {}


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
        jTextFieldStudentNumber = new javax.swing.JTextField();
        jTextFieldCourseCode = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldFirstName = new javax.swing.JTextField();
        jTextFieldPassword = new javax.swing.JTextField();
        jTextFieldConfirmPassword = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldCP = new javax.swing.JTextField();
        jTextFieldAddress = new javax.swing.JTextField();
        jComboBoxGender = new javax.swing.JComboBox<>();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jComboBoxCY = new javax.swing.JComboBox<>();
        jTextFieldBirthday = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 600));

        jLabel1.setFont(new java.awt.Font("Inter", 1, 36)); // NOI18N
        jLabel1.setText("Edit PLM Student");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jTextFieldStudentNumber.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldStudentNumber.setText("Enter Student Number");
        jTextFieldStudentNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldStudentNumberMouseClicked(evt);
            }
        });

        jTextFieldCourseCode.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldCourseCode.setText("Course Code");
        jTextFieldCourseCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldCourseCodeMouseClicked(evt);
            }
        });

        jTextFieldLastName.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldLastName.setText("Enter Last Name");
        jTextFieldLastName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldLastNameMouseClicked(evt);
            }
        });

        jTextFieldFirstName.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldFirstName.setText("Enter First Name");
        jTextFieldFirstName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldFirstNameMouseClicked(evt);
            }
        });

        jTextFieldPassword.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldPassword.setText("Enter Password");
        jTextFieldPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldPasswordMouseClicked(evt);
            }
        });

        jTextFieldConfirmPassword.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldConfirmPassword.setText("Confirm Password");
        jTextFieldConfirmPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldConfirmPasswordMouseClicked(evt);
            }
        });

        jTextFieldEmail.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldEmail.setText("Enter Email");
        jTextFieldEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldEmailMouseClicked(evt);
            }
        });

        jTextFieldCP.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldCP.setText("Enter Cellphone Number");
        jTextFieldCP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldCPMouseClicked(evt);
            }
        });

        jTextFieldAddress.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldAddress.setText("Enter Address");
        jTextFieldAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldAddressMouseClicked(evt);
            }
        });

        jComboBoxGender.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jComboBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxStatus.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jComboBoxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxCY.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jComboBoxCY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextFieldBirthday.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        jTextFieldBirthday.setText("Enter Birthday (MM/DD/YYYY)");
        jTextFieldBirthday.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldBirthdayMouseClicked(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(136, 136, 136)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(55, 55, 55)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jTextFieldStudentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(28, 28, 28)
                                                                        .addComponent(jTextFieldCourseCode, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jTextFieldConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jComboBoxGender, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jComboBoxCY, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jTextFieldCP, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addComponent(jTextFieldBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(173, 173, 173)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(44, 44, 44)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldStudentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldCourseCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldCP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBoxGender, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBoxCY, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(79, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(Student_AV_EDIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student_AV_EDIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student_AV_EDIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student_AV_EDIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Student_AV_EDIT dialog = new Student_AV_EDIT(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> jComboBoxCY;
    private javax.swing.JComboBox<String> jComboBoxGender;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldBirthday;
    private javax.swing.JTextField jTextFieldCP;
    private javax.swing.JTextField jTextFieldConfirmPassword;
    private javax.swing.JTextField jTextFieldCourseCode;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldStudentNumber;
    // End of variables declaration                   
}