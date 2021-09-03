package com.sporto.ng.gestion_ng;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.sporto.ng.gestion_ng.view.HomeForm;
import com.sporto.ng.gestion_ng.view.Splash;

@SpringBootApplication
@ComponentScan
@PropertySource(ignoreResourceNotFound = false, value = "classpath:config.properties")
public class GestionNG {
	public static void main(String[] args) {
		Splash s = new Splash();
		s.setVisible(true);
		System.out.println("Inicio");
		ApplicationContext context = new SpringApplicationBuilder(GestionNG.class).headless(false).run(args);
		HomeForm a = context.getBean(HomeForm.class);
		a.setVisible(true);
		s.setVisible(false);
	}

}