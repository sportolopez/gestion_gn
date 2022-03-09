package com.sporto.ng.gestion_gn;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.sporto.ng.gestion_gn.config.Constants;
import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.Splash;
import com.sporto.ng.gestion_gn.view.validations.ExcelParseException;

@SpringBootApplication
@ComponentScan
@PropertySource(ignoreResourceNotFound = false, value = "classpath:config.properties")
public class GestionNG {
	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(final Thread t, final Throwable e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				e.printStackTrace();
				if (e instanceof ExcelParseException) {
					JOptionPane.showMessageDialog(new Splash(), e.getMessage(), "Error al procesar el Excel",
							JOptionPane.WARNING_MESSAGE);
				} else
					JOptionPane.showMessageDialog(new Splash(), sw.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		System.out.println("Default locale:" + Locale.getDefault().toString());

		System.out.println("\nSetting default locale to es");
		Locale swedishLocale = new Locale("es", "ES");
		Locale.setDefault(swedishLocale);

		System.out.println("New default locale:" + Locale.getDefault().toString());

		// https://thebadprogrammer.com/swing-uimanager-keys/
		UIManager.put("Button.font", Constants.FUENTE_BUTTON);
		UIManager.put("TableHeader.font", Constants.FUENTE_TABLE_HEADER);
		UIManager.put("Label.font", Constants.FUENTE_LABEL);
		UIManager.put("FileChooser.openButtonText", "Guardar");
		UIManager.put("FileChooser.cancelButtonText", "Cancelar");
		UIManager.put("FileChooser.fileNameLabelText", "Nombre de archivo");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Tipo de archivo");
		UIManager.put("FileChooser.lookInLabelText", "Buscar en");

		System.out.print((double) 3.1415926535);

		Splash s = new Splash();
		s.setVisible(true);
		System.out.println("Inicio");
		try {
			ApplicationContext context = new SpringApplicationBuilder(GestionNG.class).headless(false).run(args);
			HomeForm a = context.getBean(HomeForm.class);
			a.setVisible(true);
		} catch (BeansException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.getCause().printStackTrace(pw);
			e.printStackTrace();
			JOptionPane.showMessageDialog(s, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			s.setVisible(false);
			s.dispose();
		}

		s.setVisible(false);
	}

}