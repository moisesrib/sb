package org.sb.core.structures;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.sb.core.dtos.PaginatedResponse;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Transactional
public abstract class BaseRepository<T extends BaseEntity> {

    private final Class<T> type;

    @Inject
    protected EntityManager entityManager;

    public BaseRepository(Class<T> type) {
        this.type = type;
    }

    protected JPAQuery<T> createQuery() {
        String variable = type.getSimpleName().substring(0, 1).toLowerCase(Locale.getDefault()) + type.getSimpleName().substring(1);
        EntityPathBase<T> entityPath = new EntityPathBase<>(type, variable);
        return new JPAQuery<T>(entityManager).from(entityPath);
    }

    public T findOneById(Object id) {
        String variable = type.getSimpleName().substring(0, 1).toLowerCase(Locale.getDefault()) + type.getSimpleName().substring(1);
        PathBuilder<T> entityPath = new PathBuilder<>(type, variable);

        Predicate predicate;
        if (id instanceof UUID) {
            predicate = entityPath.get("id", UUID.class).eq((UUID) id);
        } else if (id instanceof Integer) {
            predicate = entityPath.get("id", Integer.class).eq((Integer) id);
        } else {
            throw new IllegalArgumentException("Unsupported ID type: " + id.getClass());
        }

        return createQuery().where(predicate).fetchOne();
    }

    public PaginatedResponse<T> paginate(JPAQuery<T> query, Predicate predicate, Integer page, Integer size) {
        if (predicate != null) {
            query.where(predicate);
        }

        int currentPage = page != null ? page : 1;
        int pageSize = size != null ? size : 10;
        int offset = (currentPage - 1) * pageSize;

        long totalItems = query.fetch().size();

        List<T> itemsPlus = query
                .offset(offset)
                .limit(pageSize + 1L)
                .fetch();

        boolean hasMore = itemsPlus.size() > pageSize;
        List<T> items = hasMore ? itemsPlus.subList(0, pageSize) : itemsPlus;

        int totalPages = pageSize == 0 ? 0 : (int) (totalItems / pageSize) + ((totalItems % pageSize > 0) ? 1 : 0);

        return new PaginatedResponse<>(
                items,
                totalItems,
                totalPages,
                currentPage
        );
    }

    public PaginatedResponse<T> paginate(JPAQuery<T> query, Predicate predicate) {
        return paginate(query, predicate, 1, 10);
    }

    public PaginatedResponse<T> paginate(JPAQuery<T> query) {
        return paginate(query, null, 1, 10);
    }

    public List<T> findAll(Predicate predicate) {
        JPAQuery<T> query = createQuery();
        if (predicate != null) {
            query.where(predicate);
        }
        return query.fetch();
    }

    public List<T> findAll() {
        return findAll(null);
    }

    public T save(T entity) {
        if (entity.getId() != null) {
            return entityManager.merge(entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    public List<T> saveAll(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }

        for (T entity : entities) {
            if (entity.getId() != null) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
        }
        return entities;
    }

    public void delete(T entity) {
        if (entity.getId() != null) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }
}
