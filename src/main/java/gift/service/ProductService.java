package gift.service;

import gift.controller.request.ProductRequest;
import gift.controller.response.ProductResponse;
import gift.domain.Product;
import gift.domain.ProductRepository;
import gift.error.ErrorMessage;
import gift.error.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
            .map(Product::toDto)
            .collect(Collectors.toList());
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND))
            .toDto();
    }

    public void addProduct(ProductRequest request) {
        productRepository.save(request.toEntity());
    }

    public void editProduct(Long productId, ProductRequest request) {
        productRepository.edit(productId, request.toEntity());
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));

        productRepository.deleteById(product.getId());
    }

}
