package ro.msg.learning.shop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ro.msg.learning.shop.interfaces.EmployeeRepository;
import ro.msg.learning.shop.model.EmployeeModelAssembler;
import ro.msg.learning.shop.model.EmployeeNotFoundException;
import ro.msg.learning.shop.model.EmployeePayroll;

@RestController
@ComponentScan()
public class EmployeeController {

  private final EmployeeRepository repository;
  private EmployeeModelAssembler employeeModelAssembler;

  EmployeeController(EmployeeRepository repository, EmployeeModelAssembler employeeModelAssembler) {
    this.repository = repository;
    this.employeeModelAssembler = employeeModelAssembler;
  }
  
  @Bean
  public RestTemplate restTemplate() {
	  return new RestTemplate();
  }
  
  @GetMapping("/employees")
  public List<EmployeePayroll> all() {
    return repository.findAll();
  }
  
  
  @PostMapping("/employees")
  EmployeePayroll newEmployee(@RequestBody EmployeePayroll newEmployee) {
    return repository.save(newEmployee);
  }

  // Single item
  
  @GetMapping("/employeesOne/{id}")
  EntityModel<EmployeePayroll> oneEmployee(@PathVariable Long id) {

    EmployeePayroll employeesOne = repository.findById(id) //
        .orElseThrow(() -> new EmployeeNotFoundException(id));

    return employeeModelAssembler.toModel(employeesOne);
  }
  
  @GetMapping("/employees/{id}")
  public EntityModel<EmployeePayroll> one(@PathVariable Long id) {

	  EmployeePayroll employeePayroll = repository.findById(id) //
	      .orElseThrow(() -> new EmployeeNotFoundException(id));

    return EntityModel.of(employeePayroll, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).one(id)).withSelfRel(),
    		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withRel("employees"));
  }
  
  @GetMapping("/employeesPayroll")
  CollectionModel<EntityModel<EmployeePayroll>> allEmployee() {

    List<EntityModel<EmployeePayroll>> employeesPayroll = repository.findAll().stream()
        .map(employee -> EntityModel.of(employee,
        		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
        		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).allEmployee()).withRel("employees")))
        .collect(Collectors.toList());

    return CollectionModel.of(employeesPayroll, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withSelfRel());
  }

  @PutMapping("/employees/{id}")
  EmployeePayroll replaceEmployee(@RequestBody EmployeePayroll newEmployee, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newEmployee.setId(id);
        return repository.save(newEmployee);
      });
  }

  @DeleteMapping("/employeedelete/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
