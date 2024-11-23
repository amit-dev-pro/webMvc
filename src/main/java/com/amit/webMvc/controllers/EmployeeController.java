package com.amit.webMvc.controllers;

import com.amit.webMvc.dto.EmployeeDto;
import com.amit.webMvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private final EmployeeService empServ;

    public EmployeeController(EmployeeService empServ) {
        this.empServ = empServ;
    }

    /*find by id api*/
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id){
        Optional<EmployeeDto> employeeDto=empServ.employeeById(id);
        if(employeeDto.isPresent()){
            return new ResponseEntity<>(employeeDto.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*find all employee api*/
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(@RequestParam(required = false) Long id,
                                                            @RequestParam(required = false) String name){
        List<EmployeeDto> employeeDtos=empServ.allEmployee();
        if(employeeDtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employeeDtos,HttpStatus.OK);
    }

    /*save employee api*/
    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto employeeDto1=empServ.saveEmployee(employeeDto);
        return new ResponseEntity<>(employeeDto1,HttpStatus.CREATED);
    }

    /*update to employee by id api*/
    @PutMapping(path="/{id}")
    public EmployeeDto updateEmployeeById(@RequestBody EmployeeDto employeeDto,@PathVariable Long id)
    {
        return empServ.saveEmployeeById(id,employeeDto);
    }

    /*delete employees by id api*/
    @DeleteMapping(path="/{id}")
    public  void deleteEmployeeById(@PathVariable Long id){
        empServ.deleteEmployeeById(id);

    }

    /*partial update to employees by id api*/
    @PatchMapping(path="/{id}")
    public EmployeeDto updatePartialEmployeeById(@RequestBody Map<String , Object> updates,
                                                 @PathVariable Long id)
    {
        return empServ.updatePartialEmployeeById(id,updates);

    }

}
