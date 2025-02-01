import { Component, Input, OnChanges, OnInit, Output } from '@angular/core';
import { Cliente } from '../../../model/cliente';
import { EventEmitter } from 'stream';
import { FormArray, FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MaterialModule } from '../../../material/material.module';
import { TipoDocumento } from '../../../model/tipo-documento';
import { ClienteService } from '../../../services/cliente.service';
import { TipoDocumentoService } from '../../../services/tipo-documento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-cliente-form',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule],
  templateUrl: './cliente-form.component.html',
  styleUrl: './cliente-form.component.css'
})
export class ClienteFormComponent implements OnInit {   

  clienteForm: FormGroup;  
  tiposDocumento: TipoDocumento[] = [];  
  isEditing = false;
  
  

  constructor(
    private route: ActivatedRoute, //para recibir parámetros de una ruta  
    private router: Router, //para navegar
    private fb: FormBuilder,  
    private clienteService: ClienteService,  
    private tipoDocumentoService: TipoDocumentoService  
  ) {}  

  ngOnInit(): void {
    this.inicializarFormulario();
    this.cargarTipoDocumentos();

    this.clienteService.getSelectCliente().subscribe(cliente => {
      if (cliente) {
        this.isEditing = true;
        this.patchFormValues(cliente);
      }
    }) 
  }  

  inicializarFormulario(): void {
    this.clienteForm = this.fb.group({
      idCliente: new FormControl(null),
      nombres: new FormControl('', [Validators.required, Validators.minLength(2)]),
      apellidos: new FormControl('', [Validators.required, Validators.minLength(2)]),
      tipoDocumento: new FormControl(null, Validators.required),
      nroDocumento: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      estado: new FormControl(1),
      telefonos: this.fb.array([]),
      domicilios: this.fb.array([])
    });
  }  

  cargarTipoDocumentos(): void {  
    this.tipoDocumentoService.findAll().subscribe(data => {  
      this.tiposDocumento = data;  
    });  
  }  

  // Métodos para Teléfonos  
  get telefonosArray(): FormArray {  
    return this.clienteForm.get('telefonos') as FormArray;  
  }  

  agregarTelefono(): void {  
    const telefonoForm = this.fb.group({  
      numero: ['', Validators.required]  
    });  
    this.telefonosArray.push(telefonoForm);  
  }  

  eliminarTelefono(index: number): void {  
    this.telefonosArray.removeAt(index);  
  }  

  // Métodos para Domicilios  
  get domiciliosArray(): FormArray {  
    return this.clienteForm.get('domicilios') as FormArray;  
  }  

  agregarDomicilio(): void {  
    const domicilioForm = this.fb.group({  
      direccion: ['', Validators.required],  
      numero: ['', Validators.required],  
      manzana: ['', Validators.required],  
      lote: ['', Validators.required],  
      referencia: ['', Validators.required]  
    });  
    this.domiciliosArray.push(domicilioForm);  
  }  

  eliminarDomicilio(index: number): void {  
    this.domiciliosArray.removeAt(index);  
  }

  
  patchFormValues(cliente: Cliente){
    // Limpiar arrays actuales  
    while (this.telefonosArray.length) {
      this.telefonosArray.removeAt(0);
    }
    while (this.domiciliosArray.length) {
      this.domiciliosArray.removeAt(0);
    }

    // Repoblar teléfonos  
    cliente.telefonos?.forEach(telefono => {
      this.telefonosArray.push(
        this.fb.group({
          numero: [telefono.numero, Validators.required]
        })
      );
    });

    // Repoblar domicilios  
    cliente.domicilios?.forEach(domicilio => {
      this.domiciliosArray.push(
        this.fb.group({
          direccion: [domicilio.direccion, Validators.required],
          numero: [domicilio.numero, Validators.required],
          manzana: [domicilio.manzana, Validators.required],
          lote: [domicilio.lote, Validators.required],
          referencia: [domicilio.referencia, Validators.required]
        })
      );
    }); 

    // Establecer valores del formulario con el tipo de documento  
    this.clienteForm.patchValue({
      ...cliente,
      tipoDocumento: cliente.tipoDocumento?.idTipoDocumento // SobreEscritura al objeto tipoDocumento  
    }); 

    
  }

  guardarCliente(): void {  
    if (this.clienteForm.valid) {
      const formValue = this.clienteForm.value;
      
      // Crear objeto de cliente con la estructura correcta  
      // ... se llama spread para devolver el objeto json
      const cliente: Cliente = {
        ...formValue,
        tipoDocumento: {
          idTipoDocumento: formValue.tipoDocumento
        }
      };

      if(this.isEditing){
        //UPDATE
        this.clienteService.update(cliente.idCliente, cliente)
        .pipe(switchMap(() => this.clienteService.findAll()))
        .subscribe(data => {
          this.clienteService.setClienteChange(data);
          this.clienteService.setMessageChange("UPDATE!");          
        });
      }else {
        //CREATE
        this.clienteService.save(cliente)
        .pipe(switchMap(() => this.clienteService.findAll()))
        .subscribe(data => {
          this.clienteService.setClienteChange(data);
          this.clienteService.setMessageChange("CREATE!");          
        });
      }

      this.resetFormulario();
    }
    this.router.navigate(['pages/cliente']);  
  }
  
  

  resetFormulario(): void {  
    this.clienteForm.reset({ estado: 1 });  
    
    // Limpiar arrays  
    while (this.telefonosArray.length) {  
      this.telefonosArray.removeAt(0);  
    }  
    while (this.domiciliosArray.length) {  
      this.domiciliosArray.removeAt(0);  
    }  

    this.router.navigate(['pages/cliente'])  
  }  

  get f() {  
    return this.clienteForm.controls;  
  }  
}  
