package com.example.demo.controller;

import com.example.demo.bean.product.NewProductBean;
import com.example.demo.bean.product.UpdateProductBean;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@RestController
@RequestMapping(value = "/v1/product")
@Api(value = "login", description = "Login API")
public class ProductController extends AbstractRestHandler implements Serializable {

    private static final long serialVersionUID = 1470727646662631060L;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show All Product.", notes = "แสดงโปรดักทั้งหมด")
    public @ResponseBody ResponseEntity<?> showAllProduct(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            ArrayNode responseBean = productService.showAllProduct();
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Show Product By id.", notes = "แสดงโปรดักทด้วยไอดี")
    public @ResponseBody ResponseEntity<?> showProductById(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            ObjectNode responseBean = productService.showProductById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "New Product.", notes = "สร้างโปรดัก")
    public @ResponseBody ResponseEntity<?> addNewProduct(
            @RequestBody NewProductBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            ObjectNode responseBean = productService.addNewProduct(body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Product.", notes = "อัพเดทโปรดัก")
    public @ResponseBody ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductBean body,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            ObjectNode responseBean = productService.updateProduct(id, body);
            return ResponseEntity.status(HttpStatus.OK).body(responseBean);
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Product.", notes = "ลบโปรดัก")
    public @ResponseBody ResponseEntity<?> deleteProduct(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        log.info("login start");
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseSuccessMessage());
        } catch (ResourceNotFoundException e) {
            log.error("exception ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
