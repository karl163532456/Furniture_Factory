package com.example.furniture_factory.servise.admin;


import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.other.Brigade;
import com.example.furniture_factory.model.other.Material;
import com.example.furniture_factory.model.other.Product;
import com.example.furniture_factory.model.other.Work;
import com.example.furniture_factory.model.repo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AdminServiceImp implements AdminService {
    MaterialRepo materialRepo;
    WorkRepo workRepo;
    ProductRepo productRepo;

    ProductWorkOrderRepo productWorkOrderRepo;
    WorkOrderRepo workOrderRepo;
    BrigadeRepo brigadeRepo;
    EmployeeRepo employeeRepo;
    @Override
    @SneakyThrows
    public Material addMaterial(Material material, MultipartFile file){
        material.setProfilePicture(file);
        return materialRepo.save(material);
    }
    @Override
    public Work addWork(Work work){
        return workRepo.save(work);
    }

    @Override
    @SneakyThrows
    public Product addProduct(Product product, Long[] idMaterials, Long[] idWorks, MultipartFile file){
        product.setProfilePicture(file);
        Set<Material> materials= Arrays.stream(idMaterials)
                .map(f->materialRepo.findById(f).orElseThrow())
                        .collect(Collectors.toCollection(HashSet::new));
        product.setMaterial(materials);

        Set<Work> works=Arrays.stream(idWorks)
                .map(f->workRepo.findById(f).orElseThrow())
                .collect(Collectors.toCollection(HashSet::new));
        product.setWorks(works);

        return productRepo.save(product);
    }

    public ProductWorkOrder createProductWorkOrder(Product product,int count){
        return new ProductWorkOrder().addProduct(product,count);

    }
    @Override
    public WorkOrder addWorkOrder(WorkOrder workOrder, long idBrigade,Long[] products){
        /*for (Map.Entry<Long,Integer> k: map.entrySet()){
            ProductWorkOrder productWorkOrder=new ProductWorkOrder().addProduct(productRepo.findById(k.getKey()).orElseThrow(),k.getValue());

            workOrder.addProductWorkOrder(productWorkOrder);
        }*/
        List<ProductWorkOrder> productWorkOrder=Arrays.stream(products)
                .map(f->productRepo.findById(f).orElseThrow())
                .map(f->createProductWorkOrder(f,1)).toList();
        workOrder=workOrderRepo.save(workOrder);
        productWorkOrder.stream()
                .filter(f->f.getCount()>0)
                .map(f->productWorkOrderRepo.save(f))
                .forEach(workOrder::addProductWorkOrder);
        return workOrderRepo.save(workOrder);
    }
    @Override
    @SneakyThrows
    public Employee addEmployee(Employee user, MultipartFile file, long id){
        user.setProfilePicture(file);
        user.setBrigade(brigadeRepo.findById(id).orElseThrow());

        return employeeRepo.save(user);
    }
    @Override
    public Iterable<Work> findAllWork(){
        return workRepo.findAll();
    }

    @Override
    public Iterable<Material> findAllMaterial() {
        return materialRepo.findAll();
    }

    @Override
    public Iterable<Brigade> findAllBrigade() {
        return brigadeRepo.findAll();
    }

    @Override
    public Iterable<Product> findAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Iterable<WorkOrder> findAllWorkOrderByStatus(boolean status) {
        return workOrderRepo.findAllByStatus(status);
    }


}
