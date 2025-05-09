package org.sb.products.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.sb.products.dtos.CreateProductDTO;
import org.sb.products.model.Product;
import org.sb.products.service.ProductService;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProductController {

    @Inject
    ProductService productService;

    @POST
    @Transactional
    public Response create(@Valid CreateProductDTO data) {
        Product created = productService.create(data);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Transactional
    public Response findAll() {
        List<Product> products = productService.findAll();
        return Response.ok().entity(products).build();
    }
}
