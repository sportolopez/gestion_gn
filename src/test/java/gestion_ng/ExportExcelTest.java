package gestion_ng;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import com.sporto.ng.gestion_gn.utils.JTableToExcel;

public class ExportExcelTest {

	@Test
	void test() {
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Languages");
		tableModel.insertRow(0, new Object[] { "CSS" });
		tableModel.insertRow(0, new Object[] { "HTML5" });
		tableModel.insertRow(0, new Object[] { "JavaScript" });
		tableModel.insertRow(0, new Object[] { "jQuery" });
		tableModel.insertRow(0, new Object[] { "AngularJS" });
		tableModel.insertRow(tableModel.getRowCount(), new Object[] { "ExpressJS" });

		exportDataToExcel(tableModel);
	}

	@Test
	void test2() {
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Languages");
		tableModel.insertRow(0, new Object[] { "CSS" });
		tableModel.insertRow(0, new Object[] { "HTML5" });
		tableModel.insertRow(0, new Object[] { "JavaScript" });
		tableModel.insertRow(0, new Object[] { "jQuery" });
		tableModel.insertRow(0, new Object[] { "AngularJS" });
		tableModel.insertRow(tableModel.getRowCount(), new Object[] { "ExpressJS" });

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		jfc.setDialogTitle("Guardar como..");
		jfc.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xlsx")) {
			    file = new File(file.toString() + ".xlsx");
			}
			String heading = "Stock";
			JTableToExcel.export(file, heading, "", table,5);
		}

	}

	public void exportDataToExcel(DefaultTableModel model) {

		// First Download Apache POI Library For Dealing with excel files.
		// Then add the library to the current project
		FileOutputStream excelFos = null;
		XSSFWorkbook excelJTableExport = null;
		BufferedOutputStream excelBos = null;
		try {

			// Choosing Saving Location
			// Set default location to C:\Users\Authentic\Desktop or your preferred location
			JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\sebap\\Desktop");
			// Dialog box title
			excelFileChooser.setDialogTitle("Save As ..");
			// Filter only xls, xlsx, xlsm files
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");
			// Setting extension for selected file names
			excelFileChooser.setFileFilter(fnef);
			int chooser = excelFileChooser.showSaveDialog(null);
			// Check if save button has been clicked
			if (chooser == JFileChooser.APPROVE_OPTION) {
				// If button is clicked execute this code
				excelJTableExport = new XSSFWorkbook();
				XSSFSheet excelSheet = excelJTableExport.createSheet("Jtable Export");
				// Loop through the jtable columns and rows to get its values
				for (int i = 0; i < model.getRowCount(); i++) {
					XSSFRow excelRow = excelSheet.createRow(i);
					for (int j = 0; j < model.getColumnCount(); j++) {
						XSSFCell excelCell = excelRow.createCell(j);

						// Change the image column to output image path
						// Fourth column contains images
//                        if (j == model.getColumnCount() - 1) {
//                            JLabel excelJL = (JLabel) model.getValueAt(i, j);
//                            ImageIcon excelImageIcon = (ImageIcon) excelJL.getIcon();
//                            //Image Name Is Stored In ImageIcons Description first set it when saving image in the jtable cell and then retrieve it.
//                            //excelImagePath = excelImageIcon.getDescription();
//                        }
// 
						String string = model.getValueAt(i, j).toString();
						System.out.println("Secrea" + string);
						excelCell.setCellValue(string);
						if (excelCell.getColumnIndex() == model.getColumnCount() - 1) {
							// excelCell.setCellValue(excelImagePath);
						}
					}
				}
				excelFos = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
				excelBos = new BufferedOutputStream(excelFos);
				excelJTableExport.write(excelBos);

				JOptionPane.showMessageDialog(null, "Exported Successfully");
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println();
			JOptionPane.showMessageDialog(null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex);
		} finally {
			try {
				if (excelFos != null) {
					excelFos.close();
				}
				if (excelBos != null) {
					excelBos.close();
				}
				if (excelJTableExport != null) {
					excelJTableExport.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, ex);
			}
		}
	}
}
