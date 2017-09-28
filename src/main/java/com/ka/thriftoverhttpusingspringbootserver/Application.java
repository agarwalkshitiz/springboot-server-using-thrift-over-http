package com.ka.thriftoverhttpusingspringbootserver;

import javax.servlet.Servlet;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.oyo.platform.logger.Level;
import com.oyo.platform.logger.Logger;
import com.oyo.platform.thriftextension.TOyoServlet;
import com.oyo.testapp.thriftoverhttpusingspringboot.api.CalculationService;

@SpringBootApplication
public class Application {

  Logger logger = new Logger("TestApp", Application.class);

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.run(args);
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {
    logger.log(Level.FATAL, "Fatal : Registering Bean");
    logger.log(Level.ERROR, "Error : Registering Bean");
    logger.log(Level.WARN, "Warning : Registering Bean");
    logger.log(Level.INFO, "Info : Registering Bean");
    logger.log(Level.DEBUG, "Debug : Registering Bean");
    TProcessor tProcessor = new CalculationService.Processor<CalculationServiceHandler>(
        new CalculationServiceHandler());
    Servlet servlet = new TOyoServlet(tProcessor, new TJSONProtocol.Factory());
    return new ServletRegistrationBean(servlet, "/calculator/*");
  }
}
