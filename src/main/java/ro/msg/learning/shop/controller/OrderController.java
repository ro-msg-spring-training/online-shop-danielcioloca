package ro.msg.learning.shop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ro.msg.learning.shop.interfaces.OrderRepository;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderModelAssembler;
import ro.msg.learning.shop.model.OrderNotFoundException;
import ro.msg.learning.shop.model.Status;


@RestController
public class OrderController {

	  private final OrderRepository orderRepository;
	  private final OrderModelAssembler assembler;

	  OrderController(OrderRepository orderRepository, OrderModelAssembler assembler) {

	    this.orderRepository = orderRepository;
	    this.assembler = assembler;
	  }

	  // using HATEOAS with CollectionModel class		
	  @GetMapping("/orders")
	  public CollectionModel<EntityModel<Order>> all() {

	    List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
	        .map(assembler::toModel) //
	        .collect(Collectors.toList());

	    return CollectionModel.of(orders, //
	    		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).all()).withSelfRel());
	  }
	  
	  
	  @GetMapping("/orders/{id}")
	  public EntityModel<Order> one(@PathVariable Long id) {

	    Order order = orderRepository.findById(id) //
	        .orElseThrow(() -> new OrderNotFoundException(id));

	    return assembler.toModel(order);
	  }

	  @PostMapping("/orders")
	  public ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

	    order.setStatus(Status.IN_PROGRESS);
	    Order newOrder = orderRepository.save(order);

	    return ResponseEntity //
	        .created(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).one(newOrder.getId())).toUri()) //
	        .body(assembler.toModel(newOrder));
	  }
	  
	  @DeleteMapping("/orders/{id}/cancel")
	  public ResponseEntity<?> cancel(@PathVariable Long id) {

	    Order order = orderRepository.findById(id) //
	        .orElseThrow(() -> new OrderNotFoundException(id));

	    if (order.getStatus() == Status.IN_PROGRESS) {
	      order.setStatus(Status.CANCELLED);
	      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
	    }

	    return ResponseEntity //
	        .status(HttpStatus.METHOD_NOT_ALLOWED) //
	        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
	        .body(Problem.create() //
	            .withTitle("Method not allowed") //
	            .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
	  }
	  
	  @PutMapping("/orders/{id}/complete")
	  public ResponseEntity<?> complete(@PathVariable Long id) {

	    Order order = orderRepository.findById(id) //
	        .orElseThrow(() -> new OrderNotFoundException(id));

	    if (order.getStatus() == Status.IN_PROGRESS) {
	      order.setStatus(Status.COMPLETED);
	      return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
	    }

	    return ResponseEntity //
	        .status(HttpStatus.METHOD_NOT_ALLOWED) //
	        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
	        .body(Problem.create() //
	            .withTitle("Method not allowed") //
	            .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
	  }
	}