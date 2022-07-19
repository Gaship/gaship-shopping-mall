package shop.gaship.gashipshoppingmall.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.category.exception.CategoryNotFoundException;
import shop.gaship.gashipshoppingmall.category.repository.CategoryRepository;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.product.service.ProductService;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.productTag.repository.ProductTagRepository;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;
import shop.gaship.gashipshoppingmall.statuscode.repository.StatusCodeRepository;
import shop.gaship.gashipshoppingmall.statuscode.status.SalesStatus;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.exception.TagNotFoundException;
import shop.gaship.gashipshoppingmall.tag.repository.TagRepository;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 상품 서비스 구현체 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StatusCodeRepository statusCodeRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    private static final String PRODUCT_DIR = File.separator + "products";

    @Transactional
    @Override
    public void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest)
            throws IOException {
        Category category = categoryRepository.findById(createRequest.getCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(createRequest.getDeliveryTypeNo())
                .orElseThrow(StatusCodeNotFoundException::new);
        StatusCode salesStatus = statusCodeRepository
                .findByStatusCodeName(SalesStatus.SALE.getValue())
                .orElseThrow(StatusCodeNotFoundException::new);

        Product product = Product.create(category, deliveryType, createRequest);
        product.updateSalesStatus(salesStatus);

        List<String> imageLinks = FileUploadUtil.uploadFile(PRODUCT_DIR, files);
        product.updateImageLinks(imageLinks);

        productRepository.save(product);
        addProductTags(product, createRequest.getTagNos());
    }

    @Transactional
    @Override
    public void modifyProduct(List<MultipartFile> files, ProductModifyRequestDto modifyRequest)
            throws IOException {
        Product product = productRepository.findById(modifyRequest.getNo())
                .orElseThrow(ProductNotFoundException::new);

        Category category = categoryRepository.findById(modifyRequest.getCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);
        StatusCode deliveryType = statusCodeRepository.findById(modifyRequest.getDeliveryTypeNo())
                .orElseThrow(StatusCodeNotFoundException::new);

        product.updateProduct(category, deliveryType, modifyRequest);
        FileUploadUtil.deleteFiles(PRODUCT_DIR, product.getImageLinkList());

        List<String> imageLinks = FileUploadUtil.uploadFile(PRODUCT_DIR, files);
        product.updateImageLinks(imageLinks);

        productRepository.save(product);

        productTagRepository.deleteAllByPkProductNo(product.getNo());
        addProductTags(product, modifyRequest.getTagNos());
    }

    @Transactional
    public void addProductTags(Product product, List<Integer> tagNos) {
        tagNos.forEach(tagNo -> {
            Tag tag = tagRepository.findById(tagNo).orElseThrow(TagNotFoundException::new);

            ProductTag.Pk pk = new ProductTag.Pk(product.getNo(), tagNo);
            product.addProductTag(new ProductTag(pk, product, tag));
        });
    }
}
