/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package myServer.alice.web.application;

import myServer.alice.web.controller.*;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


public class AliceApplication {


    private TemplateEngine templateEngine;
    private Map<String, ImplALiceController> controllersByURL;


    public AliceApplication(final ServletContext servletContext) {

        super();

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setSuffix(".html");
        // Set template cache TTL to hibernate.cfg.xml hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);


        this.controllersByURL = new HashMap<String, ImplALiceController>();
        this.controllersByURL.put("/", new HomeController());
        this.controllersByURL.put("/list", new TaskListController());
        this.controllersByURL.put("/shop", new ShopPageController());
        this.controllersByURL.put("/settings", new SetiingsPageController());
        this.controllersByURL.put("/login", new LoginController());
    }


    public ImplALiceController resolveControllerForRequest(final HttpServletRequest request) {
        final String path = getRequestPath(request);
        return this.controllersByURL.get(path);
    }


    public ITemplateEngine getTemplateEngine() {
        return this.templateEngine;
    }


    private static String getRequestPath(final HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        final String contextPath = request.getContextPath();

        final int fragmentIndex = requestURI.indexOf(';');
        if (fragmentIndex != -1) {
            requestURI = requestURI.substring(0, fragmentIndex);
        }

        if (requestURI.startsWith(contextPath)) {
            return requestURI.substring(contextPath.length());
        }
        return requestURI;
    }


}
