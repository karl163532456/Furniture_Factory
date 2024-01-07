package com.example.furniture_factory.controllers;


import com.example.furniture_factory.model.WorkOrder.WorkOrder;
import com.example.furniture_factory.model.employee.Employee;

import com.example.furniture_factory.model.other.Material;
import com.example.furniture_factory.model.other.Product;
import com.example.furniture_factory.model.other.Work;
import com.example.furniture_factory.servise.admin.AdminService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@AllArgsConstructor
@Controller
public class AdminController {
    final AdminService adminService;

    @GetMapping("/admin/home")
    String hello(Model model){

        return "HomeAdmin";
    }
    //Админ

    //Добавить материал
    @GetMapping("/admin/addMaterial")
    String addMaterial(Model model){
        model.addAttribute("material",new Material());
        return "AddMaterial";
    }
    @PostMapping("/admin/addMaterial")
    String addMaterial(Material material,Model model,@RequestParam("image") MultipartFile file) {
        adminService.addMaterial(material,file);
        return "HomeAdmin";
    }
    //Добавить работу
    @GetMapping("/admin/addWork")
    String addWork(Model model){
        model.addAttribute("work",new Work());
        return "addWork";
    }
    @PostMapping("/admin/addWork")
    String addWork(Work work,Model model){
        adminService.addWork(work);

        return "HomeAdmin";
    }

    //Добавить изделие
    @GetMapping("/admin/addProduct")
    String addProduct(Model model,RedirectAttributes redirectAttributes){

        model.addAttribute("works",adminService.findAllWork());
        model.addAttribute("materials",adminService.findAllMaterial());
        model.addAttribute("product",new Product());
        return "AddProduct";
    }

    @PostMapping("/admin/addProduct")
    String addProduct(Product product,Model model, @RequestParam("idMaterials") Long [] idMaterials,@RequestParam("idWorks") Long [] idWorks
            ,@RequestParam("image") MultipartFile file) {

        adminService.addProduct(product,idMaterials,idWorks,file);
        return "HomeAdmin";
    }



    //Посмотреть WorkOrders
    @GetMapping("/admin/WorkOrders")
    String workOrders(Model model){
        model.addAttribute("finished",adminService.findAllWorkOrderByStatus(true));
        model.addAttribute("processing",adminService.findAllWorkOrderByStatus(false));

        return "LookWorkOrders";
    }


    //Создать WorkOrder
    @GetMapping("/admin/addWorkOrder")
    String addWorkOrder(Model model){


        model.addAttribute("products",adminService.findAllProduct());
        model.addAttribute("brigades",adminService.findAllBrigade());
        model.addAttribute("workOrder",new WorkOrder());
        return "AddWorkOrder";
    }
    @PostMapping("/admin/addWorkOrder")
    String addWorkOrder(WorkOrder workOrder,Model model,@RequestParam("brigade") long id
            ,@RequestParam("products")Long[] products){
        adminService.addWorkOrder(workOrder,id,products);

        return "HomeAdmin";
    }

    //Добавить сотрудника
    @GetMapping("/admin/addEmployee")
    String addEmployee(Model model){
        Employee employee= new Employee();
        model.addAttribute("user",employee);
        model.addAttribute("brigades",adminService.findAllBrigade());
        return "AddEmployee";
    }
    @SneakyThrows
    @PostMapping("/admin/addEmployee")
    String addEmployee(Employee user,Model model,@RequestParam("image") MultipartFile file,@RequestParam("brigade") long id){
        adminService.addEmployee(user,file,id);
        return "HomeAdmin";
    }


/*


    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    BrigadeRepo brigadeRepo;
    @Autowired
    MaterialRepo materialRepo;
    @Autowired
    ProductRepo productRepo;

    @Autowired
    WorkEmployeeRepo workEmployeeRepo;
    @Autowired
    WorkRepo workRepo;
    @Autowired
    WorkOrderRepo workOrderRepo;
    @Autowired
    ProductWorkOrderRepo productWorkOrderRepo;

    @GetMapping("/admin")
    String hello(Model model){

        return "HomeAdmin";
    }
    //Админ

    //Добавить материал
    @GetMapping("/admin/addMaterial")
    String addMaterial(Model model){
        model.addAttribute("material",new Material());
        return "AddMaterial";
    }
    @PostMapping("/admin/addMaterial")
    String addMaterial(Material material,Model model,@RequestParam("image") MultipartFile file) throws IOException {
        material.setProfilePicture(file);
        materialRepo.save(material);
        return "HomeAdmin";
    }
    //Добавить работу
    @GetMapping("/admin/addWork")
    String addWork(Model model){
        model.addAttribute("work",new Work());
        return "addWork";
    }
    @PostMapping("/admin/addWork")
    String addWork(Work work,Model model){

        workRepo.save(work);

        return "HomeAdmin";
    }

    //Добавить изделие
    @GetMapping("/admin/addProduct")
    String addProduct(Model model,RedirectAttributes redirectAttributes){
        Product product= new Product();

        model.addAttribute("works",workRepo.findAll());
        model.addAttribute("materials",materialRepo.findAll());
        model.addAttribute("product",product);
        return "AddProduct";
    }

    @PostMapping("/admin/addProduct")
    String addProduct(Product product,Model model, @RequestParam("idMaterials") Long [] idMaterials
            ,@RequestParam("idWorks") Long [] idWorks,@RequestParam("image") MultipartFile file) throws IOException {

        Arrays.stream(idMaterials).forEach(f-> System.out.println(materialRepo.findById(f).orElseThrow().getName()));
        product.setProfilePicture(file);
        HashSet<Material> materials=Arrays.stream(idMaterials).map(f->materialRepo.findById(f).orElseThrow()).collect(Collectors.toCollection(HashSet::new));
        product.setMaterial(materials);
        HashSet<Work> works=Arrays.stream(idWorks).map(f->workRepo.findById(f).orElseThrow()).collect(Collectors.toCollection(HashSet::new));
        product.setWorks(works);
        productRepo.save(product);
        return "HomeAdmin";
    }


    //Создать WorkOrder
    @GetMapping("/admin/addWorkOrder")
    String addWorkOrder(Model model){
        
//        List<Product> products = new ArrayList<>();
//        productRepo.findAll().iterator().forEachRemaining(products::add);


        model.addAttribute("products",productRepo.findAll() */
/*products.stream().map(f->new ProductWorkOrder(f)).toList()*//*
);
        model.addAttribute("brigades",brigadeRepo.findAll());
        model.addAttribute("map",new HashMap<>());
        model.addAttribute("workOrder",new WorkOrder());
        return "AddWorkOrder";
    }
    @PostMapping("/admin/addWorkOrder")
    String addWorkOrder(WorkOrder workOrder,Model model,@RequestParam("brigade") long id,@ModelAttribute Map<Long,Integer> map
            */
/*@RequestParam("idProducts") Long [] idProducts*//*
){
        //сохраняю Main обьект
        WorkOrder finalWorkOrder = workOrderRepo.save(workOrder);

        List<ProductWorkOrder> products=new ArrayList<>();
        for (Map.Entry<Long,Integer> k: map.entrySet()){
            //хитрый конструктор
            ProductWorkOrder prod=new ProductWorkOrder(productRepo.findById(k.getKey()).orElseThrow());
            prod.setWorkOrder(finalWorkOrder);
            prod.setCount(k.getValue());
            prod.getWorkEmployees().forEach(g->g.setProductWorkOrder(prod));
            productWorkOrderRepo.save(prod);
            prod.getWorkEmployees().forEach(g->workEmployeeRepo.save(g));
            products.add(prod);
        }
        //беру лист продуктов

        */
/*List<ProductWorkOrder> products = Arrays.stream(idProducts)
                .map(f->new ProductWorkOrder(productRepo.findById(f).orElseThrow()))
                .toList();*//*



//        products.forEach(f->f.setWorkOrder(finalWorkOrder));


//        products.forEach(f->f.getWorkEmployees().forEach(g->g.setProductWorkOrder(f)));
//        products=products.stream().map(f->productWorkOrderRepo.save(f)).toList();
//
//        products.forEach(f->f.getWorkEmployees().forEach(g->workEmployeeRepo.save(g)));



//        //добавляю заказу продукты
//        workOrder.setProductWorkOrders(products);
//        //сохраняю заказ
//        workOrderRepo.save(finalWorkOrder);

        //каждой работе для продукта указываю заказ и сохраняю
//        workEmployees.forEach(f->f.setWorkOrder(workOrderRepo.findById(workOrder.getId()).orElseThrow()));


        return "HomeAdmin";
    }

    //Добавить сотрудника
    @GetMapping("/admin/addEmployee")
    String addEmployee(Model model){
        Employee employee= new Employee();
        model.addAttribute("user",employee);
        model.addAttribute("brigades",brigadeRepo.findAll());
        return "AddEmployee";
    }
    @SneakyThrows
    @PostMapping("/admin/addEmployee")
    String addEmployee(Employee user,Model model,@RequestParam("image") MultipartFile file,@RequestParam("brigade") long id){
        user.setProfilePicture(file);
        user.setBrigade(brigadeRepo.findById(id).orElseThrow());
        employeeRepo.save(user);
        return "HomeAdmin";
    }

    //! Изменить сотрудника

    //Занести сотрудника "на убой"

    //Посмотреть WorkOrders
    @GetMapping("/admin/WorkOrders")
    String workOrders(Model model){
        model.addAttribute("finished",workOrderRepo.findAllByStatus(true));
        model.addAttribute("processing",workOrderRepo.findAllByStatus(false));

        return "LookWorkOrders";
    }
*/

}
