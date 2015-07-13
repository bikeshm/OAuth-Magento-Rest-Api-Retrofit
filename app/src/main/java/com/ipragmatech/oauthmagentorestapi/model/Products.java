package com.ipragmatech.oauthmagentorestapi.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by kapiljain on 12/07/15.
 */
@Root(name = "magento_api",strict = false)
public class Products {

    @ElementList(name = "data_item", inline = true, required = false)
    List<Product> product;


    public List<Product> getProduct() { return this.product; }
    public void setProduct(List<Product> _value) { this.product = _value; }


    @Root(name = "data_item")
    public static class Product {

        @Element(name="entity_id", required = false)
        String entityId;


        @Element(name="sku", required = false)
        String sku;


        @Element(name="name", required = false)
        String name;


        @Element(name="image_url", required = false)
        String imageUrl;



        public String getEntityId() { return this.entityId; }
        public void setEntityId(String _value) { this.entityId = _value; }


        public String getSku() { return this.sku; }
        public void setSku(String _value) { this.sku = _value; }


        public String getName() { return this.name; }
        public void setName(String _value) { this.name = _value; }


        public String getImageUrl() { return this.imageUrl; }
        public void setImageUrl(String _value) { this.imageUrl = _value; }


    }
}
