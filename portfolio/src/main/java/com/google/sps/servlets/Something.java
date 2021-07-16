//package example;
package com.google.sps.servlets;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.tofu.SoyTofu;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Something {
  public static void Render() {
    SoyFileSet sfs = SoyFileSet
        .builder()
        .add(Something.class.getResource("index.soy"))
        .build();

    // helloWorld
    SoyTofu tofu = sfs.compileToTofu();
    //System.out.println(
        //tofu.newRenderer("com.google.sps.index.index").render());

    // For convenience, create another SoyTofu object that has a
    // namespace specified, so you can pass partial template names to
    // the newRenderer() method.
    SoyTofu simpleTofu = tofu.forNamespace("com.google.sps.index");



    // helloName
    Map<String, Object> data = new HashMap<>();
    data.put("companyName", "companyName");
    data.put("workBalance", "yes");
    data.put("salary", "no");
    data.put("review", "sure");
    data.put("role", "roles");
    data.put("rating", "ratingzzz");
    
    System.out.println("-----------------");

    System.out.println(
        simpleTofu.newRenderer(".index").setData(data).render());


    


    

    

    // helloNames
    /*List<String> additionalNames = Arrays.asList("Bob", "Cid", "Dee");
    data.put("additionalNames", additionalNames);
    System.out.println("-----------------");
    System.out.println(
        simpleTofu.newRenderer(".helloNames").setData(data).render());*/
  }
}