package com.infy.catalyst.otsc.service;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
@Service
public class TemplateService {

    private static final String DIRECTORY = "static/content/otsc-templates/";

    // template_type = {offers, products, services,resources}
    // result is ArrayList<HashMap<String, String>>
    // i.e., list of <template_name, template_filename>
    public List<Template> getTemplates(String template_type) {
      List<Template> templates = new ArrayList<Template>();
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      ClassLoader cl = this.getClass().getClassLoader(); 
      ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
      Resource[] resources = null;
      try {
        String resolver_search_path = "classpath*:" + DIRECTORY + template_type + "/*.yaml";
        System.out.println("searching in: " + resolver_search_path);
        resources = resolver.getResources(resolver_search_path);
        System.out.println("got resources:" + resources);
        if(resources != null) { System.out.println("size:" + resources.length); }
        templates = Arrays.stream(resources).map( res -> {
          try {
            String template_filename = res.getFilename();
            InputStream template_is = res.getInputStream();
            JsonNode node = mapper.readTree(template_is);
            String template_name = node.get("template_description").asText();
            int idx = template_name.indexOf("\n");
            template_name = template_name.substring(0, idx < 0 ? template_name.length() : idx);
            System.out.println("template_name = " + template_name);
            System.out.println("template_filename = " + template_filename);
            return new Template(template_name, template_filename);
          } catch(IOException ioe) {
            System.out.println("exception in lambda" + ioe);
            return null;
          }
        }).collect(toList());
      } catch(Exception e) {
          System.out.println("Exception: " + e);
      }

      return templates;
    }

    public List<Template> getResourceTemplates(String path) {
      return getTemplates("resources");
    }

    public List<Template> getOfferTemplates(String path) {
      return getTemplates("offers");
    }

    public List<Template> getProductTemplates(String path) {
      return getTemplates("products");
    }

    public List<Template> getServiceTemplates(String path) {
        return getTemplates("services");
    }
    
    public List<Template> getBundleOfferTemplates(String path) {
        return getTemplates("bundle-offers");
    }
    
    public class Template {
      String _name;
      String _value;

      Template(String name, String value) {
        _name = name;
        _value = value;
      }

      public String getName() {return _name;}
      public String getValue() {return _value;}

    }

}
