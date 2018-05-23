/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exportation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Allan
 */
public class Excel {

    private File ruta_destino = null;
   

    public Excel() {

        
    }

    public void crear_Excel(JTable tabla,String nombreHoja) {

        Colocar_Destino();
        if (ruta_destino != null) {
            //crea el libro 
            HSSFWorkbook libro = new HSSFWorkbook();
            //crea la hoja q tiene toda la informacion
            HSSFSheet hoja = libro.createSheet(nombreHoja);

            HSSFRow filaEncabezados = hoja.createRow(0);
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                HSSFCell celda = filaEncabezados.createCell((short) i);
                HSSFRichTextString texto = new HSSFRichTextString(tabla.getColumnName(i));
                celda.setCellValue(texto);

            }

            for (int i = 0; i < tabla.getRowCount(); i++) {
                HSSFRow fila = hoja.createRow(i + 1);
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    
                    HSSFCell celdaCuerpo = fila.createCell((short) j);
                    celdaCuerpo.setCellValue(tabla.getValueAt(i, j) + "");
                }
            }

            try {
                FileOutputStream archivo = new FileOutputStream(this.ruta_destino + ".xls");
                libro.write(archivo);
                archivo.close();
                 JOptionPane.showMessageDialog(null,"El documento excel se ha creado en "+ ruta_destino);
              
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    private void Colocar_Destino() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo Excel", ".xls", ".XLS");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
        }
    }

}
