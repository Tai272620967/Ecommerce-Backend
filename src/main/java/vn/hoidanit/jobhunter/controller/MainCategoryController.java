package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.MainCategory;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.MainCategoryService;

@RestController
@RequestMapping("/api/v1")
public class MainCategoryController {
    private static final Logger logger = LoggerFactory.getLogger(MainCategoryController.class);
    private final MainCategoryService mainCategoryService;

    public MainCategoryController( MainCategoryService mainCategoryService) {
        this.mainCategoryService = mainCategoryService;
    }

    @PostMapping("/main-categories")
    public ResponseEntity<MainCategory> createMainCategory(@RequestBody MainCategory mainCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.mainCategoryService.handleCreateMainCategory(mainCategory));
    }

    @GetMapping("/main-categories")
    public ResponseEntity<ResultPaginationDTO> getAllMainCategory(
        @Filter Specification<MainCategory> spec, Pageable pageable
    ) {
        // Debug log to check if API is called
        logger.debug("GET /api/v1/main-categories called - spec: {}, pageable: {}", spec, pageable);
        return ResponseEntity.ok(this.mainCategoryService.handleGetAllMainCategory(spec, pageable));
    }

    @GetMapping("/main-categories/{id}")
    public ResponseEntity<MainCategory> getMainCategoryById(@PathVariable("id") long id) {
        Optional<MainCategory> optionalMainCategory = this.mainCategoryService.handleGetMainCategoryById(id);
        return ResponseEntity.ok().body(optionalMainCategory.get());
    }
}
