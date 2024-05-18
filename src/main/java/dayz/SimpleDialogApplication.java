package dayz;

import dayz.ui.DayZGUI;
import dayz.ui.EntityGUI;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

/**
 * This is the Spring Boot Application class.  This is where we make sure we're NOT running in Headless mode and that
 * the WebApplicationType is set to NONE.
 */
@SpringBootApplication
public class SimpleDialogApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context  =
                new SpringApplicationBuilder(SimpleDialogApplication.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        EventQueue.invokeLater(()->{
            //obtenemos el objeto form a través de Spring
            /*EntityGUI entityUI = context.getBean(EntityGUI.class);
            entityUI.setVisible(true);*/
            DayZGUI dayZGUI = context.getBean(DayZGUI.class);
            dayZGUI.setVisible(true);
        });
    }
}
