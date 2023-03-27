package com.staples;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StaplesExport {

    public static void main(String[] args) throws IOException {
        // Specify the URL to export
        String url = "https://www.quill.com/hanging-file-folders/cbk/122567.html";
        
        // Load the page using Jsoup
        Document document = Jsoup.connect(url).get();
        
        // Select the product elements from the page
        Elements products = document.select("div.row");
        
        // Initialize a list to hold the product details
        List<Product> productList = new ArrayList<Product>();
        //
        // Loop through the product elements and extract the details
        for (Element product : products) {
            String name = product.select("[href],[sctype],[onclick]").text();
            String price = product.select("div.pricing-wrap").text();
            String sku = product.attr("[data-sku]");
            String model = product.attr("[data-part-number]");
            String category = product.select(".d-inline").text();
            String description = product.select(".card-text").text();
           System.out.println("name:"+name+"price:"+price+" sku:"+ sku+"category: "+category);
            // Create a new product object and add it to the list
            Product newProduct = new Product( model, category, description, price, sku, name);
            productList.add(newProduct);
        }
        
        // Create a new CSV file to export the data to
        FileWriter csvWriter = new FileWriter("staples_products.csv");
        
        // Write the headers to the CSV file
        String[] headers = {"Product Name", "Product Price", "Item Number/SKU/Product Code", "Model Number", "Product Category", "Product Description"};
        csvWriter.write(String.join(",", headers) + "\n");
        
        // Write the top 10 products to the CSV file
        for (int i = 0; i< productList.size(); i++) {
            Product product = productList.get(i);
            csvWriter.write(product.toCsvString() + "\n");
         
        }
        
        // Close the CSV writer
        csvWriter.close();
        
        System.out.println("Product details exported to staples_products.csv");
    }
}
