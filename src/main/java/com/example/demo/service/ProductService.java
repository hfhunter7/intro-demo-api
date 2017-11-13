package com.example.demo.service;

import com.example.demo.bean.product.NewProductBean;
import com.example.demo.bean.product.UpdateProductBean;
import com.example.demo.domain.Products;
import com.example.demo.exception.DataFormatException;
import com.example.demo.repository.impl.ProductsRepositoryService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductsRepositoryService productsRepositoryService;

    public ObjectNode addNewProduct(NewProductBean body) {
        Products product = new Products();
        product.setTitle(body.getTitle());
        product.setDescription(body.getDescription());
        product.setLogo_url(body.getLogo_url());

        try {
            product = productsRepositoryService.save(product);

            return createProductDetailJson(product);
        } catch (DataFormatException e) {
            throw new DataFormatException("create product fail");
        }
    }

    private ObjectNode createProductDetailJson(Products product) {
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        jsonNodes.put("id", product.getId());
        jsonNodes.put("title", product.getTitle());
        jsonNodes.put("description", product.getDescription());
        jsonNodes.put("logo_url", product.getLogo_url());

        return jsonNodes;
    }

    public ArrayNode showAllProduct() {
        List<Products> products = productsRepositoryService.findAll();

        ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
        if (products.size() > 0) {
            for (Products product : products) {
                arrayNode.add(createProductDetailJson(product));
            }
        }

        return arrayNode;
    }

    public ObjectNode showProductById(Long id) {
        Products product = productsRepositoryService.find(id);
        ObjectNode jsonNodes = new ObjectNode(JsonNodeFactory.instance);
        if (product != null) {
            jsonNodes = createProductDetailJson(product);
        }

        return jsonNodes;
    }

    public ObjectNode updateProduct(Long id, UpdateProductBean body) {
        Products product = productsRepositoryService.find(id);

        if (product != null) {
            try {
                product.setTitle(body.getTitle());
                product.setDescription(body.getDescription());
                product.setLogo_url(body.getLogo_url());
                productsRepositoryService.update(product);

                return createProductDetailJson(product);
            } catch (DataFormatException e) {
                throw new DataFormatException("update product fail.");
            }
        } else {
            throw new DataFormatException("invalid product id");
        }
    }

    public void deleteProduct(Long id) {
        Products product = productsRepositoryService.find(id);

        if (product != null) {
            try {
                productsRepositoryService.delete(id);
            } catch (DataFormatException e) {
                throw new DataFormatException("update product fail.");
            }
        } else {
            throw new DataFormatException("invalid product id");
        }
    }
}
