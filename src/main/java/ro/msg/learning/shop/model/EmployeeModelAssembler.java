package ro.msg.learning.shop.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import ro.msg.learning.shop.controller.EmployeeController;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeePayroll, EntityModel<EmployeePayroll>> {

  @Override
  public EntityModel<EmployeePayroll> toModel(EmployeePayroll employee) {

    return EntityModel.of(employee, //
    		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
    		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withRel("employees"));
  }
}
