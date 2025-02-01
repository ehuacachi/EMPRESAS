import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { Producto } from '../../../model/producto';
import { Observable, switchMap } from 'rxjs';
import { MarcaService } from '../../../services/marca.service';
import { UnidadMedidaService } from '../../../services/unidad-medida.service';
import { CategoriaService } from '../../../services/categoria.service';
import { Marca } from '../../../model/marca';
import { UnidadMedida } from '../../../model/unidad-medida';
import { Categoria } from '../../../model/categoria';
import { AsyncPipe } from '@angular/common';


@Component({
  selector: 'app-producto-edit',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, RouterLink, AsyncPipe],
  templateUrl: './producto-edit.component.html',
  styleUrl: './producto-edit.component.css'
})
export class ProductoEditComponent implements OnInit{

  productoForm: FormGroup;
  id: number;
  isEdit: boolean;
  marcas: Marca[];
  marcas$: Observable<Marca[]>;
  unidadMedidas: UnidadMedida[];
  categorias: Categoria[];


  constructor(
    private route: ActivatedRoute, //para recibir parámetros de una ruta
    private router: Router, //para navegar
    private productoService: ProductoService, //Servicio del Producto
    private marcaService: MarcaService,
    private unidadMedidaService: UnidadMedidaService,
    private categoriaService: CategoriaService

  ) {}


  ngOnInit(): void {
    this.productoForm = new FormGroup({
      idProducto: new FormControl(0),
      descripcion: new FormControl('', [Validators.required, Validators.minLength(3)]),
      modelo: new FormControl('', [Validators.required, Validators.minLength(3)]),
      code: new FormControl('', [Validators.required, Validators.minLength(3)]),
      urlImage: new FormControl('', [Validators.required, Validators.minLength(3)]),
      precioCompra: new FormControl(0.00, [Validators.required, Validators.minLength(3)]),
      precioVenta: new FormControl(0.00, [Validators.required, Validators.minLength(3)]),
      utilidad: new FormControl(0.00, [Validators.required, Validators.minLength(3)]),
      margenUtilidad: new FormControl(0.00, [Validators.required, Validators.minLength(3)]),
      estado: new FormControl('', [Validators.required, Validators.minLength(3)]),
      marca: new FormControl('', [Validators.required, Validators.minLength(3)]),
      unidadMedida: new FormControl('', [Validators.required, Validators.minLength(3)]),
      categoria: new FormControl('', [Validators.required, Validators.minLength(3)])           
      
    });
    
    this.route.params.subscribe(data => {
      this.id = data['id'];
      this.isEdit = data['id'] != null;
      this.initForm();
    });

    // this.marcas$ = this.marcaService.findAll();
    this.marcaService.findAll().subscribe(data => {
      this.marcas = data;
    });

    this.unidadMedidaService.findAll().subscribe(data => {
      this.unidadMedidas = data;
    });

    this.categoriaService.findAll().subscribe(data => {
      this.categorias = data;
    })

  }

  initForm() {
    if(this.isEdit) {
      //Precargo los datos
      this.productoService.findBydId(this.id).subscribe(data => {
        this.productoForm = new FormGroup({
          idProducto: new FormControl(data.idProducto),
          descripcion: new FormControl(data.descripcion, [Validators.required, Validators.minLength(3)]),
          modelo: new FormControl(data.modelo, [Validators.required, Validators.minLength(3)]),
          code: new FormControl(data.code, [Validators.required, Validators.minLength(3)]),
          urlImage: new FormControl(data.urlImage, [Validators.required, Validators.minLength(3)]),
          precioCompra: new FormControl(data.precioCompra, [Validators.required, Validators.minLength(3)]),
          precioVenta: new FormControl(data.precioVenta, [Validators.required, Validators.minLength(3)]),
          utilidad: new FormControl(data.utilidad, [Validators.required, Validators.minLength(3)]),
          margenUtilidad: new FormControl(data.margenUtilidad, [Validators.required, Validators.minLength(3)]),
          estado: new FormControl(data.estado, [Validators.required, Validators.minLength(3)]),
          categoria: new FormControl(data.categoria.idCategoria, [Validators.required, Validators.minLength(3)]),
          marca: new FormControl(data.marca.idMarca, [Validators.required, Validators.minLength(3)]),
          unidadMedida: new FormControl(data.unidadMedida.idUnidadMedida, [Validators.required, Validators.minLength(3)])
        });
      });
    }
  }

  operate(){
    const producto: Producto = new Producto();
    producto.idProducto = this.productoForm.value['idProducto'];
    producto.descripcion = this.productoForm.value['descripcion'];
    producto.modelo = this.productoForm.value['modelo'];
    producto.code = this.productoForm.value['code'];
    producto.urlImage = this.productoForm.value['urlImage'];
    producto.precioCompra = this.productoForm.value['precioCompra'];
    producto.precioVenta = this.productoForm.value['precioVenta'];
    producto.utilidad = this.productoForm.value['utilidad'];
    producto.margenUtilidad = this.productoForm.value['margenUtilidad'];
    producto.estado = this.productoForm.value['estado'];
    // producto.marca = this.productoForm.value['marca'];
    // producto.unidadMedida = this.productoForm.value['unidadMedida'];
    // producto.categoria = this.productoForm.value['categoria'];     
    producto.marca = this.marcas.find(m => m.idMarca === this.productoForm.value['marca']);
    producto.unidadMedida = this.unidadMedidas.find(um => um.idUnidadMedida === this.productoForm.value['unidadMedida']);
    producto.categoria = this.categorias.find(c => c.idCategoria === this.productoForm.value['categoria']);
    
     if(this.isEdit){
      //UPDATE      
      this.productoService.update(this.id, producto)
      .pipe(switchMap( () => this.productoService.findAll() ))
      .subscribe(data => {
          this.productoService.setProductoChange(data);
          this.productoService.setMessageChange('UPDATED!');
        });      
    }else{
      //INSERT      
      this.productoService.save(producto)
      .pipe(switchMap(  ()=> this.productoService.findAll()  ))
      .subscribe(data => {
        this.productoService.setProductoChange(data);
        this.productoService.setMessageChange('CREATED!');
      });
    }

    this.router.navigate(['pages/producto']);
  };

  get f(){
    return this.productoForm.controls;
  }


}


