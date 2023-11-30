package com.example.caseExamJavaNotDatabase.controller;

import com.example.caseExamJavaNotDatabase.model.CouponDiscount;
import com.example.caseExamJavaNotDatabase.model.DTO.ProductDTO;
import com.example.caseExamJavaNotDatabase.service.ShoppingCart;
import com.example.caseExamJavaNotDatabase.service.ShoppingCartImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carrito")
@Validated
public class ShoppingCartController {

    @Autowired
    private final ShoppingCartImpl shoppingCartImpl;

    private final List<ProductDTO> productList;

    CouponDiscount couponDiscount = new CouponDiscount();


    public ShoppingCartController (ShoppingCartImpl shoppingCartImpl){
        this.shoppingCartImpl =shoppingCartImpl;
        this.productList= new ArrayList<>();


    }

    @GetMapping("/ver-productos")
    public ResponseEntity <List <ProductDTO>> viewCart() {

        List<ProductDTO> productsList = shoppingCartImpl.viewCart();
        return ResponseEntity.ok(productsList);

    }


    @PostMapping("/crear-producto")
    public ResponseEntity<String> addToCart(@Valid @RequestBody  ProductDTO product) {

        shoppingCartImpl.addProduct(product);

        return new ResponseEntity<>("Se ha añadido el producto al carrito", HttpStatus.CREATED);
    }

    @PostMapping("/eliminar-producto/{productId}")
    public ResponseEntity removeFromCart(@Valid @PathVariable Long productId) {

        if(productId==null || !shoppingCartImpl.idIsValid(productId)){

            return new ResponseEntity<>("Id de producto no válido", HttpStatus.BAD_REQUEST);
        }

        shoppingCartImpl.removeProduct(productId);


        return new ResponseEntity<>("Se ha eliminado el producto del carrito", HttpStatus.OK);

    }








    @GetMapping("/total")
    public ResponseEntity calculateTotalPrice() {

        double totalPrice = shoppingCartImpl.calculateTotalPrice();

        return ResponseEntity.ok(totalPrice);
    }


    @PostMapping("/aplicar-descuento")
    public ResponseEntity<String> applyDiscount(@RequestParam String discountType){
        List<ProductDTO> productList= shoppingCartImpl.viewCart();
        if("DISCOUNT10".equals(discountType)){

            couponDiscount.applyDiscount10(productList);
        }else if("DISCOUNT25".equals(discountType)){

            couponDiscount.applyDiscount25(productList);
        }else if("DISCOUNT30".equals(productList)){
            couponDiscount.applyDiscount30(productList);
        }else{
            return new ResponseEntity<>("Tipo de descuento no válido", HttpStatus.OK);
        }

        return new ResponseEntity<>("Descuento aplicado al carrito", HttpStatus.OK);
    }






}
