package vn.hoidanit.jobhunter.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.request.ProductDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<ResultPaginationDTO> getAllProduct(
        @Filter Specification<Product> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.productService.handleGetAllProduct(spec, pageable));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(
        @RequestParam("name") String name,
        @RequestParam("minPrice") BigDecimal minPrice,
        @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
        @RequestParam("description") String description,
        @RequestParam("stockQuantity") Integer stockQuantity,
        @RequestParam("categoryId") Long categoryId,
        @RequestParam("imageFile") MultipartFile imageFile) throws IOException, URISyntaxException {

        // Create ProductDTO object from received parameters
        ProductDTO productRequest = new ProductDTO(name, minPrice, maxPrice, description, stockQuantity, categoryId);

        // Call service to create product and upload image
        Product product = this.productService.handleCreateProduct(productRequest, imageFile);

        // Return the created product with CREATED status (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/products/sub-category/{subCategoryId}")
    public ResponseEntity<ResultPaginationDTO> getProductBySubCategoryId(
        @PathVariable("subCategoryId") long subCategoryId, Pageable pageable
    ) {
        return ResponseEntity.ok(this.productService.handleGetProductBySubCategoryId(subCategoryId, pageable));
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ResultPaginationDTO> getProductByCategoryId(
        @PathVariable("categoryId") long categoryId, Pageable pageable
    ) {
        return ResponseEntity.ok(this.productService.handleGetProductByCategoryId(categoryId, pageable));
    }

    @GetMapping("products/detail/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") long productId) {
        Optional<Product> product = this.productService.handleGetProductById(productId);
        return ResponseEntity.ok().body(product.get());
    }
}
