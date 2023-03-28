package com.quill;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QuillExport {

    public static void main(String[] args) throws IOException {
        // Specify the URL to export
        String url = "https://www.quill.com/hanging-file-folders/cbk/122567.html";
        
        // Load the page using Jsoup
        Document document = Jsoup.connect(url).get();

        // Select the product elements from the page
  
        Elements productsAL = document.select(".row,#search_result_items");
        // Initialize a list to hold the product details
        List<Product> productList = new ArrayList<Product>();
        
        // Loop through the product elements and extract the details
        for (Element product : productsAL) {
            String name = product.select(" .search-product-name").text();
            String price = product.select("div.pricing-wrap").text();
            String sku = product.attr("[sku]");
            String model = product.attr("[data-part-number]");
            String category = product.select(".d-inline").text();
            String description = product.select("div.mb-1").text();
           System.out.println("name:"+name+"price:"+price+" sku:"+ sku+"category: "+category);
            // Create a new product object and add it to the list
            Product newProduct = new Product( name,price,sku,model,category,description);
            productList.add(newProduct);
        }
        
        
        
        // Create a new CSV file to export the data to
        FileWriter cw = new FileWriter("staples_products.csv");
        
        // Write the headers to the CSV file
        String[] headers = {"Product Name", "Product Price", "Item Number/SKU/Product Code", "Model Number", "Product Category", "Product Description"};
        cw.write(String.join(",", headers) + "\n");
        
        // Write the top 10 products to the CSV file
        for (int i = 0; i< productList.size(); i++) {
            Product product = productList.get(i);
            cw.write(product.toCsvString() + "\n");
         
        }
        
        // Close the CSV writer
        cw.close();
        
        System.out.println("Product details exported to staples_products.csv");
    }
}
