package com.example.furniture_factory.servise.admin;

import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.other.Brigade;
import com.example.furniture_factory.model.other.Material;
import com.example.furniture_factory.model.other.Product;
import com.example.furniture_factory.model.other.Work;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

public interface AdminService {

    public Material addMaterial(Material material, MultipartFile file);
    public ProductWorkOrder createProductWorkOrder(Product product,int count);
    Work addWork(Work work);
    Product addProduct(Product product, Long [] idMaterials, Long [] idWorks, MultipartFile file);
    WorkOrder addWorkOrder(WorkOrder workOrder, long idBrigade,Long[] products);
    Iterable<Work> findAllWork();
    Iterable<Material> findAllMaterial();
    Iterable<Brigade> findAllBrigade();
    Iterable<Product> findAllProduct();
    Iterable<WorkOrder> findAllWorkOrderByStatus(boolean status);
    Employee addEmployee(Employee user, MultipartFile file, long id);
}
