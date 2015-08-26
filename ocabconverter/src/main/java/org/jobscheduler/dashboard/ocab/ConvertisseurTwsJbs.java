package org.jobscheduler.dashboard.ocab;


import java.awt.Color;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jobscheduler.dashboard.jobdefinition.xml.Job;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
Bouton connexion jButton1
champs ine :jTexField1
champ mot de passe :jPasswordField1
champ pour afficher mots de passe incorrete: jLabel4

*/

/**
 *
 * @author puls2
 */
public class ConvertisseurTwsJbs extends javax.swing.JFrame {


    /**
     * Creates new form SocialNetworkIHM
     */
    public ConvertisseurTwsJbs() throws RemoteException {
        
        initComponents();
        //this.getContentPane().setBackground(new Color(247,247,247));
        this.getContentPane().setBackground(Color.WHITE);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Excel", "xlsm","xlss");
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.setFileFilter(filter);
        jFileChooser1.setMultiSelectionEnabled(true);
        jProgressBar1.setMaximum(100);
       
jLabel4.setVisible(false);
        
    }
     
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jRadioButton1 = new javax.swing.JRadioButton();
        File f = new File (System.getProperty("user.dir")+"/");
        jFileChooser1.setCurrentDirectory(f);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Convertisseur TWS >>>>> JobScheduler");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);

        jButton1.setBackground(new java.awt.Color(59, 89, 152));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 204));
        jButton1.setText("Convertir");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        jLabel1.setText("Selectionner un fichier excel");

        jLabel4.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 0));
        jLabel4.setText("Echec conversion");

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Copyright © 2015 Air France . All rights reserved.");

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(59, 89, 152));
        jLabel7.setText("Convertisseur TWS - Job Scheduler");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("url.jpg"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Lucida Sans", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Logs");

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jTextPane1);

        jRadioButton1.setText("Mode test");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jRadioButton1))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(326, 326, 326))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(648, 648, 648))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(93, 93, 93))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton1)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel3)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//convertir
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    	if(jFileChooser1.getSelectedFiles().length!=0)
    	{
    		File[] f=jFileChooser1.getSelectedFiles();
    		for(int p=0; p<f.length;p++)
    		{
    		TraiterExcel(f[p].getAbsolutePath());
    		}   	
    	}
    	else
    	{
    		
    		 File di = new File(jFileChooser1.getCurrentDirectory().getAbsolutePath());
    		
    		FileFilter flft=new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.isDirectory()) return false;
					else if(pathname.getName().endsWith(".xlsm")||pathname.getName().endsWith(".xlsx")) return true;
						else return false;
				}
			};
			final File fl2[] = di.listFiles(flft);
			
			jLabel4.setText("ATTENTE DES FICHIERS");
			jLabel4.setVisible(true);
			

		SwingWorker sw = new SwingWorker(){

		      protected Object doInBackground() throws Exception {

		    	  for(int i=0;i<fl2.length;i++)
	    			{
	    				TraiterExcel(fl2[i].getAbsolutePath());
	    				jLabel4.setText(fl2[i].getName());
	    			}
		    	 
		    	  FileFilter flft2 =new FileFilter() {
						
						@Override
						public boolean accept(File pathname) {
							Date dt=new Date();
							
							if(pathname.isDirectory()&& (dt.getTime()-pathname.lastModified()<=1800000)) return true;
								else return false;
							
						}
					};
					 File di2 = new File(System.getProperty("user.dir"));
					File taille[]=di2.listFiles(flft2);
					
					jLabel4.setText("Traitement terminé, Fichier en entrée :  " +fl2.length+", Dossier récent (-30 minutes) dans le répertoire courant :"+taille.length);
		        return null;

		      }  

		    };

		    //On lance le SwingWorker

		    sw.execute();
		

    	}
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public void TraiterExcel(String absolutePath)
    {jProgressBar1.setValue(0);
    
    	try {
 			ExcelReader exrd = new ExcelReader(absolutePath,System.getProperty("user.dir")+"/", this,modeTest);
 			jProgressBar1.setValue(20);
 			if (exrd.treatExcelFile())
 				{
 				
 				int nbFichier=exrd.OutputTest(123);
 				jTextPane1.setText(jTextPane1.getText()+nbFichier+" fichier(s) ont été générés");
 				
 				jTextPane1.setText(jTextPane1.getText()+"\n"+"Operation succeeded");
 				jProgressBar1.setValue(100);
 				sQLHighlight(jTextPane1);
 				}
 			else{
 				jTextPane1.setText(jTextPane1.getText()+"\n"+"Error, conversion failed");
 			    }
 			
 		} catch (Throwable e) {
 			// TODO Auto-generated catch block
 			jTextPane1.setText(jTextPane1.getText()+"\n"+e.getMessage());
 			JOptionPane  jope = null;
	jope.showMessageDialog(null, "Erreur, fichier "+absolutePath+" "+e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
 		}
    }
    
    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        String name;
        name = jFileChooser1.getSelectedFile().getName();
        jLabel4.setText(name);
jLabel4.setVisible(true);
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if(jRadioButton1.isSelected())
        {
            modeTest=true;
        }
        else
        {
             modeTest=false;
        }
                
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    private void colorWords(String[] strsToHighlight,String text,final StyledDocument doc, Color color, boolean bold){
    	 
        for(String strToHL : strsToHighlight){
                Pattern p = Pattern.compile(strToHL);
                Matcher m = p.matcher(text);
     
                while(m.find() == true){                                                
                        MutableAttributeSet attri = new SimpleAttributeSet();
                        StyleConstants.setForeground(attri, color);
                        StyleConstants.setBold(attri, bold);
     
                        final int start = m.start();
                        final int end = m.end();
                        final int length = end-start;
                        final MutableAttributeSet style = attri;
     
                        SwingUtilities.invokeLater(new Runnable(){
                            public void run(){
                                    doc.setCharacterAttributes(start, length, style, true);
                            }
                        });
                }
        }
    }
     
     
    /**
     * Fonction qui recupere le texte d'un TextePane et lance le coloriage des mot a partir de ses tableau de mot 
     * @param c 
     */
    public void sQLHighlight(JTextPane c) {
        String[] strsToHighlightFunction ={"ATTENTION","Modification","Error","null"};
        String[] succes ={"succeeded"};
     
      
        String text = c.getText().replaceAll("\n", " ");
        final StyledDocument doc = c.getStyledDocument();
        final MutableAttributeSet normal= new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.black);
        StyleConstants.setBold(normal, false);
     
        SwingUtilities.invokeLater(new Runnable() {
     
        public void run() {
                doc.setCharacterAttributes(0, doc.getLength(), normal, true);
            }
        });
        colorWords(strsToHighlightFunction, text, doc,  Color.RED, true);
        colorWords(succes, text, doc,  Color.GREEN, true);
       
       
    } 
    public void notification(String st)
    {
    	jTextPane1.setText(jTextPane1.getText()+"\n"+st);
    }
    
    public static void main(String args[]) throws RemoteException {
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
            java.util.logging.Logger.getLogger(ConvertisseurTwsJbs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConvertisseurTwsJbs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConvertisseurTwsJbs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConvertisseurTwsJbs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ConvertisseurTwsJbs().setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(ConvertisseurTwsJbs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    
 
    public void addValueProgressBar(int vlr) {
    	jProgressBar1.setValue(jProgressBar1.getValue()+vlr);
	}

	// End of variables declaration//GEN-END:variables
    boolean modeTest=false;

}
