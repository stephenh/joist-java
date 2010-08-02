package @projectName@.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import joist.converter.ConverterRegistry;
import joist.domain.uow.UoW;
import joist.domain.util.CodeToStringConverter;
import joist.domain.util.CodeToTextStringConverter;
import joist.domain.util.DomainObjectToStringConverter;
import joist.domain.util.DomainObjectToTextStringConverter;
import joist.web.WebServlet;
import joist.web.WebConfig;
import @projectName@.Registry;

@SuppressWarnings("serial")
public class @ProjectName@Servlet extends WebServlet {

    @Override
    public void init() {
        Registry.start();
    }

    @Override
    public void destroy() {
        Registry.stop();
    }

    @Override
    protected WebConfig createWebConfig() {
        return new WebConfig("@projectName@.webapp.pages") {
            protected ConverterRegistry createUrlConverterRegistry() {
                ConverterRegistry r = super.createUrlConverterRegistry();
                r.addConverter(new DomainObjectToStringConverter());
                r.addConverter(new CodeToStringConverter());
                return r;
            }

            protected ConverterRegistry createTextConverterRegistry() {
                ConverterRegistry r = super.createUrlConverterRegistry();
                r.addConverter(new DomainObjectToTextStringConverter());
                r.addConverter(new CodeToTextStringConverter());
                return r;
            }
        };
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UoW.open();
        try {
            super.service(request, response);
        } finally {
            UoW.close();
        }
    }

}
