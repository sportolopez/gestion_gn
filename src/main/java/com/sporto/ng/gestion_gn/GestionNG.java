package com.sporto.ng.gestion_gn;

import javax.swing.JOptionPane;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.sporto.ng.gestion_gn.view.HomeForm;
import com.sporto.ng.gestion_gn.view.Splash;

@SpringBootApplication
@ComponentScan
@PropertySource(ignoreResourceNotFound = false, value = "classpath:config.properties")
public class GestionNG {
	public static void main(String[] args) {
		Splash s = new Splash();
		s.setVisible(true);
		System.out.println("Inicio");
		try {
			ApplicationContext context = new SpringApplicationBuilder(GestionNG.class).headless(false).run(args);
			HomeForm a = context.getBean(HomeForm.class);
			a.setVisible(true);
		} catch (BeansException e) {
			JOptionPane.showMessageDialog(s,
				    e.getCause().getMessage(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			s.setVisible(false);
			s.dispose();
		}
		s.setVisible(false);
	}

}