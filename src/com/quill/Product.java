package com.quill;

public class Product {
	  String name;
	    String price;
	    String sku;
	    String model;
	    String category;
	    String description;
	    
	    public Product(String name, String price, String sku, String model, String category, String description) {
	        this.name = name;
	        this.price = price;
	        this.sku = sku;
	        this.model = model;
	        this.category = category;
	        this.description = description;
	    }
	    
	    public String toCsvString() {
	        return "\"" + name + "\",\"" + price + "\",\"" + sku + "\",\"" + model + "\",\"" + category + "\",\"" + description + "\"";
	    }
}
