package com.happypaws.restcontrollers;

import com.happypaws.domain.Product;
import com.happypaws.domain.Role;
import com.happypaws.domain.User;
import com.happypaws.services.ProductService;
import com.happypaws.services.StorageService;
import com.happypaws.services.UserService;
import org.apache.tika.Tika;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;
    private final UserService userService;
    private final StorageService storageService;

    public ProductRestController(ProductService productService, UserService userService, StorageService storageService) {
        this.productService = productService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable("prodId") Long id){
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> postProduct(
            @RequestPart Product product,
            @RequestPart MultipartFile image
    ){
        JSONObject result = new JSONObject();
        try {
            if(image.isEmpty()) {
              result.put("error", "Uploaded product image cannot be empty");
              return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
            }
            Tika tika = new Tika();
            String detectedType = tika.detect(image.getBytes());
            if(!detectedType.split("/")[0].equals("image")) {
                result.put("error", "Uploaded file must be one of the formats: .png, .jpeg, .jpg, .webp");
                return new ResponseEntity<>(result.toString(), HttpStatus.BAD_REQUEST);
            }

            product.setImage(image.getOriginalFilename());
            storageService.save(image);
            productService.save(product);

            result.put("success", true);
            return new ResponseEntity<>(result.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "Internal server error");
            return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{prodId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("prodId") Long prodId, @RequestParam String username){
        JSONObject result = new JSONObject();
        try {
            User user = this.userService.findUserByUsername(username);
            if(!user.getRole().equals(Role.ADMIN.getLabel())) {
                result.put("error", "Admin privileges required");
                return new ResponseEntity<>(result.toString(), HttpStatus.FORBIDDEN);
            }
            if(productService.findById(prodId) != null){
                productService.deleteById(prodId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NoSuchElementException e) {
            result.put("error", "User was not found in db");
            return new ResponseEntity<>(result.toString(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{prodId}")
    public ResponseEntity<?> putProduct(@PathVariable("prodId") Long prodId, @RequestBody Product p){
        Product updatedProduct = productService.findById(prodId);
        if(updatedProduct != null){
            updatedProduct.setPrice(p.getPrice());
            updatedProduct.setName(p.getName());
            productService.save(updatedProduct);HttpHeaders headers = new HttpHeaders();
            headers.add("Location","/api/v1/products/"+ updatedProduct.getId().toString());
            return new ResponseEntity<>(headers,HttpStatus.CREATED);

        }
        Product newProduct = productService.save(p);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/products/"+ newProduct.getId().toString());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);

    }
}
