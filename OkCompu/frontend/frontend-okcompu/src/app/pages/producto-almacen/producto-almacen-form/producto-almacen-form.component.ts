import { Component, OnDestroy, OnInit } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Producto } from '../../../model/producto';
import { Almacen } from '../../../model/almacen';
import { ProductoAlmacenService } from '../../../services/producto-almacen.service';
import { ProductoService } from '../../../services/producto.service';
import { AlmacenService } from '../../../services/almacen.service';
import { switchMap } from 'rxjs';
import { Router } from '@angular/router';
import { ProductoAlmacen } from '../../../model/producto-almacen';

@Component({
  selector: 'app-producto-almacen-form',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './producto-almacen-form.component.html',
  styleUrl: './producto-almacen-form.component.css'
})
export class ProductoAlmacenFormComponent implements OnInit {

  form: FormGroup;  
  productos: Producto[] = [];  
  almacenes: Almacen[] = [];  
  isEditing = false;  

  selectedProducto: number | null = null;  
  selectedAlmacen: number | null = null; 

  constructor(  
    private formBuilder: FormBuilder,  
    private productoAlmacenService: ProductoAlmacenService,
    private productoService: ProductoService,  
    private almacenService: AlmacenService,
    private router: Router  
  ) {}  

  ngOnInit(): void {
    this.createForm();
    this.loadProductos();
    this.loadAlmacenes();

    
    this.productoAlmacenService.getSelectProductoAlmacen().subscribe(productoAlmacen => {
      if (productoAlmacen) {
        this.isEditing = true;
        this.selectedProducto = productoAlmacen.producto.idProducto;  
        this.selectedAlmacen = productoAlmacen.almacen.idAlmacen; 

        this.form.patchValue({
          producto: productoAlmacen.producto.idProducto,  
          almacen: productoAlmacen.almacen.idAlmacen,  
          cantidad: productoAlmacen.cantidad
        });
      }  
    });
  }  
  
  createForm() {
    this.form = this.formBuilder.group({
      producto: ['', Validators.required],
      almacen: ['', Validators.required],
      cantidad: ['', [Validators.required, Validators.min(0)]]
    });
  }



  loadProductos() {  
    this.productoService.findAll().subscribe(data => {  
        this.productos = data;  
      });  
  }  

  loadAlmacenes() {  
    this.almacenService.findAll().subscribe(data => {  
        this.almacenes = data;  
      });  
  }  

  onSubmit() {  
    if (this.form.valid) {  
      const formValue = this.form.value;  

      const productoId = this.isEditing ? this.selectedProducto : formValue.producto;  
      const almacenId = this.isEditing ? this.selectedAlmacen : formValue.almacen;  
      
      //Crear el objeto
      const productoAlmacen: ProductoAlmacen = {  
        producto: this.productos.find(p => p.idProducto === productoId)!,  
        almacen: this.almacenes.find(a => a.idAlmacen === almacenId)!,  
        cantidad: formValue.cantidad  
      };  
      
      const operation = this.isEditing ?  
        this.productoAlmacenService.updateStock(productoAlmacen.producto.idProducto, productoAlmacen.almacen.idAlmacen, productoAlmacen.cantidad):  
        this.productoAlmacenService.save(productoAlmacen);  

      operation
      .pipe(switchMap(() => this.productoAlmacenService.findAll()))
      .subscribe(data => {  
        this.productoAlmacenService.setProductoAlmacenChange(data);  
        this.resetForm();  
      });  
    }  
  }  

  resetForm() {  
    this.form.reset();  
    this.isEditing = false;  
    this.productoAlmacenService.selectProductoAlmacen(null); 
    this.router.navigate(['pages/productoAlmacen']);
  }  

}
