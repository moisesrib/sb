package org.sb.products.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.sb.core.structures.BaseRepository;
import org.sb.products.model.Product;
import org.sb.products.model.QProduct;

@ApplicationScoped
public class ProductRepository extends BaseRepository<Product> {

    public ProductRepository() {
        super(Product.class);
    }

    public Product findByName(String name) {
        return createQuery()
                .where(QProduct.product.name.eq(name))
                .fetchFirst();
    }
}
