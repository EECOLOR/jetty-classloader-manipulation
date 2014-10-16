package org.eclipse.jetty.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.JarResource;
import java.io.File;

public class EmbedWithOverridenServlet
{
    public static void main(String[] args) throws Exception
    {
        int port = 8080;
        Server server = new Server(port);

        String jardir = "../example-webapp/target/example-webapp-1-SNAPSHOT.jar";
        String descriptorFile = "../example-webapp/src/main/webapp/WEB-INF/web.xml";

        WebAppContext webapp = new WebAppContext();
        //webapp.setWar(wardir);
        WebAppClassLoader webAppClassLoader = new WebAppClassLoader(webapp);
        webAppClassLoader.addClassPath(jardir);
        webapp.setClassLoader(webAppClassLoader);
        webapp.setResourceBase(".");
        webapp.setDescriptor(descriptorFile);
        webapp.setContextPath("/");

        if(Boolean.getBoolean("use.server.class"))
        {
            // webapp cannot change / replace these classes / use server classes
            webapp.addSystemClass("com.company.foo.");
            
            // don't hide server classes from webapps (allow webapp to use ones from system classloader)
            webapp.addServerClass("-com.company.foo.");
        }
        
        server.setHandler(webapp);
        
        try
        {
            server.start();
            server.dumpStdErr();
            makeWebRequest(new URI("http://localhost:8080/test"));
        }
        finally
        {
            server.stop();
        }
    }

    private static void makeWebRequest(URI uri) throws IOException
    {
        HttpURLConnection conn = (HttpURLConnection)uri.toURL().openConnection();
        conn.setAllowUserInteraction(false);
        
        int status = conn.getResponseCode();
        System.out.printf("Response Status: %d%n", status);
        
        if(status != 200)
        {
            System.out.printf("ERROR: %s%n", conn.getResponseMessage());
            return;
        }
        
        try(InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(reader))
        {
            System.out.printf("Response Content Type: %s%n", conn.getHeaderField("Content-Type"));
            String line;
            while ((line = buf.readLine()) != null)
            {
                System.out.printf("[resp] %s%n",line);
            }
        }
    }
}
