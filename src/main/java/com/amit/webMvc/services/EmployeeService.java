package com.amit.webMvc.services;

import com.amit.webMvc.dto.EmployeeDto;
import com.amit.webMvc.entity.EmployeesEntity;
import com.amit.webMvc.repositories.employeesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private final employeesRepository empRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public EmployeeService(employeesRepository empRepo,ModelMapper modelMapper) {
        this.empRepo = empRepo;
        this.modelMapper=modelMapper;
    }

    /*Employee found by id*/
    public Optional<EmployeeDto> employeeById(Long id){
        Optional<EmployeesEntity> employeesEntity=empRepo.findById(id);
        return employeesEntity.map(employeesEntity1 -> modelMapper.map(employeesEntity1,EmployeeDto.class));
    }

    /*Employee found all*/
    public List<EmployeeDto> allEmployee(){
        List<EmployeesEntity> employeesEntities=empRepo.findAll();
        return employeesEntities.stream()
                .map(employeesEntity -> modelMapper.map(employeesEntity,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    /*Employee save*/
    public EmployeeDto saveEmployee(EmployeeDto employeeDto){
        EmployeesEntity employeesEntity=modelMapper.map(employeeDto,EmployeesEntity.class);
        EmployeesEntity saveEmployee=empRepo.save(employeesEntity);
        return modelMapper.map(saveEmployee,EmployeeDto.class);
    }

    /*update employees by id*/
    public EmployeeDto saveEmployeeById(Long id,EmployeeDto employeeDto){
        EmployeesEntity employeesEntity=modelMapper.map(employeeDto,EmployeesEntity.class);
        employeesEntity.setId(id);
        EmployeesEntity save=empRepo.save(employeesEntity);
        return modelMapper.map(save,EmployeeDto.class);
    }

    /*delete employee by id*/
    public void deleteEmployeeById(Long id){
        empRepo.deleteById(id);
    }

    public EmployeeDto updatePartialEmployeeById(Long id, Map<String, Object> updates) {

        EmployeesEntity employeesEntity=empRepo.findById(id).get();
        updates.forEach((field,value)->{
            Field fieldToUpdates= org.springframework.data.util.ReflectionUtils.findRequiredField(EmployeesEntity.class,field);
            fieldToUpdates.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdates,employeesEntity,value);

        });
        return modelMapper.map(empRepo.save(employeesEntity),EmployeeDto.class);
    }

}
