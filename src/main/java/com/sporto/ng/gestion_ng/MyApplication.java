package com.sporto.ng.gestion_ng;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.sporto.ng.gestion_ng.dao.ProductoDao;
import com.sporto.ng.gestion_ng.model.Producto;
import com.sporto.ng.gestion_ng.view.HomeForm;

@SpringBootApplication
@ComponentScan
public class MyApplication {

    public static void main(String[] args) {
    ApplicationContext context = new SpringApplicationBuilder(MyApplication.class)
    .headless(false).run(args);
    HomeForm a = context.getBean(HomeForm.class);
    
    ProductoDao dao = context.getBean(ProductoDao.class);
    Iterable<Producto> findAll = dao.findAll();
    a.setVisible(true);
    }
}