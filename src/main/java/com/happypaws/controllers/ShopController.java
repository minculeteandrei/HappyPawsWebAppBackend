package com.happypaws.controllers;

import com.happypaws.domain.Product;
import com.happypaws.services.ProductService;
import com.happypaws.services.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ProductService productService;
    private final StorageService storageService;

    public ShopController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping
    public String getShopPage(Model m){
        m.addAttribute("products", productService.findAll());
        return "shop";
    }

    @PostMapping
    private String addProduct(@ModelAttribute("product")Product product, @RequestParam("file")MultipartFile image, Model m){
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            storageService.save(image, fileName);
            product.setImage(fileName);
            productService.save(product);
        } catch (IOException e) {
            e.printStackTrace();
            m.addAttribute("error", true);
            return "admin";
        }
        return "redirect:/shop";
    }

    @GetMapping("{pid}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("pid")Long pid){
        HttpHeaders headers = new HttpHeaders();
        Product p = productService.findProductById(pid);
        byte[] file = storageService.load("static/photos/products/" + p.getImage());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ p.getImage() + "\"").body(file);
    }
}
