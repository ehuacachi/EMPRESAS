import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription, switchMap } from 'rxjs';
import { AlmacenService } from '../../../services/almacen.service';
import { Almacen } from '../../../model/almacen';
import { MaterialModule } from '../../../material/material.module';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-almacen-form',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './almacen-form.component.html',
  styleUrl: './almacen-form.component.css'
})

export class AlmacenFormComponent implements OnInit {  
  form: FormGroup;  
  isEditing = false;  
  //private subscription: Subscription;  

  constructor(  
    private fb: FormBuilder,  //para trabajar con grupos de FormGroup
    private route: ActivatedRoute, //Para recibir el parámetro de la ruta
    private almacenService: AlmacenService,
    private router: Router //para el manejo de rutas en componentes  
  ) {}  

  ngOnInit(): void {
      
    this.createForm();  
    
    this.almacenService.getSelectAlmacen().subscribe(almacen => {  
      if (almacen) {  
        this.isEditing = true;  
        this.patchFormValues(almacen);  
      }  
    });  
  }  

  // ngOnDestroy(): void {  
  //   this.subscription.unsubscribe();  
  // }  

  createForm() {  
    this.form = this.fb.group({  
      idAlmacen: [null],  
      descripcion: ['', Validators.required],  
      telefonos: this.fb.array([]),  
      domicilios: this.fb.array([])  
    });  
  }  

  get telefonos() {  
    return this.form.get('telefonos') as FormArray;  
  }  

  get domicilios() {  
    return this.form.get('domicilios') as FormArray;  
  }  

  addTelefono() {  
    this.telefonos.push(this.fb.group({  
      idTelefono: [null],  
      numero: ['', Validators.required]  
    }));  
  }  

  addDomicilio() {  
    this.domicilios.push(this.fb.group({  
      idDomicilio: [null],  
      direccion: ['', Validators.required],  
      numero: ['', Validators.required],  
      manzana: [''],  
      lote: [''],  
      referencia: ['']  
    }));  
  }  

  removeTelefono(index: number) {  
    this.telefonos.removeAt(index);  
  }  

  removeDomicilio(index: number) {  
    this.domicilios.removeAt(index);  
  }  

  // Recuperamos la informacion del elemento seleccionado
  patchFormValues(almacen: Almacen) {  
    this.form.patchValue({  
      idAlmacen: almacen.idAlmacen,  
      descripcion: almacen.descripcion  
    });  

    // Clear existing arrays  
    while (this.telefonos.length) {  
      this.telefonos.removeAt(0);  
    }  
    while (this.domicilios.length) {  
      this.domicilios.removeAt(0);  
    }  

    // Add telefonos  
  almacen.telefonos.forEach(telefono => {  
      this.telefonos.push(this.fb.group({  
        idTelefono: [telefono.idTelefono],  
        numero: [telefono.numero, Validators.required]  
      }));  
    });  

    // Add domicilios  
  almacen.domicilios.forEach(domicilio => {  
      this.domicilios.push(this.fb.group({  
        idDomicilio: [domicilio.idDomicilio],  
        direccion: [domicilio.direccion, Validators.required],  
        numero: [domicilio.numero, Validators.required],  
        manzana: [domicilio.manzana],  
        lote: [domicilio.lote],  
        referencia: [domicilio.referencia]  
      }));  
    });  
  }  

  onSubmit() {  
    if (this.form.valid) {  
      const almacen: Almacen = this.form.value;  
      
      const operation = this.isEditing ?  
        this.almacenService.update(almacen.idAlmacen, almacen) :  
        this.almacenService.save(almacen);  

      operation
      .pipe(switchMap( () => this.almacenService.findAll()))
      .subscribe(data => {  
        this.almacenService.setAlmacenChange(data);
        this.almacenService.setMessageChange('Registro Satisfactorio')
        this.resetForm();  
      });
      
      //this.router.navigate(['pages/almacen']);
    }  
  }  

  resetForm() {  
    this.form.reset();  
    while (this.telefonos.length) {  
      this.telefonos.removeAt(0);  
    }  
    while (this.domicilios.length) {  
      this.domicilios.removeAt(0);  
    }  
    this.isEditing = false;  
    this.almacenService.selectAlmacen(null);
    this.router.navigate(['pages/almacen']);  
  }  
} 
